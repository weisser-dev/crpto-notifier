package cryptodealer;

import java.util.ArrayList;
import java.util.List;

import cryptodealer.exchanges.BinanceExchange;
import cryptodealer.exchanges.Exchange;
import cryptodealer.exchanges.PoloniexExchange;

public class ExchangeFactory {

	public static List<Exchange> ensureExchanges(String name) {

		List<Exchange> exchanges = new ArrayList<>();

		if ("poloniex".equalsIgnoreCase(name)) {
			exchanges.add(new PoloniexExchange());
		} else if ("binance".equalsIgnoreCase(name)) {
			exchanges.add(new BinanceExchange());
		}

		if (exchanges.isEmpty()) {
			exchanges.add(new BinanceExchange());
			exchanges.add(new PoloniexExchange());
		}

		return exchanges;
	}
}
