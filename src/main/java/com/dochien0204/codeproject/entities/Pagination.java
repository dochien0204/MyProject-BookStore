package com.dochien0204.codeproject.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Pagination {

    private Integer pageNo;

    private Integer pageSize;

    private String sortBy;

    private Long totalElements;
}
