/*
 * $ java-algs4 Histogram 322 60 60
 * max: 3
 * $ java-algs4 Histogram 322 100 10000
 * max: 128
 * $ java-algs4 Histogram 322 97 19668  [A Tale of Two Cities has 19668 distinct words]
 * max: 252
 */ 

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.StdOut;

public class Histogram { 

   /**
    * From StdStats, with a minor change
    */
    public static void plotBars(double[] a) {
        int n = a.length;
        StdDraw.setXscale(-.5, n - .5);
        for (int i = 0; i < n; i++) {
            StdDraw.filledRectangle(i, a[i]/2, 0.35, a[i]/2);
        }
    }

    public static void main(String[] args) {
	long seed = Long.parseLong(args[0]);
	int M = Integer.parseInt(args[1]);
	int N = Integer.parseInt(args[2]);

	StdRandom.setSeed(seed);
	int[] buckets = new int[M];
	for (int i = 0; i < N; i++) {
	    int b = StdRandom.uniform(M);
	    buckets[b]++;
	}

	int max = StdStats.max(buckets);
	double[] nbuckets = new double[M];
	for (int i = 0; i < M; i++) 
	    nbuckets[i] = .5*buckets[i]/max;

	StdDraw.setCanvasSize(1200, 400);
	// StdStats.plotBars(nbuckets);
	plotBars(nbuckets);
	StdOut.println("max: " + max);
    }
}
