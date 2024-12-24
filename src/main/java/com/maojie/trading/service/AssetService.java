package com.maojie.trading.service;

import java.util.List;

import com.maojie.trading.model.Asset;
import com.maojie.trading.model.Coin;

public interface AssetService {

    Asset createAsset(Coin coin, double quantity);

    Asset getAssetById(Long assetId) throws Exception;

    List<Asset> getAssets();

    Asset updateAsset(Long assetId, double quantity) throws Exception;

    Asset findAssetByCoin(String symbol);

    void deleteAsset(Long assetId);

}
