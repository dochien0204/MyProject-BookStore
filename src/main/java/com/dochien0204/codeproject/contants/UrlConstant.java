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

    public static final String FORGOT_PASSWORD = PREFIX + "/{email}/forgot";

    public static final String CONFIRM_TOKEN = PREFIX + "/{email}/confirm";

    public static final String UPDATE_NEW_PASSWORD = PREFIX + "/{userId}/new-password";
  }

  public static final class Email {
    public static final String PREFIX = "/email";
    public static final String SEND_SIMPLE_TO_ALL = PREFIX + "/send-all";
    public static final String SEND_SIMPLE_TO_ONE = PREFIX + "/send";
  }
}
