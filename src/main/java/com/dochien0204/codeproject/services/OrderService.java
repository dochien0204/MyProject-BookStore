package com.dochien0204.codeproject.services;

import com.dochien0204.codeproject.entities.Order;

import java.util.List;

public interface OrderService {
    List<Order> findAllOrder();
    List<Order> findOrdersByUser(Integer userId);
    Order findOrderById(String orderId);
    Order addOrderForUser(Integer userId);
    void addOrderProductToOrdering(String orderId, Integer orderProductId);
    void orderedProduct(String orderId);
    void setOrderShipping(String orderId);
    void serOrderShipped(String orderId);
    void cancelOrder(String orderId);

}
