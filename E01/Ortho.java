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
    - recursos retirados das aulas: uso de StdIn, StdOut, In e Binary
    Search(este último, presente na Sandbox do site da disciplina);
    - recurso do enunciado: GetWords.java;
    - https://introcs.cs.princeton.edu/java/stdlib/javadoc/In.html -
    conhecimento da classe In e seus métodos.
    - https://www.w3schools.com/java/ref_string_compareto.asp - para 
    conhecer o funcionamento do método compareTo().

*********************************************************************/

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.In;

public class Ortho {
    public static int BinarySearch(String[] list, String word) {
        int left = 0;
        int right = list.length - 1;
        
        while (left <= right) {
            int middle = left+(right-left)/2;
            if (word.compareTo(list[middle]) < 0) 
                right = middle - 1;
            else if (word.compareTo(list[middle]) > 0)
                left = middle + 1;
            else 
                return middle;
        }
        return -1;
    }
    public static void main(String[] args) {
	    
        In listFile = new In(args[0]);
        String[] notAllowedWords = listFile.readAllLines();
        String mobyDickEntireText = StdIn.readAll();

        mobyDickEntireText = mobyDickEntireText.replaceAll("[^A-Za-z \n]", " ");
        String[] mobyWords = mobyDickEntireText.split("\\s+");

	    for (int i = 0; i < mobyWords.length; i++)
        {
	        if (BinarySearch(notAllowedWords, mobyWords[i]) == -1 &&
                BinarySearch(notAllowedWords, mobyWords[i].toLowerCase()) == -1)
            {
                StdOut.println(mobyWords[i]);
            }
        }
    }
}
