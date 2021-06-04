import edu.princeton.cs.algs4.LinearProbingHashST;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.In;
import java.lang.Math;

public class HashingProfile {
    
    private static int k;
    private static int M;
    private static int N;
    private static double logM;
    private static int hits;
    private static boolean prime;     // to choose the type of hash table.
    private static boolean universal; // to choose the type of hash table.
    private static Mode mode;
    private static LinearProbingHashST<String,Integer> hashT; // values are hashing function values.
    private static FrequencyTableLP freq; 

    enum Mode { prime, primeAndUniv, universal, std };

    // Standard:
    private static int hash(String key) {
        int h = key.hashCode();
        h ^= (h >>> 20) ^ (h >>> 12) ^ (h >>> 7) ^ (h >>> 4);
        return h & (M-1);
    }

    // Prime:
    private int hashPrime(String key) {
        int t = key.hashCode() & 0x7fffffff;
        return t % M;
    }
    
    // PrimeAndUniv/Universal:
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
        int m = (int) Math.pow((double)2,(double) k);
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

    public static void main(String[] args) {
        k = Integer.parseInt(args[0]);
        
        chooseMode(args);

        hashT = new LinearProbingHashST<String,Integer>();
        freq = new FrequencyTableLP();

        if (mode == Mode.std || mode == Mode.universal) {
            M = (int) Math.pow((double)2,(double) k);
            logM = Math.log((double) M);
        } else {
            M = findClosestPrime(k);
            logM = Math.log((double) M);
        }

        In in = new In();
        Words words = new Words(in);
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
        StdOut.printf("M log M = %.15f\n", M*logM);
        StdOut.printf("N = %d\n", N);
        StdOut.printf("N/M = %.15f\n", N/(double)M);

        // finding and printing hits value:
        SET<Integer> image = new SET<Integer>();

        for (String s: hashT.keys()) {
            int hashVal = hashT.get(s);
            if (!image.contains(hashVal))
                image.add(hashVal);
        }

        hits = image.size();

        StdOut.printf("hits = %d\n", hits);
    
        // draw histogram.
        if (args.length >= 4) {
            StdDraw.setCanvasSize(800,800);
            StdDraw.setXscale(-M/10.0, 1.1*M - 1);
            StdDraw.setYscale(-.1, 1.1);

            double hashFreq[] = new double[M];
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

        // finding and printing frequencies and hashes:
        if (args.length >= 5) {
            Iterable<String> keys = hashT.keys();

            for (int i = 0; i < M; i++) {
                Queue<String> wordsByHash = new Queue<String>();
                for (String s: keys) {
                    if (hashT.get(s) == i) 
                        wordsByHash.enqueue(s);
                }

                int f = wordsByHash.size(); // frequency of hashing value.
                if (f > 0) {
                    StdOut.printf("[ %d] %d:\n", f, i);
                    for (String s : wordsByHash) {
                        StdOut.printf("  %s\n", s);
                    }
                }
            }
        }
        // end main.
    }
}
