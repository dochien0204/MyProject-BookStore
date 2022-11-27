package com.dochien0204.codeproject.exceptions;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class BadRequestException extends RuntimeException {
  private HttpStatus status;

  private String message;

  public BadRequestException(String message) {
    this.status = HttpStatus.BAD_REQUEST;
    this.message = message;
  }
}
