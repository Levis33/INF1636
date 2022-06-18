package JFrame;

import javax.imageio.ImageIO;
import javax.swing.*;

import Controller.Observer.ObservadoIF;
import Controller.Observer.ObservadorIF;
import Model.Property.Enterprise;
import Model.Property.Ground;
import Regras.CtrlRegras;
import Model.Player.Player;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Board extends JPanel implements ObservadorIF {
	public static final int IMG_X = 0;
	public static final int IMG_Y = 0;
	private Image board;
	private Image[] pins;
	private Image[] dices = new Image[6];
	private Image[] luckyCards = new Image[30];
	private Image[] propertyCards = new Image[28];

	private Image dice1, dice2;


	// private Property[] properties = CriaPropriedades.cria();
	private int numPlayers = CtrlRegras.getInstance().getNumPlayers();
	private ArrayList<Player> playerList = CtrlRegras.getInstance().getPlayers();

	private int pinHeight = 23;
	private int pinWidth = 15;
	private int pinWidthAtual = 20;
	private int pinHeightAtual = 30;
	// private int displayCarta;

	private JButton saveButton;
	private JComboBox comboBox;

	public Board(JButton saveButton, JComboBox comboBox) {
		this.saveButton = saveButton;
		this.comboBox = comboBox;
	}

	// imagem tabuleiro
	private void getBoard() {
		try {
			board = ImageIO.read(new File("Images/tabuleiro.png"));
		} catch (IOException e) {
			System.out.print(e.getMessage());
			System.exit(1);
		}
	}

	// imagem dados
	public void getDices() {
		try {
			for (int i = 0; i < 6; i++) {
				dices[i] = ImageIO.read(new File("Images/dados/face" + (i + 1) + ".png"));
			}
		} catch (IOException e) {
			System.out.print(e.getMessage());
			System.exit(1);
		}
	}

	// imagens peoes
	public void getPawns() {
		pins = new Image[numPlayers];
		try {
			for (int i = 0; i < numPlayers; i++) {
				int pinNumber = CtrlRegras.getInstance().getPlayer(i).getPin();
				pins[i] = ImageIO.read(new File("Images/peoes/pin" + pinNumber + ".png"));
			}
		} catch (IOException e) {
			System.out.print(e.getMessage());
			System.exit(1);
		}

	}

	public void getLuckyCards() {
		try {
			for (int i = 0; i < 30; i++) {
				luckyCards[i] = ImageIO.read(new File("Images/sorteReves/chance" + (i + 1) + ".png"));
			}
		} catch (IOException e) {
			System.out.print(e.getMessage());
			System.exit(1);
		}
	}

	public void getPropertyCard() {
		try {
			// for (Property el : properties) {
			// propertyCard = ImageIO.read(new File("Images/territorios/" + el.getNome() +
			// ".png"));
			// }
			for (int i = 0; i < 28; i++) {
				propertyCards[i] = ImageIO.read(new File("Images/territorios/territorio" + (i + 1) + ".png"));
			}
		} catch (IOException e) {
			System.out.print(e.getMessage());
			System.exit(1);
		}
	}

	public void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		super.paintComponent(g2d);
		this.getBoard();
		this.getDices();
		this.getPawns();
		this.getLuckyCards();
		this.getPropertyCard();

		int larg = IMG_X, alt = IMG_Y;
		g2d.drawImage(board, larg, alt, 700, 665, null);

		CtrlRegras control = CtrlRegras.getInstance();
		for (int j = 0; j < numPlayers; j++) {
			int x = control.getPlayer(j).getPawnCoordenates()[0];
			int y = control.getPlayer(j).getPawnCoordenates()[1];
			if (control.getPlayerAtual() == control.getPlayer(j)) {
				g2d.drawImage(pins[j], x, y, pinWidthAtual, pinHeightAtual, null);
			} else {
				g2d.drawImage(pins[j], x, y, pinWidth, pinHeight, null);
			}
		}
		if (control.getShowingPropertyCardIndex() != -1) { // desenho das infos de terreno
			g2d.drawImage(propertyCards[control.getShowingPropertyCardIndex()], 110, 100, 200, 200, null);
			g2d.setStroke(new BasicStroke((float) 3));
			if (control.getShowingProperty() instanceof Ground) {
				g2d.setColor(((Ground) control.getShowingProperty()).getColor());
				g2d.drawRect(105, 98, 490, 204);
				g2d.setColor(Color.BLACK);
				g2d.setFont(new Font("Arial", Font.PLAIN, 16));
				g2d.drawString("Nome: " + control.getShowingProperty().getNome(), 320, 130);
				if (control.getShowingProperty().getProprietario() == -1) {
					g2d.drawString("Proprietario: Nenhum", 320, 160);
				} else {
					g2d.drawString("Proprietario: ", 320, 160);
					g2d.setColor(control.getPlayer(control.getShowingProperty().getProprietario()).getColor());
					g2d.drawString(
							String.valueOf(control.getPlayer(control.getShowingProperty().getProprietario()).getCor()),
							415, 160);
					g2d.setColor(Color.BLACK);
				}
				g2d.drawString("Numero de casas: " + ((Ground) control.getShowingProperty()).getHouses(), 320, 190);
				g2d.drawString("Numero de hoteis: " + ((Ground) control.getShowingProperty()).getHotels(), 320, 215);
				g2d.setStroke(new BasicStroke());
			} else if (control.getShowingProperty() instanceof Enterprise) { // desenho das infos de Empresa
				g2d.setColor(Color.GRAY);
				g2d.drawRect(105, 98, 490, 204);
				g2d.setColor(Color.BLACK);
				g2d.setFont(new Font("Arial", Font.PLAIN, 16));
				g2d.drawString("Nome: " + control.getShowingProperty().getNome(), 320, 130);
				if (control.getShowingProperty().getProprietario() == -1) {
					g2d.drawString("Proprietário: Nenhum", 320, 160);
				} else {
					g2d.drawString("Proprietário: ", 320, 160);
					g2d.setColor(control.getPlayer(control.getShowingProperty().getProprietario()).getColor());
					g2d.drawString(
							String.valueOf(control.getPlayer(control.getShowingProperty().getProprietario()).getCor()),
							415, 160);
					g2d.setColor(Color.BLACK);
				}
			}

		}

		if (control.getShowingCard() != -1) {
			g2d.drawImage(luckyCards[control.getShowingCard()], 110, 100, 200, 200, null);
		}

		dice1 = dices[control.getDicesValue()[0] - 1];
		dice2 = dices[control.getDicesValue()[1] - 1];

		g2d.setColor(control.getPlayerAtual().getColor());
		g2d.fillRect(710, 0, 210, 150);
		g2d.drawImage(dice1, 720, 50, 90, 90, null);
		g2d.drawImage(dice2, 820, 50, 90, 90, null);

		g2d.setFont(new Font("Arial", Font.PLAIN, 18));
		g2d.setColor(Color.BLACK);
		g2d.drawString("Vez: Player", 970, 20);
		g2d.setFont(new Font("Arial", Font.BOLD, 20));
		g2d.setColor(control.getPlayerAtual().getColor());
		g2d.drawString(String.valueOf(control.getPlayerAtual().getCor()), 1065, 20);
		g2d.setFont(new Font("Arial", Font.PLAIN, 16));
		g2d.setColor(Color.BLACK);
		g2d.drawString("nome: " + control.getPlayerAtual().getName(), 970, 40);
		g2d.drawString("dinheiro: " + control.getPlayerAtual().getMoney(), 970, 60);
		g2d.drawString("propriedades: ", 970, 80);

		String[] jogadorPropriedades = CtrlRegras.getInstance().getPlayerPropriedades();
		comboBox.removeAllItems();
	
		comboBox.addItem("Visualizar propriedades");
		for(int i=0; i<jogadorPropriedades.length;i++){
			comboBox.addItem(jogadorPropriedades[i]);
			System.out.println(jogadorPropriedades[i]);
		}
		
		
		// Desenha Players e dinheiro deles

		for(int i =0; i < playerList.size(); i++){
			g2d.setFont(new Font("Arial", Font.BOLD, 16));
			g2d.setColor(Color.BLACK);
			g2d.drawString("Player:", 110, 430+(i*20));

			g2d.drawString("Dinheiro: " + playerList.get(i).getMoney(), 250, 430+(i*20));

			g2d.setColor(playerList.get(i).getColor());
			g2d.drawString(playerList.get(i).getCor(), 170, 430+(i*20));

		}


		// desenho button terminar jogada
		g2d.setColor(control.getPlayerAtual().getColor());
		g2d.setStroke(new BasicStroke((float) 2));
		g2d.drawRect(710, 0, 210, 250);
		g2d.setStroke(new BasicStroke());
		g2d.fillRect(740, 200, 150, 30);
		g2d.setColor(Color.BLACK);
		g2d.setFont(new Font("Arial", Font.BOLD, 14));
		g2d.drawString("Terminar Jogada", 750, 220);

		// desenha button Comprar Casa/Hotel
		g2d.setColor(control.getPlayerAtual().getColor());
		g2d.setStroke(new BasicStroke());
		g2d.fillRect(740, 300, 150, 30);
		g2d.setColor(Color.BLACK);
		g2d.setFont(new Font("Arial", Font.BOLD, 14));
		g2d.drawString("Comprar Casa/Hotel", 745, 320);

		// desenha Button Venda propriedade
		g2d.setColor(control.getPlayerAtual().getColor());
		g2d.setStroke(new BasicStroke());
		g2d.fillRect(740, 400, 150, 30);
		g2d.setColor(Color.BLACK);
		g2d.setFont(new Font("Arial", Font.BOLD, 14));
		g2d.drawString("Vender propriedade", 745, 420);

		if (control.canSave()) {
			saveButton.setEnabled(true);
		} else {
			saveButton.setEnabled(false);
		}

		control.add(this);
	}

	public void notify(ObservadoIF o) {
		// displayCarta = o.get(1);
		this.repaint();
	}
}
