package Model.Dice;

import java.util.Random;

public class Dice {

	// Definition of Variables
	private int dice1;
	private int dice2;
	private int sumDices;

	// Random, Scanner etc
	Random rand = new Random();

	// Constructors
	public Dice() {
	}

	// Methods
	public int getDice1() {
		return this.dice1;
	}

	public int getDice2() {
		return this.dice2;
	}

	public void rollDice() {
		this.dice1 = rand.nextInt(6) + 1;
		this.dice2 = rand.nextInt(6) + 1;
	}

	public int getSumDices(Dice dice) {
		return this.sumDices = dice.getDice1() + dice.getDice2();
	}
}
