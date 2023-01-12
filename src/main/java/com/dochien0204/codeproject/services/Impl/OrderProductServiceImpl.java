package com.dochien0204.codeproject.services.Impl;

import com.dochien0204.codeproject.entities.Book;
import com.dochien0204.codeproject.entities.CartItem;
import com.dochien0204.codeproject.entities.OrderProduct;
import com.dochien0204.codeproject.exceptions.NotFoundException;
import com.dochien0204.codeproject.repositories.BookRepository;
import com.dochien0204.codeproject.repositories.CartItemRepository;
import com.dochien0204.codeproject.repositories.OrderProductRepository;
import com.dochien0204.codeproject.services.OrderProductService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderProductServiceImpl implements OrderProductService {

    private final OrderProductRepository orderProductRepository;
    private final CartItemRepository cartItemRepository;
    private final BookRepository bookRepository;

    public OrderProductServiceImpl(OrderProductRepository orderProductRepository, CartItemRepository cartItemRepository, BookRepository bookRepository) {
        this.orderProductRepository = orderProductRepository;
        this.cartItemRepository = cartItemRepository;
        this.bookRepository = bookRepository;
    }

    @Override
    public List<OrderProduct> findAllOrderProduct() {
        List<OrderProduct> orderProducts = orderProductRepository.findAll();
        return orderProducts;
    }

    @Override
    public void addBookToOrderFromCartItem(Integer cartId) {
        Optional<CartItem> cartItem  = cartItemRepository.findById(cartId);
        if(cartItem.isEmpty()) {
            throw new NotFoundException("Not found cart item " + cartId);
        }
        OrderProduct orderProduct = new OrderProduct();

        //get Book from cartItem and set to Order Product
        orderProduct.setBook(cartItem.get().getBook());
        orderProduct.setQuantity(cartItem.get().getQuantity());
        //set Price = quantity * book's price
        orderProduct.setPrice((float) (cartItem.get().getQuantity() * cartItem.get().getBook().getPrice()));
        orderProductRepository.save(orderProduct);
    }

    @Override
    public void addBookToOrderDirectly(Integer bookId, Integer quantity) {
        Optional<Book> book = bookRepository.findById(bookId);
        if (book.isEmpty()) {
            throw new NotFoundException("Not found Book " + bookId);
        }
        OrderProduct orderProduct = new OrderProduct();
        orderProduct.setBook(book.get());
        orderProduct.setQuantity(quantity);
        orderProduct.setPrice((float) (quantity * book.get().getPrice()));
        orderProductRepository.save(orderProduct);
    }

    @Override
    public void removeOrderProduct(Integer orderProductId) {
        Optional<OrderProduct> orderProduct = orderProductRepository.findById(orderProductId);
        if (orderProduct.isEmpty()) {
            throw new NotFoundException("Not found Order Product " + orderProductId);
        }
        orderProductRepository.deleteById(orderProductId);
    }
}
