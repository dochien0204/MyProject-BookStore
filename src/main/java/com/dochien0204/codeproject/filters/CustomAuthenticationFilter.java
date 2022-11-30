package com.dochien0204.codeproject.filters;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.dochien0204.codeproject.services.Impl.UserServiceImpl;
import com.dochien0204.codeproject.services.UserService;
import com.dochien0204.codeproject.utils.BeanUtils;
import com.dochien0204.codeproject.utils.DateUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

  private static final int MAX_FAILURE_ATTEMPTS = 3;
  private final AuthenticationManager authenticationManager;

  public CustomAuthenticationFilter(AuthenticationManager authenticationManager) {
    this.authenticationManager = authenticationManager;
  }

  @Override
  public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
    String userName = request.getParameter("userName");
    String password = request.getParameter("password");
    log.info("User name is {} ", userName);
    log.info("Password is {} ", password);
    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userName, password);
    return authenticationManager.authenticate(authenticationToken);
  }

  @Override
  protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException {

    Map<String, String> error = new HashMap<>();
    response.setContentType(APPLICATION_JSON_VALUE);
    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

    //get bean UserService
    UserService userService = BeanUtils.getBean(UserService.class);
    String userName = request.getParameter("userName");
    com.dochien0204.codeproject.entities.User user = userService.findUserByUserName(userName);
    System.out.println(failed.getMessage() + "hi");
    //check if failed attempts = MAX_ATTEMPTS then lock User
    if (user != null) {
      if (user.getAccountNonLocked()) {
        if (user.getFailedAttempts() <= MAX_FAILURE_ATTEMPTS - 1) {
          int enteredTimes = MAX_FAILURE_ATTEMPTS - user.getFailedAttempts();
          userService.incrementFailedAttempts(user);
          if (enteredTimes - 1 == 0) {
            userService.lockedUser(user);
            error.put("error_message", "Account is locked until " + DateUtils.toDate(System.currentTimeMillis() + UserServiceImpl.LOCK_TIME_DURATION));
          } else {
            error.put("error_message", "Password is not valid, you have " + (enteredTimes - 1) + " times entering");
          }
        }
      } else if (user.getLockTime() < System.currentTimeMillis() && !user.getAccountNonLocked()) {
        userService.unlockUser(user);
        error.put("error_message", "Account is unlocked ! Try to again");
      } else if (user.getLockTime() > System.currentTimeMillis() && !user.getAccountNonLocked()) {
        error.put("error_message", "Account is locked until " + DateUtils.toDate(user.getLockTime()));
      }
    } else {
      error.put("error_message", "Username doesn't exists");
    }
    new ObjectMapper().writeValue(response.getOutputStream(), error);
  }

  @Override
  protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException {
    response.setContentType(APPLICATION_JSON_VALUE);

    User user = (User) authentication.getPrincipal();
    //Get Bean
    UserService userService = BeanUtils.getBean(UserService.class);
    com.dochien0204.codeproject.entities.User current = userService.findUserByUserName(user.getUsername());

    System.out.println(current.getLockTime() - System.currentTimeMillis());
    if (!current.getAccountNonLocked() && (System.currentTimeMillis() < current.getLockTime())) {
      response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
      Map<String, String> error = new HashMap<>();
      error.put("error_message", "Account is locked until " + DateUtils.toDate(current.getLockTime()));
      new ObjectMapper().writeValue(response.getOutputStream(), error);
    } else if (current.getAccountNonLocked() || !current.getAccountNonLocked() && System.currentTimeMillis() > current.getLockTime()) {
      userService.unlockUser(current);
      Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());

      //generate access_token
      String access_token = JWT.create()
          .withSubject(user.getUsername())
          .withExpiresAt(new Date(System.currentTimeMillis() + 10 * 60 * 1000))
          .withIssuer(request.getRequestURL().toString())
          .withClaim("roles", user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
          .sign(algorithm);

      //generate refresh_token
      String refresh_token = JWT.create()
          .withSubject(user.getUsername())
          .withExpiresAt(new Date(System.currentTimeMillis() + 60 * 24 * 60 * 1000))
          .withIssuer(request.getRequestURL().toString())
          .sign(algorithm);

//    response.setHeader("access_token", access_token);
//    response.setHeader("refresh_token", refresh_token);

      //add response body
      Map<String, String> tokens = new HashMap<>();
      tokens.put("access_token", access_token);
      tokens.put("refresh_token", refresh_token);
      new ObjectMapper().writeValue(response.getOutputStream(), tokens);
    }

  }
}
