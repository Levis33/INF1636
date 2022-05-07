package Model;

import java.util.Scanner;
import java.util.ArrayList;  // import the ArrayList class
import java.util.Random;

public class Match {

		// Definition of variables
	
		// Players 
		private int maxPlayers = 6;
		private int minPlayers = 2;
		
		
		// Scanner, Random etc		
		Scanner scan = new Scanner(System.in);
		Random rand = new Random();
		
		// Constructors
		public Match() {}
		
		
		// Methods
		public int getNumPlayers() {  // Get number of players
			System.out.println("Digite a quantidade de jogadores:(Mínimo de 2 e máximo de 6)");
			int numPlayers = scan.nextInt();
				
			if (numPlayers > maxPlayers || numPlayers < minPlayers) {
				System.out.println("Número de jogadores inválido. Tente novamente.");
				System.exit(1);
			}
				
			scan.close();
				
			return numPlayers;
		}
		
		
		public ArrayList<String> initPlayers(int numPlayers) {
			int index, len = 6;
			String colour, pawnColours[] = {"red", "yellow", "green", "blue", "white", "black"};
			ArrayList<String> playerList = new ArrayList<String>();  // Create an ArrayList object
			
			for (int i = 0; i < numPlayers; i++) {
				index = rand.nextInt(len);
				colour = pawnColours[index];
				playerList.add(colour);
			}
			
			return playerList;
		}
		
}
