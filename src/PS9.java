import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * @title PS9 - CS4150 Algorithms 
 * @author Sabina Miani 
 * @version 23 November 2021
 */

public class PS9 {
	
	static class Road {
		Integer a;
		Double commute;
		
		public Road (Integer a, Double commute) {
			this.a = a; 
			this.commute = commute;
		}
		
		@Override
		public boolean equals(Object o) {
		    if (this == o) return true;
		    if (o == null || getClass() != o.getClass()) return false;
		    Road that = (Road) o;
		    return (a.equals(that.a));
		}
	}

	// global variables for the city outline/graph 
	static List<List<Road>> city;
	static Integer[][] intersections;
	static double[][] dist;
	static int N;
	static int M; 
	
	public static void main(String[] args) {
		
		CreateCity();
		
		System.out.println(NewCommute());
		
	}
	
	private static void CreateCity() {
		Scanner scanner = new Scanner(System.in);
		N = scanner.nextInt();  
		
		// (x,y) location pairs of all intersections  
		intersections = new Integer[N][2];
		
		// add all intersections 
		for (int i = 0; i < N; i++) {
			city.add(i, new ArrayList<Road>());
			
			intersections[i][0] = scanner.nextInt();
			intersections[i][1] = scanner.nextInt();
		}
		
		// add all roads  
		M = scanner.nextInt();  
		
//		dist = new double[N][N];
//		for (int u = 0; u < N; u++) {
//			for (int v = 0; v < N; v++) {
//				dist[u][v] = 0;  // should this be positive infinity?? 
//			}
//		}
		
		for (int i = 0; i < M; i++) {
			int a = scanner.nextInt();
			int b = scanner.nextInt();
			double commute = Math.sqrt(Math.pow(intersections[a][0] - intersections[b][0], 2) + 
					Math.pow(intersections[a][1] - intersections[b][1], 2));
			
			city.get(a).add(new Road(b,commute));
			city.get(b).add(new Road(a,commute));
			
			
//			dist[a][b] = commute;
//			dist[b][a] = commute;
		}
		
		scanner.close();
	}
	
	private static double NewCommute() {
		double newCommute = 0; 
		
		// add new edge 
		TotalCommute();
		
		return newCommute;
	}
	
	private static double TotalCommute() {
		double totalCommute = 0;
		
//		FloydWarshall Algorithm -- APSP 
		dist = new double[N][N];
		for (int u = 0; u < N; u++) {
			for (int v = 0; v < N; v++) {
				dist[u][v] = 0;  // should this be positive infinity??
			}
			for (Road r : city.get(u)) {
				dist[u][r.a] = r.commute;   
			}
		}
		
//		for all vertices r
		for (int r = 0; r < N; r++) {
//			for all vertices u
			for (int u = 0; u < N; u++) {
//				for all vertices v
				for (int v = 0; v < N; v++) {
					if (u == v)
						continue;
//					if  dist[u, v] > dist[u, r] + dist[r, v]
					if (dist[u][v] > dist[u][r] + dist[r][v])
//						dist[u, v] ← dist[u, r] + dist[r, v]
						dist[u][v] = dist[u][r] + dist[r][v];
				}
			}
		}
		
		// summation of shortest paths distances 
		for (int u = 0; u < N; u++) {
			for (int v = 0; v < N; v++) {
				if (u == v)
					continue;
				totalCommute += dist[u][v];
			}
		}
		
		return totalCommute; 
	}

}
