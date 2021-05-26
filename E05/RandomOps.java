/*
 * $ java-algs4 RandomOps 123 5 
 * $ java-algs4 RandomOps 123 5 10
 * $ java-algs4 RandomOps 123 5 -10
 */

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.RedBlackBST;

public class RandomOps {

    private static int RANGE = Integer.MAX_VALUE;
    
    public static void main(String args[]) {
	long seed = Long.parseLong(args[0]);
	int N = Integer.parseInt(args[1]);

	RedBlackBST<Integer, Integer> st = new RedBlackBST<>();
	StdRandom.setSeed(seed);
	
	while (N > 0) { 
	    int x = 1 + StdRandom.uniform(RANGE);
	    if (st.contains(x)) continue;
	    st.put(x, 0);
	    StdOut.printf("%12d\n", x);
	    N--;
	}

	int M = 0; 
	boolean CM = false;
	if (args.length > 2) {
	    M = Integer.parseInt(args[2]);
	    if (M < 0) {  // Culberson--Munro sequence
		CM = true;
		M = -M;
	    }

	    if (CM) 
		while (M-- > 0) {
		    int r = StdRandom.uniform(st.size());
		    int x = st.select(r);
		    StdOut.printf("%12d\n", -x);
		    StdOut.printf("%12d\n", x);
		}
	    else {
		while (M > 0)
		    if (StdRandom.bernoulli()) {
			int x = 1 + StdRandom.uniform(RANGE);
			while (st.contains(x))
			    x = 1 + StdRandom.uniform(RANGE);
			st.put(x, 0);
			StdOut.printf("%12d\n", x);
			M--;
		    } else {
			if (st.size() == 0) continue; 
			int r = StdRandom.uniform(st.size());
			int x = st.select(r);
			st.delete(x);
			StdOut.printf("%12d\n", -x);
			M--;
		    }
	    } 
	}
    }
}
