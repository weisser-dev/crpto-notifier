package cryptodealer.exchanges;

import java.util.List;

import cryptodealer.Candle;

public interface Exchange {

	String name();

	List<String> currencies(String base);
	

	List<Candle> candles(String symbol, String interval, Integer limit, Long endTime);

	String symbol(String baseCurrency, String currency);
}
