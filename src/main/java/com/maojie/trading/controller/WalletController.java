package com.maojie.trading.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.maojie.trading.model.Order;
import com.maojie.trading.model.Wallet;
import com.maojie.trading.service.WalletService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@RestController
@RequestMapping("/api/wallet")
public class WalletController {

    @Autowired
    private WalletService walletService;

    @GetMapping("/api/wallet")
    public ResponseEntity<Wallet> getUserWallet() {
        Wallet wallet = walletService.getUserWallet();
        return new ResponseEntity<>(wallet, HttpStatus.ACCEPTED);
    }

    /* 
    @GetMapping("/api/wallet/order/{orderId}/pay")
    public ResponseEntity<Wallet> payOrderPayment(@PathVariable Long orderId) throws Exception {
        Order order = orderService.getOrderById(orderId);

        Wallet wallet = walletService.payOrderPayment(order);
        return new ResponseEntity<>(wallet, HttpStatus.ACCEPTED);
    }
    */


    



}
