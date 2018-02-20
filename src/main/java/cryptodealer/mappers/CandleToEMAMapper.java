package cryptodealer.mappers;

import cryptodealer.Candle;
import cryptodealer.indicators.EMA;

public class CandleToEMAMapper implements Mapper<Candle, Pair<Double, Double>> {

	private EMA emaShort;
	private EMA emaLong;

	public CandleToEMAMapper() {

		this.emaShort = new EMA(3);
		this.emaLong = new EMA(15);
	}

	@Override
	public Pair<Double, Double> map(Candle input) {

		Pair<Double, Double> pair = new Pair<>();
		pair.first = emaShort.calculate(input.close.doubleValue());
		pair.second = emaLong.calculate(input.close.doubleValue());

		return pair;
	}

}
