package com.dochien0204.codeproject.dtos.books;

import com.dochien0204.codeproject.dtos.catalog.GetCatalogItemDTO;
import com.dochien0204.codeproject.entities.Catalog;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GetBookItemDTO {

  private Integer bookId;

  private String bookName;

  private Integer available;

  private Double price;

  private String image;

  private String description;

  private String author;

  private String publisher;

  private GetCatalogItemDTO catalog;

}
