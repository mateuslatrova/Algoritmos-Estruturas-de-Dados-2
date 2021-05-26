import java.awt.Color;
import edu.princeton.cs.algs4.StdDraw;

public class Plotter {
    private int N;
    private Color color; 
    
    public Plotter(int N, double max) {
	this(N, max, StdDraw.BLACK);
    }

    public Plotter(int N, double min, double max) {
	this(N, min, max, StdDraw.BLACK);
    }

    public Plotter(int N, double max, Color color) {
	this(N, 0, max, color);
    }

    public Plotter(int N, double min, double max, Color color) {
        StdDraw.setXscale(0, N);
        StdDraw.setYscale(min, max);
        StdDraw.setPenRadius(.005);
	this.color = color;
    }

    public void addDataValue(double val) {
	N++;
        StdDraw.setPenColor(color);
        StdDraw.point(N, val);
    }
    
    public static void main(String args[]) {
	int T = 100;

	Plotter p = new Plotter(T, -2, 2, StdDraw.RED);
	for (int i = 1; i <= T; i++) {
	    double x = Math.sin(2*Math.PI*i/T);
	    p.addDataValue(x);
	}

	Plotter q = new Plotter(T, -2, 2, StdDraw.BLUE);
	for (int i = 1; i <= T; i++) {
	    double x = Math.cos(4*Math.PI*i/T);
	    q.addDataValue(x);
	}
	
    }
}
