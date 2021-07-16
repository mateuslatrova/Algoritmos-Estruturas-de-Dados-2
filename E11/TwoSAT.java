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
    - https://algs4.cs.princeton.edu/lectures/keynote/42DirectedGraphs.pdf
    - https://stackoverflow.com/questions/3865946/error-generic-array-creation
    - slides sobre o tema(elaborado por D. Moshkovits): https://www.google.com/url?sa=t&rct=j&q=&esrc=s&source=web&cd=&ved=2ahUKEwiLsreh3-jxAhWDLLkGHWAgDVcQFnoECAMQAA&url=https%3A%2F%2Fwww.cs.umd.edu%2F~gasarch%2FTOPICS%2Fsat%2F2SAT.ppt&usg=AOvVaw1B8UJ7ciO-4C74gqGuJMJP
    
*********************************************************************/
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.KosarajuSharirSCC;
import edu.princeton.cs.algs4.Topological;
import edu.princeton.cs.algs4.DepthFirstDirectedPaths;
import edu.princeton.cs.algs4.Queue;
import java.util.Iterator;

import edu.princeton.cs.algs4.BreadthFirstDirectedPaths;

public class TwoSAT {
    
    private boolean hasSolution;
    private boolean[] assignment;
    private String unSATProof;

    //O(N+M). N variáveis(2N vértices) e M cláusulas(2M vértices).
    public TwoSAT(ImplicationGraph impgr) {
        Digraph g = impgr.g(); // imp graph.
        int N = impgr.N(); // number of literals
        int j = 0; // literal j such that xj <=> ~xj
        
        KosarajuSharirSCC kosaraju = new KosarajuSharirSCC(g);
        int numOfComponents = kosaraju.count();

        // let's see if it has a solution:
        hasSolution = true;
        for (int i = 1; i <= N; i++) {
            if (kosaraju.stronglyConnected(impgr.vertex(i), impgr.vertex(-i))) {
                hasSolution = false;
                j = i;
                break;
            }
        }

        // Se tem solução, calcule a atribuição.
        if (hasSolution()) {
            assignment = new boolean[N+1];

            // Construindo array de queues.
            Queue<Integer>[] components = (Queue<Integer>[]) new Queue[numOfComponents]; 
            for (int c = 0; c < numOfComponents; c++) components[c] = new Queue<Integer>();

            // Preenchendo-o
            for (int v = 0; v < 2*N; v++) {
                components[kosaraju.id(v)].enqueue(v);
            }

            // Utilizando a ordenação topológica a nosso favor.
            for (int c = numOfComponents-1; c >= 0; c--) {
                for (Integer v: components[c]) {
                    if (v%2 == 0) assignment[v/2+1] = true;
                    else assignment[(v-1)/2+1] = false;
                }
            }
        } else { // caso contrário, calcule o certificado de insatisfatibilidade.
            BreadthFirstDirectedPaths pathsFromJ = new BreadthFirstDirectedPaths(g, impgr.vertex(j));
            BreadthFirstDirectedPaths pathsFromNotJ = new BreadthFirstDirectedPaths(g, impgr.vertex(-j));
            StringBuilder certificate = new StringBuilder();
            Iterable<Integer> jToNotJ = pathsFromJ.pathTo(impgr.vertex(-j));
            Iterable<Integer> notJToJ = pathsFromNotJ.pathTo(impgr.vertex(j));

            Iterator<Integer> it = jToNotJ.iterator();
            while (it.hasNext()) {
                int literal = impgr.literal(it.next());
                certificate.append(literal);
                if (it.hasNext()) certificate.append(" => "); 
            }

            certificate.append('\n');

            it = notJToJ.iterator();
            while (it.hasNext()) {
                int literal = impgr.literal(it.next());
                certificate.append(literal);
                if (it.hasNext()) certificate.append(" => "); 
            }

            certificate.append('\n');
            
            unSATProof = certificate.toString();
        }
    }

    // O(1). resposta para este deve ser computada no construtor.
    public boolean hasSolution() {
        return hasSolution;
    }

    // O(N). resposta para este deve ser computada no construtor.
    public boolean[] assignment() {
        return assignment;
    }

    // quando a fórmula não é satisfatível, este método devolve um certificado para esse fato.
    // O(L), onde L é o comprimento do certificado. Certificado calculado no construtor.
    // certificado = xi <-> ~xi (contradição). Mostrar dois caminhos(de xi a ~xi e de ~xi a xi).
    public String unSATProof() {
        return unSATProof;
    }

}
