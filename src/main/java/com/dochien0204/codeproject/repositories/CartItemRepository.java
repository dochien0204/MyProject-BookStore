package com.dochien0204.codeproject.repositories;

import com.dochien0204.codeproject.entities.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Integer> {

    @Modifying
    @Query(value = "insert into cart_item(cart_id, book_id, quantity, active_flag, delete_flag) values (:cartId, :bookId, :quantity, 1, 0)", nativeQuery = true)
    @Transactional
    void addCartItemToCart(@Param("cartId") Integer cartId, @Param("bookId") Integer bookId, @Param("quantity") Integer quantity);

    @Modifying
    @Query(value = "update cart_item set quantity=:newQuantity where cart_item_id=:cartItemId", nativeQuery = true)
    @Transactional
    void updateQuantityOfCartItem(@Param("newQuantity") Integer newQuantity, @Param("cartItemId") Integer cartItemId);

    @Modifying
    @Query(value = "delete from cart_item where cart_id=:cartId and cart_item_id=:cartItemId", nativeQuery = true)
    @Transactional
    void deleteCartItemByCart(@Param("cartId") Integer cartId, @Param("cartItemId") Integer cartItemId);

    @Modifying
    @Query(value = "delete from cart_item where cart_id=:cartId", nativeQuery = true)
    @Transactional
    void deleteAllCartItemByCartId(@Param("cartId") Integer cartId);
}
