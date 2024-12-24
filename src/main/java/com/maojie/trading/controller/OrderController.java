package com.maojie.trading.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.maojie.trading.domain.OrderType;
import com.maojie.trading.model.Coin;
import com.maojie.trading.model.Order;
import com.maojie.trading.request.CreateOrderRequest;
import com.maojie.trading.service.CoinService;
import com.maojie.trading.service.OrderService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;



@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private CoinService coinService;

    //@Autowired
    //private WalletTransactionService WalletTransactionService;
    
    @PostMapping("/pay")
    public ResponseEntity<Order> payOrderPayment(@RequestBody CreateOrderRequest req) throws Exception {

        Coin coin = coinService.findBySymbol(req.getSymbol());

        Order order = orderService.processOrder(coin, req.getQuantity(), req.getOrderType());
        return ResponseEntity.ok(order);
       
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<Order> getOrderById(@PathVariable Long orderId) throws Exception {

        Order order = orderService.getOrderById(orderId);
        return ResponseEntity.ok(order);
    }

    @GetMapping()
    public ResponseEntity<List<Order>> getAllOrder(@RequestParam(required = false) OrderType order_type, @RequestParam(required = false) String asset_symbol) {
        List<Order> orders = orderService.getAllOrder(order_type, asset_symbol);
        return ResponseEntity.ok(orders);


    }
    
    
    



}
