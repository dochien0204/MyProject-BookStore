package com.dochien0204.codeproject.dtos.cart;

import com.dochien0204.codeproject.entities.CartItem;
import com.dochien0204.codeproject.entities.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CartDTO {

    private Integer cartId;

    private Integer total;

    private Float totalPrice;

    private UserDTO user;

    private List<CartItemDTO> cartItems;
}
