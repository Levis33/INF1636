package JFrame;

import javax.swing.*;

import Controller.Observer.ObservadoIF;
import Controller.Observer.ObservadorIF;
import Regras.CtrlRegras;

import java.awt.*;
import java.awt.event.*;

public class Frame extends JFrame implements ObservadorIF {
	private final int ALTURA = 700;
	private final int LARGURA = 1200;
	private int nPlayers;
	JPanel p;

	public Frame(String s) {
		super(s);
		nPlayers = CtrlRegras.getInstance().getNumPlayers();
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
		Board p = new Board();
		getContentPane().add(p);
		p.setLayout(null);
		setSize(LARGURA, ALTURA);

		JButton diceButton = new JButton("Roll");
		diceButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CtrlRegras.getInstance().jogaDados();
			}
		});
		
		JCheckBox dadoRoubar = new JCheckBox();
		dadoRoubar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CtrlRegras.getInstance().toggleDiceOptions();
			}
		});
		JButton dadosRoubarButton = new JButton("Choose Dices");
		dadosRoubarButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println(CtrlRegras.getInstance().isStealing());
				if(CtrlRegras.getInstance().isStealing()){
					CtrlRegras.getInstance().dadoViciado();
				}
			}
		});

		JButton finishButton = new JButton("Finalizar");
		finishButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CtrlRegras.getInstance().endGame();
			}
		});

		JButton saveButton = new JButton("Salvar");

		diceButton.setBounds(740, 10, 150, 30);
		p.add(diceButton);
		
		dadoRoubar.setBounds(710, 160, 30, 30);
		p.add(dadoRoubar);
		
		dadosRoubarButton.setBounds(740, 160, 150, 30);
		p.add(dadosRoubarButton);

		finishButton.setBounds(920, 620, 100, 30);
		p.add(finishButton);

		saveButton.setBounds(1070, 620, 100, 30);
		p.add(saveButton);

		CtrlRegras.getInstance().add(this);
	}

	@Override
	public void notify(ObservadoIF o) {
		this.repaint();
	}

}
