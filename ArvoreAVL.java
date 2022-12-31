public class ArvoreAVL {

    private Elemento elem;
    private ArvoreAVL direita;
    private ArvoreAVL esquerda;
    private int balanceamento;
    private int numElems;

    public ArvoreAVL(){
        this.elem = null;
        this.esquerda = null;
        this.direita = null;
        this.balanceamento = 0;
        this.numElems = 0;
    }

    public ArvoreAVL(Elemento elemento){
        this.elem = elemento;
        this.esquerda = null;
        this.direita = null;
        this.numElems = 1;
        this.balanceamento = 0;
    }

    // Getters e Setters
    public void setElemento(Elemento ele){
        this.elem = ele;
    }
    public void setDireita(ArvoreAVL dir){
        this.direita = dir;
    }
    public void setEsquerda(ArvoreAVL esq){
        this.esquerda = esq;
    }
    public int getBalanceamento(){
        return this.balanceamento;
    }
    public void setBalanceamento(int bal){
        this.balanceamento = bal;
    }
    public ArvoreAVL getDireita(){
        return this.direita;
    }
    public ArvoreAVL getEsquerda(){
        return this.esquerda;
    }
    public Elemento getElemento(){
        return this.elem;
    }

    // fim getters and setters

    // ------------------------
    // Métodos padrões (altura e balanceamento)
    public int calcularAltura(){
        if (this.esquerda == null && this.direita == null) { // nao tenho nenhum filho
            return 1;
        }
        else if (this.esquerda != null && this.direita == null){ // Só tem filho na esquerda
            return 1 + this.esquerda.calcularAltura();
        }
        else if (this.esquerda == null && this.direita != null){ // Só tem filho na direita
            return 1 + this.direita.calcularAltura();
        }
        else{ // Tando a direita quanto a esquerda tem filhos
            return 1 + Math.max(this.esquerda.calcularAltura(), this.direita.calcularAltura()); // A altura da arvore será a maior altura entre a esquerda e a direita
        }
    }


    public void calcularBalanceamento() {
        if (this.direita == null && this.esquerda == null){ // a arvore não tem filhos
            this.balanceamento = 0;
        }
        else if (this.esquerda == null && this.direita != null){ // a arvore tem filhos só na direita
            this.balanceamento = this.direita.calcularAltura() - 0; // a altura da direita é o balanceamento do nodo
        }
        else if (this.esquerda != null && this.direita == null){ // a arvore só tem filhos na esquerda
            this.balanceamento = 0 - this.esquerda.calcularAltura(); // a altura da esquerda (negativo) é o balanceamento do nodo
        }
        else{ // a arvore tem elementos na esquerda e na direita
            this.balanceamento = this.direita.calcularAltura() - this.esquerda.calcularAltura(); // altura da direita - altura da esquerda
        }

        // calcula o balanceamento (recursivamente) para todos os nodos:
        if (this.direita != null) this.direita.calcularBalanceamento();
        if (this.esquerda != null) this.esquerda.calcularBalanceamento();
    }

    // ------------------------
    // Métodos da rotação:

    // ROTAÇÃO SIMPLES A DIREITA
    public ArvoreAVL rotacaoSimplesDireita() {
        ArvoreAVL filhoDir;
        ArvoreAVL filhoDoFilho = null;
        filhoDir = this.getDireita();
        if (this.direita != null){
            if (this.direita.getEsquerda()!= null){
                filhoDoFilho = filhoDir.getEsquerda();
            }
        }
        filhoDir.setEsquerda(this);
        this.setDireita(filhoDoFilho);
        return filhoDir;
    }

    // ROTAÇÃO SIMPLES A ESQUERDA
    public ArvoreAVL rotacaoSimplesEsquerda(){
        ArvoreAVL filhoEsq;
        ArvoreAVL filhoDoFilho = null;
        filhoEsq = this.getEsquerda();
        if (this.esquerda != null){
            if (this.esquerda.getDireita()!= null){
                filhoDoFilho = filhoEsq.getDireita();
            }
        }
        filhoEsq.setDireita(this);
        this.setEsquerda(filhoDoFilho);
        return filhoEsq;
    }

    // ROTAÇÃO DUPLA A DIREITA
    public ArvoreAVL rotacaoDuplaDireita(){
        ArvoreAVL arvore = this;
        ArvoreAVL filhoDir = this.getDireita();
        ArvoreAVL filhoDoFilho = filhoDir.getEsquerda();
        ArvoreAVL noInserido = filhoDoFilho.getDireita();

        // parte 1: alinhar os caras
        filhoDir.setEsquerda(noInserido);
        filhoDoFilho.setDireita(filhoDir);
        this.setDireita(filhoDoFilho);

        // parte 2: tornar o filho à direita a nova raiz
        ArvoreAVL novoFilhoDireita = this.getDireita();
        arvore.setDireita(null);
        novoFilhoDireita.setEsquerda(arvore);
        return novoFilhoDireita;
    }

    // ROTAÇÃO DUPLA A ESQUERDA
    public ArvoreAVL rotacaoDuplaEsquerda(){
        ArvoreAVL arvore       = this;
        ArvoreAVL filhoEsq     = this.getEsquerda();
        ArvoreAVL filhoDoFilho = filhoEsq.getDireita();
        ArvoreAVL noInserido   = filhoDoFilho.getEsquerda();
        // parte 1: alinhar os caras
        filhoEsq.setDireita(noInserido);
        filhoDoFilho.setEsquerda(filhoEsq);
        this.setEsquerda(filhoDoFilho);
        // parte 2: tornar o filho à esquerda a nova raiz
        ArvoreAVL novoFilhoEsquerda = this.getEsquerda();
        arvore.setEsquerda(null);
        novoFilhoEsquerda.setDireita(arvore);
        return novoFilhoEsquerda;
    }

    // ------------------------
    // Métodos de controle:
    public boolean isEmpty(){
        return (this.elem == null);
    }

    public void imprimirPreOrdem(){
        if (!isEmpty()){
            System.out.print(this.elem + "  , ");
            if (this.esquerda != null){
                this.esquerda.imprimirPreOrdem();
            }
            if (this.direita != null){
                this.direita.imprimirPreOrdem();
            }
        }
    }

    public void imprimirInOrdem(){
        if (!isEmpty()){
            if (this.esquerda != null){
                this.esquerda.imprimirInOrdem();
            }
            System.out.print(this.elem + " , ");
            if (this.direita != null){
                this.direita.imprimirInOrdem();
            }
        }
    }

    public void imprimirPosOrdem(){
        if (!isEmpty()){
            if (this.direita != null){
                this.direita.imprimirPosOrdem();
            }
            if (this.esquerda != null){
                this.esquerda.imprimirPosOrdem();
            }
            System.out.print(this.elem + " , ");
        }
    }

    // ------------------------
    // Métodos padrões de árvores:
    public ArvoreAVL inserir(Elemento novo){
        if (isEmpty()){
            this.elem = novo;
        }
        else{
            ArvoreAVL novaArvore = new ArvoreAVL(novo);
            if (novo.getValor() < this.elem.getValor()){ // vou inserir na esquerda
                if (this.esquerda == null){
                    this.esquerda = novaArvore;
                    //System.out.println("O elemento "+ novo + " está à esquerda de "+this.elem);
                }
                else{
                    this.esquerda = this.esquerda.inserir(novo); // inserir na sub-árvore esquerda
                }
            }
            else if (novo.getValor() > this.elem.getValor()){ // vou inserir na direita
                if (this.direita == null){
                    this.direita = novaArvore;
                    //System.out.println("O elemento "+ novo + " está à direita de "+this.elem);
                }
                else{
                    this.direita = this.direita.inserir(novo); // inserir na sub-árvore direita
                }
            }
        }
        this.numElems += 1;
        return this;
    }

    public ArvoreAVL remover(Elemento elemento){
        // Achei o elemento e ele não tem filhos
        if ( this.elem.getValor() == elemento.getValor() &&
             this.elem.getChave() == elemento.getChave() ){
            // ele é uma folha
            if (this.esquerda == null && this.direita == null){
                return null;
            }
            else{ // ele tem filhos:

                // só tem filhos à esquerda (acaba sobrando a sub-árvore da esquerda)
                if (this.esquerda != null && this.direita == null){
                    return this.esquerda;
                }
                // só tem filhos à direita (acaba sobrando a sub-árvore da direita)
                else if (this.direita != null && this.esquerda == null){
                    return this.direita;
                }

                // Só tem 1 filho:
                else{
                    ArvoreAVL aux = this.direita;
                    while (aux.esquerda != null){
                        aux = aux.esquerda;
                    }
                    this.setElemento(aux.getElemento());
                    //aux.getElemento().setValor(elemento);
                    // Árvore da direita recebe a remoção do elemento (que vai estar numa folha)
                    this.direita = direita.remover(elemento);
                }
            }
        }
        else{
            // O elemento é menor, então vou removê-lo na esquerda
            if (elemento.getValor() < this.elem.getValor() ){
                if (this.esquerda != null){
                    this.esquerda = this.esquerda.remover(elemento);
                }
            }
            // O elemento é maior, então vou removê-lo na direita
            else if (elemento.getValor() > this.elem.getValor() ){
                if (this.direita != null){
                    this.direita = this.direita.remover(elemento);
                }
            }
        }
        this.numElems -= 1;
        //this.verificaBalanceamento();
        return this;
    }

    public boolean busca(Elemento valor){
        if (isEmpty()){
            return false;
        }
        if (this.elem.getValor() == valor.getValor() &&
            this.elem.getChave() == valor.getChave() ){
            return true;
        }
        else{
            if (valor.getValor() < this.elem.getValor() ){
                if (this.esquerda == null){
                    return false;
                }
                else{
                    return this.esquerda.busca(valor); // repassei a responsabilidade para a subarvore esquerda
                }
            }
            else if (valor.getValor() > this.elem.getValor()){
                if (this.direita == null){
                    return false;
                }
                else{
                    return this.direita.busca(valor);
                }
            }
            return false;
        }
    }

    // Balanceamento:
    public ArvoreAVL verificaBalanceamento(){
        if (this.balanceamento >=2 || this.balanceamento <= -2){
            if (this.balanceamento >= 2){
                if (this.balanceamento * this.direita.getBalanceamento() > 0){
                    return rotacaoSimplesDireita();
                }
                else{
                    return rotacaoDuplaDireita();
                }
            }
            else{  // bal <= -2
                if (this.balanceamento * this.esquerda.getBalanceamento() > 0){
                    return rotacaoSimplesEsquerda();
                }
                else{
                    return rotacaoDuplaEsquerda();
                }
            }
        }
        this.calcularBalanceamento();
        if (this.esquerda != null) this.esquerda = this.esquerda.verificaBalanceamento();
        if (this.direita != null) this.direita = this.direita.verificaBalanceamento();
        return this;
    }

}
