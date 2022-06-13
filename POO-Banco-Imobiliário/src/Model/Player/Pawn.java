package Model.Player;

public class Pawn {

    private int pos = 0;
    private int posX = 0;
    private int posY = 0;

    private int pawnNumber;

    Pawn(int pawnNumber){
        this.pawnNumber = pawnNumber;
    }

    void move(int dices) {
        pos = (pos + dices) % 40; // 40 posicoes para andar
    }

    void goTo(int position) {
        this.pos = position;
    }

    int getPos() {
        return this.pos;
    }

    int getPawnNumber(){
        return this.pawnNumber;
    }

}
