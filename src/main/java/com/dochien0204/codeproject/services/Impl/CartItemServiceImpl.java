package com.dochien0204.codeproject.services.Impl;

import com.dochien0204.codeproject.entities.Book;
import com.dochien0204.codeproject.entities.Cart;
import com.dochien0204.codeproject.exceptions.BadRequestException;
import com.dochien0204.codeproject.exceptions.NotFoundException;
import com.dochien0204.codeproject.repositories.BookRepository;
import com.dochien0204.codeproject.repositories.CartItemRepository;
import com.dochien0204.codeproject.repositories.CartRepository;
import com.dochien0204.codeproject.services.CartItemService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CartItemServiceImpl implements CartItemService {

    private final CartItemRepository cartItemRepository;
    private final BookRepository bookRepository;
    private final CartRepository cartRepository;
    public CartItemServiceImpl(CartItemRepository cartItemRepository, BookRepository bookRepository, CartRepository cartRepository) {
        this.cartItemRepository = cartItemRepository;
        this.bookRepository = bookRepository;
        this.cartRepository = cartRepository;

    }

    @Override
    public void addBookToCartById(Integer cartId, Integer bookId, Integer quantity) {
        Optional<Cart> cart = cartRepository.findById(cartId);
        Optional<Book> book = bookRepository.findById(bookId);
        if( cart.isEmpty() || book.isEmpty()) {
            throw new NotFoundException("Cart or Book not found");
        }
        int currentTotal = cart.get().getTotal();
        float currentPrice = cart.get().getTotalPrice();
        cart.get().setTotal(currentTotal + quantity);
        cart.get().setTotalPrice((float) (currentPrice + (book.get().getPrice()*quantity)));
        cartItemRepository.addCartItemToCart(cartId, bookId, quantity);
        cartRepository.save(cart.get());
    }
}
