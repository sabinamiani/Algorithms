/**
 * @title PS1 - CS4150 Algorithms
 * @author Sabina Miani 
 */

/** PS1 Programming 
 ** This is a recursion design functioning to identify the 
 ** smallest number of kayak teams that will not be able to compete.
 **
 ** Sabina Miani 
 ** Fall 2021
 **/

import java.util.Scanner;

public class PS1 {
	
	/// Global variable declarations ///
	private static int n;
	private static Boolean Damaged[];
	private static Boolean Reserve[];
	
	private static Boolean hasDamaged;
	private static Boolean hasReserve;

	/* main operational function for the kayak race problem 
	 * 
	 */
	public static void main(String[] args) {
		
		Scanner scanner = new Scanner(System.in);
		
		// number of teams 
		n = scanner.nextInt();
		
		// define arrays of size n
		Damaged = new Boolean[n];
		Reserve = new Boolean[n];
		
		// populate the arrays 
		for (int k = 0; k < n; k++) {
			Damaged[k] = scanner.nextInt() == 1;
		}
		for (int k = 0; k < n; k++) {
			Reserve[k] = scanner.nextInt() == 1;
		}
		
		// define past values 
		hasDamaged = false; 
		hasReserve = false; 
		
		// print the number of non-starter kayaks
		System.out.println(kayakTroubles(0));
		
		scanner.close();
		return; 
	}
	
	/* Recursive method to find the minimum number of 
	 * 	kayak teams that will not be able to start the race 
	 * Params: 
	 * 		int index
	 * Return:
	 * 		int recursive count of non-starting teams
	 */
	private static int kayakTroubles (int index) {
		
		if (index == n)
			return 0;
		
		if (Reserve[index] && !Damaged[index] && hasDamaged) {
			updatePast(false,false);
			return (kayakTroubles(index + 1) - 1);
		}
		else if (Damaged[index] && !Reserve[index] && !hasReserve) {
			updatePast(true,false);
			return (kayakTroubles(index + 1) + 1);
		}
		else {
			updatePast(Damaged[index],Reserve[index]);
			return kayakTroubles(index + 1);
		}
	}
	
	/* Helper function to update the past boolean values 
	 * Params: 
	 * 		boolean pastDamaged
	 * 		boolean pastReserve 
	 */
	private static void updatePast(Boolean pastDamaged, Boolean pastReserve) {
		if((pastDamaged && pastReserve) || (pastReserve && hasDamaged) || (pastDamaged && hasReserve)) {
			hasDamaged = false;
			hasReserve = false;
		}
		else {
			hasDamaged = pastDamaged;
			hasReserve = pastReserve; 
		}
		return; 
	}

}
