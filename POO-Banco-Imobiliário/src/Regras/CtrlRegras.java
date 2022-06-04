package Regras;

import java.util.Scanner;

import Model.Player.Player;
import Model.Dice;

import java.util.ArrayList; // import the ArrayList class
import java.util.Collections;
import java.awt.Color;
//import Model.Player;
import java.util.Comparator;

import javax.swing.JOptionPane;

import Controller.Observer.ObservadoIF;
import Controller.Observer.ObservadorIF;

public class CtrlRegras implements ObservadoIF {

	// Definition of variables
	private static CtrlRegras instance = null;

	private ArrayList<ObservadorIF> observers = new ArrayList<ObservadorIF>();

	private int maxPlayers = 6;
	private int minPlayers = 3;

	private int numPlayers;

	private ArrayList<Player> playerList = new ArrayList<Player>();
	private int playerAtual;

	private boolean podeJogar;

	private int dadosRepetidos = 0;
	private Dice dados = new Dice();

	int[] posicaoPropriedade = {
		1,3,4,5,6,7,8,9,11,13,14,15,17,19,21,23,25,26,28,29,31,32,33,34,35,36,39,39
	}; // para achar o index no criaPropriedades usar Arrays.asList(posicaoPropriedade).indexOf(posicao);

	// Scanner, Random etc
	Scanner scan = new Scanner(System.in);

	// Constructors
	public CtrlRegras() {
	}

	// Methods

	// public String[] getPlayersName(int numPlayers) { // Get name of players
	// String namePlayers[numPlayers];

	// for (int i = 0; i < numPlayers; i++){
	// System.out.println("Digite o seu nome: ");
	// namePlayers[i] = scan.nextLine();
	// }

	// scan.close();

	// return namePlayers;
	// }

	public void getNumPlayers() { // Get number of players
		System.out.println("Digite a quantidade de jogadores:(Mínimo de 2 e máximo de 6)");
		numPlayers = scan.nextInt();

		if (numPlayers > maxPlayers || numPlayers < minPlayers) {
			System.out.println("Número de jogadores inválido. Tente novamente.");
			System.exit(1);
		}

		scan.close();
	}

	public ArrayList<Player> initPlayers(int numPlayers) {

		Color cor, pawnColours[] = { Color.red, Color.blue, Color.orange, Color.yellow, Color.magenta, Color.gray };

		for (int i = 0; i < numPlayers; i++) {
			cor = pawnColours[i];

			playerList.add(new Player(i + 1, 4000, cor, "teste"));
		}

		return playerList;
	}

	public void controlePlayers() {

		int primPlayer = playerAtual;

		playerAtual = (playerAtual + 1) % numPlayers; // proximo jogador

		Player player_atual = playerList.get(playerAtual);

		while (player_atual.getPlayerFalencia()) {
			if (primPlayer == playerAtual) {
				endGame();
			}
			playerAtual = (playerAtual + 1) % numPlayers;
		}
		if (playerAtual == primPlayer) { // todos menos atual foram a falencia
			endGame();
		}

		dadosRepetidos = 0;
		podeJogar = true;
	}

	public int jogaDados() {

		if (podeJogar == false) {
			return 0;
		}

		Player player_atual = playerList.get(playerAtual);

		dados.rollDice();

		if (dados.dadosIguais()) {

			dadosRepetidos += 1;

			if (player_atual.getPlayerPreso()) {
				player_atual.changeStatusPreso();
				// JOptionPane.showMessageDialog(null,"você está livre da prisão");
			}

			if (dadosRepetidos >= 3) {
				player_atual.goToPrison();
				// JOptionPane.showMessageDialog(null,"você foi preso por tirar o dado 3 vezes
				// iguais");
				if (player_atual.getSaidaLivrePrisao()) {
					player_atual.changeStatusSaidaPrisao();
					// JOptionPane.showMessageDialog(null,"você usou sua carta de sair da prisão");
				}
				podeJogar = false;
				return 0;
			}
		} else {
			podeJogar = false;
		}

		return dados.getSumDices();
	}

	public static CtrlRegras getInstance() {
		if (instance == null)
			instance = new CtrlRegras();
		return instance;
	}

	Comparator<Player> comparator = new Comparator<Player>() { // compara todos os players e coloca na ordem de vencedor
		@Override
		public int compare(Player p1, Player p2) {
			int m1, m2;
			m1 = p1.getMoney();
			m2 = p2.getMoney();
			return m1 > m2 ? -1
					: m1 < m2 ? 1
							: 0;
		}
	};

	public void endGame() {

		Collections.sort(playerList, comparator);

		System.out.println("\nRANKING:\n");
		for (Player p : playerList) {
			System.out.printf("jogador: %d , dinheiro: %d, cor: %s \n", p.getNumber(), p.getMoney(),
					p.getColor().toString());
		}
	}

	@Override
	public void add(ObservadorIF o) {
		// TODO Auto-generated method stub

	}

	@Override
	public void remove(ObservadorIF o) {
		// TODO Auto-generated method stub

	}

	@Override
	public int get(int var) {
		// TODO Auto-generated method stub
		return 0;
	}
}
