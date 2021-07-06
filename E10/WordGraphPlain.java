/**
 * $ head -n1000 DATA/Pwords | java-algs4 WordGraphPlain | head -n10
 * Time to build word graph: 0.182
 * a
 *    à
 * à
 *    a
 * Aarão
 * aba
 *    abas
 * abacate
 *    abacates
 * $ head -n1000 DATA/Pwords | java-algs4 WordGraphPlain | tail -n10
 *    abeirados
 *    abeirada
 * abeirados
 *    abeirado
 *    abeiradas
 * abeirai
 *    abeirais
 *    abeira
 * abeirais
 *    abeirai
 * $ 
 */

import edu.princeton.cs.algs4.TST;
import edu.princeton.cs.algs4.Graph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.Stopwatch;

/**
 * Based on WordLadder.java
 */

public class WordGraphPlain {
    private TST<Integer> st; // string -> index
    private String[] keys;           // index  -> string
    private Graph graph;             // the underlying graph

    private boolean sameLength = true;      // all words have the same length
    private int wordLength;          // is sameLength, all words have this length
    
    public WordGraphPlain(In in) {
        st = new TST<Integer>();
        // First pass builds the index by reading strings to associate
        // distinct strings with an index
        while (!in.isEmpty()) {
            String[] a = in.readAllStrings();
	    wordLength = a[0].length();
            for (int i = 0; i < a.length; i++) {
                if (!st.contains(a[i])) {
                    st.put(a[i], st.size());
		    if (a[i].length() != wordLength) sameLength = false;
		}
            }
        }
        // inverted index to get string keys in an array
        keys = new String[st.size()];
        for (String name : st.keys()) {
            keys[st.get(name)] = name;
        }
        // second pass builds the graph
        graph = new Graph(st.size());
	for (int i = 0; i < keys.length; i++) {
	    String word1 = keys[i];
	    for (int j = i + 1; j < keys.length; j++) {
		String word2 = keys[j];
		if (isNeighbor(word1, word2)) {
		    graph.addEdge(st.get(word1), st.get(word2));
		}
	    }
	}
    }

    // return true if two strings differ in exactly one letter
    // or the shorter string can be obtained from the longer string
    // deleting exactly one letter
    public static boolean isNeighbor(String a, String b) {
	if (a.length() == b.length()) { 
	    int differ = 0;
	    for (int i = 0; i < a.length(); i++) {
		if (a.charAt(i) != b.charAt(i)) differ++;
		if (differ > 1) return false;
	    }
	    return true;
	}
	if (a.length() + 1 == b.length() || a.length() == b.length() + 1) {
	    if (a.length() > b.length()) {
		String t = a; a = b; b = t;
	    }
	    for (int j = 0; j < b.length(); j++) {
		String k = b.substring(0, j) + b.substring(j + 1);
		if (k.equals(a)) return true;
	    }
	    return false;
	}
	return false;
    }

    public boolean contains(String s) {
        return st.contains(s);
    }

    public int indexOf(String s) {
        return st.get(s);
    }

    public String nameOf(int v) {
        validateVertex(v);
        return keys[v];
    }

    public Graph graph() {
        return graph;
    }

    // throw an IllegalArgumentException unless {@code 0 <= v < V}
    private void validateVertex(int v) {
        int V = graph.V();
        if (v < 0 || v >= V)
            throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (V-1));
    }

    public static void main(String[] args) {
        In in;
	if (args.length > 0) in = new In(args[0]);
	else in = new In();
	Stopwatch sw = new Stopwatch();
        WordGraphPlain wg = new WordGraphPlain(in);
	StdOut.println("Time to build word graph: " + sw.elapsedTime());
        Graph graph = wg.graph();
	for (int i = 0; i < graph.V(); i++) {
	    StdOut.println(wg.nameOf(i));
	    for (int j : graph.adj(i)) {
		StdOut.println("   " + wg.nameOf(j));
	    }
        }
    }
}
