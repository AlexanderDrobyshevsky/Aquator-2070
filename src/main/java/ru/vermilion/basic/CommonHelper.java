package ru.vermilion.basic;

public class CommonHelper {
	
	/**
	 * a <= number <= b 
	 *  
	 * @return bounded number;
	 */
	public static int minmax(int a, int number, int b) {
		return Math.max(a, Math.min(number, b)); 
	}

}
