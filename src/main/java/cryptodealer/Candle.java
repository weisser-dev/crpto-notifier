package cryptodealer;

import java.math.BigDecimal;
import java.util.Date;

public class Candle {

	public long openTime;
	public long closeTime;

	public BigDecimal open;
	public BigDecimal high;
	public BigDecimal low;
	public BigDecimal close;

	@Override
	public String toString() {
		return "Candle [openTime=" + new Date(openTime) + ", closeTime=" + new Date(closeTime) + ", open=" + open
				+ ", high=" + high + ", low=" + low + ", close=" + close + "]";
	}
}
