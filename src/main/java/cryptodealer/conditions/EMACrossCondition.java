package cryptodealer.conditions;

import java.util.List;

import cryptodealer.mappers.Pair;

public class EMACrossCondition implements Condition {

	private List<Pair<Double, Double>> emas;

	public EMACrossCondition(List<Pair<Double, Double>> emas) {

		this.emas = emas;
	}

	@Override
	public boolean matches() {

		if (this.emas.size() - 3 < 0) {
			return false;
		}

		Pair<Double, Double> current = this.emas.get(this.emas.size() - 2);
		Pair<Double, Double> previous = this.emas.get(this.emas.size() - 3);

		return previous.first >= previous.second && current.first <= current.second;
	}

	public boolean matchesEMA(String direction, String emaCandle) {
		Pair<Double, Double> current = new Pair<>();
		Pair<Double, Double> previous = new Pair<>();
		
		
		if(emaCandle.equals("current")) {
			if (this.emas.size() - 2 < 0) {
				return false;
			}
			current = this.emas.get(this.emas.size() - 1);
			previous = this.emas.get(this.emas.size() - 2);
		} else if(emaCandle.equals("last")) {
			if (this.emas.size() - 3 < 0) {
				return false;
			}
			current = this.emas.get(this.emas.size() - 2);
			previous = this.emas.get(this.emas.size() - 3);
		}
		
		if(direction.equals("UP")) {
			return previous.first <= previous.second && current.first >= current.second;
		} else if(direction.equals("DOWN")) {
			return previous.first >= previous.second && current.first <= current.second;
		}
		return false;
	}
}
