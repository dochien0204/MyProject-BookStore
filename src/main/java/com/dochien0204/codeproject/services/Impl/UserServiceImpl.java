package com.dochien0204.codeproject.services.Impl;

import com.dochien0204.codeproject.dtos.user.CreateUserDTO;
import com.dochien0204.codeproject.dtos.user.UpdateUserDTO;
import com.dochien0204.codeproject.entities.User;
import com.dochien0204.codeproject.exceptions.NotFoundException;
import com.dochien0204.codeproject.repositories.RoleRepository;
import com.dochien0204.codeproject.repositories.UserRepository;
import com.dochien0204.codeproject.services.UserService;
import com.dochien0204.codeproject.utils.FileUtils;
import lombok.RequiredArgsConstructor;
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
public class UserServiceImpl implements UserService {

  private final RoleRepository roleRepository;
  private final UserRepository userRepository;
  private final ModelMapper modelMapper;
  private final PasswordEncoder passwordEncoder;

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
    Optional<User> user = Optional.ofNullable(userRepository.findByUserName(userName));
    if (user.isEmpty()) {
      throw new NotFoundException("Not found user have user name: " + userName);
    }
    return user.get();
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

  public boolean checkExistsUser(String userName) {
    User user = userRepository.findByUserName(userName);
    if (user == null) {
      return false;
    }
    return true;
  }
}
