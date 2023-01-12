package com.dochien0204.codeproject.services.Impl;

import com.dochien0204.codeproject.repositories.CartItemRepository;
import com.dochien0204.codeproject.services.CartItemService;
import com.dochien0204.codeproject.services.OrderService;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements OrderService {

    private final CartItemRepository cartItemRepository;

    public OrderServiceImpl(CartItemService cartItemService, CartItemRepository cartItemRepository) {
        this.cartItemRepository = cartItemRepository;
    }



}
