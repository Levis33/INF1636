package Model.Property;

public abstract class Property {

    private String nome;
    private int valorCompra, proprietario = -1; // -1 caso nao tenha, se tiver e a posicao no array de jogadores
    private int[] aluguel;
    private int posX;
    private int posY;
    private int cardNumber;

    public Property(String n, int[] a, int v, int posX, int posY, int cardNumber) {
        this.nome = n;
        this.aluguel = a;
        this.valorCompra = v;
        this.nome = n;
        this.posX = posX;
        this.posY = posY;
        this.cardNumber = cardNumber;
    }

    public Property(String n, int posX, int posY) {
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

    public int[] getPos(int i) {
        int[] positions = { this.posX, this.posY };
        if (i % 2 == 0) {
            positions[0] = this.posX + i * 8;
            positions[1] = this.posY;
        } else {
            positions[0] = this.posX + (i-1) * 8;
            positions[1] = this.posY - 20;
        }
        return positions;
    }

    public int getCardNumber(){
        return cardNumber;
    }

    public void setProprietario(int novoProprietario) {
        this.proprietario = novoProprietario;
    }

}