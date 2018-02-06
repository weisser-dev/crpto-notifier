package cryptodealer;

public class Suggestion {

	public String exchange;
	public String currency;
	private String candlesUnit;
	public int idleCandles;
	public String reason;

	public Suggestion(String exchange, String currency, String candlesUnit, int idleCandles, String reason) {

		this.exchange = exchange;
		this.currency = currency;
		this.candlesUnit = candlesUnit;
		this.idleCandles = idleCandles;
		this.reason = reason;
	}

	@Override
	public String toString() {

		return "Suggestion [exchange=" + this.exchange + ", currency=" + this.currency + ", candlesUnit="
				+ this.candlesUnit + ", idleCandles=" + this.idleCandles + ", reason=" + this.reason + "]";
	}
}
