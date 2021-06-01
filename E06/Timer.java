/*
 *
 * $ java-algs4 Timer DATA/tinyTale.txt    +....+.+ -v
 * $ java-algs4 Timer DATA/leipzig100k.txt ++.+++++
 * $ java-algs4 Timer DATA/leipzig200k.txt ++.+++++ 
 * $ java-algs4 Timer DATA/leipzig1m.txt   ++.+++++
 *
 * The 2nd argument (things like +....+.+ above) indicates which
 * frequency tables are to be run.
 *
 **/

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.Stopwatch;

public class Timer {

    private static String[] getInputStrings(String[] args) {
	In in;
	if (args[0].equals("-")) in = new In();
	else in = new In(args[0]);

	Words words = new Words(in);
	Queue<String> q = new Queue<>();
	String word = words.nextWord();
	while (word != null) {
	    q.enqueue(word);
	    word = words.nextWord();
	}
	int N = q.size(); 

	String[] input = new String[N];
	int i = 0;
	for (String w : q) input[i++] = q.dequeue();
	return input;
    }

    public static void main(String[] args) {

	String[] input = getInputStrings(args);
	int N = input.length;
	String algs = args[1];
	boolean[] run = new boolean[10];
	for (int i = 0; i < algs.length(); i++) 
	    run[i] = algs.charAt(i) == '+';

	boolean verbose = args.length > 2; 

	Stopwatch sw;

	if (run[0]) {
	    FrequencyTable freq = new FrequencyTable();
	    sw = new Stopwatch();
	    for (int i = 0; i < N; i++) freq.click(input[i]);
	    System.err.println("Time to build FrequencyTable: " + sw.elapsedTime());
	    if (verbose) freq.show();
	}

	if (run[1]) {
	    FrequencyTableRB freqRB = new FrequencyTableRB();
	    sw = new Stopwatch();
	    for (int i = 0; i < N; i++) freqRB.click(input[i]);
	    System.err.println("Time to build FrequencyTableRB: " + sw.elapsedTime());
	    if (verbose) freqRB.show();
	}

	if (run[2]) {
	    FrequencyTableSCL freqSCL = new FrequencyTableSCL();
	    sw = new Stopwatch();
	    for (int i = 0; i < N; i++) freqSCL.click(input[i]);
	    System.err.println("Time to build FrequencyTableSCL: " + sw.elapsedTime());
	    if (verbose) freqSCL.show();
	}

	if (run[3]) {
	    FrequencyTableSC freqSC = new FrequencyTableSC();
	    sw = new Stopwatch();
	    for (int i = 0; i < N; i++) freqSC.click(input[i]);
	    System.err.println("Time to build FrequencyTableSC: " + sw.elapsedTime());
	    if (verbose) freqSC.show();
	}

	if (run[4]) {
	    FrequencyTableLP freqLP = new FrequencyTableLP();
	    sw = new Stopwatch();
	    for (int i = 0; i < N; i++) freqLP.click(input[i]);
	    System.err.println("Time to build FrequencyTableLP: " + sw.elapsedTime());
	    if (verbose) freqLP.show();
	}

	if (run[5]) {
	    FrequencyTableLPP freqLPP = new FrequencyTableLPP();
	    sw = new Stopwatch();
	    for (int i = 0; i < N; i++) freqLPP.click(input[i]);
	    System.err.println("Time to build FrequencyTableLPP: " + sw.elapsedTime());
	    if (verbose) freqLPP.show();
	}

	if (run[6]) {
	    FrequencyTableLPUH freqLPUH = new FrequencyTableLPUH();
	    sw = new Stopwatch();
	    for (int i = 0; i < N; i++) freqLPUH.click(input[i]);
	    System.err.println("Time to build FrequencyTableLPUH: " + sw.elapsedTime());
	    if (verbose) freqLPUH.show();
	}

    	if (run[7]) {
	    FrequencyTableLPUHP freqLPUHP = new FrequencyTableLPUHP();
	    sw = new Stopwatch();
	    for (int i = 0; i < N; i++) freqLPUHP.click(input[i]);
	    System.err.println("Time to build FrequencyTableLPUHP: " + sw.elapsedTime());
	    if (verbose) freqLPUHP.show();
	}
    }
}
