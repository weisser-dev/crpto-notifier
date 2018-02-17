package cryptodealer;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import cryptodealer.conditions.EMADownCrossoverCondition;
import cryptodealer.conditions.EMAUpCrossoverCondition;
import cryptodealer.exchanges.Exchange;
import cryptodealer.mappers.CandleToEMAMapper;
import cryptodealer.mappers.Pair;

public class Suggestor {

	private static String DEFAULT_INTERVAL = "30m";
	
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
			
						List<Candle> candles = exchange.candles(symbol, interval, 50, null);
						if(null != candles && candles.size() > 0) {
							List<Pair<Double, Double>> emas = new CandleToEMAMapper().mapAll(candles);
							EMAUpCrossoverCondition crossoverUpCondition = new EMAUpCrossoverCondition(emas);
							EMADownCrossoverCondition crossoverDownCondition = new EMADownCrossoverCondition(emas);
							
							boolean crossoverUp = crossoverUpCondition.matches();
							boolean crossoverDown = crossoverDownCondition.matches();
							
							Candle candle = candles.get(candles.size() - 1);
							
							BigDecimal price = candle.close;
							if(crossoverDown) {
								String emaCrossover = "DOWN";
								Suggestion suggestion = new Suggestion(exchange.name(), currency, baseCurrency.getId(), price.toPlainString(),
										String.valueOf(candle.openTime), String.valueOf(candle.closeTime), interval, emaCrossover);
								suggestions.add(suggestion);
							} else if (crossoverUp) {
								String emaCrossover = "UP";
								Suggestion suggestion = new Suggestion(exchange.name(), currency, baseCurrency.getId(), price.toPlainString(),
										String.valueOf(candle.openTime), String.valueOf(candle.closeTime), interval, emaCrossover);
								suggestions.add(suggestion);
							}
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
