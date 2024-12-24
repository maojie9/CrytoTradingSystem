package com.maojie.trading;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RestController;

import com.maojie.trading.service.CoinService;

@SpringBootApplication
@EnableScheduling
@RestController
public class CryptoTradingApplication {

	@Autowired
    CoinService coinService;

	public static void main(String[] args) {
		SpringApplication.run(CryptoTradingApplication.class, args);
	}

	@Scheduled(fixedRate = 10000)
    public void greeting() {
		try {
			coinService.updateLatestCoinPriceFromSource();
			System.out.println("Coins Updated");
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		
    }

}
