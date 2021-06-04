
import edu.princeton.cs.algs4.LinearProbingHashST;
import edu.princeton.cs.algs4.StdIn;
import java.lang.Math;

import javax.lang.model.util.ElementScanner6;

public class HashingProfile {
    
    private static int k;
    private static int M;
    private static int N;
    private static int logM;
    private static int hits;
    private static boolean prime;     // to choose the type of hash table.
    private static boolean universal; // to choose the type of hash table.
    private static int mode;
    private static Object hashT;
    private static Object freq;
    private static int[] hash;


    enum Mode { prime, primeAndUniv, universal, std };

    private int hashU(Key key, int p) {
        int h = 0, a = 31415, b = 27183;
        String s = (String) key;
        for (int i = 0; i < s.length(); i++, a = a*b % (p-1))
            h = ((a*h + s.charAt(i)) & 0x7fffffff) % p;
        return h;
    }



    private static boolean isPrime(int n) {
        if (n == 1) return false;
        for (int i = 2; i <= sqrt(n); i++) {
            if (n % i == 0) {
                return false;
            }
        }
        return true;
    }

    private static int findClosestPrime(int k) {
        int M = Math.pow(2, k);
        int i = M;
        while (!isPrime(i))
            i -= 1;
        return i;
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
        k = args[0];
        
        chooseMode(args);

        switch (mode) {
            case Mode.primeAndUniv:
                M = findClosestPrime(k);
                logM = Math.log(M);
                hashT = (LinearProbingHashPUHST<String,Integer>)
                        new LinearProbingHashPUHST<String,Integer>();
                freq = (FrequencyTableLPUHP) new FrequencyTableLPUHP(); 
                break;
            case Mode.prime:
                M = findClosestPrime(k);
                logM = Math.log(M);
                hashT = (LinearProbingHashPrimesST<String,Integer>)
                        new LinearProbingHashPrimesST<String,Integer>();
                freq = (FrequencyTableLPP) new FrequencyTableLPP();    
                break;
            case Mode.universal:
                M = Math.pow(2,k);
                logM = Math.log(M);
                hashT = (LinearProbingHashUHST<String,Integer>)
                        new LinearProbingHashUHST<String,Integer>(M);
                freq = (FrequencyTableLPUH) new FrequencyTableLPUH(); 
                break;
            case Mode.std:
                M = Math.pow(2,k);
                logM = Math.log(M);
                hashT = (LinearProbingHashST<String,Integer>)
                        new LinearProbingHashST<>(M);
                freq = (FrequencyTableLP) new FrequencyTableLP(); 
                break;
        }

        In in = new In();
        Words words = new words(in);
        String word = words.nextWord();

        while (word != null) {
            if (hashT.contains(word))
                hashT.put(word,hashT.get(word)+1);
            else
                hashT.put(word,1);
        }

        N = hashT.size();
        hash = new int[N];

        // first output:
        StdOut.printf("M = %d\n", M);
        StdOut.printf("M log M = %f\n", M*logM);
        StdOut.printf("N = %d\n", N);
        StdOut.printf("N/M = %f\n", N/M);

        switch (mode) {
            case Mode.primeAndUniv:
                
                break;
            case Mode.prime:
                
                break;
            case Mode.universal:
                
                break;
            case Mode.std:
            
                break;
        }

        if (args.length >= 4) {
            // draw histogram.
            
        }

        if (args.length >= 5) {
            // print frequencies and hashes:

        }


    }
}
