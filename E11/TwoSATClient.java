/**
 * $ java-algs4 TwoSATClient DATA/p8.txt
 * Satisfying assignment:
 * 1: false
 * 2: false
 * 3: false
 * 4: false
 * 5: false
 * 6: true
 * 7: true
 * Assignment: false false false false false true true 
 * 7 variables
 * 22 implications
 * 1 [false]: 
 * -1 [true]: -5 [true] -4 [true] -3 [true] 
 * 2 [false]: 
 * [...]
 * -7 [false]: -5 [true] -3 [true] 2 [false] 
 * Truth value of 2SAT: true
 * $ java-algs4 TwoSATClient DATA/p8b.txt
 * Not satisfiable:
 * 
 * 1 => -2 => -5 => -6 => 5 => 2 => -1
 * -1 => -5 => -6 => 5 => 1
 * 
 * Check the graph:
 * 
 * 7 variables
 * 26 implications
 * 1: -2
 * -1: -5 -4 -3
 * 2: -1
 * -2: 7 -5 -4
 * [...]
 * -7: -5 -3 2
 * $ 
 */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class TwoSATClient
{
    public static void main(String[] args)
    {
	In in;
	if (args.length > 0) in = new In(args[0]);
	else in = new In();
	ImplicationGraph impgr = new ImplicationGraph(in);
	TwoSAT twoSAT = new TwoSAT(impgr);

	if (!twoSAT.hasSolution()) { 
	    StdOut.println("Not satisfiable:\n");
	    StdOut.println(twoSAT.unSATProof());
	    StdOut.println("Check the graph:\n");
	    StdOut.print(impgr);
	} else {
	    StdOut.println("Satisfying assignment:");
	    boolean[] s = twoSAT.assignment();
	    for (int i = 1; i < s.length; i++) 
		StdOut.println(i + ": " + s[i]);
	    impgr.evalFull(s);
	    StdOut.println("Truth value of 2SAT: " + impgr.eval(s));
	}
    }
}
