import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.Queue;

public class HeightPlotPlusRB {

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
	Plotter plotter = new Plotter(N, 3.0);
	RedBlackBSTPlus<Integer, Integer> rbst = new RedBlackBSTPlus<>();

	double s = 0.0;
	for (int i = 0; i < N; i++) {
	    int x = seq[i];
	    if (x > 0)
		rbst.put(x, 0);
	    else
		rbst.delete(-x);
	    int M = rbst.size();
	    double f = M > 1 ? rbst.height() / Math.floor((Math.log(M)/Math.log(2))) : 1.0; 
	    s += f;
	    plotter.addDataValue(f);
	    } 

    	int M = rbst.size();
	StdOut.println("N: " + M);
	StdOut.println("Height: " + rbst.height());
	StdOut.println("Floor of lg N: " + Math.floor(Math.log(M)/Math.log(2)));
	StdOut.println("Average ratio: " + s/N);
    }
}
