package com.dochien0204.codeproject.repositories;

import com.dochien0204.codeproject.entities.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface CartRepository extends JpaRepository<Cart, Integer> {
    @Query("select c from Cart c where c.user.userId = ?1")
    Cart findCartByUser(Integer userId);

    @Modifying
    @Query(value = "insert into cart (user_id,total,total_price) values (:userId, 0, 0)", nativeQuery = true)
    @Transactional
    void addCartToUser(@Param("userId") Integer userId);

    @Modifying
    @Query(value = "update cart c set c.total=:total, c.total_price=:totalPrice where c.cart_id=:cartId", nativeQuery = true)
    @Transactional
    void updateTotalAndTotalPriceCart(@Param("cartId") Integer cartId, @Param("total") Integer total, @Param("totalPrice") float totalPrice);
}
