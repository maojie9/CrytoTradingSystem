package com.maojie.trading.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.maojie.trading.model.Coin;

public interface CoinRepository extends JpaRepository<Coin, String> {

}
