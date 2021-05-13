import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Filter2 {

    public static void main(String[] args) {
	while (!StdIn.isEmpty()) {
	    String line = StdIn.readLine();
	    int start = line.indexOf(" from ") + 6;
	    StdOut.println(line.substring(start));
	}
    }
    
}
