package cryptodealer.exchanges;

import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import cryptodealer.Candle;

public class PoloniexExchange implements Exchange {

	@Override
	public String name() {

		return "Poloniex";
	}

	@Override
	public List<String> currencies(String baseCurrency) {

		List<String> symbols = new ArrayList<>();

		String str = "https://poloniex.com/public?command=returnCurrencies";

		try {
			URL url = new URL(str);

			InputStreamReader reader = new InputStreamReader(url.openStream());
			ObjectMapper mapper = new ObjectMapper();
			Map<String, Map<Object, Object>> wrappers = mapper.readValue(reader,
					new TypeReference<Map<String, Map<Object, Object>>>() {
					});

			for (String symbol : wrappers.keySet()) {
				Map<Object, Object> values = wrappers.get(symbol);
				int disabled = Integer.parseInt(values.get("disabled").toString());
				int delisted = Integer.parseInt(values.get("delisted").toString());
				int frozen = Integer.parseInt(values.get("frozen").toString());

				if (disabled == 0 && delisted == 0 && frozen == 0) {
					symbols.add(symbol);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return symbols;
	}

	@Override
	public String symbol(String baseCurrency, String currency) {

		return baseCurrency + "_" + currency;
	}

	@Override
	public List<Candle> candles(String symbol, String interval, Integer limit, Long endTime) {

		long endSeconds = System.currentTimeMillis() / 1000;
		long rangeSeconds = 0;
		if(interval.endsWith("m")) {
			interval = interval.replaceAll("\\D+", "");
			// 1m not available on Poloniex
			if(interval.equals("1")) {
				return null;
			}
			rangeSeconds = TimeUnit.MINUTES.toMillis(Long.valueOf(interval));
		} else if(interval.endsWith("h")) {
			interval = interval.replaceAll("\\D+", "");
			// 1h not available on Poloniex
			if(interval.equals("1")) {
				return null;
			}
			rangeSeconds = TimeUnit.HOURS.toMillis(Long.valueOf(interval));
		} else if(interval.endsWith("d")) {
			interval = interval.replaceAll("\\D+", "");
			rangeSeconds = TimeUnit.DAYS.toMillis(Long.valueOf(interval));
		}
		
		long beginSeconds = endSeconds - rangeSeconds * 500 / 1000;

		String str = "https://poloniex.com/public?command=returnChartData&currencyPair=" + symbol + "&start="
				+ beginSeconds + "&period=" + rangeSeconds / 1000;

		try {
			URL url = new URL(str);
			InputStreamReader reader = new InputStreamReader(url.openStream());
			ObjectMapper mapper = new ObjectMapper();
			List<Map<String, Object>> wrappers = mapper.readValue(reader,
					new TypeReference<List<Map<String, Object>>>() {
					});

			List<Candle> candles = new ArrayList<>(wrappers.size());

			for (Map<String, Object> map : wrappers) {
				Candle candle = new Candle();
				candle.openTime = Long.parseLong(map.get("date").toString()) * 1000;
				candle.closeTime = Long.parseLong(map.get("date").toString()) * 1000 + rangeSeconds - 1;
				candle.high = new BigDecimal(map.get("high").toString());
				candle.low = new BigDecimal(map.get("low").toString());
				candle.open = new BigDecimal(map.get("open").toString());
				candle.close = new BigDecimal(map.get("close").toString());
				candles.add(candle);
			}
			return candles;
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}

		return Collections.emptyList();
	}
}
