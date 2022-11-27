package com.dochien0204.codeproject.dtos.user;

import com.dochien0204.codeproject.entities.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GetListUserItemDTO {

  private Integer userId;

  private String userName;

  private String password;

  private String address;

  private String email;

  private String phone;

  private Integer age;

  private String fullName;

  private String avatar;

  private List<Role> roles;
}
