/*
 * // no repeated strings
 * $ java-algs4 GeneratorSET 5 .5 ab 3232021 .
 * abb
 * a
 * ababb
 * bb
 * baa
 * // repeated strings allowed
 * $ java-algs4 GeneratorSET 5 .5 ab 3232021
 * abb
 * a
 * ababb
 * a
 * bb
 */

import java.util.Arrays;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.Stopwatch;

public class GeneratorSET {

    public static String randomString(String alpha, double space) {
	Queue<Character> q = new Queue<>();
	while (!StdRandom.bernoulli(space)) {
	    int t = StdRandom.uniform(alpha.length());
	    q.enqueue(alpha.charAt(t));
	}
	char[] w = new char[q.size()];
	int i = 0; 
	for (Character c : q) w[i++] = c;
	return new String(w);
    }
    
    public static void main(String[] args) {
	int N = Integer.parseInt(args[0]);
	double space = Double.parseDouble(args[1]);
	String alpha = args[2];
	long seed = Long.parseLong(args[3]);
	StdRandom.setSeed(seed);
	boolean unique = args.length > 4;

	String[] a = new String[N];
	Stopwatch sw = new Stopwatch();
	SET<String> st = new SET<String>();
	int i = 0;
	while (i < N) {
	    String r = randomString(alpha, space);
	    if (r.length() == 0) continue;
	    if (unique && st.contains(r)) continue;
	    a[i++] = r;
	    if (unique) st.add(r);
	} 
	System.err.println("time to produce list: " + sw.elapsedTime());
	for (String s : a) StdOut.println(s);
    }
}
