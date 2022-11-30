package com.dochien0204.codeproject.services;

import com.dochien0204.codeproject.entities.User;

public interface PasswordResetTokenService {

  void createPasswordResetTokenForAccount(User user, String token);


}
