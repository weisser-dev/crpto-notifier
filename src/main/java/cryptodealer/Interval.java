package cryptodealer;

import java.util.concurrent.TimeUnit;

public enum Interval {

	MINUTES_5("5m", TimeUnit.MINUTES.toMillis(5)), //
	HOUR_1("1h", TimeUnit.HOURS.toMillis(1)), //
	DAY_1("1d", TimeUnit.DAYS.toMillis(1));

	private String id;
	private long millis;

	private Interval(String id, long millis) {

		this.id = id;
		this.millis = millis;
	}

	public String getId() {

		return this.id;
	}

	public long getMillis() {

		return this.millis;
	}

	public static Interval fromID(String id) {

		for (Interval interval : Interval.values()) {
			if (interval.id.equals(id)) {
				return interval;
			}
		}

		return null;
	}
}