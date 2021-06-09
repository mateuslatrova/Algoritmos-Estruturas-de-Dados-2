/*
 * $ java-algs4 TimerMinMaxMinus shellsST.txt < 2queries.txt
 * keys(""):
 * by 4
 * sea 6
 * sells 1
 * she 0
 * shells 3
 * shore 7
 * the 5
 * 
 * min: by
 * max: the
 * 
 * minWithPrefix()
 * s : sea
 * sh : she
 * Time to compute the min: 0.0
 * 
 * maxWithPrefix()
 * s : shore
 * sh : shore
 * Time to compute the max: 0.001
 */ 

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.Stopwatch;

public class TimerMinMaxMinus {
    
    public static void main(String[] args) {

	In in = new In(args[0]);
        TSTMinus<Integer> st = new TSTMinus<Integer>();
        for (int i = 0; !in.isEmpty(); i++) {
            String key = in.readString();
            st.put(key, i);
        }

        // print results
        if (st.size() < 100) {
            StdOut.println("keys(\"\"):");
            for (String key : st.keys()) {
                StdOut.println(key + " " + st.get(key));
            }
            StdOut.println();
        }
	StdOut.println("min: " + st.minWithPrefix(""));
	StdOut.println("max: " + st.maxWithPrefix(""));
	StdOut.println();

	Queue<String> queries = new Queue<>();
	while (!StdIn.isEmpty()) {
	    String q = StdIn.readString();
	    queries.enqueue(q);
	}

	StdOut.println("minWithPrefix()");
	Stopwatch sw = new Stopwatch();
	for (String q : queries) 
	    StdOut.println(q + " : " + st.minWithPrefix(q));
	System.err.println("Time to compute the min: " + sw.elapsedTime());

	StdOut.println("\nmaxWithPrefix()");
	sw = new Stopwatch();
	for (String q : queries) 
	    StdOut.println(q + " : " + st.maxWithPrefix(q));
	System.err.println("Time to compute the max: " + sw.elapsedTime());
    }
}
