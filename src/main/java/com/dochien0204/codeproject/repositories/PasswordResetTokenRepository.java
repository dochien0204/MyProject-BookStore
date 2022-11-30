package com.dochien0204.codeproject.repositories;

import com.dochien0204.codeproject.entities.PasswordResetToken;
import com.dochien0204.codeproject.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Integer> {

  @Query("select p from PasswordResetToken p where p.user = ?1")
  PasswordResetToken findByUser(User user);

  @Query("select p.user from PasswordResetToken p where p.token = ?1")
  User findUserByToken(String token);


  @Query("select p from PasswordResetToken p where p.token = ?1")
  PasswordResetToken findByToken(String token);

  @Transactional
  @Modifying
  @Query("delete from PasswordResetToken p where p.user = ?1")
  void deleteAllByUser(User user);

}
