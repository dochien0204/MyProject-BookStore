package com.dochien0204.codeproject.repositories;

import com.dochien0204.codeproject.entities.OrderProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderProductRepository extends JpaRepository<OrderProduct, Integer> {
}
