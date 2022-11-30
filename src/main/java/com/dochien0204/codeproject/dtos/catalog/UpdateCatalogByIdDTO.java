package com.dochien0204.codeproject.dtos.catalog;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateCatalogByIdDTO {

  private String catalogName;

  private MultipartFile file;
}
