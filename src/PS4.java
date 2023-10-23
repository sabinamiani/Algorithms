/**
 * @title PS4 - CS4150 Algorithms
 * @author Sabina Miani 
 * @version 13 October 2021
 */

import java.util.Arrays;
import java.util.Scanner;

public class PS4 {
	
	// global variables to contain plot and house diameters/diagonals
	static double P[], H[];

	/* 
	 * main computational function 
	 */
	public static void main(String[] args) {
		
		Scanner scanner = new Scanner(System.in);
		
		// parse N,M,K lengths from scanner  
		int n = scanner.nextInt();
		int m = scanner.nextInt();
		int k = scanner.nextInt();
		
		// initialize plot and house arrays
		P = new double[n];   // plots
		H = new double[m+k]; // houses
		
		// populate arrays from scanner
		for (int i = 0; i < n; i++) {
			P[i] = scanner.nextInt();
		}
		for (int i = 0; i < m; i++) {
			H[i] = scanner.nextInt();
		}
		for (int i = m; i < m+k; i++) {
			H[i] = scanner.nextInt() / Math.sqrt(2); 
		}
		
		// sort arrays from smallest to largest diameters/diagonals 
		Arrays.sort(P);
		Arrays.sort(H);
		
		System.out.println(GreedyHouses());
		
		scanner.close();
		return; 
	}
	
	/* 
	 * Greedy Algorithm solving for maximum number of houses on 
	 * set of plots. Houses may not touch plot outlines. 
	 * Using sorted global plot and house arrays.  
	 */
	private static int GreedyHouses() {
		
		// initialize count and index variables 
		int i = 0, j = 0, count = 0;
		
		while (i < P.length && j < H.length) {
			// when the plot is greater than the house, increment 
			//  count, house, and plot indices
			//  otherwise, only increment plot index 
			if (P[i] > H[j]) {
				count++;
				j++;
				i++;
			}
			else 
				i++;
		}
		
		return count;
	}

}
