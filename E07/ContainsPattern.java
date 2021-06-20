/*
 * java-algs4 ContainsPattern DATA/PallWords < 2patterns.in
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
import edu.princeton.cs.algs4.LinearProbingHashST;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.In;

public class ContainsPattern {

    public static void copyQueue(Queue<String> q1, Queue<String> q2) {
        for (String s: q2) {
            q1.enqueue(s);
        }
    }
    
    public static void main(String[] args) {
	    In in = new In(args[0]);
	    int maxLength = 0;
        TSTPlus<Queue<Integer>> suffixTrie = new TSTPlus<Queue<Integer>>();
        //LinearProbingHashST<Integer,String> allWords = new LinearProbingHashST<Integer,String>();
        TSTPlus<Queue<String>> suffixToString = new TSTPlus<Queue<String>>()
        // Building Symbol Table:
        int i = 0;
	    while (in.hasNextLine()) {
	        String key = in.readLine();
            int n = key.length();
	        if (n > maxLength) maxLength = n;
            for (int j = 0; j < n; j++) {
                String suffix = key.substring(j);
                Queue<Integer> queue = suffixTrie.get(suffix);
                if (queue == null) {
                    queue = new Queue<Integer>();
                    queue.enqueue(i);
                    suffixTrie.put(key.substring(j),queue);
                } else {
                    queue.enqueue(i);
                }
            }
            allWords.put(i,key);
            i++;
	    } 

        int numWords = allWords.size();

	    while (StdIn.hasNextLine()) {
            //Queue<String> aux = new Queue<String>();
            //copyQueue(aux, allWords);
	        String pattern = StdIn.readLine();
	        Queue<String> wordsThatContainPattern = new Queue<String>();
            Queue<String> matches = (Queue<String>) suffixTrie.keysThatStartWith(pattern);
            
            SET<Integer> vals = new SET<Integer>();

            for (String m: matches) {
                for (Integer n: suffixTrie.get(m)) vals.add(n);
            }

            for (Integer val: vals) wordsThatContainPattern.enqueue(allWords.get(val));
            
	        StdOut.println("Words that contain " + pattern + " (" + wordsThatContainPattern.size() + ")");
	        for (String w : wordsThatContainPattern) StdOut.println(w);
	        StdOut.println("- * - * -");	    
	    }
    }
}
