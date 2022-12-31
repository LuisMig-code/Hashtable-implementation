public class Elemento {
    int chave;
    int valor;

    public Elemento(int chave , int valor) {
        this.chave = chave;
        this.valor = valor;
    }

    // Getters  and Setters:
    public int getChave() {
        return chave;
    }

    public void setChave(int chave) {
        this.chave = chave;
    }

    public int getValor() {
        return valor;
    }

    public void setValor(int valor) {
        this.valor = valor;
    }

    @Override
    public String toString() {
        return ("Chave: " + this.getChave() + " | " + "Valor: " + this.getValor() );
    }
}
