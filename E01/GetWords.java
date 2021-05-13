/******************************************************************************
 * 
 * $ java-algs4 GetWords < GetWords.java
 * import
 * edu
 * princeton
 * cs
 * algs
 * StdIn
 * import
 * edu
 * [...]
 * 
 ******************************************************************************/

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class GetWords {

    public static void main(String[] args) {
	String s = StdIn.readAll();
	s = s.replaceAll("[^A-Za-z \n]", " ");
	String[] w = s.split("\\s+");
	for (int i = 0; i < w.length; i++)
	    StdOut.println(w[i]);
    }
    
}

