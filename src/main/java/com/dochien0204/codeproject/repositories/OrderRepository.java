package com.dochien0204.codeproject.repositories;

import com.dochien0204.codeproject.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, String> {
    @Query(value = "select * from orders where user_id=:userId", nativeQuery = true)
    List<Order> findByUser(@Param("userId") Integer userId);
}
