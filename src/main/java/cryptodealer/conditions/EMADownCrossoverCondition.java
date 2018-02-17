package cryptodealer.conditions;

import java.util.List;

import cryptodealer.mappers.Pair;

public class EMADownCrossoverCondition implements Condition {

	private List<Pair<Double, Double>> emas;

	public EMADownCrossoverCondition(List<Pair<Double, Double>> emas) {

		this.emas = emas;
	}

	@Override
	public boolean matches() {

		if (this.emas.size() - 2 < 0) {
			return false;
		}

		Pair<Double, Double> current = this.emas.get(this.emas.size() - 1);
		Pair<Double, Double> previous = this.emas.get(this.emas.size() - 2);

		return previous.first >= previous.second && current.first < current.second;
	}
}
