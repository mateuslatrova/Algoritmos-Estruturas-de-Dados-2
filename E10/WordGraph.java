/*********************************************************************

  AO PREENCHER ESSE CABEÇALHO COM O MEU NOME E O MEU NÚMERO USP,
  DECLARO QUE SOU O ÚNICO AUTOR E RESPONSÁVEL POR ESSE PROGRAMA.
  TODAS AS PARTES ORIGINAIS DESSE EXERCÍCIO-PROGRAMA (EP) FORAM
  DESENVOLVIDAS E IMPLEMENTADAS POR MIM SEGUINDO AS INSTRUÇÕES DESSE
  EP E QUE PORTANTO NÃO CONSTITUEM PLÁGIO. DECLARO TAMBÉM QUE SOU
  RESPONSÁVEL POR TODAS AS CÓPIAS DESSE PROGRAMA E QUE EU NÃO
  DISTRIBUI OU FACILITEI A SUA DISTRIBUIÇÃO. ESTOU CIENTE DE QUE OS
  CASOS DE PLÁGIO SÃO PUNIDOS COM REPROVAÇÃO DIRETA NA DISCIPLINA.

  NOME: Mateus Latrova Stephanin
  NUSP: 12542821

  Referências: 
    - https://docs.oracle.com/javase/7/docs/api/java/lang/StringBuilder.html
    - https://docs.oracle.com/javase/7/docs/api/java/lang/String.html#getChars(int,%20int,%20char[],%20int)
    - http://java-performance.info/changes-to-string-java-1-7-0_06/
    - https://stackoverflow.com/questions/4679746/time-complexity-of-javas-substring
    - implementações baseadas fortemente em LZW.java e TST.java de S&W
*********************************************************************/

import edu.princeton.cs.algs4.TST;

import java.util.Iterator;

import edu.princeton.cs.algs4.Graph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.Stopwatch;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.MSD;

/**
 * Based on WordLadder.java
 */

public class WordGraph {
    private TST<Integer> st; // string -> index
    private String[] keys;           // index  -> string
    private boolean[] adj;
    private Graph graph;             // the underlying graph

    private boolean sameLength = true;      // all words have the same length
    private int wordLength;          // is sameLength, all words have this length
    
    public WordGraph(In in) {
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

        //long n = st.size();
        //adj = new boolean[n*n];

        // inverted index to get string keys in an array
        keys = new String[st.size()];
        for (String name : st.keys()) {
            keys[st.get(name)] = name;
        }

        // second pass builds the graph
        graph = new Graph(st.size());
	  
        for (int i = 0; i < keys.length; i++) {
            String word = keys[i];//sortedKeys[i];
            
            int L = word.length();
            for (int j = 0; j < L; j++){
                StringBuilder lessSize = new StringBuilder(word); 
                lessSize.deleteCharAt(j);
                if (lessSize.length() != 0) {
                    Integer lessIndex = st.get(lessSize.toString());
                    if (lessIndex != null) {
                        if (!hasEdge(graph,i,lessIndex)) {
                            //adj[i*n+lessIndex] = adj[lessIndex*n+i] = true;
                            graph.addEdge(i,lessIndex);
                        }
                    }
                }

                StringBuilder sameSize = new StringBuilder(word); 
                sameSize.setCharAt(j,'.');
                Iterable<String> sameMatches = st.keysThatMatch(sameSize.toString());
                for (String s: sameMatches) {
                    Integer sIndex = st.get(s);
                    if (i != sIndex && !hasEdge(graph,i,sIndex)) {
                        //adj[i*n+sIndex] = adj[n*sIndex+i] = true;
                        graph.addEdge(i,sIndex);
                    }
                }
            }
        }
    }

    private boolean hasEdge(Graph g, int u, int v) {
        boolean found = false;
        for (int w: g.adj(u)) {
            if (w == v) return true;
        }
        return false;
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

        // size differ by 1.
	    if (a.length() + 1 == b.length() || a.length() == b.length() + 1) {
	        if (a.length() > b.length()){
                String t = a; a = b; b = t;
            }

            // Old algorithm:
	        //for (int j = 0; j < b.length(); j++) {
	    	//    String k = b.substring(0, j) + b.substring(j + 1);
	    	//    if (k.equals(a)) return true;
	        //}
            // return false;

            // New algorithm:
            int numOfDiffers = 0;
	        for (int i = 0, j = 0; i < a.length() && j < b.length(); i++,j++) {
	    	    if (a.charAt(i) != b.charAt(j)) {
                    numOfDiffers++;
                    i--;
                }    
	    	    if (numOfDiffers > 1) return false;
	        }
	        return true;
	    }

        // not same size and they don't differ size by 1.
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
        WordGraph wg = new WordGraph(in);
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
