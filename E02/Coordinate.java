import java.lang.Math;

public class Coordinate implements Comparable<Coordinate>{

    public static int first;
    public static int second;

    private double distanceToOrigin() {
        return Math.sqrt(first*first + second*second);
    }

    public Coordinate(int f, int s) {
        first = s;
        second = s;
    } 

    public int compareTo(Coordinate that) {
        if (this.distanceToOrigin() > that.distanceToOrigin())
            return 1;
        else if (this.distanceToOrigin() < that.distanceToOrigin())
            return -1;
        else return 0;
    }

    public int getFirst() {
        return first;
    }

    public int getSecond() {
        return second;
    }
}