package com.maojie.trading.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.maojie.trading.model.Wallet;


public interface WalletRepository extends JpaRepository<Wallet, Long>{
    List<Wallet> findAll(); 

}
