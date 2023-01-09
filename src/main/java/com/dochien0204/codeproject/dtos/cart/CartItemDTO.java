package com.dochien0204.codeproject.dtos.cart;

import com.dochien0204.codeproject.dtos.books.GetBookItemDTO;
import com.dochien0204.codeproject.entities.Book;
import com.dochien0204.codeproject.entities.Cart;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CartItemDTO {

    private Integer cartItemId;

    private Integer quantity;

    private BookDTO book;
}
