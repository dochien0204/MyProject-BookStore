package com.dochien0204.codeproject.repositories;

import com.dochien0204.codeproject.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

  @Query("select u from User u where u.userName = ?1")
  User findByUserName(String userName);

  @Query("select u from User u where u.email = ?1")
  User findByEmail(String email);

  @Query("select u from User u where u.fullName like %?1%")
  List<User> findByFullName(String subName);

  @Transactional
  @Query("update User u set u.failedAttempts = ?1 where u.userName = ?2")
  @Modifying
  void updateFailedAttempts(int failedAttempts, String userName);

  @Transactional
  @Query("update User u set u.accountNonLocked = 0, u.lockTime = ?2 where u.userName = ?1")
  @Modifying
  void lockedUser(String userName, Long lockTime);

  @Transactional
  @Query("update User u set u.accountNonLocked = 1, u.lockTime = 0, u.failedAttempts = 0 where u.userName = ?1")
  @Modifying
  void unlockUser(String userName);
}
