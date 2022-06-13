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
	
	private void getImage() {
		try {
			i=ImageIO.read(new File("Images/logo.png"));
		}catch(IOException e){
			System.out.print(e.getMessage());
			System.exit(1);
		}
	}
	
	public void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		super.paintComponent(g2d);
		this.getImage();
		g2d.drawImage(i, 0,0,210, 215, null);
	}
	
}
