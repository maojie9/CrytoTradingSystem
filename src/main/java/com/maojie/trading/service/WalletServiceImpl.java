package com.maojie.trading.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.maojie.trading.domain.OrderType;
import com.maojie.trading.model.Order;
import com.maojie.trading.model.Wallet;
import com.maojie.trading.repository.WalletRepository;

@Service
public class WalletServiceImpl implements WalletService{

    @Autowired
    private WalletRepository walletRepository;

    @Override
    public Wallet getUserWallet() {
        List<Wallet> walletList = walletRepository.findAll();
        return (walletList.size()==1)?walletList.get(0):null;
    }

    @Override
    public Wallet addBalance(Wallet wallet, long amount) {
        BigDecimal balance = wallet.getBalance();
        BigDecimal newBalance = balance.add(BigDecimal.valueOf(amount));
        wallet.setBalance(newBalance);

        return walletRepository.save(wallet); 
    }

    @Override
    public Wallet payOrderPayment(Order order) throws Exception{
        Wallet wallet = getUserWallet();

        if(wallet == null)
            throw new Exception("Unable to find the wallet");

        BigDecimal newBalance;
        if(order.getOrderType().equals(OrderType.BUY)){
            newBalance = wallet.getBalance().subtract(order.getPrice());
            if(newBalance.compareTo(order.getPrice())<0){
                throw new Exception("Insufficent funds for this transaction");
            }
            wallet.setBalance(newBalance);
        } else {
            newBalance = wallet.getBalance().subtract(order.getPrice());
        }
        wallet.setBalance(newBalance);
        walletRepository.save(wallet);
        return wallet;
         
    }

}
