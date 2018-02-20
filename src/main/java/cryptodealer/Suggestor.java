package cryptodealer;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.apache.commons.lang3.StringUtils;

import cryptodealer.conditions.EMACrossCondition;
import cryptodealer.exchanges.Exchange;
import cryptodealer.mappers.CandleToEMAMapper;
import cryptodealer.mappers.Pair;

public class Suggestor {

	private static String DEFAULT_INTERVAL = "30m";
	
	private final static Logger LOGGER = Logger.getLogger(Suggestor.class.getName());
	
	
	public static List<Suggestion> loadSuggestions(Exchange exchange) {

		List<Suggestion> suggestions = new ArrayList<>();
		loadSuggestions(exchange, DEFAULT_INTERVAL, suggestions);

		return suggestions;
	}
	
	public static List<Suggestion> loadSuggestions(Exchange exchange, String interval) {

		List<Suggestion> suggestions = new ArrayList<>();
		if(StringUtils.isNotBlank(interval))  {
			loadSuggestions(exchange, interval, suggestions);
		} else {
			loadSuggestions(exchange, DEFAULT_INTERVAL, suggestions);
		}
		
		

		return suggestions;
	}

	private static void loadSuggestions(Exchange exchange, String interval, List<Suggestion> suggestions) {
		List<Thread> threads = new ArrayList<>();
		for(Currency baseCurrency : Currency.values()) {
			for (String currency : exchange.currencies(baseCurrency.getId())) {
				Runnable run = new Runnable() {
					@Override
					public void run() {
						String symbol = exchange.symbol(baseCurrency.getId(), currency);
			
						List<Candle> candles = exchange.candles(symbol, interval, 41, null);
						if(null != candles && candles.size() > 0) {
							List<Pair<Double, Double>> emas = new CandleToEMAMapper().mapAll(candles);
							EMACrossCondition emaCondition = new EMACrossCondition(emas);
							
							boolean currentCrossover = emaCondition.matchesEMA("UP", "current");
							boolean lastCrossover = emaCondition.matchesEMA("UP", "last");
							
							boolean currentCrossdown = emaCondition.matchesEMA("DOWN", "current");
							boolean lastCrossdown = emaCondition.matchesEMA("DOWN", "last");
							
							Candle candle = candles.get(candles.size() - 1);
							
							BigDecimal price = candle.close;
							Suggestion suggestion = new Suggestion(exchange.name(), currency, baseCurrency.getId(), price.toPlainString(),
									String.valueOf(candle.openTime), String.valueOf(candle.closeTime), interval, "", "");
							
							if(currentCrossover) {
								suggestion.setEmaTime("current");
								suggestion.setEmaDirection("UP");
								suggestions.add(suggestion);
							} else if (currentCrossdown) {
								suggestion.setEmaTime("current");
								suggestion.setEmaDirection("DOWN");
								suggestions.add(suggestion);
							}
							
							if(lastCrossover) {
								suggestion.setEmaTime("last");
								suggestion.setEmaDirection("UP");
								suggestions.add(suggestion);
							} else if(lastCrossdown) {
								suggestion.setEmaTime("last");
								suggestion.setEmaDirection("DOWN");
								suggestions.add(suggestion);								
							}
							LOGGER.info(suggestion.toString());

						}
					}
				};
			Thread temp = new Thread(run);
			threads.add(temp);
			}
		}
		
		ThreadHandler.startAndWaitForThreads(threads);
	}
}
