package com.maojie.trading.service;

import com.maojie.trading.model.Order;
import com.maojie.trading.model.Wallet;

public interface WalletService {
    Wallet getUserWallet();

    Wallet addBalance(Wallet wallet, long amount);

    Wallet payOrderPayment(Order order) throws Exception;


}
