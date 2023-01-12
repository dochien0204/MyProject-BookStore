package com.dochien0204.codeproject.services;

import com.dochien0204.codeproject.entities.OrderProduct;

import java.util.List;

public interface OrderProductService {
    List<OrderProduct> findAllOrderProduct();
    void addBookToOrderFromCartItem(Integer cartId);
    void addBookToOrderDirectly(Integer bookId, Integer quantity);
    void removeOrderProduct(Integer orderProductId);
}
