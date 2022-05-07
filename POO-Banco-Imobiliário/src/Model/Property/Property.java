package Model.Property;

public abstract class Property {

    private String nome;
    private int valorCompra, proprietario = -1; // -1 caso nao tenha, se tiver e a posicao no array de jogadores
    private int[] aluguel;

    public Property(String n, int p, int[] a, int v) {
        this.nome = n;
        this.proprietario = p;
        this.aluguel = a;
        this.valorCompra = v;
    }

    // methods
    public String getNome() {
        return this.nome;
    }

    public int getProprietario() {
        return this.proprietario;
    }

    public int[] getAluguel() {
        return this.aluguel;
    }

    public int getValorCompra() {
        return this.valorCompra;
    }

}