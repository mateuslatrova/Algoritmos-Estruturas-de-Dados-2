import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class IPLDeluxeRB {

    public static void main(String args[]) {

	RedBlackBSTDeluxe<Integer, Integer> rbst = new RedBlackBSTDeluxe<>();

	double s = 0.0;
	int T = 0;
	while (!StdIn.isEmpty()) {
	    int x = StdIn.readInt();
	    if (x > 0)
		rbst.put(x, 0);
	    else 
		rbst.delete(-x);
	    int N = rbst.size();
	    double f = N > 1 ? rbst.ipl() / (N*(Math.log(N)/Math.log(2))) : 1.0;
	    s += f; T++;
	    } 

    	int N = rbst.size();
	StdOut.println("N: " + N);
	StdOut.println("IPL: " + rbst.ipl());
	StdOut.println("IPL/N: " + 1.0*rbst.ipl()/N);
	StdOut.println("lg N: " + Math.log(N)/Math.log(2));
	StdOut.println("Average ratio: " + s/T);
    }
}
