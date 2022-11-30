package com.dochien0204.codeproject.dtos.books;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateBookByIdDTO {

  @NotBlank(message = "Book Name is required")
  private String bookName;

  private Integer available;

  private Double price;

  private MultipartFile file;

  private String description;

  private String author;

  private String publisher;

  @NotNull
  private Integer catalogId;
}
