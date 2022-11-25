package com.dochien0204.codeproject.exceptions;

import com.dochien0204.codeproject.base.RestData;
import com.dochien0204.codeproject.base.VsResponseUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionHandlerConfig {

  @ExceptionHandler(value = {NotFoundException.class})
  public ResponseEntity<RestData<?>>  handleNotFoundException(NotFoundException ex) {
    return VsResponseUtil.error(HttpStatus.NOT_FOUND, ex.getUserMessage());
  }
}
