package com.maojie.trading.service;

import java.util.List;

import com.maojie.trading.model.Coin;

public interface CoinService {

    List<Coin> updateLatestCoinPriceFromSource() throws Exception;

    Coin findBySymbol(String symbol) throws Exception; // Source from database
    

}
