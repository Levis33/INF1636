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
	private int playerIndex = 0;
	private Player playerAtual;
	private int[] diceValues = new int[2];
	private int diceSum;
	private boolean jogou = false;
	private boolean stealing = false;

	private boolean podeJogar;
	private boolean shouldPlayAgain;

	private int dadosRepetidos = 0;
	private Dice dados = new Dice();
	private Property[] propriedades = CriaPropriedades.cria();

	private int cartaAtual = -1;
	private int propriedadeAtual = -1;

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
	}

	public int[] getDicesValue() {
		return diceValues;
	}

	public int getShowingCard() {
		return cartaAtual;
	}

	public int getShowingProperty() {
		return propriedadeAtual;
	}

	////////////////////////////////////////////////////
	public void controlePlayers() {
		if ((shouldPlayAgain || !jogou) && !stealing) {
			JOptionPane.showMessageDialog(null, "É necessário jogar os dados");
			shouldPlayAgain = false;
			return;
		}
		int primPlayer = playerIndex;
		dadosRepetidos = 0;
		jogou = false;
		playerIndex = (playerIndex + 1) % numPlayers; // proximo jogador
		playerAtual = playerList.get(playerIndex);

		while (playerAtual.getPlayerFalencia()) {
			if (primPlayer == playerIndex) {
				endGame();
			}
			playerIndex = (playerIndex + 1) % numPlayers;
			playerAtual = playerList.get(playerIndex);
		}

		if (playerIndex == primPlayer) { // todos menos atual foram a falencia
			endGame();
		}

		dadosRepetidos = 0;
		podeJogar = true;
		this.notificaAll();
	}

	public void toggleDiceOptions() {
		this.stealing = !this.stealing;
		if (stealing) {
			podeJogar = true;
		}
		notificaAll();
		return;
	}

	public boolean isStealing() {
		return this.stealing;
	}

	public void jogaDados() {
		propriedadeAtual = -1;
		cartaAtual = -1;
		if (podeJogar == false) {
			JOptionPane.showMessageDialog(null, "voce nao pode mais rolar o dado");
			return;
		}
		if (shouldPlayAgain) {
			shouldPlayAgain = false;
		}

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

		// if (esc != JOptionPane.OK_OPTION) {
		// dados.rollDice();
		// }

		diceValues[0] = d1.getSelectedIndex() + 1;
		diceValues[1] = d2.getSelectedIndex() + 1;
		dados.setDice(diceValues[0], diceValues[1]);
		diceSum = dados.getSumDices();

		lidarComDados();
		return;
	}

	public void lidarComDados() {
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
				if (playerAtual.getSaidaLivrePrisao()) {
					playerAtual.changeStatusSaidaPrisao();
					cartas.add(8);
					JOptionPane.showMessageDialog(null, "Voce usou sua carta de sair da prisao");
					// jogou 3 vezes e usou carta da prisão como proceder?
				} else {
					movePlayer(10);
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

		if (stealing || shouldPlayAgain) {
			podeJogar = true;
		}

		notificaAll();
		//verifica se passou pelo inicio
		if((playerPosition + diceSum)/40 > 0 ){
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

		if (propriedades[newPosition] instanceof Enterprise || propriedades[newPosition] instanceof Ground) {
			int displayC = lidarComPropriedade(newPosition);
			this.notificaAll();
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
					JOptionPane.showMessageDialog(null,
							"Pagamento de imposto:\n pague R$200,00");
					
					break;
				}
				case "Prisao": {// rever
					break;
				}
				case "Va para a prisao": {
					JOptionPane.showMessageDialog(null,
							"Voce caiu na casa: Va para a prisao,\npara sair deve tirar dados iguais na sua rodada");
					playerAtual.goToPrison();
					movePlayer(10);
					if (playerAtual.getSaidaLivrePrisao()) {
						playerAtual.changeStatusPreso(); // deixa de estar preso
						cartas.add(8);
					}
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
			mensagem = "voce recebeu a carta de saida livre da prisao";
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

		} else if (cartaAtual == 22) { // vai para a prisao
			playerAtual.goToPrison();
			podeJogar = false;
			if (playerAtual.getSaidaLivrePrisao()) {
				playerAtual.changeStatusPreso();
				cartas.add(8);
			}
		} else {
			playerAtual.changeMoney(cartasSorteReves[cartaAtual]);
			if(cartasSorteReves[cartaAtual] > 0){
				mensagem = "voce ganhou R$ " + cartasSorteReves[cartaAtual];
			}
			else{
				mensagem = "voce perdeu " + cartasSorteReves[cartaAtual];
			}
		}

		cartas.add(cartaAtual);

		
		JOptionPane.showMessageDialog(null, mensagem);

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
							"voce acabou de vender sua propriedade " + listaPropriedades.getSelectedIndex()
									+ " por R$: " + propriedades[propriedade].getValorCompra() * 9 / 10);

				}

				else {
					playerAtual.removePropriedade(propriedade);
					propriedades[propriedade].setProprietario(-1);
					playerAtual.changeMoney(((Ground) propriedades[propriedade]).getPriceToSellBuildings() * 9 / 10);
					this.notificaAll();
					JOptionPane.showMessageDialog(null,
							"voce acabou de vender sua propriedade " + listaPropriedades.getSelectedIndex()
									+ " por R$: "
									+ (((Ground) propriedades[propriedade]).getPriceToSellBuildings() * 9 / 10));
				}
			}
		}

		return;
	}

	public void comprarCasa() {

		ArrayList<Integer> PlayerPropriedades = playerAtual.getPropriedades();
		String[] listaNomesPropriedades = new String[PlayerPropriedades.size()];

		ArrayList<String> propriedadesGroundNome = new ArrayList<String>();

		for (int i = 0; i < PlayerPropriedades.size(); i++) {
			listaNomesPropriedades[i] = propriedades[PlayerPropriedades.get(i)].getNome();
		}

		for (int i = 0, j = 0; i < listaNomesPropriedades.length; i++) {
			if (propriedades[PlayerPropriedades.get(i)] instanceof Ground) {
				propriedadesGroundNome.add(listaNomesPropriedades[i]);
			} else {
				PlayerPropriedades.remove(j);
				j--;
			}
			j++;
		}

		if (propriedadesGroundNome.size() == 0) {
			JOptionPane.showMessageDialog(null, "Voce nao possui propriedades que dispoe da compra de casas e hoteis.");
		} else {
			String[] nomePropriedades = propriedadesGroundNome.toArray(new String[propriedadesGroundNome.size()]);

			JComboBox<String> listaPropriedades = new JComboBox<String>(listaNomesPropriedades);

			Object[] display = { "escolha uma das suas propriedades para comprar uma casa/hotel", listaPropriedades };
			int pane = JOptionPane.showOptionDialog(null, display, "Vender propriedades", JOptionPane.OK_CANCEL_OPTION,
					JOptionPane.QUESTION_MESSAGE, null, null, null);

			if (pane == JOptionPane.OK_OPTION) {
				int propriedadeEscolhida = PlayerPropriedades.get(listaPropriedades.getSelectedIndex());

				int casasEhotel = ((Ground) propriedades[propriedadeEscolhida]).getHotels()
						+ ((Ground) propriedades[propriedadeEscolhida]).getHouses();
				int precoCompra = 0;
				String compra = "";

				if (casasEhotel == 5) {
					JOptionPane.showMessageDialog(null,
							"Voce ja comporou todas as casas e hoteis disponiveis para essa propriedade");
				} else if (casasEhotel == 4) {
					precoCompra = ((Ground) propriedades[propriedadeEscolhida]).buyHotel();
					compra = "hotel";
				} else if (casasEhotel >= 1) {
					String[] casahotel = { "Casa", "Hotel" };
					int opcao = JOptionPane.showOptionDialog(null,
							"voce gostaria de comprar um hotel ou uma casa nessa propriedade"
									+ ((Ground) propriedades[propriedadeEscolhida]).getNome(),
							"click a button", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null,
							casahotel,
							casahotel[0]);
					if (opcao == 0) {
						precoCompra = ((Ground) propriedades[propriedadeEscolhida]).buyHouse();
						compra = "casa";
					} else {
						for (int i = casasEhotel; i < 4; i++) {
							precoCompra += ((Ground) propriedades[propriedadeEscolhida]).buyHouse();
						}
						precoCompra += ((Ground) propriedades[propriedadeEscolhida]).buyHotel();
						compra = "hotel";
					}
				} else {
					precoCompra = ((Ground) propriedades[propriedadeEscolhida]).buyHouse();
					compra = "casa";
				}
				playerAtual.changeMoney(-precoCompra);
				this.notificaAll();
				if (precoCompra != 0) {
					JOptionPane.showMessageDialog(null, "Voce comprou " + compra + " pelo valor de R$ " + precoCompra
							+ " na propriedade " + propriedades[propriedadeEscolhida].getNome());
				}

			}
		}

	}

	private int lidarComPropriedade(int propriedade) {

		int proprietario = propriedades[propriedade].getProprietario();
		int valorCompra = propriedades[propriedade].getValorCompra();
		String nomePropriedade = propriedades[propriedade].getNome();



		if (proprietario == -1) { // nao existe proprietario
			propriedadeAtual = propriedades[propriedade].getCardNumber();
			notificaAll();
			String[] simnao = { "sim", "nao" };
			int opcao = JOptionPane.showOptionDialog(null,
					"Você gostaria de comprar a propriedade\n" + nomePropriedade + "\npelo valor:\nR$" + valorCompra,
					"click a button", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, simnao,
					simnao[0]);

			if (opcao == 0) {
				if (playerAtual.getMoney() >= valorCompra) {
					propriedades[propriedade].setProprietario(playerIndex);
					playerAtual.changeMoney(-valorCompra);
					playerAtual.addPropriedade(propriedade);
					JOptionPane.showMessageDialog(null,
							"A propriedade:\n" + nomePropriedade + "\nfoi comprada pelo player " + playerAtual.getCor() + " por:\n R$" + valorCompra);
				} else {
					JOptionPane.showMessageDialog(null, "Voce nao tem dinheiro suficiente para comprar a propriedade:\n"
							+ nomePropriedade + " pelo valor: R$" + valorCompra);
				}
			}

		} else { // existe proprietario

			if (proprietario != playerIndex) { // player atual nao e o proprietario
				if (propriedades[propriedade] instanceof Enterprise) { // ENTERPRISE
					int aluguel = ((Enterprise) propriedades[propriedade]).getRent(dados.getSumDices());
					playerAtual.changeMoney(-aluguel);
					int playerMoney = playerAtual.getMoney();
					while (playerMoney <= 0 && playerAtual.getPlayerFalencia()) {
						int playerMoneyAntes = playerMoney;
						venderPropriedade();
						playerMoney = playerAtual.getMoney();

						JOptionPane.showMessageDialog(null,
								"Voce nao possui dinheiro suficiente, venda uma de suas propriedades para pagar o Aluguel de R$"
										+ aluguel);

						if (playerMoneyAntes == playerMoney) {
							playerAtual.changeStatusFalencia();
						}
					}

					Player playerProprietario = playerList.get(proprietario);
					playerProprietario.changeMoney(aluguel);

					if (playerAtual.getPlayerFalencia()) {
						JOptionPane.showMessageDialog(null,
								" o player " + playerAtual.getCor()
										+ " foi a falencia, pois nao conseguiu pagar o aluguel de R$" + aluguel
										+ " para o player " + playerProprietario.getCor());
					} else {
						JOptionPane.showMessageDialog(null, " o player " + playerAtual.getCor()
								+ " pagou o aluguel de R$" + aluguel + " para o player " + playerProprietario.getCor());
					}

				} else { // GROUND
					int aluguel = ((Ground) propriedades[propriedade]).getRent();
					playerAtual.changeMoney(-aluguel);
					int playerMoney = playerAtual.getMoney();
					while (playerMoney <= 0 && playerAtual.getPlayerFalencia()) {
						int playerMoneyAntes = playerMoney;
						venderPropriedade();
						playerMoney = playerAtual.getMoney();

						if (playerMoney == playerMoneyAntes) {
							playerAtual.changeStatusFalencia();
						}
					}

					Player playerProprietario = playerList.get(proprietario);
					playerProprietario.changeMoney(aluguel);

					if (playerAtual.getPlayerFalencia()) {
						JOptionPane.showMessageDialog(null,
								" o player " + playerAtual.getCor()
										+ " foi a falencia, pois nao conseguiu pagar o aluguel de R$" + aluguel
										+ " para o player " + playerProprietario.getCor());
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
