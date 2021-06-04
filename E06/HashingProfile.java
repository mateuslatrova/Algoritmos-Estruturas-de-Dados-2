import edu.princeton.cs.algs4.LinearProbingHashST;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.In;
import java.lang.Math;

public class HashingProfile {
    
    private static int k;
    private static int M;
    private static int N;
    private static int logM;
    private static int hits;
    private static boolean prime;     // to choose the type of hash table.
    private static boolean universal; // to choose the type of hash table.
    private static Mode mode;
    private static LinearProbingHashST hashT; // values are hashing function values.
    private static FrequencyTableLP freq; 
    private static Queue<Integer> hash;

    enum Mode { prime, primeAndUniv, universal, std };

    // Simple:
    private static int hash(String key) {
        int h = key.hashCode();
        h ^= (h >>> 20) ^ (h >>> 12) ^ (h >>> 7) ^ (h >>> 4);
        return h & (m-1);
    }
    
    // Prime/PrimeAndUniv/Universal:
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
            if (n % i == 0) {
                return false;
            }
        }
        return true;
    }

    private static int findClosestPrime(int k) {
        int m = Math.pow(2, k);
        while (!isPrime(m))
            m -= 1;
        return m;
    }

    private static void chooseMode(String[] args) {
        if (args[1] == "-p")
            prime = true;
        if (args[2] == "-u")
            universal = true;

        if (prime) {
            if (universal)
                mode = Mode.primeAndUniv;
            else
                mode = Mode.prime;
        } else {
            if (universal)
                mode = Mode.universal;
            else 
                mode = Mode.std;
        }
    }

    // Copied from Histogram.java()
    public static void plotBars(double[] a) {
        int n = a.length;
        StdDraw.setXscale(-.5, n - .5);
        for (int i = 0; i < n; i++) {
            StdDraw.filledRectangle(i, a[i]/2, 0.35, a[i]/2);
        }
    }

    public static void main(String[] args) {
        k = Integer.parseInt(args[0]);
        
        chooseMode(args);

        hashT = new LinearProbingHashST<String,Integer>();
        freq = new FrequencyTableLP();

        if (mode == Mode.std || mode == Mode.universal) {
            M = Math.pow(2,k);
            logM = Math.log((double) M);
        } else {
            M = findClosestPrime(k);
            logM = Math.log((double) M);
        }

        In in = new In();
        Words words = new words(in);
        String word = words.nextWord();

        while (word != null) {
            freq.click(word);
            if (mode == Mode.std) {
                hashT.put(word,hash(word));
            } else {
                hashT.put(word,hashU(word,M));
            }
            word = words.nextWord();
        }

        N = hashT.size(); // number of different words in the ST.

        // first output:
        StdOut.printf("M = %d\n", M);
        StdOut.printf("M log M = %f\n", M*logM);
        StdOut.printf("N = %d\n", N);
        StdOut.printf("N/M = %f\n", N/M);

        // finding and printing hits value:
        SET<Integer> aux = new SET<Integer>();
        aux.add(hash.peek());
        hits = 1;
        for (Integer x : hash) {
            if (!aux.contains(x)) {
                hits++;
                aux.add(x);
            }
        }

        StdOut.printf("hits = %d", hits);
    
        // draw histogram.
        if (args.length >= 4) {
            StdDraw.setCanvasSize(800,800);
            StdDraw.setXscale(-M/10.0, 1.1*M - 1);
            StdDraw.setYscale(-.1, 1.1);

            double hashFreq[] = new int[M];
            double max = 0.;

            // Getting frequencies and finding the maximum one.
            for (int i = 0; i < M; i++) {
                for (String s: hashT.keys()) {
                    if (hashT.get(s) == i)
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

        // print frequencies and hashes:
        if (args.length >= 5) {
            Queue<String> keys = hashT.keys();

            for (int i = 0; i < M; i++) {
                Queue<String> wordsByHash = new Queue<String>();
                for (String s: keys) {
                    if (hashT.get(s) == i) 
                        wordsByHash.enqueue(s);
                }

                int f = wordsByHash.length; // frequency of hashing value.
                if (f > 0) {
                    StdOut.printf("[ %d] %d:\n", f, i);
                    for (String s : wordsByHash) {
                        StdOut.println(s);
                    }
                }
            }
        }
        // end main.
    }
}
