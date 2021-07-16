/**
 * $ java-algs4 Check2SAT < DATA/p8.txt
 * Time to check: 0.006s
 * Satisfiable!
 * $ java-algs4 Check2SAT < DATA/p8b.txt
 * Time to check: 0.006s
 * Not satisfiable!
 * $ 
 */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.Stopwatch;

public class Check2SAT
{
    public static void main(String[] args)
    {
	In in = new In();
	ImplicationGraph impgr = new ImplicationGraph(in);
	Stopwatch sw = new Stopwatch();
	TwoSAT twoSAT = new TwoSAT(impgr);
	StdOut.println("Time to check: " + sw.elapsedTime() + "s");
	
	if (twoSAT.hasSolution())
	    StdOut.println("Satisfiable!");
	else 
	    StdOut.println("Not satisfiable!");
    }
}
