package Model;

import java.util.Scanner;

import Model.Dice.Dice;
import Model.Player.Player;

import java.util.ArrayList; // import the ArrayList class
import java.util.Collections;
import java.awt.Color;
//import Model.Player;
import java.util.Comparator;

public class Match {

	// Definition of variables
	// Players
	private int maxPlayers = 6;
	private int minPlayers = 2;

	private ArrayList<Player> playerList = new ArrayList<Player>();
	// private int numPlayers = 0;

	// Scanner, Random etc
	Scanner scan = new Scanner(System.in);

	// Constructors
	public Match() {
	}

	// Methods
	public int getNumPlayers() { // Get number of players
		System.out.println("Digite a quantidade de jogadores:(Mínimo de 2 e máximo de 6)");
		int numPlayers = scan.nextInt();

		if (numPlayers > maxPlayers || numPlayers < minPlayers) {
			System.out.println("Número de jogadores inválido. Tente novamente.");
			System.exit(1);
		}

		scan.close();

		return numPlayers;
	}

	public ArrayList<Player> initPlayers(int numPlayers) {

		Color cor, pawnColours[] = { Color.red, Color.blue, Color.orange, Color.yellow, Color.magenta, Color.gray };

		for (int i = 0; i < numPlayers; i++) {
			cor = pawnColours[i];

			playerList.add(new Player(i + 1, 4000, cor));
		}

		return playerList;
	}

	Comparator<Player> comparator = new Comparator<Player>() { // compara todos os players e coloca na ordem de vencedor
		@Override
		public int compare(Player p1, Player p2) {
			int m1, m2;
			m1 = p1.getPlayerMoney();
			m2 = p2.getPlayerMoney();
			return m1 > m2 ? -1
					: m1 < m2 ? 1
							: 0;
		}
	};

	public void endGame() {

		Collections.sort(playerList, comparator);

		System.out.println("\nRANKING:\n");
		for (Player p : playerList) {
			System.out.printf("jogador: %d , dinheiro: %d, cor: %s \n", p.getPlayerNumber(), p.getPlayerMoney(),
					p.getPlayerColor().toString());
		}
	}
}
