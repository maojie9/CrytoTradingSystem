package com.maojie.trading;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RestController;

import com.maojie.trading.model.Wallet;
import com.maojie.trading.repository.WalletRepository;
import com.maojie.trading.service.CoinService;
import com.maojie.trading.service.WalletService;

@SpringBootApplication
@EnableScheduling
@RestController
public class CryptoTradingApplication {

	@Autowired
    CoinService coinService;
	
	@Autowired
	WalletService walletService;

	@Autowired
	WalletRepository walletRepository;

	public static void main(String[] args) {
		SpringApplication.run(CryptoTradingApplication.class, args);
	}

	// ASSIGNMENT TASK 1
	@Scheduled(fixedRate = 10000)
    public void updateCoinPrice() {

		Wallet generateWallet = walletService.getUserWallet();

		if(generateWallet == null){
			generateWallet = new Wallet();
			generateWallet.setBalance(BigDecimal.valueOf(50000.00)); // Initial balance of 50,000 USDT
			walletRepository.save(generateWallet);
		}


		try {
			coinService.updateLatestCoinPriceFromSource();
			System.out.println("Coins Updated");
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		
    }

}
