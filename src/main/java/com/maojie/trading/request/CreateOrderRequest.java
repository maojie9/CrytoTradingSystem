package com.maojie.trading.request;

import com.maojie.trading.domain.OrderType;

import lombok.Data;

@Data
public class CreateOrderRequest {
    private String symbol;
    private double quantity;
    private OrderType orderType;
}
