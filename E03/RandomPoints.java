/* 
 * $ java-algs4 RandomPoints 5 2021
 * 0.6187223781467913 0.6796649601736005
 * 0.2200865517798617 0.7473252723869324
 * 0.47145623081026244 0.47539722774462423
 * 0.26526664838992675 0.629733365216445
 * 0.09871473196875946 0.7199513315785697
 */ 

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class RandomPoints {
    
    public static void main(String args[]) { 
	int n = Integer.parseInt(args[0]);
	long seed = Integer.parseInt(args[1]);

	StdRandom.setSeed(seed);
	while (n-- > 0) {
	    double x = StdRandom.uniform();
	    double y = StdRandom.uniform();
	    StdOut.println(x + " " + y);
	}
    }
}
