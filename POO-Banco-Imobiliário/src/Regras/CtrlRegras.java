package Regras;

import Model.Player.Player;
import Model.Dice;
import Model.Property.*;

import java.util.ArrayList; // import the ArrayList class
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.DefaultStyledDocument.ElementSpec;

import java.awt.Color;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
//import Model.Player;
import java.io.IOException;

// import javax.swing.JOptionPane;

import Controller.Observer.ObservadoIF;
import Controller.Observer.ObservadorIF;
import JFrame.GameIntro;
import JFrame.GamePlayers;

public class CtrlRegras implements ObservadoIF {

	// Definition of variables
	private static CtrlRegras instance = null;

	private ArrayList<ObservadorIF> observers = new ArrayList<ObservadorIF>();

	private int maxPlayers = 6;
	private int minPlayers = 3;

	private int numPlayers;

	private ArrayList<Player> playerList = new ArrayList<Player>();
	private int playerIndex = 0;
	private Player playerAtual;
	private int[] diceValues = new int[2];
	private int diceSum;
	private boolean jogou = false;
	private boolean stealing = false;

	private boolean jaComprouCasa = false;

	private boolean podeJogar;
	private boolean shouldPlayAgain;

	private int dadosRepetidos = 0;
	private Dice dados = new Dice();
	private Property[] propriedades = CriaPropriedades.cria();

	private int cartaAtual = -1;
	private int propriedadeAtualCardIndex = -1;
	private Property propriedadeAtual = null;
	private Property p;

	private boolean canSave = false;

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

		// Escolher nova partida ou load de jogo salvo
		String[] optionsGame = { "Nova Partida", "Continuar" };
		int optGame = JOptionPane.showOptionDialog(null, "Iniciar uma nova partida ou continuar de jogo salvo?",
				"Iniciar Partida", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, optionsGame,
				optionsGame[0]);

		if (optGame == -1) {
			// sair caso tenha clicado X
			System.exit(0);
		} else if (optGame == 0) { // Nova partida

			// preenche e faz shuffle das cartas
			for (int i = 0; i < 30; i++) {
				cartas.add(i);
			}
			Collections.shuffle(cartas);

		}
		// else { // referente ao jogo salvo

