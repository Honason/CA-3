package services;

import java.util.Calendar;
import java.util.Date;

public class Util {

	public static long getDate(boolean withTime) {
		Calendar c = Calendar.getInstance();
		if(!withTime) {
			c.set(Calendar.HOUR_OF_DAY, 0);
			c.set(Calendar.MINUTE, 0);
			c.set(Calendar.SECOND, 0);
			c.set(Calendar.MILLISECOND, 0);
		}
		return c.getTimeInMillis();
	}
	public static long getDate() {return getDate(false); }

}