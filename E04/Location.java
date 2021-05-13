/**********************************************************************
 * 
 * $ java-algs4 Location
 * AU, Queensland, South Brisbane (-27.4748, 153.017)
 * AU, Victoria, Narre Warren (-38.0267, 145.301)
 * CN, Fujian, Fuzhou (26.1008, 119.295)
 * CN, Guangdong, Guangzhou (23.1322, 113.267)
 * JP, Tokyo, Shinjuku (16) (35.6978, 139.706)
 * 
 **********************************************************************/

import java.util.Arrays;

import edu.princeton.cs.algs4.StdOut;

public class Location implements Comparable<Location> {
    private String region;
    private String country;
    private String state;
    private String city;
    private double latitude;
    private double longitude;
    
    public Location(String region,
		    String country,
		    String state,
		    String city,
		    double latitude,
		    double longitude)  {
	this.region = region;
	this.country = country;
	this.state = state;
	this.city = city;
	this.latitude = latitude;
	this.longitude = longitude;
    }

    public int compareTo(Location that) {
	return this.toString().compareTo(that.toString());
    }

    public String toString() {
	return country + ", " + state + ", " + city 
	    + " (" + latitude + ", " + longitude + ")";
    }

    public static void main(String[] args) {
	Location[] loc = new Location[5];
	loc[0] = new Location("OC", "AU", "Queensland", "South Brisbane", -27.4748, 153.017);
	loc[1] = new Location("AS", "CN", "Fujian", "Fuzhou", 26.1008, 119.295);
	loc[2] = new Location("OC", "AU", "Victoria", "Narre Warren", -38.0267, 145.301);
	loc[3] = new Location("AS", "CN", "Guangdong", "Guangzhou", 23.1322, 113.267);
	loc[4] = new Location("AS", "JP", "Tokyo", "Shinjuku (16)", 35.6978, 139.706);

	Arrays.sort(loc);

	for (Location l : loc) StdOut.println(l);
    }
    
}
