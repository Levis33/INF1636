package Model.Property;

//import Model.Player.Player;

public class Enterprise extends Property {

    private int[] loan;

    public Enterprise(String name, int owner, int[] loan, int price) {
        super(name, owner, loan, price);
    }

    public int getLoan(int sumDices) {
        return loan[0] * sumDices;
    }

}
