package de.tekup.trading;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;



import com.binance.api.client.BinanceApiClientFactory;
import com.binance.api.client.BinanceApiRestClient;

@Configuration
public class Config {
	

	@Bean
	public BinanceApiRestClient binanceApiRestClient() {
	return BinanceApiClientFactory.newInstance().newRestClient();
	

	}
}
