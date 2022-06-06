package JFrame;

import javax.swing.*;

import Regras.CtrlRegras;

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
		////////// Frame/////////////
		Toolkit tk = Toolkit.getDefaultToolkit();
		Dimension screenSize = tk.getScreenSize();
		int sl = screenSize.width;
		int sa = screenSize.height;
		int x = sl / 2 - LARGURA / 2;
		int y = sa / 2 - ALTURA / 2;
		setBounds(x, y, LARGURA, ALTURA);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		//////////////////////////
		Board p = new Board(nPlayers);
		getContentPane().add(p);
		p.setLayout(null);
		setSize(LARGURA, ALTURA);

		JButton diceButton = new JButton("Roll");
		diceButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				p.getDices();
				repaint();
			}
		});

		JButton finishButton = new JButton("Finalizar");
		finishButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CtrlRegras.getInstance().endGame();
			}
		});

		JButton passButton = new JButton("Passar a vez");
		// passButton.addActionListener(new ActionListener() {
		// public void actionPerformed(ActionEvent e) {
		// CtrlRegras.getInstance().passa_vez();
		// }
		// });

		JButton saveButton = new JButton("Salvar");

		diceButton.setBounds(770, 4, 150, 30);
		p.add(diceButton);

		passButton.setBounds(730, 620, 140, 30);
		p.add(passButton);

		finishButton.setBounds(920, 620, 100, 30);
		p.add(finishButton);

		saveButton.setBounds(1070, 620, 100, 30);
		p.add(saveButton);

	}

}
