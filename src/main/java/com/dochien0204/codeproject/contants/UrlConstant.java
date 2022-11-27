package com.dochien0204.codeproject.contants;

public class UrlConstant {

  public UrlConstant() {

  }

  public static final class User {
    public static final String PREFIX = "/users";
    public static final String LIST = "/users";
    public static final String GET = PREFIX + "/{userId}";
    public static final String GET_BY_USERNAME = PREFIX + "/{userName}/user-name";
    public static final String GET_BY_EMAIL = PREFIX + "/{email}/email";
    public static final String GET_BY_SUBNAME = PREFIX + "/search/name";
    public static final String CREATE = PREFIX + "/create";
    public static final String UPDATE = PREFIX + "/{userId}" + "/update";
    public static final String DELETE = PREFIX + "/{userId}/delete";
    public static final String REFRESH_TOKEN = PREFIX + "/refresh-token";
  }
}
