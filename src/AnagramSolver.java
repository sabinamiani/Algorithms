

import java.util.Arrays;
import java.util.Hashtable;
import java.util.Scanner;

public class AnagramSolver {

	public static void main(String[] args) {
		
		Scanner scanner = new Scanner(System.in);
		
		// parse n and k from scanner 
		int n = scanner.nextInt();
		int k = scanner.nextInt();
		
//		System.out.println(n + " " + k);
		
		// get array of words  
		Hashtable<String, Integer> words = new Hashtable<String, Integer>();
		int wordCount = 0; 
		int notAnagramCount = n;
		
		while (wordCount != n && scanner.hasNext()) {
			// insert sorted word as key and sorted repeat count as value
			String next = scanner.next();
//			System.out.println(next + " next");
			
			char[] word = next.toCharArray();
			
	        Arrays.sort(word);
	        String sorted = new String(word);
	        
//	        System.out.println(sorted + " sorted");
	        
	        Integer previousValue = words.put(sorted, 1);
	        
//	        System.out.println(previousValue + " prev");
	        
	        // if the value gets overwritten, increment integer value by one
			if (previousValue != null) {
				words.put(sorted, previousValue + 1);
				
				// if new anagram was found, remove 2 from count, otherwise only remove one
				if (previousValue == 1)
					notAnagramCount = notAnagramCount - 2;
				else 
					notAnagramCount--;
			}
			
			wordCount++;
		}
		
		// print the number of words without anagrams
		System.out.print(notAnagramCount); // + " final answer");
		
		scanner.close();
		return; 
	}
	
}
