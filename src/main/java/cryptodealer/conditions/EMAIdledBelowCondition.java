package cryptodealer.conditions;

import java.util.List;

import cryptodealer.mappers.Pair;

public class EMAIdledBelowCondition implements Condition {

	private List<Pair<Double, Double>> emas;

	private int n;
	private int count;

	public EMAIdledBelowCondition(List<Pair<Double, Double>> emas, int n) {

		this.emas = emas;
		this.n = n;
	}

	@Override
	public boolean matches() {

		for (int i = emas.size() - 2; i >= 0; --i) {
			Pair<Double, Double> ema = emas.get(i);

			if (ema.first >= ema.second) {
				break;
			}

			++this.count;
		}

		return this.count >= n;
	}

	public int getCount() {

		return this.count;
	}
}
