package com.dochien0204.codeproject.utils;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

public class SecurityUtils {
  public static String getPrincipal() {
    Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    if (principal instanceof UserDetails) {
      String userName = ((UserDetails) principal).getUsername();
      return userName;
    } else {
      String userName = principal.toString();
      return userName;
    }
  }
}
