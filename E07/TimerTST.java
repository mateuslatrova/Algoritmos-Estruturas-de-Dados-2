/*
 * $ java-algs4 TimerTST DATA/leipzig1m.txt
 * Time to build FrequencyTableTST: 6.438
 * 
 * Compare:
 * 
 * $ java-algs4 Timer DATA/leipzig1m.txt ++.+++++
 * Time to build FrequencyTable: 10.526
 * Time to build FrequencyTableRB: 13.873
 * Time to build FrequencyTableSC: 6.623
 * Time to build FrequencyTableLP: 1.82
 * Time to build FrequencyTableLPP: 1.867
 * Time to build FrequencyTableLPUH: 3.222
 * Time to build FrequencyTableLPUHP: 3.233
 **/

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.Stopwatch;

public class TimerTST {

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
	boolean verbose = args.length > 1; 
	Stopwatch sw;
	FrequencyTableTST freq = new FrequencyTableTST();
	sw = new Stopwatch();
	for (int i = 0; i < N; i++) freq.click(input[i]);
	System.err.println("Time to build FrequencyTableTST: " + sw.elapsedTime());
	if (verbose) freq.show();
    }
}
