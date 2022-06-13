package JFrame;

import javax.imageio.ImageIO;
import javax.swing.*;

import Controller.Observer.ObservadoIF;
import Controller.Observer.ObservadorIF;
import Model.Player.Pawn;
import Model.Property.CriaPropriedades;
import Model.Property.Property;
import Regras.CtrlRegras;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Random;

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

	private int pinHeight = 23;
	private int pinWidth = 15;
	private int displayCarta;

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

		g2d.drawImage(dice1, 750, 40, 90, 90, null);
		g2d.drawImage(dice2, 850, 40, 90, 90, null);
		// g2d.drawImage(luckyCards[0], 395, 350, 200, 200, null);
		// g2d.drawImage(propertyCards[0], 395, 100, 200, 200, null);
		CtrlRegras control = CtrlRegras.getInstance();
		for (int j = 0; j < numPlayers; j++) {
			int x = control.getPlayer(j).getPawnCoordenates()[0];
			int y = control.getPlayer(j).getPawnCoordenates()[1];
			g2d.drawImage(pins[j], x, y, pinWidth, pinHeight, null);
		}

		control.add(this);
	}

	public void notify(ObservadoIF o) {
		displayCarta = o.get(1);
		this.repaint();
	}

}
