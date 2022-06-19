package Model.Player;

public class Pawn {

    private int pos = 0;
    private int posX = 0;
    private int posY = 0;

    private int pawnNumber;

    public Pawn() {

    }

    public Pawn(int pawnNumber) {
        this.pawnNumber = pawnNumber;
    }

    void move(int dices) {
        pos = (pos + dices) % 40; // 40 posicoes para andar
    }

    int getPos() {
        return this.pos;
    }

    int[] getCoordenates() {
        int[] position = { this.posX, this.posY };
        return position;
    }

    int getPawnNumber() {
        return this.pawnNumber;
    }

    public void setPosition(int x) {
        this.pos = x;
        return;
    }

    public void setCoordenates(int x, int y) {
        this.posX = x;
        this.posY = y;
    }

}
