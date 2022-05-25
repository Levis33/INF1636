package JFrame;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Random;

public class Board extends JPanel {
	public static final int IMG_X=0;
	public static final int IMG_Y=0;
	private Image board;
	private Image dice1, dice2;
	private String[] dicesOptions= {"face1.png", "face2.png", "face3.png", "face4.png", "face5.png", "face6.png"};
	private String[] pawnsOptions= {"pin0.png", "pin1.png", "pin2.png", "pin3.png", "pin4.png", "pin5.png"};
	private int imgHeight;
	private int imgWidth;
	private int tam;
	private int nPlayers;
	
	public Board(int n) {
		nPlayers = n;
	}
	
	private void getBoard() {
		try {
			board=ImageIO.read(new File("tabuleiro.png"));
			imgHeight= board.getHeight(null);
			imgWidth= board.getWidth(null);
		}catch(IOException e){
			System.out.print(e.getMessage());
			System.exit(1);
		}
	}
	
	public void getDices() {
		Random gerador = new Random();
		try {
			String diceOption1 = dicesOptions[gerador.nextInt(5)];
			String diceOption2 = dicesOptions[gerador.nextInt(5)];
			dice1=ImageIO.read(new File("Images/dados/"+diceOption1));
			dice2=ImageIO.read(new File("Images/dados/"+diceOption2));
			imgHeight= dice1.getHeight(null);
			imgWidth= dice1.getWidth(null);
		}catch(IOException e){
			System.out.print(e.getMessage());
			System.exit(1);
		}
	}
	

	public void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		super.paintComponent(g2d);
		this.getBoard();
		this.getDices();
		int larg=IMG_X, alt=IMG_Y;
		g2d.drawImage(board, larg, alt, 700, 665, null);
		g2d.drawImage(dice1, 750, 30, 150, 150, null);
		g2d.drawImage(dice2, 750, 180, 150, 150, null);

	}
	
}
