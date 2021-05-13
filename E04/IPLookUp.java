/*************************************************************************
 *
 * $ java-algs4 IPLookUp DATA/dbip-city-lite-2020-05.IPv4.csv < DATA/10IPs.in
 * $ java-algs4 Filter < DATA/accessips | java-algs4 IPLookUp DATA/dbip-city-lite-2020-05.IPv4.csv > accesses.out
 * $ java-algs4 Filter2 < DATA/ssh.log | java-algs4 IPLookUp DATA/dbip-city-lite-2020-05.IPv4.csv > ssh.out
 *
 *************************************************************************/

import java.util.Arrays;
import java.util.Collections;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.Stopwatch;
import edu.princeton.cs.algs4.ST;
import edu.princeton.cs.algs4.SET;

public class IPLookUp {

    public static void main(String[] args) {

	Stopwatch sw = new Stopwatch();
	GeoLocator gl = new GeoLocator(args[0]);
	System.err.println("Time to produce geolocator: " + sw.elapsedTime());
	
	ST<Location, Integer> summary = new ST<Location, Integer>();
	SET<String> notFound = new SET<String>();

	sw = new Stopwatch();
	while (StdIn.hasNextLine()) {
	    String q = StdIn.readLine();
	    Location loc = gl.location(q);
	    if (loc == null) {
		notFound.add(q);
		continue;
	    }
	    if (summary.contains(loc)) summary.put(loc, summary.get(loc) + 1);
	    else summary.put(loc, 1);
	}
	System.err.println("Time to produce unordered summary: " + sw.elapsedTime());
	
	sw = new Stopwatch();	
	String[] output = new String[summary.size()];
	int i = 0;
	for (Location t : summary)
	    output[i++] = String.format("%8d: %s", summary.get(t), t);
	Arrays.sort(output, Collections.reverseOrder());
	System.err.println("Time to order the summary: " + sw.elapsedTime());
	
	for (String l : output) StdOut.println(l);

	if (!notFound.isEmpty()) {
	    StdOut.println("\nNot found:");
	    for (String q : notFound) StdOut.println("    " + q);
	}
    } 
} 
