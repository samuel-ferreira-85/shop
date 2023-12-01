package com.samuel.shoppingms.repository;

import com.samuel.shoppingms.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
