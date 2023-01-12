package com.dochien0204.codeproject.services.Impl;

import com.dochien0204.codeproject.entities.Order;
import com.dochien0204.codeproject.entities.OrderProduct;
import com.dochien0204.codeproject.entities.User;
import com.dochien0204.codeproject.exceptions.BadRequestException;
import com.dochien0204.codeproject.exceptions.NotFoundException;
import com.dochien0204.codeproject.repositories.OrderProductRepository;
import com.dochien0204.codeproject.repositories.OrderRepository;
import com.dochien0204.codeproject.repositories.UserRepository;
import com.dochien0204.codeproject.services.OrderService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderProductRepository orderProductRepository;
    private final UserRepository userRepository;

    public OrderServiceImpl(OrderRepository orderRepository, OrderProductRepository orderProductRepository, UserRepository userRepository) {
        this.orderRepository = orderRepository;
        this.orderProductRepository = orderProductRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<Order> findAllOrder() {
        return orderRepository.findAll();
    }

    @Override
    public List<Order> findOrdersByUser(Integer userId) {
        if(userRepository.findById(userId).isEmpty()) {
            throw new NotFoundException("Not found user " + userId);
        }
        List<Order> orders = orderRepository.findByUser(userId);
        return orders;
    }

    @Override
    public Order findOrderById(String orderId) {
        Optional<Order> order = orderRepository.findById(orderId);
        if(order.isEmpty()) {
            throw new NotFoundException("Not found order " + orderId);
        }
        return order.get();
    }

    @Override
    public Order addOrderForUser(Integer userId) {
        Optional<User> user = userRepository.findById(userId);
        if(user.isEmpty()) {
            throw new NotFoundException("Not found user " + userId);
        }
        Order order = new Order();
        UUID orderId = UUID.randomUUID();
        order.setOrderId(orderId.toString());
        order.setUser(user.get());
        order.setStatus(1);
        orderRepository.save(order);
        return order;
    }

    @Override
    public void addOrderProductToOrdering(String orderId, Integer orderProductId) {
        Optional<Order> order = orderRepository.findById(orderId);
        System.out.println(order.get().getOrderId());
        if(order.isEmpty()) {
            throw new NotFoundException("Not found order " + orderId);
        }

        //check if order is ordered => cannot order when order status is not ordering (not status: 1)
        if(order.get().getStatus() != 1) {
            throw new BadRequestException("This order is not ordering");
        }

        Optional<OrderProduct> orderProduct = orderProductRepository.findById(orderProductId);
        System.out.println(orderProduct.get().getOrderProductId());
        if(orderProduct.isEmpty()) {
            throw new NotFoundException("Not found Order Product " + orderProductId);
        }

        //set order for order_product
        orderProduct.get().setOrder(order.get());
        orderProductRepository.save(orderProduct.get());
    }
}
