/******************************************************************************
 *  See Web Exercise 45 (Gridding) at https://algs4.cs.princeton.edu/13stacks/
 * 
 *  This version produces a picture.
 * 
 * $ java-algs4 RandomPoints 100 323 | java-algs4 GridDeluxe .1 &
 * $ java-algs4 RandomPoints 100 323 | java-algs4 GridDeluxe .15 &
 * $ java-algs4 RandomPoints 100 323 | java-algs4 GridDeluxe .2 &
 * 
 * $ java-algs4 RandomPoints 10000 323 | java-algs4 GridDeluxe .01 &
 * $ java-algs4 RandomPoints 10000 323 | java-algs4 GridDeluxe .015 &
 * $ java-algs4 RandomPoints 10000 323 | java-algs4 GridDeluxe .02 &
 ******************************************************************************/

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.StdDraw;

public class GridDeluxe {

    public static void main(String[] args) {
	    double d = Double.parseDouble(args[0]);

        int rows = (int) (1.0 / d);    // # rows in grid
        int cols = (int) (1.0 / d);    // # columns in grid
    
        StdDraw.setCanvasSize(800, 800);
        StdDraw.setXscale(0, 1);
        StdDraw.setYscale(0, 1);
        StdDraw.enableDoubleBuffering();	

	    // for sanity check
        StdDraw.setPenColor(StdDraw.RED);
	    StdDraw.setPenRadius(0.02);
	    (new Point2D(.0, .0)).drawTo(new Point2D(d, .0));
	    StdDraw.setPenColor(StdDraw.BLACK);
	    StdDraw.setPenRadius(0.005);

        // initialize data structure
        Queue<Point2D>[][] grid = (Queue<Point2D>[][]) new Queue[rows+2][cols+2];
        for (int i = 0; i <= rows+1; i++) {
            for (int j = 0; j <= cols+1; j++)
                grid[i][j] = new Queue<Point2D>();
        }

	    while (!StdIn.isEmpty()) { 
	        double x = StdIn.readDouble();
	        double y = StdIn.readDouble();
	        Point2D p  = new Point2D(x, y);

            int row = 1 + (int) (x * rows);
            int col = 1 + (int) (y * cols);
            for (int i = row-1; i <= row+1; i++) {
                for (int j = col-1; j <= col+1; j++) {
                    for (Point2D q : grid[i][j]) {
                        if (p.distanceTo(q) <= d) {
	    	                StdDraw.setPenRadius(0.002);
	    	                p.drawTo(q);
	    	                StdDraw.setPenRadius(0.005);
	    	            } 
                    }
                }
                grid[row][col].enqueue(p);
	            p.draw();
            }
	        StdDraw.show();	
        }  
    } 
}
 
