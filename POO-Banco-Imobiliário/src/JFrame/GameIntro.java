package JFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GameIntro extends JFrame implements ItemListener{
	private final int ALTURA = 250;
	private final int LARGURA = 500;
	
	JPanel p;	
	static String nPlayers[] = { "1","2","3","4","5","6" };
	static JLabel title= new JLabel("Escolha a quantidade de jogadores");
	static JComboBox cb= new JComboBox(nPlayers);
	
	public GameIntro(String s) {
		super(s);
		Toolkit tk=Toolkit.getDefaultToolkit();
		Dimension screenSize=tk.getScreenSize();
		int sl=screenSize.width;
		int sa=screenSize.height;
		int x=sl/2-LARGURA/2;
		int y=sa/2-ALTURA/2;
		setBounds(x,y,LARGURA,ALTURA);
		p= new Logo();
		getContentPane().add(p);
		p.setLayout(null);
		cb.addItemListener(this);
		JButton button= new JButton("Proximo");
		title.setBounds(210, 50, 300, 20);
		cb.setSelectedItem("3");
		cb.setBounds(210, 80, 50, 20);
		button.setBounds(260, 80, 90, 20);
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(cb.getSelectedIndex()>=2) {
					new GamePlayers("Banco Imobiliario", cb.getSelectedIndex()+1);
					setVisible(false);
				}
			}
		});
		p.add(cb);
		p.add(title);
		p.add(button);
		setVisible(true);
	}
	
	public void itemStateChanged(ItemEvent e)
	{
		// if the state combobox is changed
		if (e.getSource() == cb) {
			if(cb.getSelectedIndex()<2) {
				title.setText("Precisa de pelo menos 3 jogadores");
				title.setForeground(Color.red);
			}else {
				title.setForeground(Color.black);
				title.setText("Escolha a quantidade de jogadores");
			}
		}
	}
	
}
