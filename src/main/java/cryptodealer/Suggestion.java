package cryptodealer;

public class Suggestion {

	public String exchange;
	public String currency;
	public String price;
	public String openTime;
	public String closeTime;
	public String candlesUnit;
	public String reason;

	public Suggestion(String exchange, String currency, String price, String openTime, String closeTime,
			String candlesUnit, String reason) {

		this.exchange = exchange;
		this.currency = currency;
		this.price = price;
		this.openTime = openTime;
		this.closeTime = closeTime;
		this.candlesUnit = candlesUnit;
		this.reason = reason;
	}

	@Override
	public String toString() {
		return "Suggestion [exchange=" + exchange + ", currency=" + currency + ", price=" + price + ", openTime="
				+ openTime + ", closeTime=" + closeTime + ", candlesUnit=" + candlesUnit + ", reason=" + reason + "]";
	}
}
