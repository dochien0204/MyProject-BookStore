package com.dochien0204.codeproject.services.Impl;

import com.dochien0204.codeproject.entities.User;
import com.dochien0204.codeproject.exceptions.NotFoundException;
import com.dochien0204.codeproject.repositories.UserRepository;
import com.dochien0204.codeproject.services.UserService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;

  public UserServiceImpl(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public List<User> findAllUser() {
    return userRepository.findAll();
  }

  @Override
  public User findUserById(Integer userId) {
    Optional<User> user = userRepository.findById(userId);
    if(user.isEmpty()) {
      throw new NotFoundException("Not found user have id " + userId);
    }
    return user.get();

  }

  @Override
  public User findUserByUserName(String userName) {
    Optional<User> user = Optional.ofNullable(userRepository.findByUserName(userName));
    if(user.isEmpty()) {
      throw new NotFoundException("Not found user have user name: " + userName);
    }
    return user.get();
  }

  @Override
  public User findUserByEmail(String email) {
    Optional<User> user = Optional.ofNullable(userRepository.findByEmail(email));
    if(user.isEmpty()) {
      throw new NotFoundException("Not found user have email: " + email);
    }
    return user.get();
  }

  @Override
  public List<User> findUserBySubName(String subName) {
    List<User> users = userRepository.findByFullName(subName);
    if(users.isEmpty()) {
      throw new NotFoundException("Not found sub search string: " + subName);
    }
    return users;
  }
}
