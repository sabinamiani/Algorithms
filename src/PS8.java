import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Scanner;

/**
 * @title PS8 - CS4150 Algorithms 
 * @author Sabina Miani 
 * @version 16 November 2021
 */

public class PS8 {
	
	static class Corridor {
		Integer a; 
		Double factor;
		Double dist;
		
		public Corridor (Integer a, Double factor) {
			this.a = a;
			this.factor = factor;
			dist = Double.NEGATIVE_INFINITY;
		}
		
		@Override
		public boolean equals(Object o) {
		    if (this == o) return true;
		    if (o == null || getClass() != o.getClass()) return false;
		    Corridor that = (Corridor) o;
		    return (a.equals(that.a));
		}
	}
	
	static class Comp implements Comparator<Corridor> {
	    public int compare(Corridor a, Corridor b) {
	        return b.factor.compareTo(a.factor);
	    }
	}
	
	// global variable for the adjacency list 
	static List<List<Corridor>> dungeon;

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		
		// parse N,M from scanner  
		int N = scanner.nextInt();  
		int M = scanner.nextInt();
		
		dungeon = new ArrayList<>();
		CreateDungeon(scanner, N, M);
		
		System.out.printf("%.4f", MikaelsPride(N));
		
		scanner.close();
		return; 
	}

	private static void CreateDungeon(Scanner scanner, int N, int M) {
		
		// add all intersections
		for (int i = 0; i < N; i++ ) {
			dungeon.add(i, new ArrayList<Corridor>());
		}
		
		// add all corridors 
		for (int i = 0; i < M; i++) {
			int a = scanner.nextInt();
			int b = scanner.nextInt();
			double f = scanner.nextDouble();
			
			dungeon.get(a).add(new Corridor(b,f));
			dungeon.get(b).add(new Corridor(a,f));
		}
	}
	
	/*
	 * implementation of Dijkstra's algorithm for nonnegative edge weights 
	 */
	private static double MikaelsPride(int N) {
		double Mikael = 0;
		Comparator<Corridor> comparator = new Comp();
		PriorityQueue<Corridor> pq = new PriorityQueue<Corridor>(N, comparator);
		
//		InitSSSP(s)
		// Mikael starts at intersection 0 with height at one
		Corridor u = new Corridor(0, 1.0);
		u.dist = 1.0;
		pq.add(u);
		
//		while the priority queue is not empty 
		while (!pq.isEmpty()) {
//			u ← ExtractMax( )
			u = pq.poll(); 
			// Mikael shrinks with the distance
			if (u.a == N-1 && u.dist > Mikael)
				Mikael = u.dist;
//			for all edges u->v
			for (Corridor v : dungeon.get(u.a)) {
//				if u->v is tense 
				if (u.dist * v.factor > v.dist) { 
//					Relax(u->v)
//					dist(v) ← dist(u) + w(u->v)
					v.dist = u.dist * v.factor;
//					DecreaseKey(v,dist(v))
					pq.add(v); 
				}
			}
		}
		return Mikael;
	}
}
