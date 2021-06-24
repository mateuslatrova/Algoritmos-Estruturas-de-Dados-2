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
    - https://www.ime.usp.br/~pf/estruturas-de-dados/aulas/tries.html
    - https://beginnersbook.com/2013/12/java-string-substring-method-example/
    - https://www.cs.cmu.edu/~ckingsf/bioinfo-lectures/suffixtrees.pdf
    - https://algs4.cs.princeton.edu/code/edu/princeton/cs/algs4/TrieST.java

*********************************************************************/

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.TST;
import edu.princeton.cs.algs4.LinearProbingHashST;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.In;

public class ContainsPattern {
    
    public static void main(String[] args) {
	    In in = new In(args[0]);
        TSTPlus<Queue<Integer>> suffixTrie = new TSTPlus<Queue<Integer>>();
        Queue<String> auxAllWords = new Queue<String>();

        int i = 0;
	    while (!in.isEmpty()) {
	        String key = in.readString();
            int n = key.length();
            auxAllWords.enqueue(key);
            for (int j = 0; j < n; j++) {
                String suffix = key.substring(j);
                Queue<Integer> testQ = suffixTrie.get(suffix);
                if (testQ == null) testQ = new Queue<Integer>();
                testQ.enqueue(i);
                suffixTrie.put(suffix,testQ);
            }
            i++;
	    } 

        String[] allWords = new String[i];
        for (int j = 0; j < i; j++) {
            allWords[j] = auxAllWords.dequeue();
        }

	    while (!StdIn.isEmpty()) {
	        String pattern = StdIn.readString();

            Queue<String> matches = (Queue<String>) suffixTrie.keysThatStartWith(pattern);
            SET<Integer> wordsID = new SET<Integer>();

            for (String m: matches) {
                Queue<Integer> substringOf = suffixTrie.get(m);
                for (int id: substringOf) 
                    wordsID.add(id);
            }
    
            int numWords = wordsID.size();
            String[] wordsThatContainPattern = new String[numWords];
            int j = 0;
            for (int id: wordsID) {
                wordsThatContainPattern[j] = allWords[id];
                j++;
            }
            
	        StdOut.println("Words that contain " + pattern + " (" + wordsThatContainPattern.length + ")");
            for (int k = 0; k < numWords; k++) StdOut.println(wordsThatContainPattern[k]);
	        StdOut.println("- * - * -");	    
	    }
    }
}
