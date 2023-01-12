package com.dochien0204.codeproject.controllers;

import com.dochien0204.codeproject.base.RestApiV1;
import com.dochien0204.codeproject.base.VsResponseUtil;
import com.dochien0204.codeproject.contants.UrlConstant;
import com.dochien0204.codeproject.dtos.order_products.OrderProductDTO;
import com.dochien0204.codeproject.services.OrderProductService;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestApiV1
public class OrderProductController {

    private final ModelMapper modelMapper;
    private final OrderProductService orderProductService;

    public OrderProductController(ModelMapper modelMapper, OrderProductService orderProductService) {
        this.modelMapper = modelMapper;
        this.orderProductService = orderProductService;
    }

    @GetMapping(UrlConstant.OrderProduct.LIST)
    public ResponseEntity<?> findAllOrderProducts() {
        List<OrderProductDTO> output = orderProductService.findAllOrderProduct().stream()
                .map(item -> modelMapper.map(item, OrderProductDTO.class)).collect(Collectors.toList());
        return VsResponseUtil.ok(output);
    }

    @PostMapping(UrlConstant.OrderProduct.ADD_BOOK_ORDER_PRODUCT)
    public ResponseEntity<?> addCartItemToOrderProduct(@PathVariable(name = "cartItemId") Integer cartItemId) {
        orderProductService.addBookToOrderFromCartItem(cartItemId);
        return VsResponseUtil.ok("Add book to order product successfully");
    }

    @PostMapping(UrlConstant.OrderProduct.ADD_BOOK_TO_ORDER_PRODUCT_DIRECTLY)
    public ResponseEntity<?> addBookToOrderProductDirectly(@PathVariable(name = "bookId") Integer bookId,
                                                           @RequestParam(name = "quantity") Integer quantity) {
        orderProductService.addBookToOrderDirectly(bookId, quantity);
        return VsResponseUtil.ok("Add book " + bookId +" to order product successfully");
    }

    @DeleteMapping(UrlConstant.OrderProduct.REMOVE_ORDER_PRODUCT)
    public ResponseEntity<?> removeOrderProduct(@PathVariable(name = "orderProductId") Integer orderProductId) {
        orderProductService.removeOrderProduct(orderProductId);
        return VsResponseUtil.ok("Delete Order Product " + orderProductId + "successfully");
    }
}
