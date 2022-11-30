package com.dochien0204.codeproject.services.Impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.dochien0204.codeproject.dtos.user.response.token.RefreshTokenResponse;
import com.dochien0204.codeproject.entities.Role;
import com.dochien0204.codeproject.entities.User;
import com.dochien0204.codeproject.exceptions.BadRequestException;
import com.dochien0204.codeproject.repositories.UserRepository;
import com.dochien0204.codeproject.services.RefreshTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class RefreshTokenImpl implements RefreshTokenService {

  private final HttpServletResponse response;
  private final HttpServletRequest request;
  private final UserRepository userRepository;

  @Override
  public RefreshTokenResponse refreshAccessToken(String refresh_token) {
    try {
      Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
      JWTVerifier verifier = JWT.require(algorithm).build();
      DecodedJWT decodedJWT = verifier.verify(refresh_token);
      String userName = decodedJWT.getSubject();
      User user = userRepository.findByUserName(userName);
      if (user == null || decodedJWT.getExpiresAt().before(new Date())) {
        throw new BadRequestException("Refresh Token ko hợp lệ hoặc đã hết hạn");
      }
      String access_token = JWT.create()
          .withSubject(userName)
          .withExpiresAt(new Date(System.currentTimeMillis() + 10  * 60 * 1000))
          .withClaim("roles", user.getRoles().stream().map(Role::getRoleName).collect(Collectors.toList()))
          .sign(algorithm);
      return new RefreshTokenResponse(access_token, refresh_token);
    } catch (Exception ex) {
      return new RefreshTokenResponse(ex.getMessage());
    }
  }
}
