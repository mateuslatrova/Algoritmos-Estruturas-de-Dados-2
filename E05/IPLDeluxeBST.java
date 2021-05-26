import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class IPLDeluxeBST {

    public static double H(int N) {
	double h = 0.0;
	for (int i = N; i > 0; i--)
	    h += 1.0/i;
	return h;
    }

    public static void main(String args[]) {

	BSTDeluxe<Integer, Integer> bst = new BSTDeluxe<>();

	double s = 0.0;
	int T = 0;
	while (!StdIn.isEmpty()) {
	    int x = StdIn.readInt();
	    if (x > 0)
		bst.put(x, 0);
	    else 
		bst.delete(-x);
	    int N = bst.size();
	    double f = N > 1 ? bst.ipl() / (N*(2*Math.log(N) - 2.8455686702)) : 1.0;
	    // 2.8455686702 = 2(2 - \gamma), where \gamma = Euler's constant
	    s += f; T++;
	    } 

    	int N = bst.size();
	StdOut.println("N: " + N);
	StdOut.println("IPL: " + bst.ipl());
	StdOut.println("IPL/N: " + 1.0*bst.ipl()/N);
	StdOut.println("Expected: " + (2*(H(N+1) - 1)*(1+1.0/N) - 2));
	StdOut.println("Average ratio: " + s/T);
    }
}
