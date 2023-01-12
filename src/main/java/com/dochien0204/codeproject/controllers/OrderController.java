package com.dochien0204.codeproject.controllers;

import com.dochien0204.codeproject.base.RestApiV1;
import com.dochien0204.codeproject.base.VsResponseUtil;
import com.dochien0204.codeproject.contants.UrlConstant;
import com.dochien0204.codeproject.dtos.orders.OrderDTO;
import com.dochien0204.codeproject.entities.Order;
import com.dochien0204.codeproject.services.OrderService;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.stream.Collectors;

@RestApiV1
public class OrderController {

    private final OrderService orderService;
    private final ModelMapper modelMapper;

    public OrderController(OrderService orderService, ModelMapper modelMapper) {
        this.orderService = orderService;
        this.modelMapper = modelMapper;
    }

    @GetMapping(UrlConstant.Order.LIST)
    private ResponseEntity<?> findAllOrder() {
        List<OrderDTO> output = orderService.findAllOrder().stream().map(item -> modelMapper.map(item, OrderDTO.class)).collect(Collectors.toList());
        return VsResponseUtil.ok(output);
    }

    @PostMapping(UrlConstant.Order.ADD_ORDER_FOR_USER)
    public ResponseEntity<?> addOrderForUser(@PathVariable(name = "userId") Integer userId) {
        Order output = orderService.addOrderForUser(userId);
        return VsResponseUtil.ok(output);
    }

    @PostMapping(UrlConstant.Order.ADD_ORDER_PRODUCT_TO_ORDERING)
    public ResponseEntity<?> addOrderProductToOrdering(@PathVariable(name = "orderId") String orderId,
                                                       @PathVariable(name = "orderProductId") Integer orderProductId) {
        orderService.addOrderProductToOrdering(orderId, orderProductId);
        return VsResponseUtil.ok("Add Order Product " + orderProductId + " to Order " + orderId + " successfully");
    }

    @GetMapping(UrlConstant.Order.FIND_ORDERS_BY_USER)
    public ResponseEntity<?> findOrdersByUser(@PathVariable(name = "userId") Integer userId) {
        List<OrderDTO> output =  orderService.findOrdersByUser(userId).stream().map(item -> modelMapper.map(item, OrderDTO.class)).collect(Collectors.toList());
        return VsResponseUtil.ok(output);
    }

    @GetMapping(UrlConstant.Order.FIND_ORDERS_BY_ID)
    public ResponseEntity<?> findOrderById(@PathVariable(name = "orderId") String orderId) {
        OrderDTO output = modelMapper.map(orderService.findOrderById(orderId), OrderDTO.class);
        return VsResponseUtil.ok(output);
    }
}
