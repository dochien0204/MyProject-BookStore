package com.dochien0204.codeproject.dtos.emails;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SimpleMessageForAllDTO {

  String[] emails;

  String subject;

  String text;


}
