package com.dochien0204.codeproject.dtos.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GetUserByUserNameDTO {

  private Integer userId;

  private String userName;

  private String password;

  private String address;

  private String email;

  private String phone;

  private Integer age;

  private String fullName;

  private String avatar;

}
