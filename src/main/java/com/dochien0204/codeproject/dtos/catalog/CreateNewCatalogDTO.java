package com.dochien0204.codeproject.dtos.catalog;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Nationalized;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateNewCatalogDTO {

  @Nationalized
  @NotBlank(message = "Catalog Name is required")
  private String catalogName;

  @NotBlank(message = "Catalog Image is required")
  private MultipartFile file;
}
