/*
 * $ java-algs4 GeneratorForPatterns 5 3 .8 ab 888 
 * ..b
 * ..b
 * ..a
 * a..
 * ..a
 * $ java-algs4 GeneratorForPatterns 5 3 .8 ab 888 .
 * ..b
 * ..a
 * a..
 * b..
 * ba.
 */

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.TST;

public class GeneratorForPatterns {

    public static String randomString(String alpha, int length, double dot) {
	char[] w = new char[length];
	boolean chars = false;
	while (!chars)
	    for (int i = 0; i < length; i++) 
		if (StdRandom.bernoulli(dot))
		    w[i] = '.';
		else {
		    int t = StdRandom.uniform(alpha.length());
		    w[i] = alpha.charAt(t);
		    chars = true;
		}
	return new String(w);
    }
    
    public static void main(String[] args) {
	int N = Integer.parseInt(args[0]);
	int length = Integer.parseInt(args[1]);
	double dot = Double.parseDouble(args[2]);
	String alpha = args[3];
	long seed = Long.parseLong(args[4]);
	StdRandom.setSeed(seed);
	boolean unique = args.length > 5;
	
	String[] a = new String[N];
	// use TST to hold a set
	TST<Integer> st = new TST<>();
	int i = 0;
	while (i < N) {
	    String r = randomString(alpha, length, dot);
	    if (unique && st.contains(r)) continue;
	    a[i++] = r;
	    if (unique) st.put(r, 0);
	} 
	for (String s : a) StdOut.println(s);
    }
}
