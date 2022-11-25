package com.dochien0204.codeproject.repositories;

import com.dochien0204.codeproject.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

  @Query("select u from User u where u.userName = ?1")
  User findByUserName(String userName);

  @Query("select u from User u where u.email = ?1")
  User findByEmail(String email);

  @Query("select u from User u where u.fullName like %?1%")
  List<User> findByFullName(String subName);
}
