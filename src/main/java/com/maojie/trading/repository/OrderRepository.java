package com.maojie.trading.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.maojie.trading.model.Order;


public interface OrderRepository extends JpaRepository<Order, Long>{

}
