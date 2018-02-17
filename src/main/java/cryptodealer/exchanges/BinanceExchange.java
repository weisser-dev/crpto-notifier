package cryptodealer.exchanges;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import com.binance.api.client.BinanceApiClientFactory;
import com.binance.api.client.BinanceApiRestClient;
import com.binance.api.client.domain.market.Candlestick;
import com.binance.api.client.domain.market.CandlestickInterval;

import cryptodealer.Candle;
import cryptodealer.Currency;

public class BinanceExchange implements Exchange {

	private BinanceApiRestClient client;

	public BinanceExchange() {

		BinanceApiClientFactory factory = BinanceApiClientFactory.newInstance();

		this.client = factory.newRestClient();
	}

	@Override
	public String name() {

		return "Binance";
	}

	@Override
	public List<String> currencies(String baseCurrency) {

		return this.client.getExchangeInfo().getSymbols().stream().map((s) -> s.getSymbol())
				.filter((s) -> s.endsWith(baseCurrency)).map((s) -> s.substring(0, s.length() - baseCurrency.length()))
				.collect(Collectors.toList());
	}

	@Override
	public String symbol(String baseCurrency, String currency) {
		
		return currency + baseCurrency;
	}

	@Override
	public List<Candle> candles(String symbol, String interval, Integer limit, Long endTime) {
		for (CandlestickInterval candlestickInterval : CandlestickInterval.values()) {
			if(candlestickInterval.getIntervalId().equals(interval)) {
				List<Candlestick> candlesticks = this.client.getCandlestickBars(symbol, candlestickInterval, limit, null, endTime);
				return candlesticks.stream().map(this::convert).collect(Collectors.toList());
			}
		}
		return null;
	}

	private Candle convert(Candlestick candlestick) {

		Candle candle = new Candle();

		candle.openTime = candlestick.getOpenTime();
		candle.closeTime = candlestick.getCloseTime();
		candle.open = new BigDecimal(candlestick.getOpen());
		candle.high = new BigDecimal(candlestick.getHigh());
		candle.low = new BigDecimal(candlestick.getLow());
		candle.close = new BigDecimal(candlestick.getClose());

		return candle;
	}
}
