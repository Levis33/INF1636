package JFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Frame extends JFrame {
	private final int ALTURA = 700;
	private final int LARGURA = 1200;
	private int nPlayers;
	JPanel p;	
	
	public Frame(String s, int n) {
		super(s);
		nPlayers = n;
		//////////Frame/////////////
		Toolkit tk=Toolkit.getDefaultToolkit();
		Dimension screenSize=tk.getScreenSize();
		int sl=screenSize.width;
		int sa=screenSize.height;
		int x=sl/2-LARGURA/2;
		int y=sa/2-ALTURA/2;
		setBounds(x,y,LARGURA,ALTURA);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		//////////////////////////
		Board p= new Board(nPlayers);
		getContentPane().add(p);
		p.setLayout(null);
		setSize(LARGURA, ALTURA);
		JButton diceButton = new JButton("lançar");
		diceButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				p.getDices();
				repaint();
			}
		});
		diceButton.setBounds(750, 0, 150, 30);
		p.add(diceButton);
		
	}
	
	
}
