import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdDraw;

public class IPLPlotDeluxeRB {

    public static double H(int N) {
	double h = 0.0;
	for (int i = N; i > 0; i--)
	    h += 1.0/i;
	return h;
    }

    public static void main(String args[]) {
	int[] seq = HeightPlotPlusRB.readOpSequence();
	int N = seq.length;
	StdDraw.enableDoubleBuffering();	
	Plotter plotter = new Plotter(N, 2.0);
	RedBlackBSTDeluxe<Integer, Integer> rbst = new RedBlackBSTDeluxe<>();

	double s = 0.0;
	for (int i = 0; i < N; i++) {
	    int x = seq[i];
	    if (x > 0)
		rbst.put(x, 0);
	    else 
		rbst.delete(-x);
	    int M = rbst.size();
	    double f = M > 1 ? rbst.ipl() / (M*(Math.log(M)/Math.log(2))) : 1.0;
	    s += f;
	    plotter.addDataValue(f);
	} 

	StdDraw.show();	
    	int M = rbst.size();
	StdOut.println("N: " + M);
	StdOut.println("IPL: " + rbst.ipl());
	StdOut.println("IPL/N: " + 1.0*rbst.ipl()/M);
	StdOut.println("lg N: " + Math.log(N)/Math.log(2));
	StdOut.println("Average ratio: " + s/N);
    }
}
