import edu.princeton.cs.algs4.LinearProbingHashST;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.SeparateChainingHashST;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Quick3string;
import java.lang.Math;

public class HashingProfile {
    
    // All possible types of Hash table needed for the exercise:
    //private static LinearProbingHashST<String,Integer> stdST;
    private static SeparateChainingLiteHashST<String,Integer> HT;
    private static LinearProbingHashPrimesST<String,Integer> primesST;
    private static LinearProbingHashPUHST<String,Integer> PUHST;
    private static LinearProbingHashUHST<String,Integer> UHST;

    enum Mode { prime, primeAndUniv, universal, standard };

    // For LinearProbingHashST
    //private static int hash(String key, int M) {
    //    int h = key.hashCode();
    //    h ^= (h >>> 20) ^ (h >>> 12) ^ (h >>> 7) ^ (h >>> 4);
    //    return h & (M-1);
    //}
    private static int hash(String key, int M) {
        return (key.hashCode() & 0x7fffffff) % M;
    } 

    // For LinearProbingHashPrimesST
    private static int hashPrime(String key, int M) {
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
        if (args[1] == "-p") {
            if (args[2] == "-u") 
                return Mode.primeAndUniv;
            else 
                return Mode.prime;
        } else {
            if (args[2] == "-u") 
                return Mode.universal;
            else
                return Mode.standard;
        }
    }

    private static void initializeHashTable(Mode mode) {
        if (mode.equals(Mode.standard)) 
            stdST = new SeparateChainingLiteHashST<String,Integer>();
        else if (mode.equals(Mode.prime))
            primesST = new LinearProbingHashPrimesST<String,Integer>(); 
        else if (mode.equals(Mode.primeAndUniv))
            PUHST = new LinearProbingHashPUHST<String,Integer>();
        else
            UHST = new LinearProbingHashUHST<String,Integer>();
        //stdST = new LinearProbingHashST<String,Integer>();
        //    switch (mode) {
        //    case standard: 
        //        stdST = new LinearProbingHashST<String,Integer>();
        //        break;
        //    case universal: 
        //        primesST = new LinearProbingHashPrimesST<String,Integer>();
        //        break;
        //    case prime: 
        //        PUHST = new LinearProbingHashPUHST<String,Integer>();
        //        break;
        //    case primeAndUniv: 
        //        UHST = new LinearProbingHashUHST<String,Integer>();
        //        break;
        //    default:
        //        StdOut.println("Erro: nenhum dos possíveis modos escolhidos.");
        //}
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
                    //m = stdST.size();
                    stdST.put(word,hash(word,M));
                    word = words.nextWord();
                }
                break;
            case universal:
                while (word != null) {
                    //m = UHST.size();
                    UHST.put(word,hashU(word,M)); 
                    word = words.nextWord();    
                }
                break;
            case prime:
                //m = primesST.size();
                while (word != null) {
                    primesST.put(word,hashPrime(word,M));
                    word = words.nextWord();
                }
                break;    
            case primeAndUniv:
                //m = PUHST.size();
                while (word != null) {
                    PUHST.put(word,hashU(word,M)); 
                    word = words.nextWord();
                }
                break;
        }
    }

    private static int findN(Mode mode) {
        switch (mode) {
            case standard:
                return stdST.size();
            case universal:
                return UHST.size();
            case prime:
                return primesST.size();
            case primeAndUniv:
                return PUHST.size();
            default:
                return 0;
        }
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

    private static void drawHistogram(Mode mode, Integer M) {
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

    private static void secondOutput(Mode mode, Integer M) {
        Iterable<String> keys;
        switch (mode) {
            case standard:
                keys = stdST.keys();

                for (int i = 0; i < M; i++) {
                    Queue<String> aux = new Queue<String>();
                    String[] wordsByHash;
                    int f = 0;
                    for (String s: keys) {
                        if (stdST.get(s) == i) { 
                            aux.enqueue(s);
                            f++;
                        }
                    }

                    wordsByHash = new String[f];

                    for (int j = 0; j < f; j++) {
                        wordsByHash[j] = aux.dequeue();
                    }

                    Quick3string.sort(wordsByHash); // sorting
                    
                    // printing:
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
        Integer k =   Integer.parseInt(args[0]);
        Mode mode =   Mode.standard;
        
        if (args[1].equals("-p") && args[2].equals("-u")) 
            mode = Mode.primeAndUniv;
        else if (args[1].equals("-p") && args[2].equals("-s"))  
            mode = Mode.prime;
        else if (args[1].equals("-2") && args[2].equals("-u"))
            mode = Mode.universal;
        else if (args[1].equals("-2") && args[2].equals("-s"))
            mode = Mode.standard;

        Integer M =   findM(mode,k); // size of hash table
        Double logM = findLogM(M);
       
        switch (mode) {
            case standard:
                StdOut.println("Std");
                break;
            case universal:
                StdOut.println("Universal");
                break;
            case prime:
                StdOut.println("Prime");
                break;
            case primeAndUniv:
                StdOut.println("Prime and Universal");
                break;
        }

        initializeHashTable(mode);
        fillHashTable(mode,M);

        Integer N =   findN(mode); // number of different words in the ST.

        firstOutput(M,logM,N);
        findAndPrintHits(mode);
    
        if (args.length >= 4) 
            drawHistogram(mode,M);

        if (args.length >= 5) 
            secondOutput(mode,M);
    }
}
