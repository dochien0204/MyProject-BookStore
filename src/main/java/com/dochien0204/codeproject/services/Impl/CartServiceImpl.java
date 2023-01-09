package com.dochien0204.codeproject.services.Impl;

import com.dochien0204.codeproject.entities.Book;
import com.dochien0204.codeproject.entities.Cart;
import com.dochien0204.codeproject.entities.CartItem;
import com.dochien0204.codeproject.exceptions.BadRequestException;
import com.dochien0204.codeproject.exceptions.NotFoundException;
import com.dochien0204.codeproject.repositories.BookRepository;
import com.dochien0204.codeproject.repositories.CartRepository;
import com.dochien0204.codeproject.services.CartService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;

    private final BookRepository bookRepository;

    public CartServiceImpl(CartRepository cartRepository, BookRepository bookRepository) {
        this.cartRepository = cartRepository;
        this.bookRepository = bookRepository;
    }

    @Override
    public List<Cart> findAllCart() {
        List<Cart> carts = cartRepository.findAll();
        return carts;
    }

    @Override
    public void addCartForUser(Integer userId) {
        if(cartRepository.findCartByUser(userId) != null) {
            System.out.println(cartRepository.findCartByUser(userId));
            throw new BadRequestException("User had cart before!");
        }
        cartRepository.addCartToUser(userId);
    }

    @Override
    public Cart findCartByUser(Integer userId) {
        Cart cart = cartRepository.findCartByUser(userId);
        if (cart == null) {
            throw new NotFoundException("Not found cart of user: " + userId);
        }
        return  cart;
    }
}
