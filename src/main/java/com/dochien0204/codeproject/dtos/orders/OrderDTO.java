package com.dochien0204.codeproject.dtos.orders;

import com.dochien0204.codeproject.entities.OrderProduct;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

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

    private List<OrderProduct> orderProducts;
}
