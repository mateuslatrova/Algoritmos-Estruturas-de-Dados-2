/******************************************************************************
 * 
 * $ head -n300 < DATA/Gutenberg/Braz_Cubas.txt | tail | java-algs4 Words > BC.out
 * 
 ******************************************************************************/

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class Words {
    In in;

    public Words(In in) { this.in = in; }

    private enum State { OUT, IN }

    public String nextWord() {

	State state = State.OUT;
	StringBuilder s = new StringBuilder();
	String word = null;

	while (!in.isEmpty()) {
	    char c = in.readChar();
	    switch (state) {
	    case OUT:
		if (Character.isAlphabetic(c) || Character.isDigit(c)) { 
		    state = State.IN;
		    s.append(c);
		}
		break;
	    case IN:
		if (Character.isAlphabetic(c) || Character.isDigit(c))
		    s.append(c);
		else {
		    state = State.OUT;
		    return s.toString();
		} 	
		break;
	    }
	}
	if (state == State.IN) word = s.toString();
	return word;
    }

    public static void main(String[] args) {

	In in; 
	if (args.length > 0) {
	    in = new In(args[0]); // in serve para leitura do arquivo args[0]
	} else
	    in = new In(); // in serve para leitura da entrada padrao

	Words w = new Words(in);
	String word = w.nextWord();
	
	while (word != null) {
	    StdOut.println(word);
	    word = w.nextWord();
	}
	
    }
}

