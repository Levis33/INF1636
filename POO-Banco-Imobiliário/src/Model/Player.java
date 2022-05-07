package Model;

import java.util.Random;

public class Player {
	
	// Definition of variables
	private int playerNumber;
	private String playerColour;
	private float playerMoney;
	
	// Random, Scanner etc
	Random rand = new Random();

	// Constructors
	public Player() {}
	
	public Player(float money, String colour) {
		this.playerMoney = money;
		this.playerColour = colour;
	}
	
	
	// Methods
	public int getPlayerNumber() { return this.playerNumber; }
	
	public String getPlayerColour() { return this.playerColour; }
	
	public float getPlayerMoney() { return this.playerMoney; }

}