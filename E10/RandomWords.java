/*
 * $ java-algs4 RandomWords 50 888 < DATA/Pwords > 50words.in
 */ 

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class RandomWords
{
    public static void main(String[] args)
    {
	int n = Integer.parseInt(args[0]);
	long seed = Long.parseLong(args[1]);
	String[] words = StdIn.readAllStrings();
	StdRandom.setSeed(seed);
	
	int N = words.length;
	for (int i = 0; i < n; i++) {
	    int j = StdRandom.uniform(N);
	    StdOut.println(words[j]);
	}
    }
}
