import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

/**
 * @title PS5 - CS4150 Algorithms - Collecting Gold as an Adventurer 
 * @author Sabina Miani 
 * @version 28 October 2021
 */

public class PS5 {
	
	// global variable - Treasure Map  
	static char[][] TreasureMap;

	public static void main(String[] args) {
		
		Scanner scanner = new Scanner(System.in);
		
		// parse W,H from scanner  
		int W = scanner.nextInt();  // x
		int H = scanner.nextInt();  // y
		
		TreasureMap = new char[W][H];
		int[] P = CreateMap(scanner,W,H);
		
		System.out.println(ReachableGold(P));
		
		scanner.close();
		return; 
	}

	/*
	 * Populates a 2D array with the given Treasure Grid and returns 
	 *  the index at the players starting position (P)
	 */
	private static int[] CreateMap (Scanner scanner, int W, int H) {
		int[] P = new int[2];
		
		scanner.nextLine(); // added to get to the second line 
		
		for (int j = 0; j < H; j++) {
			String line = scanner.nextLine();
			for (int i = 0; i < W; i++) {
				// each char is one floor space on the treasure map
				char tile = line.charAt(i);
				TreasureMap[i][j] = tile;
				if (tile == 'P') {
					P[0] = i;
					P[1] = j;
				}
			}
		}
		return P;
	}
	
	/*
	 * Whatever-First-Search implementation to count 
	 *  the number of reachable gold pieces within the 
	 *  connected component of P (the players starting 
	 *  position) 
	 * Return: int - number of reachable gold pieces
	 * Parameter: int[] P - starting position of player
	 */
	private static int ReachableGold(int[] P) {
		int goldPieces = 0;
		Queue<int[]> bag = new LinkedList<int[]>(); 
		
//		put P into the bag
		bag.add(P);
//		while the bag is not empty
		while (!bag.isEmpty()) {
//			take v from the bag 
			int[] v = bag.remove();
//			if v is unmarked
			// no need to create another array when you already have one
			if(TreasureMap[v[0]][v[1]] != 'm') {
//				mark v
				TreasureMap[v[0]][v[1]] = 'm';
				
//				for each edge vw
				// only 4 possible edges from v
				HashSet<int[]> edges = new HashSet<int[]>();
				edges.add( new int[] {v[0]+1,v[1]} );
				edges.add( new int[] {v[0]-1,v[1]} );
				edges.add( new int[] {v[0],v[1]+1} );
				edges.add( new int[] {v[0],v[1]-1} );
				
				// created so deleting spaces is within the laws of java
				HashSet<int[]> noWalls = new HashSet<int[]>();
				noWalls.addAll(edges);
				
				int looseChange = 0;
				Boolean draft = false;
				
				for(int[] w : noWalls) {
					// if draft can be felt from trap, no space is safe
					if (TreasureMap[w[0]][w[1]] == 'T')
						draft = true;
					// player cannot enter a wall 
					else if (TreasureMap[w[0]][w[1]] == '#')
						edges.remove(w);
					// the scavenger picks up precious gold 
					else if (TreasureMap[w[0]][w[1]] == 'G')
						looseChange++;
				}
				
				if(!draft) {
					// put w into the bag
					bag.addAll(edges);
					goldPieces += looseChange;
					
					// to make sure the gold does not get counted more than 
					// once, change 'G' to '.'
					if(looseChange > 0) {
						for(int[] w : edges) {
							if (TreasureMap[w[0]][w[1]] == 'G')
								TreasureMap[w[0]][w[1]] = '.';
						}
					}
				}
			}
		}
		
		return goldPieces;
	}
	
}
