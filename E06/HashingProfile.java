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
    - API das classes SET e Quick3string presentes em: 
    https://algs4.cs.princeton.edu/code/javadoc/edu/princeton/cs/algs4/
    - Para plotar o histograma, me baseei no programa Histogram.java
    (presente em https://www.ime.usp.br/~yoshi/2021i/mac0323/sandbox/2021.05.20/)
    e na resposta do professor Yoshi na seguinte dúvida do fórum de discussões:
    https://edisciplinas.usp.br/mod/forum/discuss.php?d=668038
    - as funções de Hashing usadas(hash, hashU e hashPrime) são uma
    cópia das presentes nos arquivos .java presentes nos anexos do exercício.
*********************************************************************/

import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.LinearProbingHashST;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Quick3string;
import java.lang.Math;

public class HashingProfile {
    
    private static LinearProbingHashST<String,Integer> HT;

    enum Mode { prime, primeAndUniv, universal, standard };

    // hash function from LinearProbingHashST
    private static int hash(String key, int M) {
        return (key.hashCode() & 0x7fffffff) % M;
    } 

    // hash function from LinearProbingHashPrimesST
    private static int hashPrime(String key, int M) {
        int t = key.hashCode() & 0x7fffffff;
        return t % M;
    }
    
    // hash function from LinearProbingHashPUHS and LinearProbingHashUHST
    private static int hashU(String key, int M) {
        int h = 0, a = 31415, b = 27183;
        String s = key;
        for (int i = 0; i < s.length(); i++, a = a*b % (M-1))
            h = ((a*h + s.charAt(i)) & 0x7fffffff) % M;
        return h;
    }

    private static boolean isPrime(int n) {
        if (n == 1) return false;
        for (int i = 2; i <= Math.sqrt(n); i++) {
            if (n % i == 0) 
                return false;
        }
        return true;
    }

    private static Integer findClosestPrime(Integer k) {
        Integer m = (int) Math.pow((double)2,(double) k);
        while (!isPrime(m)) {
            m -= 1;
        }
        return m;
    }

    private static Mode chooseMode(String[] args) {
        if (args[1].equals("-p") && args[2].equals("-u")) 
            return Mode.primeAndUniv;
        else if (args[1].equals("-p") && args[2].equals("-s"))  
            return Mode.prime;
        else if (args[1].equals("-2") && args[2].equals("-u"))
            return Mode.universal;
        else
            return Mode.standard;
    }

    private static void initializeHashTable(Mode mode, int M) {
        HT = new LinearProbingHashST<String,Integer>(M);
    }

    private static int findM(Mode mode, int k) {
        if (mode.equals(Mode.standard) || mode.equals(Mode.universal))
            return (int) Math.pow((double)2,(double) k); 
        else
            return findClosestPrime(k);
    }

    private static double findLogM(int M) {
        return Math.log((double) M);
    }

    private static void fillHashTable(Mode mode, Integer M) {
        In in = new In();
        Words words = new Words(in);
        String word = words.nextWord();
        int m;

        switch (mode) {
            case standard:        
                while (word != null) {
                    HT.put(word,hash(word,M));
                    word = words.nextWord();
                }
                break;
            case universal:
                while (word != null) {
                    HT.put(word,hashU(word,M)); 
                    word = words.nextWord();    
                }
                break;
            case prime:
                while (word != null) {
                    HT.put(word,hashPrime(word,M));
                    word = words.nextWord();
                }
                break;    
            case primeAndUniv:
                while (word != null) {
                    HT.put(word,hashU(word,M)); 
                    word = words.nextWord();
                }
                break;
        }
    }

    private static int findN() {
        return HT.size();
    }

    private static void firstOutput(Integer M, Double logM, Integer N) {
        StdOut.printf("M = %d\n", M);
        StdOut.printf("M log M = %.15f\n", M*logM);
        StdOut.printf("N = %d\n", N);
        StdOut.printf("N/M = %.15f\n", N/(double)M);
    }

    private static void findAndPrintHits(Mode mode) {
        SET<Integer> image = new SET<Integer>();
        int hits = 0;

        for (String s: HT.keys()) {
            int hashVal = HT.get(s);
            if (!image.contains(hashVal))
                image.add(hashVal);
        }

        hits = image.size();
        StdOut.printf("hits = %d\n", hits);
    }

    private static void drawHistogram(Mode mode, Integer M) {
        StdDraw.setCanvasSize(800,800);
        StdDraw.setXscale(-M/10.0, 1.1*M - 1);
        StdDraw.setYscale(-.1, 1.1);
        double hashFreq[] = new double[M];
        double max = 0.;

        for (int i = 0; i < M; i++) {
            for (String s: HT.keys()) {
                if (HT.get(s) == i)
                    hashFreq[i]++;
            }
            if (hashFreq[i] > max) max = hashFreq[i];
        }
        
        // Normalizing and drawing.
        for (int i = 0; i < M; i++) {
            hashFreq[i] /= max;
            StdDraw.filledRectangle(i, hashFreq[i]/2, 0.25, hashFreq[i]/2);
        }
    }

    private static void secondOutput(Mode mode, Integer M) {
        Iterable<String> keys;
                keys = HT.keys();

        for (int i = 0; i < M; i++) {
            Queue<String> aux = new Queue<String>();
            String[] wordsByHash;
            int f = 0;
            for (String s: keys) {
                if (HT.get(s) == i) { 
                    aux.enqueue(s);
                    f++;
                }
            }
            
            wordsByHash = new String[f];

            for (int j = 0; j < f; j++) 
                wordsByHash[j] = aux.dequeue();
            
            Quick3string.sort(wordsByHash); // sorting
            
            // printing:
            if (f > 0) {
                StdOut.printf("[ %d] %d:\n", f, i); 
                for (String s : wordsByHash)
                    StdOut.printf("  %s\n", s);     
            }
        }
    }

    public static void main(String[] args) {
        Integer k =   Integer.parseInt(args[0]);
        Mode mode =   chooseMode(args);
        Integer M =   findM(mode,k); // size of hash table
        Double logM = findLogM(M);

        initializeHashTable(mode,M);
        fillHashTable(mode,M);

        Integer N = findN(); // number of different words in the ST.

        firstOutput(M,logM,N);
        findAndPrintHits(mode);
    
        if (args.length >= 4) 
            drawHistogram(mode,M);

        if (args.length >= 5) 
            secondOutput(mode,M);
    }

}
