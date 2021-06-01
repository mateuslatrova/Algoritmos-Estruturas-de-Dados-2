/*
 * $ java-algs4 MGenerator 8 3 Aa BB 201905
 * $ java-algs4 MGenerator 8 3 ÅûÕ íOI 201905
 * $ java-algs4 MGenerator 8 3 ANwA Ian 201905
 * $ java-algs4 MGenerator 100000 17 ÅûÕ íOI 201905 > 100000.17.ÅûÕ.íOI.in
 * $ java-algs4 MGenerator 100000 17 Aa BB 201905 > 100000.17.Aa.BB.in
 */

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class MGenerator {

    public static void main(String[] args) {
	int N = Integer.parseInt(args[0]);
	int p = Integer.parseInt(args[1]);
	String[] collision = new String[2];
	collision[0] = args[2];
	collision[1] = args[3];
	long seed = Long.parseLong(args[4]);
	StdRandom.setSeed(seed);

	for (int i = 0; i < N; i++) {
	    String s = new String();	    
	    for (int j = 0; j < p; j++) {
		s += collision[StdRandom.uniform(2)];
	    }
	    StdOut.println(s);
	}
	
    }
    
}
