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
    private static int M; // size of hash table
    private static int N; // number of different words in the ST.
    private static double logM;
    private static int hits; // cardinality of the image of the hashing function.
    private static boolean prime = false;     // to choose the type of hash table.
    private static boolean universal = false; // to choose the type of hash table.
    private static Mode mode;
    // All possible types of Hash table needed for the exercise:
    private static LinearProbingHashST<String,Integer> stdST;
    private static LinearProbingHashPrimesST<String,Integer> primesST;
    private static LinearProbingHashPUHST<String,Integer> PUHST;
    private static LinearProbingHashUHST<String,Integer> UHST;

    enum Mode { prime, primeAndUniv, universal, standard };

    // For LinearProbingHashST
    private static int hash(String key) {
        int h = key.hashCode();
        h ^= (h >>> 20) ^ (h >>> 12) ^ (h >>> 7) ^ (h >>> 4);
        return h & (M-1);
    }

    // For LinearProbingHashPrimesST
    private static int hashPrime(String key) {
        int t = key.hashCode() & 0x7fffffff;
        return t % M;
    }
    
    // For both LinearProbingHashPUHS and LinearProbingHashUHST
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
                mode = Mode.standard;
        }

        // Initializing chosen hash table, M and logM.
        switch (mode) {
            case standard: 
                stdST = new LinearProbingHashST<String,Integer>();
                M = (int) Math.pow((double)2,(double) k);
                logM = Math.log((double) M);
                break;
            case universal: 
                primesST = new LinearProbingHashPrimesST<String,Integer>();
                M = (int) Math.pow((double)2,(double) k);
                logM = Math.log((double) M);
                break;
            case prime: 
                PUHST = new LinearProbingHashPUHST<String,Integer>();
                M = findClosestPrime(k);
                logM = Math.log((double) M);
                break;
            case primeAndUniv: 
                UHST = new LinearProbingHashUHST<String,Integer>();
                M = findClosestPrime(k);
                logM = Math.log((double) M);
                break;
        }
    }

    private static void fillHashTable() {
        In in = new In();
        Words words = new Words(in);
        String word = words.nextWord();
        
        switch (mode) {
            case standard:
                while (word != null) {
                    stdST.put(word,hash(word));
                    word = words.nextWord();
                }
                break;
            case universal:
                while (word != null) {
                    UHST.put(word,hashU(word,M)); 
                    word = words.nextWord();    
                }
                break;
            case prime:
                while (word != null) {
                    primesST.put(word,hashPrime(word));
                    word = words.nextWord();
                }
                break;    
            case primeAndUniv:
                while (word != null) {
                    PUHST.put(word,hashU(word,M)); 
                    word = words.nextWord();
                }
                break;
        }
    }

    private static int findN() {
        switch (mode) {
            case standard:
                return stdST.size();
            case universal:
                return UHST.size();
            case prime:
                return primesST.size();
            case primeAndUniv:
                return PUHST.size();
        }
        return 0;
    }

    private static void firstOutput() {
        StdOut.printf("M = %d\n", M);
        StdOut.printf("M log M = %.15f\n", M*logM);
        StdOut.printf("N = %d\n", N);
        StdOut.printf("N/M = %.15f\n", N/(double)M);
    }

    private static void findAndPrintHits() {
        SET<Integer> image = new SET<Integer>();

        switch (mode) {
            case standard:
                for (String s: stdST.keys()) {
                    int hashVal = stdST.get(s);
                    if (!image.contains(hashVal))
                        image.add(hashVal);
                }
                break;
            case universal:
                for (String s: UHST.keys()) {
                    int hashVal = UHST.get(s);
                    if (!image.contains(hashVal))
                        image.add(hashVal);
                }
                break;
            case prime:
                for (String s: primesST.keys()) {
                    int hashVal = primesST.get(s);
                    if (!image.contains(hashVal))
                        image.add(hashVal);
                }
                break;    
            case primeAndUniv:
                for (String s: PUHST.keys()) {
                    int hashVal = PUHST.get(s);
                    if (!image.contains(hashVal))
                        image.add(hashVal);
                }
                break;
        }
        hits = image.size();
        StdOut.printf("hits = %d\n", hits);
    }

    private static void drawHistogram() {
        StdDraw.setCanvasSize(800,800);
        StdDraw.setXscale(-M/10.0, 1.1*M - 1);
        StdDraw.setYscale(-.1, 1.1);
        double hashFreq[] = new double[M];
        double max = 0.;
        
        // Getting frequencies and finding the maximum one.
        switch (mode) {
            case standard:
                for (int i = 0; i < M; i++) {
                    for (String s: stdST.keys()) {
                        if (stdST.get(s) == i)
                            hashFreq[i]++;
                    }
                    if (hashFreq[i] > max) max = hashFreq[i];
                }
                break;
            case universal:
                for (int i = 0; i < M; i++) {
                    for (String s: UHST.keys()) {
                        if (UHST.get(s) == i)
                            hashFreq[i]++;
                    }
                    if (hashFreq[i] > max) max = hashFreq[i];
                } 
                break;
            case prime:
                for (int i = 0; i < M; i++) {
                    for (String s: primesST.keys()) {
                        if (primesST.get(s) == i)
                            hashFreq[i]++;
                    }
                    if (hashFreq[i] > max) max = hashFreq[i];
                }
                break;    
            case primeAndUniv:
                for (int i = 0; i < M; i++) {
                    for (String s: PUHST.keys()) {
                        if (PUHST.get(s) == i)
                            hashFreq[i]++;
                    }
                    if (hashFreq[i] > max) max = hashFreq[i];
                }
                break;
        }

        // Normalizing and drawing.
        for (int i = 0; i < M; i++) {
            hashFreq[i] /= max;
            StdDraw.filledRectangle(i, hashFreq[i]/2, 0.25, hashFreq[i]/2);
        }
    }

    private static void secondOutput() {
        // Getting frequencies and finding the maximum one.
        Iterable<String> keys;
        switch (mode) {
            case standard:
                keys = stdST.keys();

                for (int i = 0; i < M; i++) {
                    Queue<String> wordsByHash = new Queue<String>();
                    for (String s: keys) {
                        if (stdST.get(s) == i) 
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
                break;
            case universal:
                keys = UHST.keys();

                for (int i = 0; i < M; i++) {
                    Queue<String> wordsByHash = new Queue<String>();
                    for (String s: keys) {
                        if (UHST.get(s) == i) 
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
                break;
            case prime:
                keys = primesST.keys();

                for (int i = 0; i < M; i++) {
                    Queue<String> wordsByHash = new Queue<String>();
                    for (String s: keys) {
                        if (primesST.get(s) == i) 
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
                break;    
            case primeAndUniv:
                keys = PUHST.keys();

                for (int i = 0; i < M; i++) {
                    Queue<String> wordsByHash = new Queue<String>();
                    for (String s: keys) {
                        if (PUHST.get(s) == i) 
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
                break;
        }
    }

    public static void main(String[] args) {
        k = Integer.parseInt(args[0]);
    
        chooseMode(args);

        fillHashTable();

        N = findN(); 

        firstOutput();
        
        findAndPrintHits();
    
        if (args.length >= 4) 
            drawHistogram();

        if (args.length >= 5) 
            secondOutput();
    }
}
