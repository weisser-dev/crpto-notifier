package cryptodealer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import cryptodealer.conditions.EMAIdledBelowCondition;
import cryptodealer.conditions.EMAUpCrossoverCondition;
import cryptodealer.exchanges.Exchange;
import cryptodealer.mappers.CandleToEMAMapper;
import cryptodealer.mappers.Pair;

public class Suggestor {

	public static List<Suggestion> loadSuggestions(Exchange exchange) {

		List<Suggestion> suggestions = new ArrayList<>();
		loadSuggestions(exchange, Interval.MINUTES_15, suggestions);

		Collections.sort(suggestions, new Comparator<Suggestion>() {

			@Override
			public int compare(Suggestion o1, Suggestion o2) {

				return o1.idleCandles - o2.idleCandles;
			}
		});

		return suggestions;
	}

	private static void loadSuggestions(Exchange exchange, Interval interval, List<Suggestion> suggestions) {

		final int IDLE_BARS = 5;

		for (String currency : exchange.currencies()) {
			String symbol = exchange.symbol(currency);

			List<Candle> candles = exchange.candles(symbol, interval, 50, null);
			List<Pair<Double, Double>> emas = new CandleToEMAMapper().mapAll(candles);

			EMAUpCrossoverCondition crossoverCondition = new EMAUpCrossoverCondition(emas);
			EMAIdledBelowCondition belowCondition = new EMAIdledBelowCondition(emas, IDLE_BARS);

			boolean crossover = crossoverCondition.matches();
			boolean idled = belowCondition.matches();

			if (crossover && idled) {
				int idleCandles = belowCondition.getCount();

				String reason = interval.getId() + " candle EMA crossover and came from " + idleCandles
						+ " negative bars";

				Suggestion suggestion = new Suggestion(exchange.name(), symbol, interval.getId(), idleCandles, reason);

				suggestions.add(suggestion);
			}
		}
	}
}
