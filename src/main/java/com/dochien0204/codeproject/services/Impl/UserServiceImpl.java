package com.dochien0204.codeproject.services.Impl;

import com.dochien0204.codeproject.dtos.user.CreateUserDTO;
import com.dochien0204.codeproject.dtos.user.UpdateNewPasswordDTO;
import com.dochien0204.codeproject.dtos.user.UpdateUserDTO;
import com.dochien0204.codeproject.entities.PasswordResetToken;
import com.dochien0204.codeproject.entities.User;
import com.dochien0204.codeproject.exceptions.BadRequestException;
import com.dochien0204.codeproject.exceptions.NotFoundException;
import com.dochien0204.codeproject.repositories.PasswordResetTokenRepository;
import com.dochien0204.codeproject.repositories.RoleRepository;
import com.dochien0204.codeproject.repositories.UserRepository;
import com.dochien0204.codeproject.services.MailService;
import com.dochien0204.codeproject.services.PasswordResetTokenService;
import com.dochien0204.codeproject.services.UserService;
import com.dochien0204.codeproject.utils.FileUtils;
import com.dochien0204.codeproject.utils.RandomStringUtils;
import com.dochien0204.codeproject.utils.SecurityUtils;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@Slf4j
public class UserServiceImpl implements UserService {

  public static final Long LOCK_TIME_DURATION = Long.valueOf(10 * 60 * 1000);
  private final RoleRepository roleRepository;
  private final UserRepository userRepository;
  private final ModelMapper modelMapper;
  private final PasswordEncoder passwordEncoder;
  private final MailService mailService;
  private final PasswordResetTokenService passwordResetTokenService;

  private final PasswordResetTokenRepository passwordResetTokenRepository;

  public UserServiceImpl(RoleRepository roleRepository, UserRepository userRepository, ModelMapper modelMapper, PasswordEncoder passwordEncoder, MailService mailService, PasswordResetTokenService passwordResetTokenService, PasswordResetTokenRepository passwordResetTokenRepository) {
    this.roleRepository = roleRepository;
    this.userRepository = userRepository;
    this.modelMapper = modelMapper;
    this.passwordEncoder = passwordEncoder;
    this.mailService = mailService;
    this.passwordResetTokenService = passwordResetTokenService;
    this.passwordResetTokenRepository = passwordResetTokenRepository;
  }

  @Override
  public List<User> findAllUser() {
    return userRepository.findAll();
  }

  @Override
  public User findUserById(Integer userId) {
    Optional<User> user = userRepository.findById(userId);
    if (user.isEmpty()) {
      throw new NotFoundException("Not found user have id " + userId);
    }
    return user.get();

  }

  @Override
  public User findUserByUserName(String userName) {
    User user = userRepository.findByUserName(userName);
    return user;
  }

  @Override
  public User findUserByEmail(String email) {
    Optional<User> user = Optional.ofNullable(userRepository.findByEmail(email));
    if (user.isEmpty()) {
      throw new NotFoundException("Not found user have email: " + email);
    }
    return user.get();
  }

  @Override
  public List<User> findUserBySubName(String subName) {
    List<User> users = userRepository.findByFullName(subName);
    if (users.isEmpty()) {
      throw new NotFoundException("Not found sub search string: " + subName);
    }
    return users;
  }

  @Override
  public boolean save(CreateUserDTO userDTO) throws IOException {
    if (checkExistsUser(userDTO.getUserName())) {
      return false;
    }
    User user = modelMapper.map(userDTO, User.class);
    user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
    user.setRoles(roleRepository.findByRoleName("ROLE_USER"));
    if(userDTO.getFile() != null) {
      user.setAvatar(FileUtils.uploadFile(userDTO.getFile()));
    } else {
      user.setAvatar(null);
    }
    user.setCreatedBy(user.getUserName());
    user.setLockTime(Long.valueOf(0));
    user.setAccountNonLocked(true);
    user.setFailedAttempts(0);
    userRepository.save(user);
    return true;
  }

