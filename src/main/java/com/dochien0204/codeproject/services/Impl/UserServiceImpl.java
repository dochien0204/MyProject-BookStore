package com.dochien0204.codeproject.services.Impl;

import com.dochien0204.codeproject.dtos.user.CreateUserDTO;
import com.dochien0204.codeproject.dtos.user.UpdateUserDTO;
import com.dochien0204.codeproject.entities.User;
import com.dochien0204.codeproject.exceptions.NotFoundException;
import com.dochien0204.codeproject.repositories.RoleRepository;
import com.dochien0204.codeproject.repositories.UserRepository;
import com.dochien0204.codeproject.services.UserService;
import com.dochien0204.codeproject.utils.FileUtils;
import com.dochien0204.codeproject.utils.SecurityUtils;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

  private final RoleRepository roleRepository;
  private final UserRepository userRepository;
  private final ModelMapper modelMapper;
  private final PasswordEncoder passwordEncoder;
  public static final Long LOCK_TIME_DURATION = Long.valueOf(10 * 60 * 1000);

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
    user.setAvatar(FileUtils.uploadFile(userDTO.getFile()));
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
    user.get().setPassword(passwordEncoder.encode(userDTO.getPassword()));
    user.get().setAvatar(FileUtils.uploadFile(userDTO.getFile()));
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

  public boolean checkExistsUser(String userName) {
    User user = userRepository.findByUserName(userName);
    if (user == null) {
      return false;
    }
    return true;
  }
}
