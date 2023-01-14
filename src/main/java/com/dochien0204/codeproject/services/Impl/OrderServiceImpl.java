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

        //check exists order product in order
        order.get().getOrderProducts().forEach(item -> {
            if(item.getOrderProductId() == orderProductId) {
                throw new BadRequestException("Order Product exists");
            }
        });

        //set order for order_product
        orderProduct.get().setOrder(order.get());
        orderProductRepository.save(orderProduct.get());
    }

    @Override
    public void orderedProduct(String orderId) {
        Order order = findOrderById(orderId);

        //check if status is 1 (ordering), set status of order is 2 (ordered)
        if(order.getStatus() != 1) {
            throw new BadRequestException("This order is not ordering");
        }
        order.setStatus(2);
        order.setOrderDate(System.currentTimeMillis());
        orderRepository.save(order);
    }

    @Override
    public void setOrderShipping(String orderId) {
        Order order = findOrderById(orderId);

        //check if order is not ordered
        if(order.getStatus() != 2) {
            throw new BadRequestException("You haven't ordered this before");
        }

        //set status is 3 (shipping)
        order.setStatus(3);
        order.setShippingDate(System.currentTimeMillis());
        orderRepository.save(order);
    }

    @Override
    public void serOrderShipped(String orderId) {
        Order order = findOrderById(orderId);
        if (order.getStatus() != 3) {
            throw new BadRequestException("This order is not shipping status!");
        }

        //set order's status is 4 (shipped)
        order.setStatus(4);
        order.setShippedDate(System.currentTimeMillis());
        orderRepository.save(order);
    }

    @Override
    public void cancelOrder(String orderId) {
        Order order = findOrderById(orderId);
        if (order.getStatus() != 2) {
            throw new BadRequestException("This order is shipping now or is shipped");
        }

        //set order's status is 0 (cancel)
        order.setStatus(0);
        order.setRequireDate(System.currentTimeMillis());
        orderRepository.save(order);
    }

}
