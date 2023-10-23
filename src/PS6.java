import java.util.Scanner;

/**
 * @title PS6 - CS4150 Algorithms 
 * @author Sabina Miani 
 * @version 2 November 2021
 */

public class PS6 {
	
	// global variable for the graph
	private static int[][] Recipe;
	private static Boolean[] marked;
	private static long[] shoppingList;
	private static int N;
	private static int M;

	public static void main(String[] args) {
		
		Scanner scanner = new Scanner(System.in);
		
		// parse N,M from scanner  
		N = scanner.nextInt();  
		M = scanner.nextInt();  
		
		int[] wanted = new int[N];  // wanted quantities array
		long[] Product = new long[N];  // output array 
		
		Recipe = new int[N][N]; 
		marked = new Boolean[N];
		shoppingList = new long[N]; 
		
		// initialize all weights in array to zero aka no connections  
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				Recipe[i][j] = 0;
			}
		}
		
		// read and save second input line 
		// initialize marked array to false and product array to 0 
		for (int i = 0; i < N; i++) {
			wanted[i] = scanner.nextInt();
			marked[i] = false;
			Product[i] = (long) 0;
			shoppingList[i] = (long) 0;
		}
		
		// read and save ingredient edges and weights 
		for (int j = 0; j < M; j++) {
			Recipe[scanner.nextInt()][scanner.nextInt()] = scanner.nextInt();
		}
		
		
		// for each wanted ingredient, run topological DFS 
		for (int i = 0; i < N; i++) {
			shoppingList[i] = wanted[i];
			if (wanted[i] != 0) {
				PostProcessDag();
				for (int j = 0; j < N; j++)
					Product[j] += shoppingList[j];
			}
			for (int j = 0; j < N; j++)
				shoppingList[j] = 0;
		}
		
		// print the product array as spaced separated numbers
		for (int i = 0; i < N; i++) {
			System.out.print(Product[i] + " ");
		}
		
		scanner.close();
		return; 
	}

	
	private static void PostProcessDag() {
//		for all vertices v
		for (int i = 0; i < N; i++) {
//			unmark v
			marked[i] = false;
		}
//		for all vertices v
		for (int i = 0; i < N; i++) {
//			if v is unmarked
			if (!marked[i]) {
				PostProcessDagDFS(i);
			}
		}
	}
	
	private static void PostProcessDagDFS(int v) {
//		mark v
		marked[v] = true;
//		for each edge v->w
		for (int w = 0; w < N; w++) {
//			if w is unmarked
			if (Recipe[v][w] != 0 && !marked[w]) {
				PostProcessDagDFS(w); 
			}
		}
//		Process(v)
		Process(v);
	}
	
	// computes total ingredient quantity and adds results to 
	//  the global product array  
	private static void Process(int v) {
//		for each edge v->w
		for (int w = 0; w < N; w++) {
			if (Recipe[v][w] != 0) {
				shoppingList[v] += Recipe[v][w] * shoppingList[w];
			}
		}
	}
}
