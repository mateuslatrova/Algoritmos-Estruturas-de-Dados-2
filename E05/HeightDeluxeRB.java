import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class HeightDeluxeRB {

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
	    double f = N > 1 ? rbst.height() / Math.floor((Math.log(N)/Math.log(2))) : 1.0; 	    
	    s += f; T++;
	    } 

    	int N = rbst.size();
	StdOut.println("N: " + N);
	StdOut.println("Height: " + rbst.height());
	StdOut.println("Floor of lg N: " + Math.floor(Math.log(N)/Math.log(2)));
	StdOut.println("Average ratio: " + s/T);
    }
}
