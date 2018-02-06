package cryptodealer.indicators;

public class EMA {

	private double k;

	private double previous;

	public EMA(double n) {

		this.k = 2d / (n + 1d);
	}

	public double calculate(double value) {

		if (previous == 0) {
			previous = value;
			return previous;
		}

		return previous = (value * k) + (previous * (1d - k));
	}

}