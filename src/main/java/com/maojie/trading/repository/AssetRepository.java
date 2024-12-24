package com.maojie.trading.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.maojie.trading.model.Asset;
 
public interface AssetRepository extends JpaRepository<Asset, Long> {

    Asset findAssetByCoinSymbol(String symbol);

}