		// }

	}

	// instance
	public static CtrlRegras getInstance() {
		if (instance == null)
			instance = new CtrlRegras();
		return instance;
	}

	public boolean canSave() {
		return canSave;
	}

	// Methods
	public boolean checkNumPlayers(int num) { // Check number of players
		if (num > maxPlayers || num < minPlayers) {
			JOptionPane.showMessageDialog(null, "Número de jogadores inválido. Tente novamente.");
			return false;
		}
		numPlayers = num;
		return true;
	}

	public int getNumPlayers() {
		return numPlayers;
	}

	public void initPlayers(JComboBox<String> pColors[], JTextField pNames[]) {
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

	public Player getPlayerAtual() {
		return playerAtual;
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
		playerIndex = 0;
		playerAtual = playerList.get(playerIndex);
		diceValues[0] = 1;
		diceValues[1] = 1;
		podeJogar = true;
		jogou = false;
		shouldPlayAgain = false;
		canSave = false;
	}

	public int[] getDicesValue() {
		return diceValues;
	}

	public int getShowingCard() {
		return cartaAtual;
	}

	public int getShowingPropertyCardIndex() {
		return propriedadeAtualCardIndex;
	}

	public Property getShowingProperty() {
		return propriedadeAtual;
	}

	public String[] getPlayerPropriedades() {
		ArrayList<Integer> listaPropriedades = playerAtual.getPropriedades();
		String[] nomePropriedades = new String[listaPropriedades.size()];

		for (int i = 0; i < listaPropriedades.size(); i++) {
			nomePropriedades[i] = propriedades[listaPropriedades.get(i)].getNome();
		}

		return nomePropriedades;
	}

	public ArrayList<Player> getPlayers() {
		return playerList;
	}

	////////////////////////////////////////////////////
	public void controlePlayers() {
		if (shouldPlayAgain || !jogou) {
			JOptionPane.showMessageDialog(null, "É necessário jogar os dados");
			return;
		}
		int primPlayer = playerIndex;
		dadosRepetidos = 0;
		jogou = false;
		playerIndex = (playerIndex + 1) % numPlayers; // proximo jogador
		playerAtual = playerList.get(playerIndex);
		propriedadeAtualCardIndex = -1;
		propriedadeAtual = null;
		cartaAtual = -1;
		canSave = true;

		falenciaCartaEspecial();

		while (playerAtual.getPlayerFalencia()) {
			if (primPlayer == playerIndex) {
				endGame();
			}
			playerIndex = (playerIndex + 1) % numPlayers;
			playerAtual = playerList.get(playerIndex);
			falenciaCartaEspecial();
		}

		if (playerIndex == primPlayer) { // todos menos atual foram a falencia
			endGame();
		}

		dadosRepetidos = 0;
		podeJogar = true;
		jaComprouCasa = false;
		this.notificaAll();
	}

	private void falenciaCartaEspecial() {
		int playerMoney = playerAtual.getMoney();
		notificaAll();

		while (playerMoney < 0 && !playerAtual.getPlayerFalencia()) {
			int playerMoneyAntes = playerMoney;
			JOptionPane.showMessageDialog(null,
					"Voce nao possui dinheiro suficiente, venda uma de suas propriedades para a pagar R$ 50");
			venderPropriedade();
			playerMoney = playerAtual.getMoney();

			if (playerMoneyAntes == playerMoney) {
				playerAtual.changeStatusFalencia();
				break;
			}

		}

		if (playerAtual.getPlayerFalencia() && !playerAtual.getSaiuDoJogo()) {
			JOptionPane.showMessageDialog(null,
					" o player " + playerAtual.getCor()
							+ " foi a falencia, pois nao conseguiu pagar R$ 50");
			podeJogar = false;
			shouldPlayAgain = false;
			playerAtual.setSaiuDoJogo();
		}
		return;

	}

	public void toggleDiceOptions() {
		this.stealing = !this.stealing;
		notificaAll();
		return;
	}

	public boolean isStealing() {
		return this.stealing;
	}

	public void jogaDados() {
		if (podeJogar == false) {
			JOptionPane.showMessageDialog(null, "Voce nao pode mais rolar o dado");
			return;
		}
		if (shouldPlayAgain) {
			shouldPlayAgain = false;
		}
		propriedadeAtualCardIndex = -1;
		propriedadeAtual = null;
		cartaAtual = -1;
		canSave = false;

		dados.rollDice();

		diceValues[0] = dados.getDice1();
		diceValues[1] = dados.getDice2();
		diceSum = dados.getSumDices();

		notificaAll();

		lidarComDados();
		return;
	}

	public void dadoViciado() { // Usado para pegar manualmente o valor dos dados
		if (!podeJogar) {
			JOptionPane.showMessageDialog(null, "Voce nao pode mais rolar o dado.");
			return;
		}
		String[] valDados = { "1", "2", "3", "4", "5", "6" };
		JComboBox<String> d1 = new JComboBox<String>(valDados);
		JComboBox<String> d2 = new JComboBox<String>(valDados);

		Object[] diags = { "Escolha valores para os dois dados\nDado 1:", d1, "Dado 2:", d2 };
		int esc = JOptionPane.showOptionDialog(null, diags, "Valor dos Dados",
				JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);

		if (esc != JOptionPane.OK_OPTION) {
			return;
		}

		if (shouldPlayAgain) {
			shouldPlayAgain = false;
		}
		propriedadeAtualCardIndex = -1;
		propriedadeAtual = null;
		cartaAtual = -1;
		canSave = false;

		diceValues[0] = d1.getSelectedIndex() + 1;
		diceValues[1] = d2.getSelectedIndex() + 1;
		dados.setDice(diceValues[0], diceValues[1]);
		diceSum = dados.getSumDices();
		notificaAll();

		lidarComDados();
		return;
	}

	private void lidarComDados() {
		playerAtual = playerList.get(playerIndex);
		int playerPosition = playerAtual.getPawnPos();
		jogou = true;
		podeJogar = false;
		int newPosition = (playerPosition + diceSum) % 40;

		if (dados.dadosIguais()) {
			dadosRepetidos += 1;
			if (playerAtual.getPlayerPreso()) {
				playerAtual.changeStatusPreso();
				JOptionPane.showMessageDialog(null, "Voce esta livre da prisao");
				dadosRepetidos = 0;
				// dados iguais e jogador preso: como proceder? por enquanto, joga novamente os
				// dados nessa rodada para andar
				shouldPlayAgain = true;
			} else if (dadosRepetidos >= 3) {
				playerAtual.goToPrison();
				JOptionPane.showMessageDialog(null, "Voce foi preso por tirar o dado 3 vezes iguais");
				movePlayer(10);
				if (playerAtual.getSaidaLivrePrisao()) {
					playerAtual.changeStatusSaidaPrisao();
					playerAtual.changeStatusPreso(); // deixa de estar preso
					cartas.add(8);
					JOptionPane.showMessageDialog(null, "Voce usou sua carta de sair da prisão");
					// jogou 3 vezes e usou carta da prisão como proceder? Sugestão: continua a
					// jogada, mas não joga dnv o dado
				}
			} else {
				shouldPlayAgain = true;
				movePlayer(newPosition);
			}
		} else {
			if (!playerAtual.getPlayerPreso()) {
				movePlayer(newPosition);
			} else {
				JOptionPane.showMessageDialog(null, "Para sair da prisao voce precisa tirar dados iguais");
			}
		}

		if (shouldPlayAgain) {
			podeJogar = true;
		}

		notificaAll();
		// verifica se passou pelo inicio
		if ((playerPosition + diceSum) / 40 > 0) {
			JOptionPane.showMessageDialog(null, "Honorários: recebe R$200,00");
			playerAtual.changeMoney(200);
		}

		return;
	}

	private void movePlayer(int newPosition) {
		int playerPin = playerAtual.getPin();
		playerAtual.setPosition(newPosition);
		playerAtual.setCoordenates(propriedades[newPosition].getPos(playerPin)[0],
				propriedades[newPosition].getPos(playerPin)[1]);

		this.notificaAll();
		if (propriedades[newPosition] instanceof Enterprise || propriedades[newPosition] instanceof Ground) {
			int lidarProp = lidarComPropriedade(newPosition);
			return;
		} else {
			switch (propriedades[newPosition].getNome()) {
				case "Sorte/Reves": {
					lidarComCartas();
					break;
				}
				case "Ganhe": {
					playerAtual.changeMoney(200);
					JOptionPane.showMessageDialog(null,
							"Sua ação subiu :)\n ganhou R$200,00");
					break;
				}
				case "Imposto": {
					playerAtual.changeMoney(-200);
					int playerMoney = playerAtual.getMoney();
					while (playerMoney < 0 && !playerAtual.getPlayerFalencia()) {
						JOptionPane.showMessageDialog(null,
								"Voce nao possui dinheiro suficiente, venda uma de suas propriedades para pagar o imposto de R$200");
						int playerMoneyAntes = playerMoney;
						venderPropriedade();
						playerMoney = playerAtual.getMoney();

						if (playerMoneyAntes == playerMoney) {
							playerAtual.changeStatusFalencia();
							break;
						}
					}

					if (playerAtual.getPlayerFalencia()) {
						JOptionPane.showMessageDialog(null,
								" o player " + playerAtual.getCor()
										+ " foi a falencia, pois nao conseguiu pagar o imposto de R$ 200");
						podeJogar = false;
						shouldPlayAgain = false;
						playerAtual.setSaiuDoJogo();
					} else {
						JOptionPane.showMessageDialog(null, " o player " + playerAtual.getCor()
								+ " pagou o imposto de R$200");
					}
					break;
				}
				case "Prisao": {// rever
					break;
				}
				case "Va para a prisao": {
					JOptionPane.showMessageDialog(null,
							"Voce caiu na casa: Va para a prisao,\ncaso não tenha a carta de saída\ndeverá tirar dados iguais na sua rodada para sair");
					playerAtual.goToPrison();
					movePlayer(10);
					if (playerAtual.getSaidaLivrePrisao()) {
						playerAtual.changeStatusSaidaPrisao();
						playerAtual.changeStatusPreso(); // deixa de estar preso
						JOptionPane.showMessageDialog(null, "Voce usou sua carta de sair da prisão");
						cartas.add(8);
					}
					podeJogar = false;
					shouldPlayAgain = false;
					break;
				}

			}
		}

		this.notificaAll();
		return;
	}

	public int lidarComCartas() {
		playerAtual = playerList.get(playerIndex);
		// System.out.println(playerIndex);
		cartaAtual = cartas.remove(0);

		String mensagem = "";

		if (cartaAtual == 8) { // Saida da prisão
			playerAtual.changeStatusSaidaPrisao();
			mensagem = "Voce recebeu a carta de saida livre da prisao";
			JOptionPane.showMessageDialog(null, mensagem);
			return cartaAtual;
		} else if (cartaAtual == 10) { // receba 50 de cada jogador
			for (int i = 0; i < numPlayers; i++) {
				if (i != playerIndex) {
					playerList.get(i).changeMoney(-50);
				}
			}
			mensagem = "Todos os players te deram R$ 50, voce ganhou R$ " + 50 * (numPlayers - 1);
			playerAtual.changeMoney(50 * (numPlayers - 1));
			JOptionPane.showMessageDialog(null, mensagem);
		} else if (cartaAtual == 22) { // vai para a prisao
			playerAtual.goToPrison();
			movePlayer(10);
			mensagem = "Voce recebeu a carta de ir para a prisao e por isso voce foi preso!";
			podeJogar = false;
			shouldPlayAgain = false;
			if (playerAtual.getSaidaLivrePrisao()) {
				playerAtual.changeStatusSaidaPrisao();
				playerAtual.changeStatusPreso();
				JOptionPane.showMessageDialog(null, "Voce usou sua carta de sair da prisão");
				cartas.add(8);
			}
			JOptionPane.showMessageDialog(null, mensagem);
		} else {
			playerAtual.changeMoney(cartasSorteReves[cartaAtual]);
			if (cartasSorteReves[cartaAtual] > 0) {
				mensagem = "voce ganhou R$ " + cartasSorteReves[cartaAtual];
				JOptionPane.showMessageDialog(null, mensagem);
			} else {
				int playerMoney = playerAtual.getMoney();
				while (playerMoney < 0 && !playerAtual.getPlayerFalencia()) {
					int playerMoneyAntes = playerMoney;
					JOptionPane.showMessageDialog(null,
							"Voce nao possui dinheiro suficiente, venda uma de suas propriedades para pagar R$ "
									+ cartasSorteReves[cartaAtual] * -1);
					venderPropriedade();
					playerMoney = playerAtual.getMoney();

					if (playerMoneyAntes == playerMoney) {
						playerAtual.changeStatusFalencia();
						break;
					}
				}

				if (playerAtual.getPlayerFalencia()) {
					JOptionPane.showMessageDialog(null,
							" o player " + playerAtual.getCor()
									+ " foi a falencia, pois nao conseguiu pagar R$ "
									+ cartasSorteReves[cartaAtual] * -1);
					podeJogar = false;
					shouldPlayAgain = false;
					playerAtual.setSaiuDoJogo();
				} else {
					JOptionPane.showMessageDialog(null, " o player " + playerAtual.getCor()
							+ " pagou R$ " + cartasSorteReves[cartaAtual] * -1);
				}
			}
		}

		cartas.add(cartaAtual);

		return cartaAtual;
	}

	public void venderPropriedade() {

		ArrayList<Integer> PlayerPropriedades = playerAtual.getPropriedades();
		String[] listaNomesPropriedades = new String[PlayerPropriedades.size()];

		for (int i = 0; i < PlayerPropriedades.size(); i++) {
			listaNomesPropriedades[i] = propriedades[PlayerPropriedades.get(i)].getNome();
		}

		if (playerAtual.getPropriedades().size() > 0) {
			JComboBox<String> listaPropriedades = new JComboBox<String>(listaNomesPropriedades);
			Object[] display = { "escolha uma das suas propriedades para vender", listaPropriedades };
			int pane = JOptionPane.showOptionDialog(null, display, "Vender propriedades", JOptionPane.OK_CANCEL_OPTION,
					JOptionPane.QUESTION_MESSAGE, null, null, null);

			if (pane == JOptionPane.OK_OPTION) {
				int propriedade = playerAtual.getPropriedades().get(listaPropriedades.getSelectedIndex());
				if (propriedades[propriedade] instanceof Enterprise) {
					playerAtual.removePropriedade(propriedade);
					propriedades[propriedade].setProprietario(-1);
					playerAtual.changeMoney(propriedades[propriedade].getValorCompra() * 9 / 10);
					this.notificaAll();
					JOptionPane.showMessageDialog(null,
							"voce acabou de vender sua propriedade " + propriedades[propriedade].getNome()
									+ " por R$: " + propriedades[propriedade].getValorCompra() * 9 / 10);

				}

				else {
					playerAtual.removePropriedade(propriedade);
					propriedades[propriedade].setProprietario(-1);
					playerAtual.changeMoney(((Ground) propriedades[propriedade]).getPriceToSellBuildings() * 9 / 10);
					this.notificaAll();
					JOptionPane.showMessageDialog(null,
							"voce acabou de vender sua propriedade " + propriedades[propriedade].getNome()
									+ " por R$: "
									+ (((Ground) propriedades[propriedade]).getPriceToSellBuildings() * 9 / 10));
				}
			}
		} else {
			JOptionPane.showMessageDialog(null, "Voce nao possui nenhuma propriedade que possa ser vendida");
		}

		return;
	}

	public void comprarCasa() {

		if (jaComprouCasa) {
			JOptionPane.showMessageDialog(null,
					"voce ja comprou uma casa/hotel nessa rodada, espere mais uma rodada para comprar novamente");
			return;
		}

		int posicao = playerAtual.getPawnPos();

		boolean pertenceAoPlayer = false;

		ArrayList<Integer> PlayerPropriedades = playerAtual.getPropriedades();

		ArrayList<Integer> propriedadesGround = new ArrayList<Integer>();

		for (int i = 0; i < PlayerPropriedades.size(); i++) {
			if (propriedades[PlayerPropriedades.get(i)] instanceof Ground) {
				propriedadesGround.add(PlayerPropriedades.get(i));
			}
		}

		for (int i = 0; i < propriedadesGround.size(); i++) {
			if (propriedadesGround.get(i) == posicao) {
				pertenceAoPlayer = true;
			}
		}

		if (pertenceAoPlayer) {

			String mensagem = "";

			int casasEhotel = ((Ground) propriedades[posicao]).getHotels()
					+ ((Ground) propriedades[posicao]).getHouses();

			if (casasEhotel == 0) {
				mensagem = "Voce gostaria de comprar uma casa na propriedade " + propriedades[posicao].getNome();
			} else {
				mensagem = "Voce gostaria de comprar uma casa ou hotel na propriedade "
						+ propriedades[posicao].getNome();
			}

			String[] simnao = { "sim", "nao" };
			int opcao = JOptionPane.showOptionDialog(null,
					mensagem,
					"click a button", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, simnao,
					simnao[0]);

			if (opcao == 0) {

				if (playerAtual.getMoney() < ((Ground) propriedades[posicao]).getPriceHouse()) {
					JOptionPane.showMessageDialog(null,
							"Voce nao possui dinheiro para comprar uma casa no valor de R$ "
									+ ((Ground) propriedades[posicao]).getPriceHouse());
				} else {

					int precoCompra = 0;
					String compra = "";

					if (casasEhotel == 5) {
						JOptionPane.showMessageDialog(null,
								"Voce ja comprou todas as casas e hoteis disponiveis para essa propriedade");
					} else if (casasEhotel == 4) {
						precoCompra = ((Ground) propriedades[posicao]).buyHotel();
						compra = "hotel";
					} else if (casasEhotel >= 1) {
						String[] casahotel = { "Casa", "Hotel" };
						int opcao2 = JOptionPane.showOptionDialog(null,
								"voce gostaria de comprar um hotel ou uma casa nessa propriedade"
										+ ((Ground) propriedades[posicao]).getNome(),
								"click a button", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null,
								casahotel,
								casahotel[0]);
						if (opcao2 == 0) {
							precoCompra = ((Ground) propriedades[posicao]).buyHouse();
							compra = "casa";
						} else {
							if (playerAtual.getMoney() < (4 - casasEhotel)
									* ((Ground) propriedades[posicao]).getPriceHouse()) {
								JOptionPane.showMessageDialog(null,
										"Voce nao possui dinheiro para essa compra no valor de R$ "
												+ ((Ground) propriedades[posicao]).getPriceHouse());
							} else {
								for (int i = casasEhotel; i < 4; i++) {
									precoCompra += ((Ground) propriedades[posicao]).buyHouse();
								}
								precoCompra += ((Ground) propriedades[posicao]).buyHotel();
								compra = "hotel";
							}
						}
					} else {
						precoCompra = ((Ground) propriedades[posicao]).buyHouse();
						compra = "casa";
					}
					playerAtual.changeMoney(-precoCompra);
					jaComprouCasa = true;
					this.notificaAll();
					if (precoCompra != 0) {
						JOptionPane.showMessageDialog(null,
								"Voce comprou " + compra + " pelo valor de R$ " + precoCompra
										+ " na propriedade " + propriedades[posicao].getNome());
					}
				}
			}
		} else {
			JOptionPane.showMessageDialog(null, "voce nao pode comprar uma casa nessa propriedade");
		}

	}

	private int lidarComPropriedade(int propriedade) {

		int proprietario = propriedades[propriedade].getProprietario();
		int valorCompra = propriedades[propriedade].getValorCompra();
		String nomePropriedade = propriedades[propriedade].getNome();

		if (proprietario == -1) { // nao existe proprietario
			propriedadeAtualCardIndex = propriedades[propriedade].getCardNumber();
			propriedadeAtual = propriedades[propriedade];
			notificaAll();
			String[] simnao = { "sim", "nao" };
			int opcao = JOptionPane.showOptionDialog(null,
					"Voce gostaria de comprar a propriedade\n" + nomePropriedade + "\npelo valor:\nR$" + valorCompra,
					"click a button", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, simnao,
					simnao[0]);

			if (opcao == 0) {
				if (playerAtual.getMoney() >= valorCompra) {
					propriedades[propriedade].setProprietario(playerIndex);
					playerAtual.changeMoney(-valorCompra);
					playerAtual.addPropriedade(propriedade);
					JOptionPane.showMessageDialog(null,
							"A propriedade:\n" + nomePropriedade + "\nfoi comprada pelo player " + playerAtual.getCor()
									+ " por:\n R$" + valorCompra);
				} else {
					JOptionPane.showMessageDialog(null, "Voce nao tem dinheiro suficiente para comprar a propriedade:\n"
							+ nomePropriedade + " pelo valor: R$" + valorCompra);
				}
			}

		} else { // existe proprietario
			propriedadeAtualCardIndex = propriedades[propriedade].getCardNumber();
			propriedadeAtual = propriedades[propriedade];
			notificaAll();

			if (proprietario != playerIndex) { // player atual nao e o proprietario
				if (propriedades[propriedade] instanceof Enterprise) { // ENTERPRISE
					int aluguel = ((Enterprise) propriedades[propriedade]).getRent(dados.getSumDices());
					playerAtual.changeMoney(-aluguel);
					int playerMoney = playerAtual.getMoney();
					while (playerMoney < 0 && !playerAtual.getPlayerFalencia()) {
						int playerMoneyAntes = playerMoney;
						JOptionPane.showMessageDialog(null,
								"Voce nao possui dinheiro suficiente, venda uma de suas propriedades para pagar o Aluguel de R$"
										+ aluguel);
						venderPropriedade();
						playerMoney = playerAtual.getMoney();

						if (playerMoneyAntes == playerMoney) {
							playerAtual.changeStatusFalencia();
							break;
						}
					}

					Player playerProprietario = playerList.get(proprietario);
					playerProprietario.changeMoney(aluguel);

					if (playerAtual.getPlayerFalencia()) {
						JOptionPane.showMessageDialog(null,
								" o player " + playerAtual.getCor()
										+ " foi a falencia, pois nao conseguiu pagar o aluguel de R$" + aluguel
										+ " para o player " + playerProprietario.getCor());
						podeJogar = false;
						shouldPlayAgain = false;
						playerAtual.setSaiuDoJogo();
					} else {
						JOptionPane.showMessageDialog(null, " o player " + playerAtual.getCor()
								+ " pagou o aluguel de R$" + aluguel + " para o player " + playerProprietario.getCor());
					}

				} else { // GROUND
					int aluguel = ((Ground) propriedades[propriedade]).getRent();
					playerAtual.changeMoney(-aluguel);
					int playerMoney = playerAtual.getMoney();
					while (playerMoney < 0 && !playerAtual.getPlayerFalencia()) {
						int playerMoneyAntes = playerMoney;
						JOptionPane.showMessageDialog(null,
								"Voce nao possui dinheiro suficiente, venda uma de suas propriedades para pagar o Aluguel de R$"
										+ aluguel);
						venderPropriedade();
						playerMoney = playerAtual.getMoney();

						if (playerMoney == playerMoneyAntes) {
							playerAtual.changeStatusFalencia();
							break;
						}
					}

					Player playerProprietario = playerList.get(proprietario);
					playerProprietario.changeMoney(aluguel);

					if (playerAtual.getPlayerFalencia()) {
						JOptionPane.showMessageDialog(null,
								" o player " + playerAtual.getCor()
										+ " foi a falencia, pois nao conseguiu pagar o aluguel de R$" + aluguel
										+ " para o player " + playerProprietario.getCor());
						podeJogar = false;
						shouldPlayAgain = false;
						playerAtual.setSaiuDoJogo();
					} else {
						JOptionPane.showMessageDialog(null, " o player " + playerAtual.getCor()
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
			return m1 > m2 ? -1 : m1 < m2 ? 1 : 0;
		}
	};

	private void venderTodasPropriedades(int player) {
		ArrayList<Integer> playerPropriedades = playerList.get(player).getPropriedades();

		while (playerPropriedades.size() != 0) {
			int prop = playerPropriedades.remove(0);

			if (propriedades[prop] instanceof Enterprise) { // Empresas
				playerList.get(player).removePropriedade(prop);
				propriedades[prop].setProprietario(-1);
				playerList.get(player).changeMoney(propriedades[prop].getValorCompra() * 9 / 10);
			} else { // Ground

				playerList.get(player).removePropriedade(prop);
				propriedades[prop].setProprietario(-1);
				playerList.get(player).changeMoney(((Ground) propriedades[prop]).getPriceToSellBuildings() * 9 / 10);

			}
		}

		return;
	}

	public void endGame() {

		for (int i = 0; i < playerList.size(); i++) {
			if (!playerList.get(i).getPlayerFalencia())
				venderTodasPropriedades(i);
		}

		Collections.sort(playerList, comparator);

		String ranking = "RANKING:\n";
		for (Player p : playerList) {
			ranking += String.format("jogador: %d , dinheiro: %d, cor: %s \n", p.getNumber(), p.getMoney(),
					p.getCor());
		}
		JOptionPane.showMessageDialog(null, ranking);
		System.exit(1);
	}

	public void saveGame() throws IOException {
		JFileChooser fc = new JFileChooser(".");
		fc.setFileFilter(new FileNameExtensionFilter("TXT Files (*.txt)", "txt"));

		if (fc.showOpenDialog(null) != JFileChooser.APPROVE_OPTION) {
			// cancelar save caso tenha clicado cancel ou X
			return;
		}

		File file = fc.getSelectedFile();

		FileWriter writer = new FileWriter(file);

		writer.append("numplayers: " + numPlayers + ";\n");
		for (int i = 0; i < this.numPlayers; i++) {
			writer.append("\tPlayer " + i + ": ");
			writer.append(playerList.get(i).genSaveString());
			writer.append(";\n");
		}
		writer.append("Player da vez: " + playerAtual.getCor() + ";\n");
		writer.append("Cartas Sorte ou Revés: " + cartas.toString() + ";\n");
		writer.append("Propriedades: " + propriedades.length + ";\n");
		for (int i = 0; i < propriedades.length; i++) {
			Property p = propriedades[i];
			if (p instanceof Ground) {
				writer.append("\tTerreno " + i + ": ");
				Ground t = (Ground) p;
				writer.append(t.genSaveString());
				writer.append(";\n");
			} else if (p instanceof Enterprise) {
				writer.append("\tEmpresa " + i + ";\n");
				// Enterprise e = (Enterprise) p;
				// writer.append(e.genSaveString());
				// writer.append(";\n");
			}
		}

		writer.close();

		JOptionPane.showMessageDialog(null, "O jogo foi salvo com sucesso!");
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
