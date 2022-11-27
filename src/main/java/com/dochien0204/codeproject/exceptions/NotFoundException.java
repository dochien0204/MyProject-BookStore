package com.dochien0204.codeproject.exceptions;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class NotFoundException extends RuntimeException{

  private HttpStatus status;

  private String userMessage;

  private String devMessage;

  public NotFoundException(HttpStatus status, String userMessage, String devMessage) {
    super(userMessage);
    this.status = status;
    this.userMessage = userMessage;
    this.devMessage = devMessage;
  }
  public NotFoundException(String userMessage, String devMessage) {
    super(userMessage);
    this.status = HttpStatus.NOT_FOUND;
    this.userMessage = userMessage;
    this.devMessage = devMessage;
  }

  public NotFoundException(String userMessage) {
    super(userMessage);
    this.status = HttpStatus.NOT_FOUND;
    this.userMessage = userMessage;
  }

}
