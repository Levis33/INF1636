package JFrame;

import javax.swing.*;

import Controller.Observer.ObservadoIF;
import Controller.Observer.ObservadorIF;
import Model.Player.Player;
import Regras.CtrlRegras;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class GamePlayers extends JFrame implements ItemListener, ObservadorIF {
	private final int ALTURA = 250;
	private final int LARGURA = 700;

	JPanel p;
	static String Cores[] = {"Vermelho", "Azul", "Laranja", "Amarelo", "Roxo", "Cinza" };
	static JComboBox j1 = new JComboBox(Cores);
	static JComboBox j2 = new JComboBox(Cores);
	static JComboBox j3 = new JComboBox(Cores);
	static JComboBox j4 = new JComboBox(Cores);
	static JComboBox j5 = new JComboBox(Cores);
	static JComboBox j6 = new JComboBox(Cores);
	static JTextField name1 = new JTextField(8);
	static JTextField name2 = new JTextField(8);
	static JTextField name3 = new JTextField(8);
	static JTextField name4 = new JTextField(8);
	static JTextField name5 = new JTextField(8);
	static JTextField name6 = new JTextField(8);
	JLabel error = new JLabel("");
	
	static int nPlayers = 0;

	private static boolean isBlocked = false;

	static JComboBox playerColors[] = { j1, j2, j3, j4, j5, j6 };
	static JTextField playerNames[] = { name1, name2, name3, name4, name5, name6 };

	public GamePlayers(String s) {
		super(s);
		///// FRAME SIZE AND LOCATION/////
		Toolkit tk = Toolkit.getDefaultToolkit();
		Dimension screenSize = tk.getScreenSize();
		int sl = screenSize.width;
		int sa = screenSize.height;
		int x = sl / 2 - LARGURA / 2;
		int y = sa / 2 - ALTURA / 2;
		setBounds(x, y, LARGURA, ALTURA);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		/////////////////

		nPlayers = CtrlRegras.getInstance().getNumPlayers();
		p = new JPanel();
		// p.setLayout(new FlowLayout());
		p.setLayout(null);
		getContentPane().add(p);
		JButton start = new JButton("Iniciar");
		for (int i = 0; i < nPlayers; i++) {
			playerColors[i].setSelectedIndex(i);
			playerColors[i].addItemListener(this);
			playerColors[i].setBounds(50 + (100 * i), 30, 80, 30);
			playerNames[i].setBounds(50 + (100 * i), 90, 80, 30);
			playerNames[i].addKeyListener(new KeyAdapter() {
				int n = 0;

				public void keyTyped(KeyEvent e) {
					char c = e.getKeyChar();
					if (((c < '0') || (c > '9' && c < 'A') || c > 'z') && (c != KeyEvent.VK_BACK_SPACE)) {
						e.consume(); // if it's not a number, ignore the event
					} else if (c == KeyEvent.VK_BACK_SPACE) {
						if (n > 0)
							n--;
					} else {
						n++;
					}
					if (n > 8) {
						e.consume();
						n--;
					}
				}
			});
			p.add(playerColors[i]);
			p.add(playerNames[i]);
		}

		start.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				if (!isBlocked) {
					setVisible(false);
					Frame f = new Frame("Banco Imobiliario");
					CtrlRegras.getInstance().initPlayers(playerColors, playerNames);
					CtrlRegras.getInstance().organizePlay();
					f.setVisible(true);
					removeSelf();
				}
			}

		});
		start.setBounds(580, 150, 80, 30);
		error.setBounds(15, 180, 300, 30);
		p.add(error);
		p.add(start);
		setVisible(true);
		CtrlRegras.getInstance().add(this);
	}

	public void itemStateChanged(ItemEvent e) {
		boolean entrou = false;
		// if the state combobox is changed
		for (int i = 0; i < nPlayers; i++) {
			String a = (String) playerColors[i].getSelectedItem();
			for (int j = 0; j < nPlayers; j++) {
				String b = (String) playerColors[j].getSelectedItem();
				if (i != j && b == a) {
					entrou = true;
					isBlocked = true;
					error.setText("Nao pode ter 2 jogadores com a mesma cor");
					error.setForeground(Color.red);
				}
			}
		}
		if (isBlocked && !entrou) {
			error.setText("");
			isBlocked = false;
		}
		entrou = false;
	}

	private void removeSelf(){
		CtrlRegras.getInstance().remove(this);
	}

	@Override
	public void notify(ObservadoIF o) {
		this.repaint();
	}

}
