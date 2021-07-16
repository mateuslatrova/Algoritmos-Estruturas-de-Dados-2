/**
 * $ java-algs4 InstanceGenerator 3 9 3232021
 * 3
 * -3 -2
 * -2 -1
 * 2 3
 * -3 1
 * -2 3
 * -3 3
 * 1 2
 * 1 3
 * -1 3
 * $ 
 */

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.LinearProbingHashST;

public class InstanceGenerator
{
    public static int[] instance(int N, int M) {
	int[] f = new int[1 + 2*M];
	f[0] = N;
	LinearProbingHashST<TwoClause, Integer> tsat = new LinearProbingHashST<>();
	int m = 0;
	while (m < M) {
	    TwoClause c = new TwoClause(N);
	    if (tsat.contains(c)) continue;
	    tsat.put(c, 0);
	    m++;
	}
	int i = 1;
	for (TwoClause c : tsat.keys()) {
	    f[i++] = c.a();
	    f[i++] = c.b();
	}
	return f;
    }
    
    public static void main(String[] args)
    {
	int N = Integer.parseInt(args[0]);
	int M = Integer.parseInt(args[1]);
	long seed = Long.parseLong(args[2]);
	StdRandom.setSeed(seed);
	int[] f = instance(N, M);
	
	StdOut.println(f[0]);
	for (int i = 1; i <= M; i++) 
	    StdOut.println(f[2*i - 1] + " " + f[2*i]);
    }
}
