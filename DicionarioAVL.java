import java.util.ArrayList;
public class DicionarioAVL {
    // Árvore AVL
    int tamLista = 100;

    int numElems = 0;
    ArrayList<ArvoreAVL> elementos;
    ArrayList<Elemento> elementos_copy;
    int posicoes_chaves_copy = 0;

    public DicionarioAVL() {
        elementos = new ArrayList<ArvoreAVL>(tamLista);
        elementos_copy = new ArrayList<Elemento>(tamLista);

        for(int i=0 ; i < this.tamLista ; i++) {
            elementos.add( new ArvoreAVL() );
        }
    }

    public void balanceia() {
        for(int i = 0 ; i< this.tamLista ; i++) {
            this.elementos.get(i).verificaBalanceamento();
        }
    }

    public int size() { return this.numElems; }

    public void insert(Elemento elem) {
        int posicao = elem.getChave() % this.tamLista;

        if( this.elementos.get(posicao) != null ) {
            aumentaLista();
            if( this.elementos.get(posicao).busca(elem) == false ) {
                this.elementos.get(posicao).inserir(elem);

                // criando um backup das chaves
                elementos_copy.add(elem);
                elementos.get(posicao).verificaBalanceamento();
                this.numElems++;
            }
            posicoes_chaves_copy++;
        }
    }

    public void remove(Elemento elem) {
        int posicao = elem.getChave() % this.tamLista;

        if ( this.elementos.get(posicao).busca(elem) == true ) {
            this.elementos.get(posicao).remover(elem);
            this.posicoes_chaves_copy--;
            this.numElems--;
        }
    }

    public int get(Elemento elem) {
        int posicao = elem.getChave() % this.tamLista;

        if ( this.elementos.get(posicao).busca(elem) == true ) {
            System.out.println("O elemento " + elem.getChave() + " está na tabela Hash na posição: " + posicao);
            return posicao;
        }

        System.out.println("O elemento " + elem.getChave() + " não está na tabela Hash");
        return -1;
    }

    public void imprimir() {
        for(int i=0 ; i<this.tamLista ; i++) {
            System.out.println("Posição [" + i + "]:");
            System.out.print("[");
            this.elementos.get(i).imprimirInOrdem();
            System.out.print("]");
            System.out.println();
        }
    }

    private void aumentaLista() {
        if(this.numElems >= 0.9*this.tamLista) {
            // criando cópias da lista de backup:
            ArrayList<Elemento> elementos_copy_temp = (ArrayList<Elemento>) this.elementos_copy;
            this.elementos_copy = new ArrayList<Elemento>(this.tamLista*2);
            this.elementos = new ArrayList<ArvoreAVL>(this.tamLista*2);

            // copiando os dados da lista temp para a chaves_copy
            for(int i=0 ; i<this.posicoes_chaves_copy ; i++) {
                elementos_copy.add( elementos_copy_temp.get(i) );
            }

            // preenchendo a hashtable com as árvores:
            for(int i=0 ; i < this.tamLista*2 ; i++) {
                this.elementos.add( new ArvoreAVL() );
            }

            // preenchendo a hashtable com os elementos:
            for(int i=0 ; i < this.posicoes_chaves_copy ; i++) {
                int posicao = this.elementos_copy.get(i).getChave() % this.tamLista*2;
                this.elementos.get(posicao).inserir( elementos_copy.get(i) );
            }

            // atualizando o valor do tamanho da lista
            this.tamLista = this.tamLista*2 ;
        }
    }

    public void imprimeBkp() {
        for (int i=0 ; i<this.posicoes_chaves_copy ; i++) {
            System.out.println(this.elementos_copy.get(i) + " , ");
        }
    }

}
