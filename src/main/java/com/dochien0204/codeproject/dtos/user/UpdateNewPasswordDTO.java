package com.dochien0204.codeproject.dtos.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateNewPasswordDTO {

  private String newPassword;

  private String confirmNewPassword;
}
