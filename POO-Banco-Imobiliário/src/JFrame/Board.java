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
	private Image pin1, pin2, pin3, pin4, pin5, pin6;
	private Image luckyCard;
	private Image propertyCard;
	private Image[] pins = {pin1, pin2, pin3, pin4, pin5, pin6};
	private String[] dicesOptions= {"face1.png", "face2.png", "face3.png", "face4.png", "face5.png", "face6.png"};
	private String[] pinsOptions= {"pin0.png", "pin1.png", "pin2.png", "pin3.png", "pin4.png", "pin5.png"};
	private int nPlayers;
	
	private int pinHeight = 23;
	private int pinWidth = 15;
	
	public Board(int n) {
		nPlayers = n;
	}
	
	private void getBoard() {
		try {
			board=ImageIO.read(new File("Images/tabuleiro.png"));
		}catch(IOException e){
			System.out.print(e.getMessage());
			System.exit(1);
		}
	}
	
	public void getDices() {
		Random gerador = new Random();
		try {
			String diceOption1 = dicesOptions[gerador.nextInt(6)];
			String diceOption2 = dicesOptions[gerador.nextInt(6)];
			dice1=ImageIO.read(new File("Images/dados/"+diceOption1));
			dice2=ImageIO.read(new File("Images/dados/"+diceOption2));
		}catch(IOException e){
			System.out.print(e.getMessage());
			System.exit(1);
		}
	}
	
	public void getPawns() {
		try {
			for(int i=0; i<nPlayers; i++) {
				pins[i]= ImageIO.read(new File("Images/peoes/"+ pinsOptions[i]));
			}
		}catch(IOException e){
			System.out.print(e.getMessage());
			System.exit(1);
		}
		
	}

	public void getLuckyCards(){
		Random gerador = new Random();
		try{
			luckyCard= ImageIO.read(new File("Images/sorteReves/chance"+gerador.nextInt(30)+".png"));
		}
		catch(IOException e){
			System.out.print(e.getMessage());
			System.exit(1);
		}
	}

	public void getPropertyCard(){
		try{
			propertyCard = ImageIO.read(new File("Images/territorios/Leblon.png"));
		}
		catch(IOException e){
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
		int larg=IMG_X, alt=IMG_Y;
		g2d.drawImage(board, larg, alt, 700, 665, null);
		g2d.drawImage(dice1, 750, 30, 150, 150, null);
		g2d.drawImage(dice2, 750, 180, 150, 150, null);
		g2d.drawImage(luckyCard, 395, 350, 200, 200, null);
		g2d.drawImage(propertyCard, 395, 100, 200, 200, null);
		for(int k=0; k<11; k++) {
		int dist;
			if(k==0) {
				dist = 15;
				for(int j=0; j<9; j++) {
				for(int i=0; i<nPlayers; i++) {
					if(i%2 ==0) {
						g2d.drawImage(pins[i], dist+i*8, 105+j*525/10, pinWidth, pinHeight, null);
					}else {
						g2d.drawImage(pins[i], dist+(i-1)*8, 125+j*525/10, pinWidth, pinHeight, null);
					}
				}
				}
			}else if(k==10){
				dist = 630;
				for(int j=0; j<9; j++) {
					for(int i=0; i<nPlayers; i++) {
						if(i%2 ==0) {
							g2d.drawImage(pins[i], dist+i*8, 105+j*525/10, pinWidth, pinHeight, null);
						}else {
							g2d.drawImage(pins[i], dist+(i-1)*8, 125+j*525/10, pinWidth, pinHeight, null);
						}
					}
					}
			}else {
				dist = 105+ 55*(k-1);				
			}
		for(int i=0; i<nPlayers; i++) {
			if(i%2 ==0) {
				g2d.drawImage(pins[i], dist+i*8, 30, pinWidth, pinHeight, null);
			}else {
				g2d.drawImage(pins[i], dist+(i-1)*8, 50, pinWidth, pinHeight, null);
			}
			
		}
		for(int i=0; i<nPlayers; i++) {
			if(i%2 ==0) {
				g2d.drawImage(pins[i], dist+i*8, 615, pinWidth, pinHeight, null);
			}else {
				g2d.drawImage(pins[i], dist+(i-1)*8, 635, pinWidth, pinHeight, null);
			}
			
		}

		}

	}
	
}
