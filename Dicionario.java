import java.util.ArrayList;

public class Dicionario {
    // tentativa linear
    int tamLista = 10;

    // Possíveis bandeiras:
    // L = Posição Livre
    // O = Posição Ocupada
    // R = Posição Removida

    int numElems = 0;
    ArrayList<Elemento> elementos;
    String[] bandeiras;


    public Dicionario() {
        elementos = new ArrayList<Elemento>(tamLista);
        bandeiras = new String[tamLista];

        for(int i = 0 ; i < tamLista ; i++) {
            if (this.bandeiras[i] == null) {
                this.bandeiras[i] = "L";
            }
        }
    }

    private void aumentaLista() {
        if (numElems == 0.9 * this.tamLista) {
            ArrayList<Elemento> elementos_copy = this.elementos;
            String[] bandeiras_copy = this.bandeiras;

            this.elementos = new ArrayList<Elemento>(this.tamLista*2);
            this.bandeiras = new String[this.tamLista*2];

            // Fazendo as cópias
            for(int k=0 ; k<this.tamLista ; k++) {
                try{
                    this.elementos.add(k, elementos_copy.get(k));
                    this.bandeiras[k] = bandeiras_copy[k];
                } catch (Exception e){

                }


            }

            this.tamLista = 2*this.tamLista;

            // Preenchendo as flags
            for(int i = 0 ; i < this.tamLista ; i++) {
                if (this.bandeiras[i] == null) {
                    this.bandeiras[i] = "L";
                }
            }
        }

    }

    public int size() {
        return this.numElems;
    }

    public int get(Elemento elem) {
        int posicao = elem.getChave() % this.tamLista;

        for (int k = 0; k < this.numElems; k++) {
            if (this.elementos.get(k).getChave() == elem.getChave() ) {
                System.out.println("Está na tabela hash - posição: " + posicao);
                return posicao;
            } else {
                posicao++;
            }
        }

        // Não achou:
        System.out.println("Não encontrado");
        return -1;
    }

    public void insert(Elemento elem) {
        int posicao = elem.getChave() % this.tamLista;
        aumentaLista();

        for (int k = 0; k < this.tamLista; k++) {
            // O Elemento está em uma posição vazia
            if (this.bandeiras[posicao] == "L") {
                this.elementos.add(k,elem);
                this.bandeiras[posicao] = "O";
                this.numElems++;
                break;
            }

            // O Elemento já está lá
            if (this.elementos.get(k).getChave() == elem.getChave() ) {
                this.elementos.add(k,elem);
                this.bandeiras[posicao] = "O";
                break;

                // O Elemento da posição foi removido
            } else if (this.bandeiras[posicao] == "R") {
                this.elementos.add(k,elem);
                this.bandeiras[posicao] = "O";
                this.numElems++;
                break;

                // O Elemento da chave está ocupado mas a chave é diferente;
            } else if (this.bandeiras[posicao] == "O" &&
                       this.elementos.get(k).getChave() != elem.getChave() ) {
                posicao++;

            }
        }
    }

    public void remove(Elemento elem) {
        int posicao = elem.getChave() % this.tamLista;

        for (int i = 0; i < tamLista * 0.25; i++) {
            if (this.elementos.get(i).getChave() == elem.getChave()) {
                System.out.println("Achei");
                this.bandeiras[posicao] = "R";
                this.numElems--;
                break;
            }
            posicao++;
        }
    }

    public void imprimir() {
        for(int i = 0 ; i < this.tamLista ; i++) {
            if(this.bandeiras[i] == "L") {
                System.out.println("Bandeira: " + this.bandeiras[i]);
            } else {
                System.out.println("Bandeira: " + this.bandeiras[i] + " - chave/valor:  " + this.elementos.get(i).getChave() + " / " + this.elementos.get(i).getValor());
            }
        }
    }

}
