package com.dochien0204.codeproject.services;

import com.dochien0204.codeproject.dtos.user.CreateUserDTO;
import com.dochien0204.codeproject.dtos.user.UpdateUserDTO;
import com.dochien0204.codeproject.entities.User;

import java.io.IOException;
import java.util.List;

public interface UserService {

  List<User> findAllUser();

  User findUserById(Integer id);

  User findUserByUserName(String userName);

  User findUserByEmail(String email);

  List<User> findUserBySubName(String subName);

  boolean save(CreateUserDTO userDTO) throws IOException;

  boolean update(UpdateUserDTO userDTO, Integer userId) throws IOException;

  boolean delete(Integer userId);

  void incrementFailedAttempts(User user);

  void lockedUser(User user);

  void unlockUser(User user);

}
