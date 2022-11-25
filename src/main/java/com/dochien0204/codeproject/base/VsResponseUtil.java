package com.dochien0204.codeproject.base;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class VsResponseUtil {

  public static ResponseEntity<RestData<?>> ok(HttpHeaders headers, HttpStatus status, Object data) {
    RestData<?> response = new RestData<>(data);
    return new ResponseEntity<>(response, headers, status);
  }

  public static ResponseEntity<RestData<?>> ok(Object data) {
    return ok(null, HttpStatus.OK, data);
  }

  public ResponseEntity<RestData<?>> ok(HttpHeaders headers, Object data) {
    return ok(headers, HttpStatus.OK, data);
  }

  public static ResponseEntity<RestData<?>> error(HttpHeaders headers, HttpStatus status, Object userMessage,
                                                  String devMessage) {
    RestData<?> response = RestData.error(userMessage, devMessage);
    return new ResponseEntity<>(response, headers, status);
  }

  public static ResponseEntity<RestData<?>> error(HttpHeaders headers, HttpStatus status, Object userMessage) {
    RestData<?> response = RestData.error(userMessage);
    return new ResponseEntity<>(response, headers, status);
  }

  public static ResponseEntity<RestData<?>> error(HttpStatus status, Object userMessage, String devMessage) {
    RestData<?> response = RestData.error(userMessage, devMessage);
    return new ResponseEntity<>(response, status);
  }

  public static ResponseEntity<RestData<?>> error(HttpStatus status, Object userMessage) {
    RestData<?> response = RestData.error(userMessage);
    return new ResponseEntity<>(response, status);
  }

}
