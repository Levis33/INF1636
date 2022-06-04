package Regras;

import java.util.Scanner;

import Model.Player.Player;
import Model.Dice;

import java.util.ArrayList; // import the ArrayList class
import java.util.Collections;
import java.awt.Color;
//import Model.Player;
import java.util.Comparator;

// import javax.swing.JOptionPane;

import Controller.Observer.ObservadoIF;
import Controller.Observer.ObservadorIF;

public class CtrlRegras implements ObservadoIF {

	// Definition of variables
	private static CtrlRegras instance = null;

	private ArrayList<ObservadorIF> observers = new ArrayList<ObservadorIF>();

	private int maxPlayers = 6;
	private int minPlayers = 3;

	private int numPlayers;

	private Player player_atual;

	private ArrayList<Player> playerList = new ArrayList<Player>();
	private int playerAtual;

	private boolean podeJogar;

	private int dadosRepetidos = 0;
	private Dice dados = new Dice();

	private int cartaAtual = -1;

	int[] posicaoPropriedade = {
			1, 3, 4, 5, 6, 7, 8, 9, 11, 13, 14, 15, 17, 19, 21, 23, 25, 26, 28, 29, 31, 32, 33, 34, 35, 36, 38, 39
	};
	// para achar o index no criaPropriedades usar
	// Arrays.asList(posicaoPropriedade).indexOf(posicao);

	private ArrayList<Integer> cartas = new ArrayList<Integer>();
	private int[] cartasSorteReves = { // Cartas especiais: 9, 11, 23
			25, 150, 80, 200, 50, 50, 100, 100, 0, 200, 50, 45, 100, 100, 20, -15, -25, -45, -30, -100, -100, -40, -1,
			-30, -50, -25, -30, -45, -50, -50,
	};
	// Carta especial 9 (index 8): valor 0 -> saída livre da prisão
	// Carta especial 11 (index 10): valor 50 -> receba 50 de cada jogador
	// Carta especial 23 (index 22): valor -1 -> ir para a prisão

	// Scanner, Random etc
	Scanner scan = new Scanner(System.in);

	// Constructors
	public CtrlRegras() {

		for (int i = 0; i < 30; i++) {
			cartas.add(i);
		}
		Collections.shuffle(cartas);
	}

	// Methods
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
		player_atual = playerList.get(playerAtual);

		while (player_atual.getPlayerFalencia()) {
			if (primPlayer == playerAtual) {
				endGame();
			}
			playerAtual = (playerAtual + 1) % numPlayers;
			player_atual = playerList.get(playerAtual);
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
		player_atual = playerList.get(playerAtual);
		dados.rollDice();
		if (dados.dadosIguais()) {
			dadosRepetidos += 1;
			if (player_atual.getPlayerPreso()) {
				player_atual.changeStatusPreso();
				// JOptionPane.showMessageDialog(null,"Você está livre da prisão");
			}
			if (dadosRepetidos >= 3) {
				player_atual.goToPrison();
				// JOptionPane.showMessageDialog(null,"Você foi preso por tirar o dado 3 vezes
				// iguais");
				if (player_atual.getSaidaLivrePrisao()) {
					player_atual.changeStatusSaidaPrisao();
					cartas.add(8);
					// JOptionPane.showMessageDialog(null,"Você usou sua carta de sair da prisão");
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



	public int lidarComCartas() {
		cartaAtual = cartas.remove(0);

		if (cartaAtual == 8) { // Saída da prisão
			player_atual.changeStatusSaidaPrisao();
			return cartaAtual;
		} 
		else if (cartaAtual == 10) { // receba 50 de cada jogador
			for(int i=0; i < numPlayers; i++){
				if(i != playerAtual){
					playerList.get(i).changeMoney(-50);
				}
			}
			player_atual.changeMoney(50*(numPlayers-1));

		}
		else if (cartaAtual == 22) { // vai para a prisão
			player_atual.goToPrison();
			podeJogar=false;
			if(player_atual.getSaidaLivrePrisao()){
				player_atual.changeStatusPreso();
				cartas.add(8);
			}
		}
		else {
			player_atual.changeMoney(cartasSorteReves[cartaAtual]);
		}

		cartas.add(cartaAtual);	

		return cartaAtual;
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
