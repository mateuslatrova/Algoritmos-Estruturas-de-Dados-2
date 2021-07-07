/******************************************************************************
 * java-algs4 CheckCycle DATA/Pwords
 * Finished reading word list
 * estudante [11 words]: estudante estudaste estufaste estufastes estufasses estufasse estufassem estudassem estudasses estudastes estudantes estudante 
 * Cycle!
 * professor [3 words]: professor professou professo professor 
 * Cycle!
 * professor [3 words]: professor professou professo professoe professor 
 * Word not allowed: professoe
 * $ 
 ******************************************************************************/

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.SET;

public class CheckCycle {

    public static void main(String[] args) {
		In in = new In(args[0]);
		SET<String> allWords = new SET<>();
        while (!in.isEmpty()) {
            String word = in.readString();
            allWords.add(word);
        }
        System.err.println("Finished reading word list");

        while (!StdIn.isEmpty()) {
	    	String[] cycle = StdIn.readLine().split("\\s+");

	    	int N = cycle.length;

	    	SET<String> words = new SET<>();

	    	String root = cycle[0];
	    	if (!allWords.contains(root)) { 
				StdOut.println("Word not allowed: " + root);
				System.exit(0);		
	    	}
	    	if (!cycle[3].equals(root) || !cycle[N - 1].equals(root)) {
				StdOut.println("Starts or ends wrong: " 
					       + cycle[0] + " / " 
					       + cycle[3] + " / "
					       + cycle[N - 1]);
				System.exit(0);
	    	} 

	    	for (int i = 4; i < N; i++) words.add(cycle[i]);
	    	
			if (words.size() != N - 4) { 
				StdOut.println("There are repeated words");
				System.exit(0);		
	    	}

	    	for (int i = 4; i < N; i++) {
				if (!allWords.contains(cycle[i])) { 
				    StdOut.println("Word not allowed: " + cycle[i]);
				    System.exit(0);		
				}
			
				if (!WordGraphPlain.isNeighbor(cycle[i - 1], cycle[i])) { 
				    StdOut.println("Not adjacent: " 
						   + cycle[i - 1] + " / " 
						   + cycle[i]);
				    System.exit(0);
				}
	    	} 

	    	StdOut.println("Cycle!");
        }
    }
}
