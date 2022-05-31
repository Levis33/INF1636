package JFrame;

import javax.imageio.*;
import java.io.*;
import java.awt.*;

public class ListaImagens{
	private Image []vet= new Image[100];
	private Image i;
	private int imgHeight;
	private int imgWidth;
	private int tam;
	
	public ListaImagens() {
		try {
			i=ImageIO.read(new File("Images/tabuleiro.png"));
			imgHeight= i.getHeight(null);
			imgWidth= i.getWidth(null);
		}catch(IOException e){
			System.out.print(e.getMessage());
			System.exit(1);
			
		}
	}
	
	public ListaImagens(String imgName) {
		try {
			i=ImageIO.read(new File(imgName));
			imgHeight= i.getHeight(null);
			imgWidth= i.getWidth(null);
		}catch(IOException e){
			System.out.print(e.getMessage());
			System.exit(1);
		}
	}
	
	public void novaImagem() {
		vet[tam]=i;
		tam++;
	}
	int getTam() {return tam;}
	Image []getImage() {return vet;}
}
