package cryptodealer;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import cryptodealer.conditions.EMAUpCrossoverCondition;
import cryptodealer.exchanges.Exchange;
import cryptodealer.mappers.CandleToEMAMapper;
import cryptodealer.mappers.Pair;

public class Suggestor {

	public static List<Suggestion> loadSuggestions(Exchange exchange) {

		List<Suggestion> suggestions = new ArrayList<>();
		loadSuggestions(exchange, Interval.HOUR_1, suggestions);

		return suggestions;
	}

	private static void loadSuggestions(Exchange exchange, Interval interval, List<Suggestion> suggestions) {

		for (String currency : exchange.currencies()) {
			String symbol = exchange.symbol(currency);

			List<Candle> candles = exchange.candles(symbol, interval, 50, null);
			List<Pair<Double, Double>> emas = new CandleToEMAMapper().mapAll(candles);

			EMAUpCrossoverCondition crossoverCondition = new EMAUpCrossoverCondition(emas);

			boolean crossover = crossoverCondition.matches();

			if (crossover) {
				Candle candle = candles.get(candles.size() - 1);
				String reason = interval.getId() + " candle EMA crossover";
				BigDecimal price = candle.close;

				Suggestion suggestion = new Suggestion(exchange.name(), currency, price.toPlainString(),
						String.valueOf(candle.openTime), String.valueOf(candle.closeTime), interval.getId(), reason);

				suggestions.add(suggestion);
			}
		}
	}
}
