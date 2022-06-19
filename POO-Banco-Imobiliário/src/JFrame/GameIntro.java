package JFrame;

import javax.swing.*;

import Controller.Observer.ObservadoIF;
import Controller.Observer.ObservadorIF;
import Regras.CtrlRegras;

import java.awt.*;
import java.awt.event.*;

public class GameIntro extends JFrame implements ItemListener, ObservadorIF {
	private final int ALTURA = 250;
	private final int LARGURA = 500;

	JPanel p;
	static String nPlayers[] = { "3", "4", "5", "6" };
	static JLabel title = new JLabel("Escolha a quantidade de jogadores");
	static JComboBox<String> cb = new JComboBox<String>(nPlayers);

	public GameIntro(String s) {
		super(s);
		Toolkit tk = Toolkit.getDefaultToolkit();
		Dimension screenSize = tk.getScreenSize();
		int sl = screenSize.width;
		int sa = screenSize.height;
		int x = sl / 2 - LARGURA / 2;
		int y = sa / 2 - ALTURA / 2;
		setBounds(x, y, LARGURA, ALTURA);

		p = new Logo();
		getContentPane().add(p);
		p.setLayout(null);
		cb.addItemListener(this);
		JButton nextButton = new JButton("Nova Partida");
		JButton loadButton = new JButton("Carregar Partida");

		title.setBounds(250, 50, 300, 25);
		cb.setSelectedItem("3");
		cb.setBounds(270, 75, 50, 25);

		nextButton.setBounds(320, 75, 110, 25);
		nextButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int numPlayers = cb.getSelectedIndex() + 3;
				if (CtrlRegras.getInstance().checkNumPlayers(numPlayers)) {
					new GamePlayers("Banco Imobiliario");
					removeSelf();
					setVisible(false);
				}
			}
		});

		loadButton.setBounds(270, 130, 160, 25);
		loadButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
					CtrlRegras.getInstance().organizeLoadedGame();
					
					Frame f = new Frame("Banco Imobiliario");
					f.setVisible(true);
					removeSelf();
					setVisible(false);
			}
		});



		p.add(cb);
		p.add(title);
		p.add(nextButton);
		p.add(loadButton);
		setVisible(true);
		CtrlRegras.getInstance().add(this);
	}

	public void itemStateChanged(ItemEvent e) {
		// if the state combobox is changed
		if (e.getSource() == cb) {
			title.setForeground(Color.black);
			title.setText("Escolha a quantidade de jogadores:");
		}
	}

	private void removeSelf() {
		CtrlRegras.getInstance().remove(this);
	}

	@Override
	public void notify(ObservadoIF o) {
		this.repaint();
	}

}
