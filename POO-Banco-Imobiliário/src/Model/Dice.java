package Model;

import java.util.Random;

public class Dice {
	
	// Definition of Variables
	private int diceValue;
	private int maxValueDice = 6;
	
	// Random, Scanner etc
	Random rand = new Random();
	
	// Constructors
	public Dice () {}
	
	// Methods
	public int getDiceValue() { return this.diceValue; }
	
	public int rollDice() {
		this.diceValue = rand.nextInt(maxValueDice) + 1;
		return diceValue;
	}
}
