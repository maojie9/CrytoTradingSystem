package com.maojie.trading.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import com.maojie.trading.model.BinanceCoin;
import com.maojie.trading.model.Coin;
import com.maojie.trading.model.HuobiCoin;
import com.maojie.trading.repository.CoinRepository;

@Service 
public class CoinServiceImpl implements CoinService{

    @Autowired
    private CoinRepository coinRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public List<Coin> updateLatestCoinPriceFromSource ()throws Exception{
        String binanceURL = "https://api.binance.com/api/v3/ticker/bookTicker";
        String houbiURL = "https://api.huobi.pro/market/tickers";

        RestTemplate restTemplate = new RestTemplate();

        try {
            HttpHeaders httpHeaders = new HttpHeaders();
            HttpEntity <String> entity = new HttpEntity<String>(httpHeaders);
            
            ResponseEntity<String> responseBinance = restTemplate.exchange(binanceURL,HttpMethod.GET, entity, String.class);
            ResponseEntity<String> responseHoubi= restTemplate.exchange(houbiURL,HttpMethod.GET, entity, String.class);
            
            // Obtain the list of Coins
            List<BinanceCoin> binanceCoinList = objectMapper.readValue(responseBinance.getBody(), new TypeReference<List<BinanceCoin>>(){});
            JsonNode huobiJsonData = objectMapper.readTree(responseHoubi.getBody());
            List<HuobiCoin> huoBiCoinList = objectMapper.readValue(huobiJsonData.get("data").toString(), new TypeReference<List<HuobiCoin>>(){});

            // Filter out that only required coins are allowed
            String[]acceptableSymbol = {"ETHUSDT","BTCUSDT"};
            List<Coin> coinList = new ArrayList<>();
            
            // Compare the coins which is the cheapest
            for(String symbol:acceptableSymbol){
                BinanceCoin selectedBinanceCoin = binanceCoinList.stream()
                .filter(c -> symbol.equalsIgnoreCase(c.getSymbol())).findAny().orElse(null);

                HuobiCoin selectedHuobiCoin = huoBiCoinList.stream()
                .filter(c -> symbol.equalsIgnoreCase(c.getSymbol())).findAny().orElse(null);

                if(selectedBinanceCoin==null || selectedHuobiCoin == null) 
                    throw new Error(String.format("Unable to update the latest price for symbol ", symbol));

                Coin coin = new Coin();
                coin.setSymbol(symbol);
                coin.setAskPrice(Math.max(selectedBinanceCoin.getAskPrice(), selectedHuobiCoin.getAsk()));
                coin.setBidPrice(Math.min(selectedBinanceCoin.getBidPrice(), selectedHuobiCoin.getBid()));
                coin.setLastUpdated(new Date());
                coinList.add(coin);

            }

            // Store into the database
            for(Coin currentCoin:coinList){
               coinRepository.save(currentCoin);
            }

            // Return the latest results
            return coinList;
        } catch (HttpClientErrorException | HttpServerErrorException e) {
            throw new Exception(e.getMessage());
        }

    }
    
    @Override
    public Coin findBySymbol(String symbol) throws Exception{
       Optional<Coin> optionalCoin = coinRepository.findById(symbol);

       if(optionalCoin.isEmpty()) throw new Exception("Coin not supported");

       return optionalCoin.get();
    }

}
