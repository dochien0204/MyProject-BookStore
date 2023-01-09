package com.dochien0204.codeproject.repositories;

import com.dochien0204.codeproject.entities.Cart;
import com.dochien0204.codeproject.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface CartRepository extends JpaRepository<Cart, Integer> {
    @Query("select c from Cart c where c.user.userId = ?1")
    Cart findCartByUser(Integer userId);

    @Modifying
    @Query(value = "insert into cart (user_id,total,total_price) values (:userId, 0, 0)", nativeQuery = true)
    @Transactional
    void addCartToUser(@Param("userId") Integer userId);
}
