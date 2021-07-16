/**
 * $ java-algs4 TwoClause 3 6
 * -3 -2
 * -1 1
 * -3 3
 * 2 3
 * -1 2
 * -3 2
 * $ 
 */

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class TwoClause
{
    private int a;
    private int b;
    private int hash;

    public TwoClause(int a, int b) {
	this.a = Math.min(a, b);
	this.b = Math.max(a, b);
    }
    
    public TwoClause(int N) {
	int aa = 1 + StdRandom.uniform(N);
	aa = StdRandom.bernoulli(.5) ? -aa : aa;
	int bb;
	do {
	    bb = 1 + StdRandom.uniform(N);
	    bb = StdRandom.bernoulli(.5) ? -bb : bb;
	} while (bb == aa);
	a = Math.min(aa, bb);
	b = Math.max(aa, bb);
    }

    public int a() { return a; }
    public int b() { return b; }

    public boolean equals(Object other) {
        if (other == this) return true;
        if (other == null) return false;
        if (other.getClass() != this.getClass()) return false;
        TwoClause that = (TwoClause) other;
        return this.a == that.a && this.b == that.b;
    }

    public int hashCode() {
	if (hash != 0) return hash;
	hash = 31*a + b;
        return hash;
    }

    public String toString() {
	return a + " " + b;
    }
    
    public static void main(String[] args)
    {
	int N = Integer.parseInt(args[0]);
	int M = Integer.parseInt(args[1]);

	for (int i = 0; i < M; i++) {
	    StdOut.println(new TwoClause(N));
	}
    }
}
