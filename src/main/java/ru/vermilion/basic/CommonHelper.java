package ru.vermilion.basic;

import java.util.concurrent.TimeUnit;

public class CommonHelper {
	
	/**
	 * a <= number <= b 
	 *  
	 * @return bounded number;
	 */
	public static int minmax(int a, int number, int b) {
		return Math.max(a, Math.min(number, b)); 
	}


	public static String getElapsedTime(long startTime) {
		long elapsedTime = System.currentTimeMillis() - startTime;

		final long d = TimeUnit.MILLISECONDS.toDays(elapsedTime);
		final long hr = TimeUnit.MILLISECONDS.toHours(elapsedTime - TimeUnit.DAYS.toMillis(d));
		final long min = TimeUnit.MILLISECONDS.toMinutes(elapsedTime - TimeUnit.DAYS.toMillis(d) - TimeUnit.HOURS.toMillis(hr));
		final long sec = TimeUnit.MILLISECONDS.toSeconds(elapsedTime - TimeUnit.DAYS.toMillis(d) - TimeUnit.HOURS.toMillis(hr) - TimeUnit.MINUTES.toMillis(min));
		final long ms = TimeUnit.MILLISECONDS.toMillis(elapsedTime - TimeUnit.DAYS.toMillis(d) - TimeUnit.HOURS.toMillis(hr) - TimeUnit.MINUTES.toMillis(min) - TimeUnit.SECONDS.toMillis(sec));

		return String.format("%d %02d:%02d:%02d", d, hr, min, sec, ms);
	}

}
