package cryptodealer;

public class Suggestion {

	public String exchange;
	public String currency;
	public String baseCurrency;
	public String price;
	public String openTime;
	public String closeTime;
	public String candlesUnit;
	public String emaCrossover;

	public Suggestion(String exchange, String currency, String baseCurrency, String price, String openTime, String closeTime,
			String candlesUnit, String emaCrossover) {
		this.exchange = exchange;
		this.currency = currency;
		this.baseCurrency = baseCurrency;
		this.price = price;
		this.openTime = openTime;
		this.closeTime = closeTime;
		this.candlesUnit = candlesUnit;
		this.emaCrossover = emaCrossover;
	}

	@Override
	public String toString() {
		return "Suggestion [exchange=" + exchange + ", currency=" + currency + ", baseCurrency=" + baseCurrency + ", price=" + price + ", openTime="
				+ openTime + ", closeTime=" + closeTime + ", candlesUnit=" + candlesUnit + ", emaCrossover=" + emaCrossover + "]";
	}
}
