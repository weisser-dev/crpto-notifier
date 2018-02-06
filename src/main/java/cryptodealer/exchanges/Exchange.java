package cryptodealer.exchanges;

import java.util.List;

import cryptodealer.Candle;
import cryptodealer.Interval;

public interface Exchange {

	String name();

	List<String> currencies();

	String symbol(String currency);

	List<Candle> candles(String symbol, Interval interval, Integer limit, Long endTime);
}
