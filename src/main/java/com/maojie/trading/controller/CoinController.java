package com.maojie.trading.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.maojie.trading.model.Coin;
import com.maojie.trading.service.CoinService;

@RestController
@RequestMapping("/coins")
public class CoinController {

    @Autowired
    CoinService coinService;

    @Autowired
    ObjectMapper objectMapper;

    // ASSIGNMENT TASK 2
    @GetMapping
    ResponseEntity<List<Coin>>updateLatestCoinPriceFromSource() throws Exception{
        List<Coin> coins = coinService.updateLatestCoinPriceFromSource();
        return new ResponseEntity<>(coins, HttpStatus.ACCEPTED);
    };
}
