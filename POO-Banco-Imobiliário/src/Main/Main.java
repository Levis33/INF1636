package Main;

import Model.*;

public class Main {

	public static void main(String[] args) {
		Match match = new Match();

		int numPlayers = match.getNumPlayers();
		System.out.println(match.initPlayers(numPlayers)); // OK!
		match.endGame();
	}

}
