package com.dochien0204.codeproject.services;

import com.dochien0204.codeproject.dtos.user.response.token.RefreshTokenResponse;

public interface RefreshTokenService {

  RefreshTokenResponse refreshAccessToken(String refresh_token);
}
