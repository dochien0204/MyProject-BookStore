package com.dochien0204.codeproject.utils;

import com.dochien0204.codeproject.entities.User;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtils {
  public static User getPrincipal() {
    return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
  }
}
