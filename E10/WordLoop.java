/******************************************************************************
 * $ java-algs4 -Xss10m WordLoop DATA/Pwords -v
 * Time to produce wordgraph: 3.945
 * estudante
 * estudante [11 words]: estudante estudaste estufaste estufastes estufasses estufasse estufassem estudassem estudasses estudastes estudantes estudante 
 * professor
 * professor [3 words]: professor professou professo professor 
 * zigoto
 * zigoto: NO WORD LOOP!
 * $ 
 ******************************************************************************/

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.Stopwatch;

public class WordLoop {

    public static void main(String[] args) {
        In in = new In(args[0]);
		boolean verbose = args.length > 1;
		Stopwatch sw = new Stopwatch();
		WordGraph wg = new WordGraph(in);
		System.err.println("Time to produce wordgraph: " + sw.elapsedTime());

        while (!StdIn.isEmpty()) {
            String s = StdIn.readString();
            if (!wg.contains(s)) throw new RuntimeException(s + " is not in word list");
	    	Cycle finder = new Cycle(wg.graph(), wg.indexOf(s));
	    	if (finder.hasCycle()) {
				if (verbose) {
				    StdOut.print(s + " [" + finder.length() + " words]: ");
				    for (int v : finder.cycle()) 
					StdOut.print(wg.nameOf(v) + " ");
				    StdOut.println();
				} else
				    StdOut.println(s + ": word loop with " + s + " exists");
	    	} else StdOut.println(s + ": NO WORD LOOP!");
        }
    }
}
