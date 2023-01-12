package com.dochien0204.codeproject.services.Impl;

import com.dochien0204.codeproject.entities.CartItem;
import com.dochien0204.codeproject.entities.OrderProduct;
import com.dochien0204.codeproject.exceptions.NotFoundException;
import com.dochien0204.codeproject.repositories.CartItemRepository;
import com.dochien0204.codeproject.repositories.OrderProductRepository;
import com.dochien0204.codeproject.services.OrderProductService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class OrderProductServiceImpl implements OrderProductService {

    private final OrderProductRepository orderProductRepository;
    private final CartItemRepository cartItemRepository;

    public OrderProductServiceImpl(OrderProductRepository orderProductRepository, CartItemRepository cartItemRepository) {
        this.orderProductRepository = orderProductRepository;
        this.cartItemRepository = cartItemRepository;
    }

    @Override
    public void addCartItemToOrderProductFromCart(Integer cartItemId) {
//        Optional<CartItem> cartItem = cartItemRepository.findById(cartItemId);
//        if (cartItem.isEmpty()) {
//            throw new NotFoundException("Not found cart item " + cartItemId);
//        }
//        OrderProduct orderProduct = new OrderProduct();
//        orderProduct.setCartItem(cartItem.get());
//
//        //calculate price of order product price = quantity * price of book
//        float price = (float) (cartItem.get().getQuantity() * cartItem.get().getBook().getPrice());
//        orderProduct.setPrice(price);
//        orderProductRepository.save(orderProduct);
//        cartItem.get().setDeleteFlag(true);
//        cartItemRepository.save(cartItem.get());
    }
}
