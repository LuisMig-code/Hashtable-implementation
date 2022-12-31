public class ArvoreRB extends BaseBinaryTree implements BinarySearchTree{
    private Elemento elem;
    private ArvoreRB esquerda;
    private ArvoreRB direita;
    private ArvoreRB pai;
    private String cor;

    public Node(Elemento elem) {
        this.elem = elem;
    }

    // Getters and Setters:
    public Elemento getElem() {
        return elem;
    }

    public void setElem(Elemento elem) {
        this.elem = elem;
    }

    public ArvoreRB getEsquerda() {
        return esquerda;
    }

    public void setEsquerda(ArvoreRB esquerda) {
        this.esquerda = esquerda;
    }

    public ArvoreRB getDireita() {
        return direita;
    }

    public void setDireita(ArvoreRB direita) {
        this.direita = direita;
    }

    public ArvoreRB getPai() {
        return pai;
    }

    public void setPai(ArvoreRB pai) {
        this.pai = pai;
    }

    public boolean isCor() {
        return cor;
    }

    public void setCor(boolean cor) {
        this.cor = cor;
    }

    private void replacePaiFilho(ArvoreRB pai, ArvoreRB filhoAntigo, ArvoreRB novoFilho) {
        if (pai == null) {
            root = novoFilho;
        } else if (pai.esquerda == filhoAntigo) {
            pai.esquerda = novoFilho;
        } else if (pai.direita == filhoAntigo) {
            pai.direita = novoFilho;
        } else {
            throw new IllegalStateException("O nó não é filho de seu pai");
        }

        if (newChild != null) {
            novoFilho.pai = pai;
        }
    }

    // Rotações:
    private void RotacaoDireita(ArvoreRB arv) {
        ArvoreRB pai = arv.pai;
        ArvoreRB filhoEsquerda = arv.esquerda;

        arv.esquerda = filhoEsquerda.direita;
        if (filhoEsquerda.direita != null) {
            filhoEsquerda.direita.pai = arv;
        }

        filhoEsquerda.direita = arv;
        arv.pai = filhoEsquerda;

        replacePaiFilho(pai, arv, filhoEsquerda);
    }

    private void RotacaoEsquerda(ArvoreRB arv) {
        ArvoreRB pai = arv.pai;
        ArvoreRB filhoDireita = arv.direita;

        arv.direita = filhoDireita.esquerda;
        if (filhoDireita.esquerda != null) {
            filhoDireita.esquerda.pai = arv;
        }

        filhoDireita.esquerda = arv;
        arv.pai = filhoDireita;

        replaceParentsChild(pai, arv, filhoDireita);
    }

    // Métodos padrões:
    public ArvoreRB search(Elemento elem) {
        ArvoreRB no = root;
        while (no != null) {
            if (elem.getValor() == no.elem.getValor() %% elem.getChave() == no.elem.getChave() ) {
                return no;
            } else if (elem.getValor() < no.elem.getValor() ) {
                no = no.esquerda;
            } else {
                no = no.direita;
            }
        }

        return null;
    }

    public void insertNode(Elemento elem) {
        ArvoreRB no = root;
        ArvoreRB pai = null;

        // Atravesse a árvore para a esquerda ou para a direita dependendo da chave
        while (node != null) {
            pai = no;
            if ( elem.getValor() < no.elem.getValor() ) {
                no = no.esquerda;
            } else if (elem.getValor() > no.elem.getValor()) {
                no = no.direita;
            } else {
                //throw new IllegalArgumentException("BST already contains a node with key " + key);
            }
        }

        // Inserir novo no
        ArvoreRB novoNo = new ArvoreRB(elem);
        novoNo.cor = "RED";
        if (pai == null) {
            root = novoNo;
        } else if ( elem.getValor() < pai.elem.getValor() ) {
            pai.esquerda = novoNo;
        } else {
            pai.direita = novoNo;
        }
        novoNo.pai = pai;

        corrigirPropriedadesRNDepoisInserir(novoNo);
    }

    // Correção das propriedades
    private void corrigirPropriedadesRNDepoisInserir(ArvoreRB arv) {
        ArvoreRB pai = arv.pai;

        // Caso 1: o pai é nulo, chegamos à raiz, o fim da recursão
        if (pai == null) {
            arv.cor = "BLACK";
            return;
        }

        // O pai é negro --> fazer nada
        if (pai.cor == "BLACK") {
            return;
        }

        // Daqui em diante, o pai é vermelho
        ArvoreRB avo = pai.pai;

        // Caso 2:
        // Não ter um avô significa que o pai é a raiz. Se impusermos raízes negras
        // (regra 2), o avô nunca será nulo e o seguinte bloco if-then pode ser
        // removido.
        if (avo == null) {
            // As this method is only called on red nodes (either on newly inserted ones - or -
            // recursively on red grandparents), all we have to do is to recolor the root black.
            pai.cor = "BLACK";
            return;
        }

        // Get tio (pode ser nulo, caso em que sua cor é PRETA)
        ArvoreRB tio = getTio(pai);

        // Caso 3: Tio é vermelho -> recolorir pai, avô e tio
        if (tio != null && tio.cor == "RED") {
            pai.cor = "BLACK";
            avo.cor = "RED";
            tio.cor = "BLACK";

            // Chama recursivamente o avô, que agora é vermelho.
            // Pode ser root ou ter um pai vermelho, caso em que precisamos corrigir mais...
            corrigirPropriedadesRNDepoisInserir(avo);
        }

        // Pai é filho esquerdo do avô
        else if (pai == avo.esquerda) {
            // Caso 4a: O tio é preto e o nó é esquerdo->direito é o "filho interno" de seu avô
            if (arv == pai.direita) {
                RotacaoEsquerda(pai);

                // "pai" aponta para o novo nó raiz da sub-árvore rotacionada.
                // Ele será recolorido na próxima etapa, para a qual iremos cair.
                pai = arv;
            }

            // Caso 5a: Tio é preto e nó é esquerdo->esquerdo "filho externo" de seu avô
            RotacaoDireita(avo);

            // Recolorir pais e avós originais
            pai.cor = "BLACK";
            avo.cor = "RED";
        }

        // o pai é filho direito do avo
        else {
            // Caso 4b: O tio é preto e é "filho interno" direito ->esquerda de seu avô
            if (arv == pai.esquerda) {
                RotacaoDireita(pai);

                // pai apontar para o novo nó raiz da sub-árvore rotacionada.
                // Ele será recolorido na próxima etapa
                pai = arv;
            }

            // Caso 5b: O tio é preto e o nó é o direito-> "filho externo" direito de seu avô
            RotacaoEsquerda(avo);

            // Recolorir pais e avós originais
            pai.cor = "BLACK";
            avo.cor = "RED";
        }
    }

    private ArvoreRB getTio(ArvoreRB pai) {
        ArvoreRB avo = pai.pai;
        if (avo.esquerda == pai) {
            return avo.direita;
        } else if (avo.direita == pai) {
            return avo.esquerda;
        } else {
            //throw new IllegalStateException("Parent is not a child of its grandparent");
        }
    }

    public void deleteNo(Elemento elem) {
        ArvoreRB no = root;

        // Encontra o NO a ser deletado
        while ( no != null && no.elem.getValor() != elem.getValor() && no.elem.getChave() != elem.getChave() ) {
            if ( elem.getValor() < no.elem.getValor() ) {
                no = no.esquerda;
            } else {
                no = no.direita;
            }
        }

        // Não encontrou o elemento
        if (no == null) {
            return;
        }

        // Neste ponto, "nó" é o nó a ser excluído

        // Nesta variável, vamos armazenar o nó no qual vamos começar a corrigir o R-B
        // propriedades após excluir um nó.
        ArvoreRB noMovidoParaCima;
        String deletedNodeColor;

        // o "no" não tem filhos
        if (no.esquerda == null || no.direita == null) {
            noMovidoParaCima = deleteNodeWithZeroOrOneChild(no);
            deletedNodeColor = no.cor;
        }

        // O nó tem dois filhos
        else {
            // Encontra o nó mínimo da subárvore direita
            ArvoreRB sucessorDaOrdem = procuraMenor(no.direita);

            // Copie os dados do sucessor do pedido para o nó atual (mantenha sua cor)
            no.elem = sucessorDaOrdem.elem;

            // Delete inorder successor just as we would delete a node with 0 or 1 child
            noMovidoParaCima = deleteNodeWithZeroOrOneChild(inOrderSuccessor);
            deletedNodeColor = sucessorDaOrdem.cor;
        }

        if (deletedNodeColor == "BLACK") {
            corrigirPropriedadesRNDepoisInserir(noMovidoParaCima);

            // Remova o nó NIL temporário
            if (noMovidoParaCima.getClass() == NilNode.class) {
                replacePaiFilho(noMovidoParaCima.pai, noMovidoParaCima, null);
            }
        }
    }

    private ArvoreRB deleteNodeWithZeroOrOneChild(ArvoreRB no) {
        // O nó tem APENAS um filho esquerdo --> substitua por seu filho esquerdo
        if (no.esquerda != null) {
            replacePaiFilho(no.pai, no, no.esquerda);
            return no.esquerda; // moved-up node
        }

        // Node has ONLY a right child --> replace by its right child
        else if (node.right != null) {
            replaceParentsChild(node.parent, node, node.right);
            return node.right; // moved-up node
        }

        // Node has no children -->
        // * node is red --> just remove it
        // * node is black --> replace it by a temporary NIL node (needed to fix the R-B rules)
        else {
            Node newChild = node.color == BLACK ? new NilNode() : null;
            replaceParentsChild(node.parent, node, newChild);
            return newChild;
        }
    }


}
