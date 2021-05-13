/*

  From S & W

 */

public class Counter {

    private static void print(int[] a) {
	for (int i = 0; i < a.length; i++) 
	    StdOut.print(a[i] + " ");
	StdOut.println();
    }

    private static void enumerate(int[] a, int k) {
	if (k == a.length) {
	    print(a);
	    return;
	}
	enumerate(a, k+1);
	a[k] = 1;
	enumerate(a, k+1);
	a[k] = 0;
    }

    public static void enumerate(int N) {
	int[] a = new int[N];
	enumerate(a, 0);
    }

    public static void main(String[] args) {
	int N = Integer.parseInt(args[0]);
	enumerate(N);
    }
    
}
