/******************************************************************************
 *  Compilation:  javac WordLadder.java
 *  Execution:    java WordLadder wordlist.txt < word1 word2
 *  Dependencies: Graph.java IndexSET.java In.java BreadthFirstPaths.java
 *
 *  Data files:   https://algs4.cs.princeton.edu/41graph/words5.txt
 *                https://algs4.cs.princeton.edu/41graph/words6.txt
 *                https://algs4.cs.princeton.edu/41graph/words5-knuth.txt
 *
 *  Creates a minimum length word ladder connecting two words.
 *
 *  java WordLadder words5.txt
 *  flirt break
 *  length = 11
 *  flirt
 *  flint
 *  fling
 *  cling
 *  clink
 *  click
 *  clock
 *  cloak
 *  croak
 *  creak
 *  break
 *
 *  allow brown
 *  NOT CONNECTED
 *
 *  white house
 *  length = 18
 *  white
 *  while
 *  whale
 *  shale
 *  shake
 *  slake
 *  slate
 *  plate
 *  place
 *  peace
 *  peach
 *  poach
 *  coach
 *  couch
 *  cough
 *  rough
 *  rouge
 *  rouse
 *  house
 *  
 *  % java WordLadder words5-knuth.txt
 *  white house
 *  length = 9
 *  white
 *  whits
 *  shits
 *  shots
 *  soots
 *  roots
 *  routs
 *  route
 *  rouse
 *  house
 *
 ******************************************************************************/

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.Graph;

public class WordLadder {

    // return true if two strings differ in exactly one letter
    public static boolean isNeighbor(String a, String b) {
        assert a.length() == b.length();
        int differ = 0;
        for (int i = 0; i < a.length(); i++) {
            if (a.charAt(i) != b.charAt(i)) differ++;
            if (differ > 1) return false;
        }
        return true;
    }

    public static void main(String[] args) {

       /*******************************************************************
        *  Read a list of strings, all of the same length.
        *******************************************************************/
        In in = new In(args[0]);
        IndexSET<String> words = new IndexSET<String>();
        while (!in.isEmpty()) {
            String word = in.readString();
            words.add(word);
        }
        System.err.println("Finished reading word list");

       /*******************************************************************
        *  Insert connections between neighboring words into graph.
        *  This construction process can be improved from LN^2 in the worst
        *  case to L^2 N in the worst case by L radix sorts where
        *  N = number of strings and L = length of each words.
        *
        *  We avoid inserting two copies of each edge by checking if
        *  word1.compareTo(word2) < 0
        *
        *******************************************************************/
        Graph G = new Graph(words.size());
        for (String word1 : words.keys()) {
            for (String word2 : words.keys()) {
                if (word1.length() != word2.length()) {
                    throw new RuntimeException("Words have different lengths");
                }
                if (word1.compareTo(word2) < 0 && isNeighbor(word1, word2)) {
                    G.addEdge(words.indexOf(word1), words.indexOf(word2));
                }
            }
        }
        System.err.println("Finished building graph");

       /*******************************************************************
        *  Run breadth first search
        *******************************************************************/
        while (!StdIn.isEmpty()) {
            String from = StdIn.readString();
            String to   = StdIn.readString();
            if (!words.contains(from)) throw new RuntimeException(from + " is not in word list");
            if (!words.contains(to))   throw new RuntimeException(to   + " is not in word list");

            BreadthFirstPaths bfs = new BreadthFirstPaths(G, words.indexOf(from));
            if (bfs.hasPathTo(words.indexOf(to))) {
                StdOut.println("length = " + bfs.distTo(words.indexOf(to)));
                for (int v : bfs.pathTo(words.indexOf(to))) {
                    StdOut.println(words.keyOf(v));
                }
            }
            else StdOut.println("NOT CONNECTED");
            StdOut.println();
        }
    }
}
