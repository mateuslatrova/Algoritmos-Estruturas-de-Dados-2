/*
 * java-algs4 RandomIPGenerator 5
 * 254.179.189.197
 * 213.189.118.237
 * 246.149.171.11
 * 20.6.127.230
 * 44.200.228.114
 */ 

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class RandomIPGenerator {

    public static void main(String[] args) {
	int N = Integer.parseInt(args[0]);

	for (int i = 0; i < N; i++) {
	    long l = StdRandom.uniform(1L << 32);
	    String ip = IPConv.ip(l);
	    StdOut.println(ip);
	}
    }
    
}
