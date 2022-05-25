package JFrame;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class Logo extends JPanel {
	public static final int IMG_X=0;
	public static final int IMG_Y=0;
	private Image i;
	private int imgHeight;
	private int imgWidth;
	private int tam;
	
	private void getImage() {
		try {
			i=ImageIO.read(new File("Images/logo.png"));
			imgHeight= i.getHeight(null);
			imgWidth= i.getWidth(null);
		}catch(IOException e){
			System.out.print(e.getMessage());
			System.exit(1);
		}
	}
	
	public void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		super.paintComponent(g2d);
		this.getImage();
		int larg=IMG_X, alt=IMG_Y;
		g2d.drawImage(i, larg, alt, null);

	}
	
}
