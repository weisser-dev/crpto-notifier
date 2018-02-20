package cryptodealer;

public class Suggestion {

	public String exchange;
	public String currency;
	public String baseCurrency;
	public String price;
	public String openTime;
	public String closeTime;
	public String candlesUnit;
	public String emaDirection;
	public String emaTime;

	public Suggestion(String exchange, String currency, String baseCurrency, String price, String openTime, String closeTime,
			String candlesUnit, String emaDirection, String emaTime) {
		this.exchange = exchange;
		this.currency = currency;
		this.baseCurrency = baseCurrency;
		this.price = price;
		this.openTime = openTime;
		this.closeTime = closeTime;
		this.candlesUnit = candlesUnit;
		this.emaDirection = emaDirection;
		this.emaTime = emaTime;
	}

	@Override
	public String toString() {
		return "Suggestion [exchange=" + exchange + ", currency=" + currency + ", baseCurrency=" + baseCurrency + ", price=" + price + ", openTime="
				+ openTime + ", closeTime=" + closeTime + ", candlesUnit=" + candlesUnit + ", emaDirection=" + emaDirection + ", emaTime=" + emaTime + "]";
	}
	
	public void setEmaTime(String emaTime) {
		this.emaTime = emaTime;
	}
	
	public void setEmaDirection(String emaDirection) {
		this.emaDirection = emaDirection;
	}
}
