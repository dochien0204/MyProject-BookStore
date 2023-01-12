package com.dochien0204.codeproject.services;

import com.dochien0204.codeproject.entities.Order;

import java.util.List;

public interface OrderService {
    List<Order> findAllOrder();
    Order addOrderForUser(Integer userId);
    void addOrderProductToOrdering(String orderId, Integer orderProductId);

}
