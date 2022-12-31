import java.lang.Math;
import java.util.Scanner;

public class Main {
    public static int geraRandom() {
        int max = 100000;
        int min = 0;
        return (int)(Math.random() * (max - min + 1) + min);
    }

    public static void main(String[] args) {
        DicionarioAVL meuDictAVL = new DicionarioAVL();
        Dicionario meuDict = new Dicionario();
        DicionarioLL meuDictLL= new DicionarioLL();

        Elemento elem_temp = new Elemento(1 , 1);


        long tempoDecorrido = 0 ;
        long final_ = 0 ;
        long inicio = 0 ;

        int numElems;

        while (true) {
            Scanner scan = new Scanner(System.in);

            System.out.println("Quantos elementos deseja inserir?");
            System.out.print("> ");

            while (true) {
                try{
                    numElems = scan.nextInt();
                    break;
                } catch (Exception E) {
                    System.out.println("Valor inválido , tente novamente...");
                    System.out.print("> ");
                    numElems = scan.nextInt();
                }
            }


            Display.menuDicionarios();
            System.out.print("> ");
            int selecao = scan.nextInt();
            switch (selecao) {
                case 1: // Tentativa Linear
                    inicio = System.currentTimeMillis();
                    for(int i=0 ; i<numElems ; i++) {
                        Elemento elem = new Elemento( i,geraRandom() );
                        meuDict.insert(elem_temp);
                        meuDict.insert(elem);

                    }
                    final_ = System.currentTimeMillis();
                    tempoDecorrido = final_ - inicio;
                    //meuDict.imprimir();

                case 2: // Lista Lincada
                    inicio = System.currentTimeMillis();
                    for(int i=0 ; i<numElems ; i++) {
                        Elemento elem = new Elemento( i,geraRandom() );
                        meuDictLL.insert(elem);
                    }
                    final_ = System.currentTimeMillis();
                    tempoDecorrido = final_ - inicio;
                    //meuDictLL.imprimir();

                case 3: // Árvore AVL
                    inicio = System.currentTimeMillis();
                    for(int i=0 ; i<numElems ; i++) {
                        Elemento elem = new Elemento( i,geraRandom() );
                        meuDictAVL.insert(elem);
                    }
                    final_ = System.currentTimeMillis();
                    tempoDecorrido = final_ - inicio;
                    //meuDictAVL.imprimir();
            }

            System.out.println("Tempo decorrido para inerção de " + numElems + " elementos foi: ");
            System.out.println( tempoDecorrido + " ms");

            meuDict.get(elem_temp);

            break;

        }


        //meuDict.imprimir();

    }


}
