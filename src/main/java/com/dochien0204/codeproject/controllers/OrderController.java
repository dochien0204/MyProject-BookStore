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
}
