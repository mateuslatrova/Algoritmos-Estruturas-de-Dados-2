/**
 * $ java-algs4 Estimate 1000 1150 100 888 -
 * 1111001100001010101110001101100010001101000010000110000100010101110011010101000100010110110010011001
 * Simulation time: 0.427 seconds
 * Fraction satisfiable: 0.44
 * $ java-algs4 Estimate 1000 1150 10000 888
 * Simulation time: 11.205 seconds
 * Fraction satisfiable: 0.498
 * $ java-algs4 Estimate 1000 1150 10000 323
 * Simulation time: 10.434 seconds
 * Fraction satisfiable: 0.4931
 * $ 
 */

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.Stopwatch;

public class Estimate {

    // do T trials and return fraction that are satisfiable
    public static double eval(int N, int M, int T, boolean verbose) {
        int count = 0;
        for (int k = 0; k < T; k++) {
	    int[] f = InstanceGenerator.instance(N, M);
	    ImplicationGraph impgr = new ImplicationGraph(f);	    
	    TwoSAT twoSAT = new TwoSAT(impgr);
	    if (twoSAT.hasSolution()) { 
                count++;
	    }
	    if (verbose)
		StdOut.print(twoSAT.hasSolution() ? 1 : 0);
        }
	if (verbose) StdOut.println();
        return (double) count / T;
    }

    public static double eval(int N, int M, int T) {
	return eval(N, M, T, false);
    } 

    public static void main(String[] args) {
        int N = Integer.parseInt(args[0]);
        int M = Integer.parseInt(args[1]);
        int T = Integer.parseInt(args[2]);
        long seed = Long.parseLong(args[3]);
	boolean verbose = args.length > 4;
	StdRandom.setSeed(seed);
	Stopwatch sw = new Stopwatch();
        double p = eval(N, M, T, verbose);
	System.err.println("Simulation time: " + sw.elapsedTime() + " seconds");
        System.err.println("Fraction satisfiable: " + p);
    }
}
