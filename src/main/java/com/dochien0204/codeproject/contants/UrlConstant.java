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

  //Email
  public static final class Email {
    public static final String PREFIX = "/email";
    public static final String SEND_SIMPLE_TO_ALL = PREFIX + "/send-all";
    public static final String SEND_SIMPLE_TO_ONE = PREFIX + "/send";
  }

  //Catalog
  public static final class Catalog {
    public static final String PREFIX = "/catalog";
    public static final String LIST = "/catalog/list";
    public static final String GET_BY_ID = PREFIX + "/{catalogId}";
    public static final String GET_BY_CATALOG_NAME = PREFIX + "/search";
    public static final String CREATE = PREFIX + "/create";
    public static final String UPDATE = PREFIX + "/{catalogId}/update";
    public static final String DELETE = PREFIX + "/{catalogId}/delete";
  }

  public static final class Book {
    public static final String PREFIX = "/book";
    public static final String LIST = "/book/list";
    public static final String CREATE = PREFIX + "/{catalogId}/create";
    public static final String GET_BY_ID = PREFIX + "/{bookId}/search";
    public static final String GET_BY_NAME = PREFIX + "/book-name/search";
    public static final String GET_BY_SUBNAME = PREFIX + "/sub-name/search";
    public static final String GET_BY_PRICE_GREATER = PREFIX + "/greater-price/search";
    public static final String GET_BY_PRICE_LESS = PREFIX + "/less-price/search";
    public static final String GET_BY_CATALOG = PREFIX + "/catalog/{catalogId}/search";
    public static final String UPDATE_BOOK = PREFIX + "/{bookId}/update";
    public static final String DELETE_BOOK = PREFIX + "/{bookId}/delete";

  }

  public static final class Cart {

    public static final String PREFIX = "cart";
    public static final String LIST = PREFIX + "/list";
    public static final String USER_CART = PREFIX + "/{userId}/cart-user";
    public static final  String ADD_CART_FOR_USER = PREFIX + "/{userId}/add-cart";
    public static final String ADD_BOOK_TO_CART = PREFIX + "/{cartId}/{bookId}/add-book";
    public static final String EDIT_QUANTITY_OF_CART_ITEM = PREFIX + "/{cartId}/{cartItemId}/edit-cart-item";
    public static final String DELETE_CART_ITEM = PREFIX + "/{cartId}/{cartItemId}/delete-cart-item";
    public static final String DELETE_ALL_CART_ITEM = PREFIX + "/{cartId}/delete-all-cart-item";
  }

}
