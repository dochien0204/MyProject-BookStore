package com.dochien0204.codeproject.dtos.order_products;

import com.dochien0204.codeproject.entities.Book;
import com.dochien0204.codeproject.entities.Order;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderProductDTO {

    private Integer orderProductId;

    private Integer quantity;

    private float price;

    private BookDTO book;

    private Order order;
}
