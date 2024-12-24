package com.maojie.trading.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.maojie.trading.model.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long>{

}
