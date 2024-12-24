package com.maojie.trading.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.maojie.trading.model.Asset;
import com.maojie.trading.service.AssetService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@RestController
@RequestMapping("/api/asset")
public class AssetController {

    @Autowired
    private AssetService assetService;

    @GetMapping("/{assetId}")
    public ResponseEntity<Asset> getAssetById(@PathVariable Long assetId) throws Exception {
        Asset asset = assetService.getAssetById(assetId);
        return ResponseEntity.ok().body(asset);
    }

    @GetMapping("/coin/{symbol}/user")
    public ResponseEntity<Asset> getAssetById(@PathVariable String symbol) throws Exception {
        Asset asset = assetService.findAssetByCoin(symbol);
        return ResponseEntity.ok().body(asset);
    }
    
    @GetMapping()
    public ResponseEntity<List<Asset>> getAssets() {
        List<Asset> assets = assetService.getAssets();
        return ResponseEntity.ok().body(assets);
    }
    



}
