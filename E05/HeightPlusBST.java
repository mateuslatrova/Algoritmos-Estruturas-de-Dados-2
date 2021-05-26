import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class HeightPlusBST {

    public static void main(String args[]) {

	BSTPlus<Integer, Integer> bst = new BSTPlus<>();

	double s = 0.0;
	int T = 0;
	while (!StdIn.isEmpty()) {
	    int x = StdIn.readInt();
	    if (x > 0)
		bst.put(x, 0);
	    else
		bst.delete(-x);
	    int N = bst.size();
	    double f = N > 1 ? bst.height() / Math.floor((Math.log(N)/Math.log(2))) : 1.0;
	    s += f; T++;
	    } 

    	int N = bst.size();
	StdOut.println("N: " + N);
	StdOut.println("Height: " + bst.height());
	StdOut.println("Floor of lg N: " + Math.floor(Math.log(N)/Math.log(2)));	
	StdOut.println("Average ratio: " + s/T);
    }
}
