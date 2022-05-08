package Model.Property;

import java.awt.Color;

public class Ground extends Property {

    private Color color;
    private int priceBuilding;
    private int numHotels = 0;
    private int numHouses = 0;
    private int[] rent;

    public Ground(String name, int owner, int[] rent, int[] price, Color c) {
        super(name, owner, rent, price[0]);
        priceBuilding = price[1];
        color = c;
    }

    public int getRent() {
        return rent[numHouses + numHotels];
    }

    public Color getColor() {
        return color;
    }

    // Building
    public int getPriceToSellBuildings() {
        return priceBuilding * (numHouses + numHotels);
    }

    public int sellBuildings() {
        int price = getPriceToSellBuildings();
        numHouses = 0;
        numHotels = 0;
        return price;
    }

    // House
    public int buyHouse() {
        numHouses += 1;
        return priceBuilding;
    }

    public int getHouses() {
        return numHouses;
    }

    // Hotel
    public int buyHotel() {
        numHotels += 1;
        return priceBuilding;
    }

    public int getHotels() {
        return numHotels;
    }

}
