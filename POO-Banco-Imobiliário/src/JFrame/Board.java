package JFrame;

import javax.imageio.ImageIO;
import javax.swing.*;

import Controller.Observer.ObservadoIF;
import Controller.Observer.ObservadorIF;
import Regras.CtrlRegras;

import java.awt.*;
import java.io.File;
import java.io.IOException;

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
	private int pinWidthAtual = 20;
	private int pinHeightAtual = 30;
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
		if (control.getShowingProperty() != -1) {
			g2d.drawImage(propertyCards[control.getShowingProperty()], 395, 100, 200, 200, null);
		}
		if (control.getShowingCard() != -1) {
			g2d.drawImage(luckyCards[control.getShowingCard()],395, 350, 200, 200, null);
		}
		// g2d.drawImage(luckyCards[0], 395, 350, 200, 200, null);

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
		g2d.drawString(String.valueOf(control.getPlayerAtual().getNumber()), 1065, 20);
		g2d.setFont(new Font("Arial", Font.PLAIN, 16));
		g2d.setColor(Color.BLACK);
		g2d.drawString("nome: "+ control.getPlayerAtual().getName(), 970, 40);
		g2d.drawString("dinheiro: "+ control.getPlayerAtual().getMoney(), 970, 60);
		g2d.drawString("propriedades: ", 970, 80);
		// new JComboBox(control.getPlayerAtual().getPropriedades())		

		
		
		
		// desenho button terminar jogada
		g2d.setColor(control.getPlayerAtual().getColor());
		g2d.setStroke(new BasicStroke((float) 2));
		g2d.drawRect(710, 0, 210, 250);
		g2d.setStroke(new BasicStroke());
		g2d.fillRect(740, 200, 150, 30);
		g2d.setColor(Color.BLACK);
		g2d.setFont(new Font("Arial", Font.BOLD, 15));
		g2d.drawString("Terminar Jogada", 750, 220);

		// desenha button Comprar Casa/Hotel

		g2d.setColor(control.getPlayerAtual().getColor());
		g2d.setStroke(new BasicStroke());
		g2d.fillRect(740, 400, 150, 30);
		g2d.setColor(Color.BLACK);
		g2d.setFont(new Font("Arial", Font.BOLD, 15));
		g2d.drawString("Comprar Casa/Hotel", 745, 420);

		// desenha Button Venda propriedade

		// g2d.setColor(control.getPlayerAtual().getColor());
		// g2d.setStroke(new BasicStroke());
		// g2d.fillRect(740, 400, 150, 30);
		// g2d.setColor(Color.BLACK);
		// g2d.setFont(new Font("Arial", Font.BOLD, 15));
		// g2d.drawString("Vender propriedades", 745, 420);



		control.add(this);
	}

	public void notify(ObservadoIF o) {
		displayCarta = o.get(1);
		this.repaint();
	}
}
