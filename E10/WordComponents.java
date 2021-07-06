/**
 * $ head -n10 DATA/Pwords | java-algs4 WordComponents
 * Time to produce wordgraph: 0.009
 * 10 vertices and 4 edges
 * Ave degree: 0.8
 * 6 components
 * a à 
 * Aarão 
 * aba 
 * abacate abacates 
 * abacateiro abacateiros 
 * abacaxi abacaxis 
 * $ head -n1000 DATA/Pwords | java-algs4 WordComponents | head -n5
 * Time to produce wordgraph: 0.069
 * 1000 vertices and 1777 edges
 * Ave degree: 3.554
 * 128 components
 * a à 
 * Aarão 
 * $ 
 */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Graph;
import edu.princeton.cs.algs4.CC;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.Stopwatch;

public class WordComponents
{
    public static void main(String[] args) {
	    In in; 
	    if (args.length == 0) in = new In();
	    else  in = new In(args[0]);
	    Stopwatch sw = new Stopwatch();
        WordGraph wg = new WordGraph(in);
	    System.err.println("Time to produce wordgraph: " + sw.elapsedTime());
        Graph G = wg.graph();

	    System.err.println(G.V() + " vertices and " + G.E() + " edges");
	    System.err.println("Ave degree: " + 2.0*G.E()/G.V());

        CC cc = new CC(G);

        // number of connected components
        int m = cc.count();
        System.err.println(m + " components");

        // compute list of vertices in each connected component
        Queue<Integer>[] components = (Queue<Integer>[]) new Queue[m];
        for (int i = 0; i < m; i++) {
            components[i] = new Queue<Integer>();
        }
        for (int v = 0; v < G.V(); v++) {
            components[cc.id(v)].enqueue(v);
        }

        // print results
        for (int i = 0; i < m; i++) {
            for (int v : components[i]) {
                StdOut.print(wg.nameOf(v) + " ");
            }
            StdOut.println();
        }
    }
}
