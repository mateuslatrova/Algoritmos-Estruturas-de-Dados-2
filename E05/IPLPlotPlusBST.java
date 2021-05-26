import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class IPLPlotPlusBST {

    public static double H(int N) {
	double h = 0.0;
	for (int i = N; i > 0; i--)
	    h += 1.0/i;
	return h;
    }

    public static void main(String args[]) {
	int[] seq = HeightPlotPlusBST.readOpSequence();
	int N = seq.length;
	Plotter plotter = new Plotter(N, 2.0);
	BSTPlus<Integer, Integer> bst = new BSTPlus<>();

	double s = 0.0;
	for (int i = 0; i < N; i++) {
	    int x = seq[i];
	    if (x > 0)
		bst.put(x, 0);
	    else 
		bst.delete(-x);
	    int M = bst.size();
	    double f = M > 1 ? bst.ipl() / (M*(2*Math.log(M) - 2.8455686702)) : 1.0;
	    // 2.8455686702 = 2(2 - \gamma), where \gamma = Euler's constant
	    s += f;
	    plotter.addDataValue(f);
	} 

    	int M = bst.size();
	StdOut.println("N: " + M);
	StdOut.println("IPL: " + bst.ipl());
	StdOut.println("IPL/N: " + 1.0*bst.ipl()/M);
	StdOut.println("Expected: " + (2*(H(M+1) - 1)*(1+1.0/M) - 2));
	StdOut.println("Average ratio: " + s/N);
    }
}
