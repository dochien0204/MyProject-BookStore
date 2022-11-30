package com.dochien0204.codeproject.dtos.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Nationalized;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateUserDTO {

  @NotBlank(message = "Username is required")
  private String userName;

  @NotBlank(message = "Username is required")
  private String password;

  @Nationalized
  private String address;

  @NotBlank(message = "Email is required")
  private String email;

  private String phone;

  @Min(value = 1)
  @Max(value = 100)
  private Integer age;

  @NotBlank(message = "Full Name is required")
  @Nationalized
  private String fullName;

  private MultipartFile file;

}
