/**********************************************************************
 * 
 * $ java-algs4 TFClient < DATA/10.txt > 10.out
 * 
 **********************************************************************/

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class TFClient {

    public static void main(String[] args) {

	for (int i = 0; !StdIn.isEmpty(); i++) {
	    String line = StdIn.readLine();
	    TokenFinder tf = new TokenFinder(line);
	    StdOut.println(i + ":");
	    String token = tf.nextToken();
	    int j = 0;
	    while (token != null) {
		StdOut.printf("  %3d: %s\n", j++, token);
		token = tf.nextToken();		
	    }
	}
    }
    
}
