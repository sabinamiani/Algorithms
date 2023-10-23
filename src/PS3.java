/**
 * @title PS3 - CS4150 Algorithms
 * @author Sabina Miani 
 */

import java.util.Scanner;

public class PS3 {
	
	// global variable to contain gallery values 
	static int G[][];
	static int total; 

	/* 
	 * main computational function 
	 */
	public static void main(String[] args) {
		
		Scanner scanner = new Scanner(System.in);
		
		// parse N and k from scanner 
		int N = scanner.nextInt();
		int k = scanner.nextInt();
		
		G = new int[N][2];
		total = 0;
		
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < 2; j++) {
				G[i][j] = scanner.nextInt();
				total += G[i][j];
			}
		}
		
		System.out.println(GalleryValue(N,k));
		
		scanner.close();
		return; 
	}
	
	
	/*
	 *  iterative dynamic programming method for finding the maximum 
	 *  public gallery value with k closed galleries from 2N galleries
	 */
	static int GalleryValue (int N, int K) {
		
		int[][][][] MV = new int[N+1][2][2][K+1]; 
		
		
		for (int i = N; i >= 0; i--) {
			for (int k = 0; k <= K; k++) {
				for (int west = 1; west >= 0; west--) {
					for (int east = 1; east >= 0; east--) {
					
						// base case(s)
						if (k == 0)
							MV[i][west][east][k] = total;
						else if (i == N)
							MV[i][west][east][k] = 0;
						else if (!(west == 1 && east == 1)) {
							int co,ce,cn;
							co = ce = cn = Integer.MIN_VALUE;
							
							if(east == 0)
								co = -G[i][0] + MV[i+1][1][0][k-1];
						
							if(west == 0)
								ce = -G[i][1] + MV[i+1][0][1][k-1];
							
							cn = MV[i+1][0][0][k];
							
							MV[i][west][east][k] = max(co,ce,cn);
						}
					}	
				}
			}
		}
		
		return MV[0][0][0][K];
	}

	/* 
	 * helper method to return the maximum value from three integer 
	 * 	inputs/parameters
	 */
	private static int max(int co, int ce, int cn) {
		return Math.max(Math.max(co, ce), cn);
	}

}
