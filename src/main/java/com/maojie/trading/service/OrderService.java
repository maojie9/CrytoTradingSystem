package com.maojie.trading.service;

import java.util.List;

import com.maojie.trading.domain.OrderType;
import com.maojie.trading.model.Coin;
import com.maojie.trading.model.Order;
import com.maojie.trading.model.OrderItem;

public interface OrderService {
    Order createOrder(OrderItem orderItem, OrderType orderType);

    Order getOrderById(Long orderId) throws Exception;

    List<Order> getAllOrder(OrderType orderType, String assetSymbol);

    Order processOrder(Coin coin, double quantity, OrderType orderType) throws Exception;

}
