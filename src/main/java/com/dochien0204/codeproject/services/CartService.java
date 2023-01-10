package com.dochien0204.codeproject.services;

import com.dochien0204.codeproject.entities.Cart;

import java.util.List;

public interface CartService {
    List<Cart> findAllCart();
    void addCartForUser(Integer userId);
    Cart findCartByUser(Integer userId);
}
