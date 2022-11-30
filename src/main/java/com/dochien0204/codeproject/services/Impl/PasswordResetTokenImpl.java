package com.dochien0204.codeproject.services.Impl;

import com.dochien0204.codeproject.entities.PasswordResetToken;
import com.dochien0204.codeproject.entities.User;
import com.dochien0204.codeproject.repositories.PasswordResetTokenRepository;
import com.dochien0204.codeproject.repositories.UserRepository;
import com.dochien0204.codeproject.services.PasswordResetTokenService;
import com.dochien0204.codeproject.utils.RandomStringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class PasswordResetTokenImpl implements PasswordResetTokenService {

  private static final long RESET_TOKEN_EXPIRED = 2 * 60 * 1000;
  private final UserRepository userRepository;
  private final PasswordResetTokenRepository passwordResetTokenRepository;

  public PasswordResetTokenImpl(UserRepository userRepository, PasswordResetTokenRepository passwordResetTokenRepository) {
    this.userRepository = userRepository;
    this.passwordResetTokenRepository = passwordResetTokenRepository;
  }

  @Override
  public void createPasswordResetTokenForAccount(User user, String token) {
    PasswordResetToken passwordResetToken = new PasswordResetToken();
    passwordResetToken.setUser(user);
    passwordResetToken.setToken(token);
    passwordResetToken.setExpiryTime(System.currentTimeMillis() + RESET_TOKEN_EXPIRED);
    passwordResetTokenRepository.save(passwordResetToken);
  }


}
