
/**
*  Filename: 	HitRate.java
*  Written by:	William Loring
*  Written on:	12-12-2010
*  Calculate and return a formatted string with the hit rate percentage of Wins/Games
*/

import java.text.NumberFormat;

public class HitRate {

	// Create a percent number format, converts number to string
	private static final NumberFormat nf = NumberFormat.getPercentInstance();

	public static String Calculate(int Wins, int Games) {
		// Create variable to hold HitRate result
		double dblPercent;

		// Convert integers to doubles to get the correct answer
		dblPercent = ((double) Wins) / ((double) Games);

		return nf.format(dblPercent);
	}
}