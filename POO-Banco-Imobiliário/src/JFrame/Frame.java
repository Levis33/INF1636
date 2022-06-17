package JFrame;

import javax.swing.*;

import Controller.Observer.ObservadoIF;
import Controller.Observer.ObservadorIF;
import Regras.CtrlRegras;

import java.awt.*;
import java.awt.event.*;

public class Frame extends JFrame implements ObservadorIF, MouseListener{
	private final int ALTURA = 700;
	private final int LARGURA = 1200;
	private int nPlayers;
	JPanel p;

	public Frame(String s) {
		super(s);
		CtrlRegras control = CtrlRegras.getInstance();
		nPlayers = control.getNumPlayers();
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

		addMouseListener(this);

		JButton diceButton = new JButton("Roll");
		diceButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!control.isStealing()){
					control.jogaDados();
				}else{
					JOptionPane.showMessageDialog(null, "Modo de jogo: Roubando\nMude o modo para poder jogar os dados");
				}
			}
		});
		
		JCheckBox dadoRoubar = new JCheckBox();
		dadoRoubar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				control.toggleDiceOptions();
			}
		});
		JButton dadosRoubarButton = new JButton("Dados Viciados");
		dadosRoubarButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(control.isStealing()){
					control.dadoViciado();
				}else{
					JOptionPane.showMessageDialog(null, "Modo de jogo: Normal\nMude o modo para poder escolher os dados");
				}
				
			}
		});

		JButton finishButton = new JButton("Finalizar");
		finishButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				control.endGame();
			}
		});

		JButton saveButton = new JButton("Salvar");

		diceButton.setBounds(740, 10, 150, 30);
		p.add(diceButton);
		
		dadoRoubar.setBounds(715, 160, 25, 30);
		p.add(dadoRoubar);
		
		dadosRoubarButton.setBounds(740, 160, 150, 30);
		p.add(dadosRoubarButton);

		finishButton.setBounds(920, 620, 100, 30);
		p.add(finishButton);

		saveButton.setBounds(1070, 620, 100, 30);
		p.add(saveButton);


		control.add(this);
	}
	
	@Override
	public void notify(ObservadoIF o) {
		this.repaint();
	}

	@Override
	public void mousePressed(MouseEvent e) {
		System.out.println("'pressed'");
		int x = e.getX();
		int y = e.getY();
		System.out.println(x);
		System.out.println(y);
		
		if ( x > 740 && x < 890 && y >230 && y < 280){
			System.out.print("'spot'");
			CtrlRegras.getInstance().controlePlayers();
		}

		if ( x > 740 && x < 890 && y >400 && y < 450){
			System.out.print("'spot'");
			CtrlRegras.getInstance().comprarCasa();
		}
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

}
