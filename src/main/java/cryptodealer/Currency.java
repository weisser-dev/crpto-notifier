package cryptodealer;

public enum Currency {

	
	BTC("BTC"),
	USDT("USDT");
	
	private String id;
	
	private Currency(String id) {
		this.id = id;
	}
	
	public String getId() {
		return this.id;
	}
}
