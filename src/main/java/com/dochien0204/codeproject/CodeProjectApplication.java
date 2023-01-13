package com.dochien0204.codeproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class CodeProjectApplication {

  public static void main(String[] args) {
    SpringApplication.run(CodeProjectApplication.class, args);
  }

}
