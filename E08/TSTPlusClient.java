/*
 * $ java-algs4 TSTPlusClient shellsST.txt 
 * keys(""):
 * by 4
 * [...]
 * 
 * min: by
 * max: the
 * 
 * What do you want?
 * 1. longestPrefixOf()
 * [...]
 * 7. floor()
 * 8. ceiling()
 * 0. to be left alone
 * Your choice: 7
 * Query: sure
 * shore
 * 
 * What do you want?
 * 1. longestPrefixOf()
 * [...]
 * 7. floor()
 * 8. ceiling()
 * 0. to be left alone
 * Your choice: 8
 * Query: sure
 * the
 * 
 * What do you want?
 * 1. longestPrefixOf()
 * [...]
 * 7. floor()
 * 8. ceiling()
 * 0. to be left alone
 * Your choice: 8
 * Query: think
 * java.util.NoSuchElementException: argument to ceiling() is too large
 * 
 * What do you want?
 * 1. longestPrefixOf()
 * 2. keysWithPrefix()
 * [...]
 */ 

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.In;

public class TSTPlusClient {
    
    public static void main(String[] args) {

	In in = new In(args[0]);
        TSTPlus<Integer> st = new TSTPlus<Integer>();
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
	
	while (true) {
	    StdOut.println("What do you want?");
	    StdOut.println("1. longestPrefixOf()");
	    StdOut.println("2. keysWithPrefix()");
	    StdOut.println("3. keysThatMatch()");
	    StdOut.println("4. keysThatStartWith()");
	    StdOut.println("5. minWithPrefix()");
	    StdOut.println("6. maxWithPrefix()");
	    StdOut.println("7. floor()");
	    StdOut.println("8. ceiling()");
	    StdOut.println("0. to be left alone");
	    boolean readint = false;
	    int opt = 0 ;
	    while (!readint) 
		try {
		    StdOut.print("Your choice: ");
		    String a = StdIn.readString();
		    opt = Integer.parseInt(a);
		    readint = true;
		} catch (Exception e) {
		    StdOut.println(e);
		}
	    if (opt == 0) break;
	    StdOut.print("Query: ");
	    String query = StdIn.readString(); 
	    switch (opt) {
	    case 1:
		StdOut.println(st.longestPrefixOf(query));		
		break;
	    case 2:
		for (String s : st.keysWithPrefix(query))
		    StdOut.println(s);
		break;
	    case 3:
		for (String s : st.keysThatMatch(query))
		    StdOut.println(s);
		break;
	    case 4:
		for (String s : st.keysThatStartWith(query))
		    StdOut.println(s);
		break;
	    case 5:
		StdOut.println(st.minWithPrefix(query));
		break;
	    case 6:
		StdOut.println(st.maxWithPrefix(query));
		break;
	    case 7:
		try { 
		    StdOut.println(st.floor(query));
		} catch (Exception e) {
		    StdOut.println(e);
		}
		break;
	    case 8:
		try { 
		    StdOut.println(st.ceiling(query));
		} catch (Exception e) {
		    StdOut.println(e);
		}
		break;
	    }
	    StdOut.println();
	}

    }
}
