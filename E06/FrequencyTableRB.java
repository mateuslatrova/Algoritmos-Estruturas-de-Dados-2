/******************************************************************************
 *  Compilation:  javac FrequencyTableRB
 *  Dependencies: StdIn.java StdOut.java RedBlackBST.java
 *  Execution:    java FrequencyTableRB < words.txt
 *  Data file:    http://introcs.cs.princeton.edu/java/44st/mobydick.txt
 *
 *  Read in a list of words from standard input and print out
 *  each word and the number of times it appears.
 *
 * $ java-algs4 FrequencyTableRB < DATA/Gutenberg/mobydick.txt | head -n8
 *      132  A
 *        7  AD
 *        1  AHAB
 *        4  ALL
 *        1  AND
 *        2  AZORE
 *        1  Abashed
 *        1  Abednego
 * $ java-algs4 FrequencyTableRB < DATA/Gutenberg/mobydick.txt | sort -nr | head -n8
 *    13553  the
 *     6409  of
 *     5936  and
 *     4463  a
 *     4446  to
 *     3835  in
 *     2871  that
 *     2438  his
 ******************************************************************************/

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.RedBlackBST;

public class FrequencyTableRB {
    private RedBlackBST<String, Integer> st = new RedBlackBST<String, Integer>();

    public void click(String key) {
        int count = count(key);
        st.put(key, count + 1);
    }

    // return the number of times the key appears
    public int count(String key) {
        if (!st.contains(key)) return 0;
        else return st.get(key);
    }

    // print table to standard output
    public void show() {
        for (String key : st.keys()) {
	    // StdOut.println(count(key) + " " + key);
	    StdOut.printf("%8d  %s\n", count(key), key);
        }
    }

    public static void main(String[] args) {

        // build frequency table from words on standard input
        FrequencyTableRB freq = new FrequencyTableRB();
        while (!StdIn.isEmpty()) {
            String key = StdIn.readString();
	    key = key.replaceAll("[^\\p{IsAlphabetic}]", "");
	    // key = key.replaceAll("[^\\p{IsAlphabetic}\\p{IsDigit}]", "");
	    if (key.equals("")) continue;
	    // key = key.toLowerCase();
            freq.click(key);
        }

        // print frequency table to standard output
        freq.show();

    }

}

