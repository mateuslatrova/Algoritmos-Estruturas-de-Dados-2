/**
 * $ java-algs4 ImplicationGraph DATA/p8.txt < DATA/ass_p8_unsat.txt 
 * 7 variables
 * 22 implications
 * 1:
 * -1: -5 -4 -3
 * [...]
 * -7: -5 -3 2
 * Assignment: false false false false false false false 
 * 7 variables
 * 22 implications
 * 1 [false]: 
 * -1 [true]: -5 [true] -4 [true] -3 [true] 
 * 2 [false]: 
 * -2 [true]: 7 [false] 
 * Truth value of 2SAT: false
 * $ java-algs4 ImplicationGraph DATA/p8.txt < DATA/ass_p8_sat.txt 
 * 7 variables
 * 22 implications
 * 1:
 * -1: -5 -4 -3
 * [...]
 * -7: -5 -3 2
 * Assignment: false false true false false false true 
 * 7 variables
 * 22 implications
 * 1 [false]: 
 * -1 [true]: -5 [true] -4 [true] -3 [true] 
 * 2 [true]: 
 * -2 [false]: 7 [false] -5 [true] -4 [true] 
 * 3 [false]: 7 [false] 1 [false] 
 * -3 [true]: 6 [true] 
 * 4 [false]: 2 [true] 1 [false] 
 * -4 [true]: 6 [true] 
 * 5 [false]: 7 [false] 2 [true] 1 [false] 
 * -5 [true]: 6 [true] 
 * 6 [true]: 
 * -6 [false]: 5 [false] 4 [false] 3 [false] 
 * 7 [false]: 
 * -7 [true]: -5 [true] -3 [true] 2 [true] 
 * Truth value of 2SAT: true
 * $ 
 */ 

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.Digraph;

public class ImplicationGraph
{
    private final int N;      // N variables
    private final Digraph g;  // V = 2N; one vertex per literal

    // in contains a 2SAT formula
    public ImplicationGraph(In in) {
	N = in.readInt();
	g = new Digraph(2*N);
	// use SET to avoid repetition of arcs
	SET<Integer>[] out = (SET<Integer>[]) new SET[2*N];
	for (int i = 0; i < 2*N; i++) out[i] = new SET<Integer>();
	while (!in.isEmpty()) {
	    int l = in.readInt();
	    int ll = in.readInt();
	    out[vertex(-l)].add(vertex(ll));
	    out[vertex(-ll)].add(vertex(l));
	}
	for (int i = 0; i < 2*N; i++)
	    for (int j : out[i]) 
		g.addEdge(i, j);
    }

    // f contains a 2SAT formula
    public ImplicationGraph(int[] f) {
	N = f[0];
	int M = (f.length - 1)/2; // number of clauses
	g = new Digraph(2*N);
	// use SET to avoid repetition of arcs
	SET<Integer>[] out = (SET<Integer>[]) new SET[2*N];
	for (int i = 0; i < 2*N; i++) out[i] = new SET<Integer>();
	for (int i = 1; i <= M; i++) {
	    int l = f[2*i - 1];
	    int ll = f[2*i];
	    out[vertex(-l)].add(vertex(ll));
	    out[vertex(-ll)].add(vertex(l));
	}
	for (int i = 0; i < 2*N; i++)
	    for (int j : out[i]) 
		g.addEdge(i, j);
    }
    
    public int N() { return N; }

    public Digraph g() { return g; }

    // variable x_i gets value s[i] (i = 1, ..., N)
    public boolean eval(boolean[] s) {
	for (int i = 0; i < g.V(); i++) {
	    boolean ii; 
	    if (literal(i) < 0) ii = !s[-literal(i)];
	    else ii = s[literal(i)];
	    for (int j: g.adj(i)) {
		boolean jj;
		if (literal(j) < 0) jj = !s[-literal(j)];
		else jj = s[literal(j)];
		if (!(!ii || jj)) return false;
	    }
	}
	return true;
    }

    public void evalFull(boolean[] s) {
	StdOut.print("Assignment: ");
	for (int i = 1; i <= N; i++) 
	    StdOut.print(s[i] + " ");
	StdOut.println();
	StdOut.println(N + " variables\n" + g.E() + " implications");
	for (int i = 0; i < g.V(); i++) {
	    boolean ii; 
	    if (literal(i) < 0) ii = !s[-literal(i)];
	    else ii = s[literal(i)];
	    StdOut.print(literal(i) + " [" + ii + "]" + ": ");	    
	    for (int j: g.adj(i)) {
		boolean jj;
		if (literal(j) < 0) jj = !s[-literal(j)];
		else jj = s[literal(j)];
		StdOut.print(literal(j) + " [" + jj + "]" + " ");
		if (!(!ii || jj)) {
		    StdOut.println();
		    return;
		}
	    }
	    StdOut.println();
	}
    }

    public String toString() {
	StringBuilder s = new StringBuilder();
	s.append(N + " variables\n" + g.E() + " implications\n");
	for (int i = 0; i < g.V(); i++) {
	    s.append(literal(i) + ":");
	    for (int j: g.adj(i))
		s.append(" " + literal(j));
	    s.append('\n');
	}
	return s.toString();
    }

    // variables are, say, x_1, ..., x_N
    // literal i (that is, x_i) maps to vertex 2i - 2
    // literal -i (that is, negation of x_i) maps to vertex 2i - 1
    public int vertex(int l) {
	if (l > 0) return 2*l - 2;
	else return 2*(-l) - 1;
    }

    // variables are, say, x_1, ..., x_N
    // vertex j, if even, maps to literal (j + 2)/2
    // vertex j, if odd, maps to literal -(j + 1)/2 = - floor of (j + 2)/2
    public int literal(int j) {
	if (j % 2 == 0) return (j + 2)/2;
	else return -(j + 2)/2;
	
    }
    
    public static void main(String[] args)
    {
	In in = new In(args[0]);
	ImplicationGraph impgr = new ImplicationGraph(in);
	StdOut.print(impgr);
	// read truth assignment
	// s[i] is the value of x_i (1 <= i <= N)
	boolean[] s = new boolean[impgr.N() + 1];
	for (int i = 1; i <= impgr.N(); i++) s[i] = StdIn.readBoolean();
	impgr.evalFull(s);
	StdOut.println("Truth value of 2SAT: " + impgr.eval(s));
    }
}
