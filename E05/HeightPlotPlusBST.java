import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.Queue;

public class HeightPlotPlusBST {

    public static int[] readOpSequence() {
	Queue<Integer> q = new Queue<>();

	while (!StdIn.isEmpty())
	    q.enqueue(StdIn.readInt());
	int[] s = new int[q.size()];
	for (int i = 0; i < s.length; i++)
	    s[i] = q.dequeue();
	return s;
    }

    public static void main(String args[]) {
	int[] seq = readOpSequence();
	int N = seq.length;
	Plotter plotter = new Plotter(N, 6.0);
	BSTPlus<Integer, Integer> bst = new BSTPlus<>();

	double s = 0.0;
	for (int i = 0; i < N; i++) {
	    int x = seq[i];
	    if (x > 0)
		bst.put(x, 0);
	    else
		bst.delete(-x);
	    int M = bst.size();
	    double f = M > 1 ? bst.height() / Math.floor((Math.log(M)/Math.log(2))) : 1.0; 
	    s += f;
	    plotter.addDataValue(f);
	    } 

    	int M = bst.size();
	StdOut.println("N: " + M);
	StdOut.println("Height: " + bst.height());
	StdOut.println("Floor of lg N: " + Math.floor(Math.log(M)/Math.log(2)));
	StdOut.println("Average ratio: " + s/N);
    }
}
