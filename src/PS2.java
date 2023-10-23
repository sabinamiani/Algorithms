/**
 * @title PS2 - CS4150 Algorithms
 * @author Sabina Miani 
 */

import java.util.Scanner;

public class PS2 {

	/*
	 * main computational function 
	 */
	public static void main(String[] args) {

		Scanner scanner = new Scanner(System.in);
		
		// parse a, b, and m from scanner 
		int a = scanner.nextInt();
		int b = scanner.nextInt();
		int m = scanner.nextInt();
		
		int R = 0;
		
		// solve for R based on x or y being 1
		// R=ax+by
		if (a > b)
			R = a*(m-1) + b;
		else
			R = a + b*(m-1);
		
		System.out.println(R);
		
		scanner.close();
		return;

	}

}
