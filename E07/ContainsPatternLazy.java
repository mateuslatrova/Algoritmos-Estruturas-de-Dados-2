/*
 * java-algs4 ContainsPatternLazy DATA/Pwords < 2patterns.in
 * Words that contain .a.b.c.d. (4)
 * embasbacada
 * embasbacadas
 * embasbacado
 * embasbacados
 * - * - * -
 * Words that contain .a.e.i.o. (24)
 * agradecidos
 * alfabetizou
 * aparecidos
 * baleeiros
 * carecidos
 * [...]
 */

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.TST;
import edu.princeton.cs.algs4.In;

public class ContainsPatternLazy {
    
    public static void main(String[] args) {
	In in = new In(args[0]);
	int maxLength = 0;
	// use TST to hold a set of strings; i.e., ignore values
        TSTMinus<Integer> st = new TSTMinus<>();
	while (!in.isEmpty()) {
	    String key = in.readString();
	    if (key.length() > maxLength) maxLength = key.length();
	    st.put(key, 0);
	} 
	while (!StdIn.isEmpty()) {
	    String p = StdIn.readString();
	    String pp = p;
	    TST<Integer> words = new TST<>();
	    while (pp.length() <= maxLength) {
		for (String s : st.keysThatStartWith(pp)) 
		    words.put(s, 0);
		pp = "." + pp;
	    }
	    StdOut.println("Words that contain " + p + " (" + words.size() + ")");
	    for (String w : words.keys()) StdOut.println(w);
	    StdOut.println("- * - * -");	    
	}
    }
}
