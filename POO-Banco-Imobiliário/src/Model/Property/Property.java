package Model.Property;

public abstract class Property {

    private String nome;
    private int valorCompra, proprietario = -1; // -1 caso nao tenha, se tiver e a posicao no array de jogadores
    private int[] aluguel;
    private int posX;
    private int posY;

    public Property(String n, int[] a, int v, int posX, int posY) {
        this.nome = n;
        this.aluguel = a;
        this.valorCompra = v;
        this.nome = n;
        this.posX = posX;
        this.posY = posY;
    }
    
    public Property(String n, int posX, int posY){
        this.nome = n;
        this.posX = posX;
        this.posY = posY;
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

    public int[] getPos() {
        int[] positions = {this.posX, this.posY};
        return positions;
    }

    public void setProprietario(int novoProprietario){
        this.proprietario = novoProprietario;
    }

}