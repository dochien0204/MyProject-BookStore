package com.dochien0204.codeproject.services;

import com.dochien0204.codeproject.entities.Cart;

import java.util.List;

public interface CartItemService {
    void addBookToCartById(Integer cartId, Integer bookId, Integer quantity);
    void editCartItemById(Integer cartId, Integer cartItemId, Integer quantity);
    void deleteCartItemById(Integer cartId, Integer cartItemId);
    void updateCartInformation(Integer cartId);
    
}
