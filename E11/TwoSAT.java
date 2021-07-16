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
    - https://docs.oracle.com/javase/7/docs/api/java/lang/StringBuilder.html
    - https://www.cs.princeton.edu/courses/archive/spring19/cos226/lectures/41UndirectedGraphs.pdf
    - Cycle.java foi uma modificação de Cycle.java de S&W(https://algs4.cs.princeton.edu/41graph/Cycle.java.html)
*********************************************************************/
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.KosarajuSharirSCC;

public class TwoSAT {
    
    private boolean hasSolution;
    private boolean[] assignment;
    private String unSATProof;
    //private Digraph g;

    //O(N+M). N variáveis(2N vértices) e M cláusulas(2M vértices).
    public TwoSAT(ImplicationGraph impgr) {
        Digraph g = impgr.g();
        int N = impgr.N();
        int contradiction = 0; // literal j such that xj <=> ~xj
        KosarajuSharirSCC kosaraju = new KosarajuSharirSCC(g);
        int components = kosaraju.count();

        // let's see if it has a solution:
        hasSolution = true;
        for (int i = 1; i <= N; i++) {
            if (kosaraju.stronglyConnected(impgr.vertex(i), impgr.vertex(-i))) {
                hasSolution = false;
                contradiction = i;
                break;
            }
        }

        // Se tem solução, calcule a atribuição.
        if (hasSolution()) {

        } else { // caso contrário, calcule o certificado de insatisfatibilidade.
            
        }
    }

    // O(1). resposta para este deve ser computada no construtor.
    public boolean hasSolution() {
        return hasSolution;
    }

    // O(N). resposta para este deve ser computada no construtor.
    public boolean[] assignment() {

    }

    // quando a fórmula não é satisfatível, este método devolve um certificado para esse fato.
    // O(L), onde L é o comprimento do certificado. Certificado calculado no construtor.
    // certificado = xi <-> ~xi (contradição). Mostrar dois caminhos(de xi a ~xi e de ~xi a xi).
    String unSATProof() {

    }

}
