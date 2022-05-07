package Main;

import Model.*;

public class Main {

	public static void main(String[] args) {
		Match match = new Match();
		Dice dice = new Dice();

		
		dice.rollDice(); // OK!

		System.out.printf("%d %d", dice.getDice1(), dice.getDice2()); // OK!
		
		int numPlayers = match.getNumPlayers();
		System.out.println(match.initPlayers(numPlayers)); // OK!
		match.Fim();

	}

}
