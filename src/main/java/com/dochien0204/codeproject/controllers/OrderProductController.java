package com.dochien0204.codeproject.controllers;

import com.dochien0204.codeproject.base.RestApiV1;
import com.dochien0204.codeproject.base.VsResponseUtil;
import com.dochien0204.codeproject.contants.UrlConstant;
import com.dochien0204.codeproject.services.OrderProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@RestApiV1
public class OrderProductController {

    private final OrderProductService orderProductService;

    public OrderProductController(OrderProductService orderProductService) {
        this.orderProductService = orderProductService;
    }

    @PostMapping(UrlConstant.OrderProduct.ADD_CART_ITEM_TO_ORDER_PRODUCT)
    public ResponseEntity<?> addCartItemToOrderProduct(@PathVariable(name = "cartItemId") Integer cartItemId) {
        orderProductService.addCartItemToOrderProductFromCart(cartItemId);
        return VsResponseUtil.ok("Add cart item " + cartItemId + " to order product successfully");
    }
}
