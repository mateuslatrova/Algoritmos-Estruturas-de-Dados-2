/*********************************************************************

  AO PREENCHER ESSE CABEÇALHO COM O MEU NOME E O MEU NÚMERO USP,
  DECLARO QUE SOU O ÚNICO AUTOR E RESPONSÁVEL POR ESSE PROGRAMA.
  TODAS AS PARTES ORIGINAIS DESSE EXERCÍCIO-PROGRAMA (EP) FORAM
  DESENVOLVIDAS E IMPLEMENTADAS POR MIM SEGUINDO AS INSTRUÇÕES DESSE
  EP E QUE PORTANTO NÃO CONSTITUEM PLÁGIO. DECLARO TAMBÉM QUE SOU
  RESPONSÁVEL POR TODAS AS CÓPIAS DESSE PROGRAMA E QUE EU NÃO
  DISTRIBUI OU FACILITEI A SUA DISTRIBUIÇÃO. ESTOU CIENTE DE QUE OS
  CASOS DE PLÁGIO SÃO PUNIDOS COM REPROVAÇÃO DIRETA NA DISCIPLINA.

  NOME: Mateus Latrova Stephanin
  NUSP: 12542821

  Referências: 
    - API das classes Queue, StdDraw, UF, Point2D e Interval1D presen-
    tes em: https://algs4.cs.princeton.edu/code/javadoc/edu/princeton/cs/algs4/
    - GridDeluxe.java - meu código foi fortemente baseado nessa implementação.
    - vídeo sobre o método de bissecção: https://www.youtube.com/watch?v=OzFuihxtbtA
    - página que visitei para aprender como arredondar números reais sem truncar:
    https://www.educative.io/edpresso/how-to-convert-a-double-to-int-in-java
*********************************************************************/
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.UF;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.Interval1D;

public class Threshold {

    // Constants:
    private static final boolean numericalOnly = false;
    private static final boolean graphicalAndNumerical = true;
    
    // Attribute:
    private static boolean executionMode;

    private static class Vertex {
        public Point2D point;
        public int index;

        private Vertex(Point2D p, int i) {
            point = p;
            index = i;
        }
    }

    public static void main(String[] args) {
	    executionMode = numericalOnly;
        if (args.length >= 1)
            executionMode = graphicalAndNumerical;
        
        Queue<Vertex> vertices = new Queue<Vertex>();
        Interval1D interval = new Interval1D(0, Math.sqrt(2));

        // Reading input and creating a correspondence between indexes of vertices
        // in the queue and in the UF.
        int cnt = 0;
        while (!StdIn.isEmpty()) {
            double x = StdIn.readDouble();
	        double y = StdIn.readDouble();
	        Vertex v = new Vertex(new Point2D(x, y), cnt);
            vertices.enqueue(v);
            cnt++;
        }

        int numOfVertices = vertices.size();

        while (interval.length() > 1E-9) {
            double d = (interval.max() + interval.min())/2;
            int rows = (int) Math.ceil(1.0 / d);    // # rows in grid
            int cols = (int) Math.ceil(1.0 / d);    // # columns in grid
            UF graph = new UF(numOfVertices);
            
            Queue<Vertex>[][] grid = (Queue<Vertex>[][]) new Queue[rows+2][cols+2];
            for (int i = 0; i <= rows+1; i++) {
                for (int j = 0; j <= cols+1; j++)
                    grid[i][j] = new Queue<Vertex>();
            }

            for (Vertex v : vertices) {
                int row = 1 + (int) (v.point.x() * rows);
                int col = 1 + (int) (v.point.y() * cols);
                for (int i = row-1; i <= row+1; i++) {
                    for (int j = col-1; j <= col+1; j++) {
                        for (Vertex u : grid[i][j]) {
                            double currDist = v.point.distanceTo(u.point);
                            if (currDist <= d && graph.find(v.index) != graph.find(u.index))
                                graph.union(v.index, u.index);
                        }
                    }
                    grid[row][col].enqueue(v);
                }
            }

            if (graph.count() != 1) {
                Interval1D aux = new Interval1D(d, interval.max());
                interval = aux;
            }else {
                Interval1D aux = new Interval1D(interval.min(),d);
                interval = aux;
            }
        }

        String output = "Connectivity threshold in " + interval.toString();
        StdOut.println(output);
        
        if (executionMode == graphicalAndNumerical) {
            double d = (interval.max() + interval.min())/2;
            double maxDist = interval.max();
            int rows = (int) Math.ceil(1.0 / d);  
            int cols = (int) Math.ceil(1.0 / d);
            
            StdDraw.setCanvasSize(800, 800);
            StdDraw.setXscale(0, 1);
            StdDraw.setYscale(0, 1);
            StdDraw.enableDoubleBuffering();	
	        StdDraw.setPenColor(StdDraw.BLACK);
	        StdDraw.setPenRadius(0.005);
            
            Queue<Point2D>[][] grid = (Queue<Point2D>[][]) new Queue[rows+2][cols+2];
            for (int i = 0; i <= rows+1; i++) {
                for (int j = 0; j <= cols+1; j++)
                    grid[i][j] = new Queue<Point2D>();
            }

	        for (Vertex v : vertices) {
	            double x = v.point.x();
	            double y = v.point.y();
	            Point2D p  = new Point2D(x, y);
                int row = 1 + (int) (x * rows);
                int col = 1 + (int) (y * cols);
                for (int i = row-1; i <= row+1; i++) {
                    for (int j = col-1; j <= col+1; j++) {
                        for (Point2D q : grid[i][j]) {
                            if (p.distanceTo(q) <= maxDist) {
                                StdDraw.setPenColor(StdDraw.BLACK);
                                StdDraw.setPenRadius(0.002);
                                if (interval.contains(p.distanceTo(q)))
                                    StdDraw.setPenColor(StdDraw.RED);
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
}
