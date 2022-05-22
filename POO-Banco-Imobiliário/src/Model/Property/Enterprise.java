package Model.Property;

//import Model.Player.Player;

public class Enterprise extends Property {

    private int[] rent;

    public Enterprise(String name,  int[] rent, int price) {
        super(name, rent, price);
    }

    public int getRent(int sumDices) {
        return rent[0] * sumDices;
    }

}
