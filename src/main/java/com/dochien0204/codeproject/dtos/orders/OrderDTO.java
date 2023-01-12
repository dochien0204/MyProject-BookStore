package com.dochien0204.codeproject.dtos.orders;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO {

    private String orderId;

    private Long orderDate;

    private Long requireDate;

    private Long shippedDate;

    private Integer status; //0:Cancel 1: Ordering, 2: Ordered, 3:Shipping, 4:Shipped

    private UserDTO user;
}
