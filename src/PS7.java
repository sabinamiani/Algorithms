import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Scanner;

/**
 * @title PS7 - CS4150 Algorithms 
 * @author Sabina Miani 
 * @version 9 November 2021
 */

public class PS7 {
	
	static class Cable {
		Integer a, b;  // ID's for two connected trees 
		Double wt;
		public Cable (Integer a, Integer b, double wt) {
			this.a = a;
			this.b = b;
			this.wt = wt;
		}
		
		@Override
		public boolean equals(Object o) {
		    if (this == o) return true;
		    if (o == null || getClass() != o.getClass()) return false;
		    Cable that = (Cable) o;
		    return (a.equals(that.a) && b.equals(that.b));
		}
	}
	
	static class Comp implements Comparator<Cable> {
	    public int compare(Cable a, Cable b) {
	        return a.wt.compareTo(b.wt);
	    }
	}
	
	// global variable for the adjacency list 
	static List<List<Cable>> cables;

	public static void main(String[] args) {
		
		Scanner scanner = new Scanner(System.in);
		
		// parse n,e,p from scanner  
		int N = scanner.nextInt();  
		int E = scanner.nextInt();
		int P = scanner.nextInt(); 

		double[][] trees = new double[N][2];
		cables = new ArrayList<> ();
		
		for (int i = 0; i < N; i++) {
			// add trees 
			trees[i][0] = scanner.nextDouble();
			trees[i][1] = scanner.nextDouble();
			
			// initialize cables adjacency list 
			cables.add(i, new ArrayList<>());
			
			// add cables aka edges 
			for (int k = 0; k < i; k++) {
				// all first e edges are weight 0
				if (i < E) {
					cables.get(i).add(new Cable(i,k,0));
					cables.get(k).add(new Cable(k,i,0));
				}
				// otherwise the cable has a length
				else {
					double length = Math.sqrt(Math.pow(trees[i][0]-trees[k][0], 2) 
							+ Math.pow(trees[i][1]-trees[k][1], 2));
					cables.get(i).add(new Cable(i,k,length));
					cables.get(k).add(new Cable(k,i,length));
				}
			}
		}
		
		// for P existing cables, replace their weights as zero 
		for (int i = 0; i < P; i++) {
			int a = scanner.nextInt() - 1;
			int b = scanner.nextInt() - 1;
			
			// change the cable weight 	
			// since its a priority queue, we don't need to remove the previous cable 
			cables.get(a).add(new Cable(a,b,0.0));
			cables.get(b).add(new Cable(b,a,0.0));
		}
		
		System.out.println(PrimsAlg());
		
		scanner.close();
		return; 
	}

	
	private static double PrimsAlg () {
		Comparator<Cable> comparator = new Comp();
		PriorityQueue<Cable> PQ = new PriorityQueue<Cable> (1, comparator);
		List<Cable> T = new ArrayList<>();
		Cable s = new Cable(null,0,0);
		double length = 0;
		int count = 0;
		
//		put (∅, s) in the PQ
		PQ.add(s);
		
//		while PQ is not empty
		while (!PQ.isEmpty()) {
//			take (p,v) from the PQ
			Cable v = PQ.remove();
			
//			if (p,v) is a safe edge
			if (isSafe(v, T)) {
//				add (v,v.parent) to T
				T.add(v);
				T.add(new Cable(v.b, v.a, v.wt));
				
				// all safe edges get added to T
				// add the weights of all safe edges
				length += v.wt;
				
				count++;
				System.out.println(count);
				
//				for each edge vw = e
				for (Cable e : cables.get(v.b)) {
//					put (wt(e),e) in the PQ
					PQ.add(e);
					PQ.add(new Cable(e.b, e.a, e.wt));
				}
			}
		}
		
		return length;
	}
	
	
	private static Boolean isSafe (Cable v, List<Cable> T) {
		// starting tree 
		if (v.a == null)
			return true;
		
		// search T for connecting trees 
		Boolean hasA = false; 
		Boolean hasB = false; 
		for (Cable c : T) {
			if (c.a == v.a || c.b == v.a)
				hasA = true; 
			if (c.a == v.b || c.b == v.b)
				hasB = true; 
		}
		
		// if both trees are already in T, v is a useless edge
		if (hasA && hasB)
			return false;
		
		// if T has one end-point of v and v is the minimum weight-edge, it is safe 
		// due to PQ, edge already guaranteed to be minimum... right?
		if (hasA) {
			for (Cable e : cables.get(v.b)) {
				Boolean ThasA = false;
				for (Cable c : T) {
					if (c.a == e.a || c.b == e.a)
						ThasA = true; 
				}
				if (!T.contains(e) && !ThasA && e.wt < v.wt) 
						return false;
			}
			return true;
		} 
		
		if (hasB) {
			for (Cable e : cables.get(v.a)) {
				Boolean ThasB = false;
				for (Cable c : T) {
					if (c.a == e.b || c.b == e.b)
						ThasB = true; 
				}
				if (!T.contains(e) && !ThasB && e.wt < v.wt) 
						return false;
//				if (!T.contains(e) && e.b != v.b) //e.wt < v.wt && 
//						return false;
			}
			return true;
		}
		
		return false; 
	}
	
}
