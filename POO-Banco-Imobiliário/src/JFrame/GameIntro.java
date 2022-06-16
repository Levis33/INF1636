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
	static JComboBox cb = new JComboBox(nPlayers);

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
		JButton newGameButton = new JButton("Proximo");

		title.setBounds(250, 70, 300, 20);
		cb.setSelectedItem("3");
		cb.setBounds(280, 95, 50, 20);

		newGameButton.setBounds(330, 95, 90, 20);
		newGameButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int numPlayers = cb.getSelectedIndex() + 3;
				if (CtrlRegras.getInstance().checkNumPlayers(numPlayers)) {
					new GamePlayers("Banco Imobiliario");
					removeSelf();
					setVisible(false);
				}
			}
		});

		JButton continueGameButton = new JButton("Continuar");
		// continueGameButton.setBounds(210, 70, 30, 20);
		// continueGameButton.addActionListener(new ActionListener() {
		// public void actionPerformed(ActionEvent e) {
		// }
		// });

		p.add(cb);
		p.add(title);
		p.add(newGameButton);
		// p.add(continueGameButton);
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
