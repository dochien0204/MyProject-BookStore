package com.dochien0204.codeproject.dtos.catalog;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GetCatalogItemDTO {

  private Integer catalogId;

  private String catalogName;

  private String catalogImage;
}
