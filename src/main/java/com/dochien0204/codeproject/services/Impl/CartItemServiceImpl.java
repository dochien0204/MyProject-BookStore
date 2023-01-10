package com.dochien0204.codeproject.services.Impl;

import com.dochien0204.codeproject.entities.Book;
import com.dochien0204.codeproject.entities.Cart;
import com.dochien0204.codeproject.entities.CartItem;
import com.dochien0204.codeproject.exceptions.NotFoundException;
import com.dochien0204.codeproject.repositories.BookRepository;
import com.dochien0204.codeproject.repositories.CartItemRepository;
import com.dochien0204.codeproject.repositories.CartRepository;
import com.dochien0204.codeproject.services.CartItemService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        //get current total of cart
        int currentTotal = cart.get().getTotal();
        //get current total price of cart
        float currentPrice = cart.get().getTotalPrice();
        //check book's exists in cart,if exists increment quantity
        boolean isExists = false;
        for(int i = 0; i < cart.get().getCartItems().size(); i++) {
            if(cart.get().getCartItems().get(i).getBook().getBookId() == bookId){
                isExists = true;
                int newQuantity = cart.get().getCartItems().get(i).getQuantity() + quantity;
                cart.get().getCartItems().get(i).setQuantity(newQuantity);
            }
        }
        cart.get().setTotal(currentTotal + quantity);
        cart.get().setTotalPrice((float) (currentPrice + (book.get().getPrice()*quantity)));
        if(!isExists) {
            cartItemRepository.addCartItemToCart(cartId, bookId, quantity);
        }
        cartRepository.save(cart.get());
    }

    @Override
    public void editCartItemById(Integer cartId, Integer cartItemId, Integer quantity) {
        if(checkItemExists(cartId, cartItemId)) {
            cartItemRepository.updateQuantityOfCartItem(quantity, cartItemId);
            //update total and total price
            updateCartInformation(cartId);
        } else {
            throw new NotFoundException("Not found cart item " + cartItemId +" in your cart");
        }
    }

    @Override
    @Transactional
    public void deleteCartItemById(Integer cartId, Integer cartItemId) {
        if(checkItemExists(cartId, cartItemId)) {
            cartItemRepository.deleteCartItemByCart(cartId, cartItemId);
        } else {
            throw new NotFoundException("Not found cart item " + cartItemId + " in your cart");
        }
    }

    public boolean checkItemExists(Integer cartId, Integer cartItemId) {
        Optional<Cart> cart = cartRepository.findById(cartId);
        if(cart.isEmpty()) {
            throw new NotFoundException("Cart Not Found");
        }
        for(CartItem cartItem : cart.get().getCartItems()) {
            if(cartItem.getCartItemId() == cartItemId) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void updateCartInformation(Integer cartId) {
        Optional<Cart> cart = cartRepository.findById(cartId);
        int total = 0;
        int price = 0;
        for(CartItem cartItem : cart.get().getCartItems()) {
            total += cartItem.getQuantity();
            price += cartItem.getQuantity() * cartItem.getBook().getPrice();
        }
        cartRepository.updateTotalAndTotalPriceCart(cartId, total, price);
    }
}
