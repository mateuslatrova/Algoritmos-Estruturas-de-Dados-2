/******************************************************************************
 * $ head -n50000 DATA/Pwords > Pwords.50000
 * $ java-algs4 WordLadderGeneralizedPlain Pwords.50000 
 * Time to produce wordgraph: 132.011
 * From: avião
 * To: barco
 * length = 6
 * avião
 * avio
 * afio
 * afro
 * aro
 * arco
 * barco
 * 
 ******************************************************************************/

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.Stopwatch;

public class WordLadderGeneralizedPlain {

    public static void main(String[] args) {
        In in = new In(args[0]);
	Stopwatch sw = new Stopwatch();
	WordGraphPlain wg = new WordGraphPlain(in);
	System.err.println("Time to produce wordgraph: " + sw.elapsedTime());

        while (true) {
	    StdOut.print("From: ");
            String s = StdIn.readString();
	    StdOut.print("To: ");
	    String t = StdIn.readString();
            if (!wg.contains(s)) throw new RuntimeException(s + " is not in word list");
            if (!wg.contains(t)) throw new RuntimeException(t + " is not in word list");

	    BreadthFirstPaths bfs = new BreadthFirstPaths(wg.graph(), wg.indexOf(s));
            if (bfs.hasPathTo(wg.indexOf(t))) {
                StdOut.println("length = " + bfs.distTo(wg.indexOf(t)));
                for (int v : bfs.pathTo(wg.indexOf(t))) {
                    StdOut.println(wg.nameOf(v));
                }
            }
            else StdOut.println("NOT CONNECTED");
            StdOut.println();
        }
    }
}