  @Override
  public boolean update(UpdateUserDTO userDTO, Integer userId) throws IOException {
    Optional<User> user = userRepository.findById(userId);
    if (user.isEmpty()) {
      throw new NotFoundException("Not found user " + userId);
    }
    if(userDTO.getPassword() != null) {
      user.get().setPassword(passwordEncoder.encode(userDTO.getPassword()));
    }
    user.get().setAddress(userDTO.getAddress());
    user.get().setEmail(userDTO.getEmail());
    user.get().setPhone(userDTO.getPhone());
    user.get().setAge(userDTO.getAge());
    user.get().setFullName(userDTO.getFullName());
    if(userDTO.getFile() != null) {
      user.get().setAvatar(FileUtils.uploadFile(userDTO.getFile()));
    }
    user.get().setLastModifiedDate(System.currentTimeMillis());
    user.get().setLastModifiedBy(SecurityUtils.getPrincipal());
    userRepository.save(user.get());
    return true;
  }

  @Override
  public boolean delete(Integer userId) {
    Optional<User> user = userRepository.findById(userId);
    if (user.isEmpty()) {
      throw new NotFoundException("Not found user " + userId);
    }
    userRepository.delete(user.get());
    return true;
  }

  @Override
  public void incrementFailedAttempts(User user) {
    int newFailedAttempts = user.getFailedAttempts() + 1;
    userRepository.updateFailedAttempts(newFailedAttempts, user.getUserName());
  }

  @Override
  public void lockedUser(User user) {
    userRepository.lockedUser(user.getUserName(), System.currentTimeMillis() + LOCK_TIME_DURATION);
  }

  @Override
  public void unlockUser(User user) {
    userRepository.unlockUser(user.getUserName());
  }

  @Override
  public String forgotPassword(String email) {
    User user = userRepository.findByEmail(email);
    if (user == null) {
      throw new NotFoundException("Not found user has email " + email);
    }

    //delete all previous token related user
    passwordResetTokenRepository.deleteAllByUser(user);

    //generate token
    String token = RandomStringUtils.getAlphaNumericString(6);
    passwordResetTokenService.createPasswordResetTokenForAccount(user, token);


    //send mail with token
    mailService.sendSimpleMailToUser(email, "RESET PASSWORD", token);
    return user.getEmail();
  }

  @Override
  public Integer confirmResetToken(String email, String token) {
    User user = userRepository.findByEmail(email);


    //check exists token
    if (user == null ) {
      throw new NotFoundException("Not found User have email", email);
    }

    //check expired token
    PasswordResetToken passwordResetToken = user.getPasswordResetToken();
    if(passwordResetToken.getToken().compareTo(token) != 0 || (System.currentTimeMillis() - passwordResetToken.getExpiryTime() > 0)) {
      System.out.println(passwordResetToken.getToken() + " " + token + " " + (System.currentTimeMillis() - passwordResetToken.getExpiryTime() > 0));
        throw new BadRequestException("Token was expired or invalid");
    }
    return user.getUserId();
  }
  public boolean updateNewPassword(Integer userId, UpdateNewPasswordDTO updateNewPasswordDTO) {
    Optional<User> user = userRepository.findById(userId);
    if(user.isEmpty()) {
      throw new NotFoundException("Not found user");
    }

    //check confirm password
    if(updateNewPasswordDTO.getNewPassword().compareTo(updateNewPasswordDTO.getConfirmNewPassword()) != 0) {
      throw new BadRequestException("The confirm password is not the same new Password");
    }

    //set newPassword for User
    user.get().setPassword(passwordEncoder.encode(updateNewPasswordDTO.getNewPassword()));
    userRepository.save(user.get());
    return true;
  }

  public boolean checkExistsUser(String userName) {
    User user = userRepository.findByUserName(userName);
    if (user == null) {
      return false;
    }
    return true;
  }
}
