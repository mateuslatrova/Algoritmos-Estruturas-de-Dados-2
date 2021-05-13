/**********************************************************************
 *
 * $ cat ipconv.in
 * 117.37.36.118
 * 1881612713
 * [...]
 *
 * $ java-algs4 IPConv < ipconv.in
 * 117.37.36.118 / 1965368438 / 117.37.36.118
 * 1881612713 / 112.39.33.169 / 1881612713
 * [...]
 * 
 **********************************************************************/

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class IPConv {

    public static long noip(String s) {
    	String[] d = s.split("\\.");
    	long ip = 0;
    	for (String t : d) {
    	    ip <<= 8;
    	    ip += Integer.parseInt(t);
    	}
    	return ip; 
    }

    public static String ip(long x) {
	String ip = "";
	long t = 0xff000000;
	ip += ((x & t) >> 24) + ".";
	t = 0x00ff0000;
	ip += ((x & t) >> 16) + ".";
	t = 0x0000ff00;
	ip += ((x & t) >> 8) + ".";
	t = 0x000000ff;
	ip += x & t;
    	return ip;
    }
    
    public static void main(String[] args) {

	while (!StdIn.isEmpty()) {
	    String t = StdIn.readLine();
	    if (t.contains(".")) {
		long nt = noip(t);
		String tt = ip(nt);
		StdOut.println(t + " / " + nt + " / " + tt);
	    } else {
		long tl = Long.parseLong(t);
		String ip = ip(tl);
		long ttl = noip(ip);
		StdOut.println(tl + " / " + ip + " / " + ttl);
	    }
	}

    }

}
