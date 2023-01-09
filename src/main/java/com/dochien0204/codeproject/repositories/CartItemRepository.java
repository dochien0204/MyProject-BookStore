package com.dochien0204.codeproject.repositories;

import com.dochien0204.codeproject.entities.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Integer> {

    @Modifying
    @Query(value = "insert into cart_item(cart_id, book_id, quantity, active_flag, delete_flag) values (:cartId, :bookId, :quantity, 1, 0)", nativeQuery = true)
    @Transactional
    void addCartItemToCart(@Param("cartId") Integer cartId, @Param("bookId") Integer bookId, @Param("quantity") Integer quantity);
}
