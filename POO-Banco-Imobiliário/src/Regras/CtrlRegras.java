package Regras;

import Model.Player.Player;
import Model.Dice;
import Model.Property.*;

import java.util.ArrayList; // import the ArrayList class
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;

import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import java.awt.Color;
//import Model.Player;

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

	private ArrayList<Player> playerList = new ArrayList<Player>();
	private int playerAtual = 0;
	private Player player_atual;
	private String cor;

	private boolean podeJogar;

	private int dadosRepetidos = 0;
	private Dice dados = new Dice();
	private Property[] propriedades = CriaPropriedades.cria();

	private int cartaAtual = -1;

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

	// instance
	public static CtrlRegras getInstance() {
		if (instance == null)
			instance = new CtrlRegras();
		return instance;
	}

	// Methods
	public boolean checkNumPlayers(int num) { // Check number of players
		if (num > maxPlayers || num < minPlayers) {
			System.out.println("Número de jogadores inválido. Tente novamente.");
			return false;
		}
		numPlayers = num;
		return true;
	}

	public int getNumPlayers() {
		return numPlayers;
	}

	public void initPlayers(JComboBox pColors[], JTextField pNames[]) {
		Color auxColor = Color.red;
		String auxColorName = "Vermelho";
		int pinNumber = 0;
		for (int i = 0; i < numPlayers; i++) {
			switch (pColors[i].getSelectedIndex()) {
				case 0: {
					auxColor = Color.red;
					auxColorName = "Vermelho";
					pinNumber = 0;
					break;
				}
				case 1: {
					auxColor = Color.blue;
					auxColorName = "Azul";
					pinNumber = 1;
					break;
				}
				case 2: {
					auxColor = Color.orange;
					auxColorName = "Laranja";
					pinNumber = 2;
					break;
				}
				case 3: {
					auxColor = Color.yellow;
					auxColorName = "Amarelo";
					pinNumber = 3;
					break;
				}
				case 4: {
					auxColor = Color.magenta;
					auxColorName = "Roxo";
					pinNumber = 4;
					break;
				}
				case 5: {
					auxColor = Color.gray;
					auxColorName = "Cinza";
					pinNumber = 5;
					break;
				}
				default:
					auxColor = Color.red;
					auxColorName = "Vermelho";
					pinNumber = 0;
			}
			playerList.add(new Player(i + 1, 4000, auxColor, auxColorName, pinNumber, pNames[i].getText()));
		}
	}

	public Player getPlayer(int i) {
		return playerList.get(i);
	}

	public ArrayList<Player> getAllPlayers() {
		return playerList;
	}

	public Property[] getAllProperties() {
		return propriedades;
	}

	public Property getProperty(int i) {
		return propriedades[i];
	}

	public void organizePlay() {
		for (int i = 0; i < numPlayers; i++) {
			int posX = propriedades[0].getPos(i)[0];
			int posY = propriedades[0].getPos(i)[1];
			playerList.get(i).setPosition(0);
			playerList.get(i).setCoordenates(posX, posY);
		}
	}

	////////////////////////////////////////////////////
	public void controlePlayers(int playerAtual) {
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

		dados.rollDice();

		return lidarComDados();
	}

	public int lidarComDados() {
		player_atual = playerList.get(playerAtual);
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

	public int lidarComCartas() {
		player_atual = playerList.get(playerAtual);
		// System.out.println(playerAtual);
		cartaAtual = cartas.remove(0);

		if (cartaAtual == 8) { // Saida da prisão
			player_atual.changeStatusSaidaPrisao();
			return cartaAtual;
		} else if (cartaAtual == 10) { // receba 50 de cada jogador
			for (int i = 0; i < numPlayers; i++) {
				if (i != playerAtual) {
					playerList.get(i).changeMoney(-50);
				}
			}
			player_atual.changeMoney(50 * (numPlayers - 1));

		} else if (cartaAtual == 22) { // vai para a prisao
			player_atual.goToPrison();
			podeJogar = false;
			if (player_atual.getSaidaLivrePrisao()) {
				player_atual.changeStatusPreso();
				cartas.add(8);
			}
		} else {
			player_atual.changeMoney(cartasSorteReves[cartaAtual]);
		}

		cartas.add(cartaAtual);

		return cartaAtual;
	}

	public int movePlayer(int valDados) {

		player_atual.movePawn(valDados);

		int posicao = player_atual.getPawnPos();

		if (propriedades[posicao] instanceof Enterprise || propriedades[posicao] instanceof Ground) {
			int displayC = lidarComPropriedade(posicao);
			this.notificaAll();
			return displayC;
		} else {
			switch (propriedades[posicao].getNome()) {
				case "Sorte/Reves": {
					return lidarComCartas();
				}
				case "Ganhe": {
					player_atual.changeMoney(200);
					break;
				}
				case "Perde": {
					player_atual.changeMoney(-200);
					break;
				}
				case "Prisao": {//rever
					break;
				}
				case "Va para a prisao": {
					player_atual.goToPrison();
					if (player_atual.getSaidaLivrePrisao()) {
						player_atual.changeStatusPreso(); // deixa de estar preso
						cartas.add(8);
					}
					break;
				}

			}
		}

		this.notificaAll();
		return -1;
	}

	public void venderPropriedade() {

		ArrayList<Integer> PlayerPropriedades = player_atual.getPropriedades();
		String[] listaNomesPropriedades = new String[PlayerPropriedades.size()];

		for(int i=0;i<PlayerPropriedades.size();i++){
			listaNomesPropriedades[i] = propriedades[PlayerPropriedades.get(i)].getNome();
		}

		if(player_atual.getPropriedades().size() > 0){
			JComboBox<String> listaPropriedades = new JComboBox<String>(listaNomesPropriedades);
			Object[] display = {"escolha uma das suas propriedades para vender", listaPropriedades};
			int pane = JOptionPane.showOptionDialog(null, display, "Vender propriedades", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);

			if(pane == JOptionPane.OK_OPTION){
				int propriedade = player_atual.getPropriedades().get(listaPropriedades.getSelectedIndex());
				if(propriedades[propriedade] instanceof Enterprise){
					player_atual.removePropriedade(propriedade);
					propriedades[propriedade].setProprietario(-1);
					player_atual.changeMoney(propriedades[propriedade].getValorCompra()*9/10);
					this.notificaAll();
					JOptionPane.showMessageDialog(null, "voce acabou de vender sua propriedade "+ listaPropriedades.getSelectedIndex() + " por R$: " + propriedades[propriedade].getValorCompra()*9/10);

				}

				else{
					player_atual.removePropriedade(propriedade);
					propriedades[propriedade].setProprietario(-1);
					player_atual.changeMoney(((Ground)propriedades[propriedade]).getPriceToSellBuildings()*9/10);
					this.notificaAll();
					JOptionPane.showMessageDialog(null, "voce acabou de vender sua propriedade "+ listaPropriedades.getSelectedIndex() + " por R$: " + (((Ground)propriedades[propriedade]).getPriceToSellBuildings()*9/10));
				}
			}
		}

		return;
	}

	public void comprarCasa(){
		return;
	}

	private int lidarComPropriedade(int propriedade) {

		int proprietario = propriedades[propriedade].getProprietario();
		int valorCompra = propriedades[propriedade].getValorCompra();
		String nomePropriedade = propriedades[propriedade].getNome();

		if (proprietario == -1) { // nao existe proprietario

			String[] simnao = { "sim", "nao" };
			int opcao = JOptionPane.showOptionDialog(null,
					"voce gostaria de comprar a propriedade" + nomePropriedade + " pelo preço: R$" + valorCompra,
					"click a button", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, simnao,
					simnao[0]);

			if (opcao == 0) {
				if (player_atual.getMoney() >= valorCompra) {
					propriedades[propriedade].setProprietario(playerAtual);
					player_atual.changeMoney(-valorCompra);
					player_atual.addPropriedade(propriedade);
					JOptionPane.showMessageDialog(null,
							"A propriedade:" + nomePropriedade + " foi comprada por: R$" + valorCompra);
				} else {
					JOptionPane.showMessageDialog(null, "Voce nao tem dinheiro suficiente para comprar a propriedade: "
							+ nomePropriedade + " pelo valor: R$" + valorCompra);
				}
			}

		} else { // existe proprietario
			if (proprietario != playerAtual) { // player atual nao e o proprietario
				if (propriedades[propriedade] instanceof Enterprise) { // ENTERPRISE
					int aluguel = ((Enterprise) propriedades[propriedade]).getRent(dados.getSumDices());
					player_atual.changeMoney(-aluguel);
					int playerMoney = player_atual.getMoney();
					while (playerMoney <= 0 && player_atual.getPlayerFalencia()) {
						int playerMoneyAntes = playerMoney;
						venderPropriedade();
						playerMoney = player_atual.getMoney();

						JOptionPane.showMessageDialog(null,
								"Voce nao possui dinheiro suficiente, venda uma de suas propriedades para pagar o Aluguel de R$"
										+ aluguel);

						if (playerMoneyAntes == playerMoney) {
							player_atual.changeStatusFalencia();
						}
					}

					Player playerProprietario = playerList.get(proprietario);
					playerProprietario.changeMoney(aluguel);

					if (player_atual.getPlayerFalencia()) {
						JOptionPane.showMessageDialog(null,
								" o player " + player_atual.getCor()
										+ " foi a falencia, pois nao conseguiu pagar o aluguel de R$" + aluguel
										+ " para o player " + playerProprietario.getCor());
					} else {
						JOptionPane.showMessageDialog(null, " o player " + player_atual.getCor()
								+ " pagou o aluguel de R$" + aluguel + " para o player " + playerProprietario.getCor());
					}

				} else { // GROUND
					int aluguel = ((Ground) propriedades[propriedade]).getRent();
					player_atual.changeMoney(-aluguel);
					int playerMoney = player_atual.getMoney();
					while (playerMoney <= 0 && player_atual.getPlayerFalencia()) {
						int playerMoneyAntes = playerMoney;
						venderPropriedade();
						playerMoney = player_atual.getMoney();

						if (playerMoney == playerMoneyAntes) {
							player_atual.changeStatusFalencia();
						}
					}

					Player playerProprietario = playerList.get(proprietario);
					playerProprietario.changeMoney(aluguel);

					if (player_atual.getPlayerFalencia()) {
						JOptionPane.showMessageDialog(null,
								" o player " + player_atual.getCor()
										+ " foi a falencia, pois nao conseguiu pagar o aluguel de R$" + aluguel
										+ " para o player " + playerProprietario.getCor());
					} else {
						JOptionPane.showMessageDialog(null, " o player " + player_atual.getCor()
								+ " pagou o aluguel de R$" + aluguel + " para o player " + playerProprietario.getCor());
					}
				}
			}
		}

		return 0;
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

		String ranking = "RANKING:\n";
		for (Player p : playerList) {
			ranking += String.format("jogador: %d , dinheiro: %d, cor: %s \n", p.getNumber(), p.getMoney(),
					p.getCor());
		}
		JOptionPane.showMessageDialog(null, ranking);
		System.exit(1);
	}

	@Override
	public void add(ObservadorIF o) {
		observers.add(o);

	}

	@Override
	public void remove(ObservadorIF o) {
		observers.remove(o);

	}

	@Override
	public int get(int var) {
		if (var == 1) {
			return cartaAtual;
		}
		return -1;
	}

	private void notificaAll() {
		for (ObservadorIF obs : observers) {
			obs.notify(this);
		}
	}
}
