package Model.Property;

//import Model.Player.Player;

public class Enterprise extends Property {

    private int[] rent;

    public Enterprise(String name,  int[] rent, int price, int x, int y, int cardNumber) {
        super(name, rent, price, x, y, cardNumber);
    }

    public int getRent(int sumDices) {
        return rent[0] * sumDices;
    }

}
