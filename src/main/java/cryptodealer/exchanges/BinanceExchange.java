package cryptodealer.exchanges;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import com.binance.api.client.BinanceApiClientFactory;
import com.binance.api.client.BinanceApiRestClient;
import com.binance.api.client.domain.market.Candlestick;
import com.binance.api.client.domain.market.CandlestickInterval;

import cryptodealer.Candle;
import cryptodealer.Interval;

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
	public List<String> currencies() {

		return this.client.getExchangeInfo().getSymbols().stream().map((s) -> s.getSymbol())
				.filter((s) -> s.endsWith("BTC")).map((s) -> s.substring(0, s.length() - 3))
				.collect(Collectors.toList());
	}

	@Override
	public String symbol(String currency) {

		return currency + "BTC";
	}

	@Override
	public List<Candle> candles(String symbol, Interval interval, Integer limit, Long endTime) {

		CandlestickInterval intervalImpl = null;

		if (Interval.MINUTES_5 == interval) {
			intervalImpl = CandlestickInterval.FIVE_MINUTES;
		} else if (Interval.HOUR_1 == interval) {
			intervalImpl = CandlestickInterval.HOURLY;
		} else if (Interval.DAY_1 == interval) {
			intervalImpl = CandlestickInterval.DAILY;
		}

		List<Candlestick> candlesticks = this.client.getCandlestickBars(symbol, intervalImpl, limit, null, endTime);

		return candlesticks.stream().map(this::convert).collect(Collectors.toList());
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
