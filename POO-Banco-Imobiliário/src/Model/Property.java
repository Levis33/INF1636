package Model;

public abstract class Property {

    private String nome;
    private int proprietario = -1; // -1 caso nao tenha, se tiver e a posicao no array de jogadores
    private int[] aluguel;


    Property(String n, int p, int[] a) {
        this.nome = n;
        this.proprietario = p;
        this.aluguel = a;
    }

    // methods

    public String getNome() { return this.nome; }

    public int getProprietario() { return this.proprietario; }

    public int[] getAluguel() { return this.aluguel; }

}