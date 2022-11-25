package com.dochien0204.codeproject.services;

import com.dochien0204.codeproject.entities.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

  List<User> findAllUser();

  User findUserById(Integer id);

  User findUserByUserName(String userName);

  User findUserByEmail(String email);

  List<User> findUserBySubName(String subName);
}
