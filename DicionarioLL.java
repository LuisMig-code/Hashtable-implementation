import java.util.LinkedList;
import java.util.ArrayList;

public class DicionarioLL {
    // Lista Encadeada
    int tamLista = 15;

    int numElems = 0;
    ArrayList<LinkedList<Elemento>> elementos;

    public DicionarioLL() {
        elementos = new ArrayList<LinkedList<Elemento>>(tamLista);

        for(int i = 0 ; i < tamLista ; i++) {
            this.elementos.add(new LinkedList<Elemento>());
        }
    }


    public int size() {
        return this.numElems;
    }

    private int procurar(Elemento elem) {
        int posicao = elem.getChave() % this.tamLista;

        int tamLL = this.elementos.get(posicao).size();
        int chaveEstaNaHash = -1;

        for(int i= 0 ; i < tamLL; i++) {
            if( this.elementos.get(posicao).get(i).getChave() == elem.getChave() &&
                this.elementos.get(posicao).get(i).getValor() == elem.getValor() ) {
                chaveEstaNaHash = 1;
                break;
            }
        }

        if(chaveEstaNaHash == -1) {return -1;} // n está na tabela
        else { return posicao;} // está na tabela e retorna a posição


    }
    public int get(Elemento elem) {
        int posicao = elem.getChave() % this.tamLista;

        int tamLL = this.elementos.get(posicao).size();
        int chaveEstaNaHash = -1;

        for(int i= 0 ; i < tamLL; i++) {
            if(this.elementos.get(posicao).get(i).getChave() == elem.getChave() ) {
                chaveEstaNaHash = 1;
                break;
            }
        }

        if(chaveEstaNaHash == -1) {
            System.out.println("O elemento :  [" + elem.getChave() + "/"+ elem.getValor() + "] não está na tabela hash");
        } else {
            System.out.println("O elemento :  [" + elem.getChave() + "/"+ elem.getValor() + "] está na tabela hash");
            return posicao;
        }

        // Não está na tabelaHash
        return -1;

    }

    public void aumentaLista() {
        if(this.numElems >= 0.9*this.tamLista ) {
            ArrayList<LinkedList<Elemento>> elementos_copy = this.elementos ;
            this.elementos = new ArrayList<LinkedList<Elemento>>(this.tamLista*2);


            // Fazendo a cópia das chaves -------------------------

            // Inicializando as LL em cada posição
            for (int i=0 ; i<this.tamLista*2 ; i++) {
                this.elementos.add(new LinkedList<Elemento>());
            }

            // Criando cópia das listas
            for(int i=0 ; i<this.tamLista ; i++) {
                for(int k = 0 ; k<elementos_copy.get(i).size() ; k++){
                    this.elementos.get(i).add(elementos_copy.get(i).get(k));
                }
            }

            this.tamLista = this.tamLista*2;
        }
    }

    public void insert(Elemento elem) {
        int posicao = elem.getChave() % this.tamLista;
        int chaveEstaNaHash = 0;
        chaveEstaNaHash = this.procurar(elem);

        // Aumentando a Lista:
        aumentaLista();

        // indexOf() == -1 se não econtrar a chave na lista encadeada
        if(chaveEstaNaHash == -1) {
            this.elementos.get(posicao).add(elem);
            numElems++;
        }

    }

    public void remove(Elemento elem) {
        int posicao = elem.getChave() % this.tamLista;
        this.elementos.get(posicao).remove(elem);
    }


    public void imprimir() {
        for(int i = 0 ; i < this.tamLista ; i++) {
            System.out.println("Posição [" + i + "]:");
            for(int k = 0 ; k < this.elementos.get(i).size() ; k++) {
                System.out.print(this.elementos.get(i).get(k) + " , ");
            }
            System.out.println();
        }
    }


}
