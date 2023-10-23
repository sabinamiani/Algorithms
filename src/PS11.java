import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

/**
 * @title PS11 - CS4150 Algorithms - TSP Hamiltonian cycles 
 * @author Sabina Miani 
 * @version 9 December 2021
 */

public class PS11 {

	static int[][] weights;
	static int N;
	static HashMap<ArrayList<Integer>,Integer> hamcycle;
	
	public static void main(String[] args) {
		
		Scanner scanner = new Scanner(System.in);
		
		// parse n from scanner  
		N = scanner.nextInt(); 
		weights = new int[N][N];
		ArrayList<Integer> others = new ArrayList<Integer>();
		hamcycle = new HashMap<ArrayList<Integer>,Integer>();
		
		// adjacency matrix input 
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				weights[i][j] = scanner.nextInt();
			}
			if (i != 0)
				others.add(i);
		}
		
		System.out.println(tsp(0, others));
		
		scanner.close();
		return; 

	}

	private static int tsp(int start, ArrayList<Integer> others) {
		
		int min = Integer.MAX_VALUE;
		
		// base case -- others is empty, get last vert to start 
		//  vert edge and return weight
		if (others.isEmpty()) {
			return weights[start][0];
		}
		
		for (int next : others) {
			ArrayList<Integer> v2 = new ArrayList<Integer>();
			for (int old : others)
				v2.add(old); 
			v2.remove((Integer)next);
			v2.add(0,next);
			int cost; 
			if(hamcycle.containsKey(v2))
				cost = weights[start][next] + hamcycle.get(v2);
			else {
				v2.remove((Integer)next);
				int k = tsp(next, v2);
				v2.add(0,next);
				hamcycle.put(v2,k);
				cost = weights[start][next] + k;
			}
			min = Math.min(min, cost);
		}
		
		
		return min;
	}
	
}
