/*
 * $ java-algs4 RBSTFloorClient DATA/GUTENBERG/tinyTale.txt < shellsST.txt 
 * time to read in DATA/GUTENBERG/tinyTale.txt: 0.008
 * keys(""):
 * age 21
 * belief 29
 * best 3
 * darkness 47
 * [...]
 * worst 9
 * 
 * season
 * season
 * of
 * season
 * best
 * the
 * of
 * season
 * time to process stdin: 0.003
*/

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.RedBlackBST;
import edu.princeton.cs.algs4.Stopwatch;

public class RBSTFloorClient {
    
    public static void main(String[] args) {

	In in = new In(args[0]);
	Stopwatch sw = new Stopwatch();
        RedBlackBST<String, Integer> st = new RedBlackBST<>();
        for (int i = 0; !in.isEmpty(); i++) {
            String key = in.readString();
            st.put(key, i);
        }
	System.err.println("time to read in " + args[0] + ": " + sw.elapsedTime());

        // print results
        if (st.size() < 100) {
            StdOut.println("keys(\"\"):");
            for (String key : st.keys()) {
                StdOut.println(key + " " + st.get(key));
            }
            StdOut.println();
        }

	Stopwatch sw2 = new Stopwatch();
	while (!StdIn.isEmpty()) {
	    String query = StdIn.readString(); 	    
	    StdOut.println(st.floor(query));
	}
	System.err.println("time to process stdin: " + sw2.elapsedTime());
    }
}
