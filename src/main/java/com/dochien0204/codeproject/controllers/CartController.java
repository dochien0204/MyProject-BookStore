package com.dochien0204.codeproject.controllers;

import com.dochien0204.codeproject.base.RestApiV1;
import com.dochien0204.codeproject.base.VsResponseUtil;
import com.dochien0204.codeproject.contants.UrlConstant;
import com.dochien0204.codeproject.dtos.cart.CartDTO;
import com.dochien0204.codeproject.services.CartItemService;
import com.dochien0204.codeproject.services.CartService;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.stream.Collectors;

@RestApiV1
public class CartController {

    private final CartService cartService;
    private final CartItemService cartItemService;

    private final ModelMapper modelMapper;

    public CartController(CartService cartService, CartItemService cartItemService, ModelMapper modelMapper) {
        this.cartService = cartService;
        this.cartItemService = cartItemService;
        this.modelMapper = modelMapper;
    }

    @GetMapping(UrlConstant.Cart.LIST)
    public ResponseEntity<?> findAllCart() {
        List<CartDTO> output = cartService.findAllCart().stream().map(item -> modelMapper.map(item, CartDTO.class)).collect(Collectors.toList());
        return VsResponseUtil.ok(output);
    }

    @GetMapping(UrlConstant.Cart.USER_CART)
    public ResponseEntity<?> findCartByUser(@PathVariable(name = "userId") Integer userId) {
        CartDTO output = modelMapper.map(cartService.findCartByUser(userId), CartDTO.class);
        return VsResponseUtil.ok(output);
    }

    @PostMapping(UrlConstant.Cart.ADD_CART_FOR_USER)
    public ResponseEntity<?> addCartForUser(@PathVariable(name = "userId") Integer userId) {
        cartService.addCartForUser(userId);
        return VsResponseUtil.ok("Add cart to user successfully");
    }

    @PostMapping(UrlConstant.Cart.ADD_BOOK_TO_CART)
    public ResponseEntity<?> addBookToCart(@PathVariable(name = "cartId") Integer cartId,
                                           @PathVariable(name = "bookId") Integer bookId,
                                           @RequestParam(name = "quantity") Integer quantity) {
        cartItemService.addBookToCartById(cartId, bookId, quantity);
        return VsResponseUtil.ok("Add Book To Cart Successfully");
    }
}
