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
    - consultei a documentação das seguintes classes apresentadas em aula:
        - StdOut: https://algs4.cs.princeton.edu/code/javadoc/edu/princeton/cs/algs4/StdOut.html
        - IndexMinPQ: https://algs4.cs.princeton.edu/code/javadoc/edu/princeton/cs/algs4/IndexMinPQ.html
        - Integer - https://docs.oracle.com/javase/7/docs/api/java/lang/Integer.html
        - Character - https://docs.oracle.com/javase/7/docs/api/java/lang/Character.html
    - recurso do enunciado: KnightOnePlain.java - o código foi forte-
    mente baseado nessa implementação. Foram feitas apenas algumas mu-
    danças(adição de novos atributos e métodos que ajudaram a resolver
    o problema dada a otimização necessária).
    - https://www.javatpoint.com/java-int-to-char (para melhor entendimento
    da conversão de tipos).

*********************************************************************/

import java.lang.Integer;
import java.lang.Character;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.IndexMinPQ;

public class KnightOneDeluxe {

    static int[][] board;
    static char[][] degrees; // number of moves possible in a given Coordinate.
    static boolean executionMode; // true -> shows board; false -> tells if found tour or not.                 
    static int N;
    static int[] iMove = {2, 1, -1, -2, -2, -1, 1, 2};
    static int[] jMove = {1, 2, 2, 1, -1, -2, -2, -1};

    private static void initializeBoard() {
	    board = new int[N][N];
    }

    private static void initializeDegrees() {
        degrees = new char[N][N];
    
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                int degree = 0;
                for (int t = 0; t < 8; t++) {
                    int ii = i + iMove[t];
                    int jj = j + jMove[t];
                    if (valid(ii, jj)) degree++;
                }
                degrees[i][j] = Character.forDigit(degree, 10);
            }
        }
    }

    private static void updateNeighbourDegrees(int i, int j) {
        for (int t = 0; t < 8; t++) {
            int ii = i + iMove[t];
            int jj = j + jMove[t];
            if (valid(ii, jj) && degrees[ii][jj] > '0')    
                degrees[ii][jj]--;
        }
    }

    private static void deupdateNeighbourDegrees(int i, int j) {
        for (int t = 0; t < 8; t++) {
            int ii = i + iMove[t];
            int jj = j + jMove[t];
            if (valid(ii, jj) && degrees[ii][jj] >= '0')    
                degrees[ii][jj]++;
        }
    }

    private static void findTours(int i, int j, int k) {
	    if (!valid(i, j) || board[i][j] != 0) return;
	    board[i][j] = k; // number of cells visited.
        char currentDegree = degrees[i][j];
        degrees[i][j] = '-';
        updateNeighbourDegrees(i, j);

	    if (k == N * N) {
            if (executionMode) printBoard();
            else StdOut.printf("There is a Knight's tour on an %dx%d board\n",N,N);
	        System.exit(0);
	    }
        
        // Defines priority to choose the next cell using degrees:
        IndexMinPQ<Character> degreePQ = new IndexMinPQ<Character>(8); 

        for (int t = 0; t < 8; t++) {
	        int ii = i + iMove[t];
	        int jj = j + jMove[t];
	        if (valid(ii, jj) && degrees[ii][jj] != '-') {
                char nextDegree = degrees[ii][jj]; 
                degreePQ.insert(t,nextDegree);
            }
	    }
        
        while (!degreePQ.isEmpty()) {
            int minDegreeIndex = degreePQ.minIndex();
            char minDegree = degreePQ.keyOf(minDegreeIndex);
            degreePQ.delete(minDegreeIndex);

            for (int t = 0; t < 8; t++) {
	            int ii = i + iMove[t];
	            int jj = j + jMove[t];
	            if (valid(ii, jj) && degrees[ii][jj] == minDegree) {
                    findTours(ii, jj, k+1);
                    break;
                }
	        }
        }
        
	    board[i][j] = 0;
        degrees[i][j] = currentDegree;
        deupdateNeighbourDegrees(i, j);
    }

    private static void findTours() {
	    initializeBoard();
        initializeDegrees();
	    for (int i = 0; i < N; i++) {
	        for (int j = 0; j < N; j++)
	    	    findTours(i, j, 1);
        }
    }

    private static void printBoard() {
	    for (int i = 0; i < N; i++) {
	        for (int j = 0; j < N; j++) 
	    	    StdOut.printf("%2d ", board[i][j]);
	        StdOut.println();
	    }
    }

    private static boolean valid(int i, int j) {
	    return 0 <= i && i < N && 0 <= j && j < N;
    }

    public static void main(String[] args) {
	    N = Integer.parseInt(args[0]);
        if (args.length <= 1)
            executionMode = true;
	    findTours();
	    StdOut.println("Found no tours");
    }
}
