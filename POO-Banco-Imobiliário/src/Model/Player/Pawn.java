package Model.Player;

public class Pawn {

    private int pos = 0;

    void move(int dices) {
        pos = (pos + dices) % 40; // 40 posicoes para andar
    }

    void goTo(int position) {
        this.pos = position;
    }

    int getPos() {
        return this.pos;
    }

}
