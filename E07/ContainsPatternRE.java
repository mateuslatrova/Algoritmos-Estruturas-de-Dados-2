/* 
 * $ java-algs4 ContainsPatternRE DATA/Pwords < 2patterns.in
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

import java.util.regex.Pattern;
import java.util.regex.Matcher;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.TST;
import edu.princeton.cs.algs4.In;

public class ContainsPatternRE {
    
    public static void main(String[] args) {
	In in = new In(args[0]);
	// use TST to hold a set (i.e., ignore values)	
        TST<Integer> st = new TST<>(); 
	while (!in.isEmpty()) { 
            String key = in.readString();
	    st.put(key, 0);
        }
	while (!StdIn.isEmpty()) {
	    String p = StdIn.readString();
            Pattern pattern = Pattern.compile(".*" + p + ".*");
	    TST<Integer> pset = new TST<>();
	    for (String s : st.keys()) {
		Matcher matcher = pattern.matcher(s);
		if (matcher.find()) pset.put(s, 0);
	    }
	    StdOut.println("Words that contain " + p + " (" + pset.size() + ")");
	    for (String s : pset.keys()) StdOut.println(s);
	    StdOut.println("- * - * -");
	}
    }
}
