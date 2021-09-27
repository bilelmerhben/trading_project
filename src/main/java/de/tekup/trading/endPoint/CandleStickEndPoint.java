package de.tekup.trading.endPoint;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.binance.api.client.domain.market.CandlestickInterval;

import de.tekup.trading.services.CandleStickService;


@RestController
public class CandleStickEndPoint {
	@Autowired
	private CandleStickService cservice;
	
	
@GetMapping("/")
	public HashMap<String, Integer> filerTest(Model model) {
	

		cservice.filterPatternAfterTestWithCouple("ETCUSDT", CandlestickInterval.DAILY,450);
		
		cservice.alertUser();
	
		return cservice.filterPatternAfterTestWithCouple("ETCUSDT", CandlestickInterval.DAILY,450);
	}
}
