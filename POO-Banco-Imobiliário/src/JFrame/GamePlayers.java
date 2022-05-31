package JFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GamePlayers extends JFrame implements ItemListener{
	private final int ALTURA = 250;
	private final int LARGURA = 700;
	
	JPanel p;	
	static String Cores[]= {"vermelho","azul", "preto", "amarelo", "verde", "branco"};
	static JComboBox j1=new JComboBox(Cores);
	static JComboBox j2=new JComboBox(Cores);
	static JComboBox j3=new JComboBox(Cores);
	static JComboBox j4=new JComboBox(Cores);
	static JComboBox j5=new JComboBox(Cores);
	static JComboBox j6=new JComboBox(Cores);
	static JTextField name1=new JTextField(8);
	static JTextField name2=new JTextField(8);
	static JTextField name3=new JTextField(8);
	static JTextField name4=new JTextField(8);
	static JTextField name5=new JTextField(8);
	static JTextField name6=new JTextField(8);
	JLabel error= new JLabel("");
	
	static int nPlayers = 0;
	
	private static boolean isBlocked= false;
	
	static JComboBox players[]= {j1, j2, j3, j4, j5, j6};
	static JTextField names[]= {name1, name2, name3, name4, name5, name6};
	
	public GamePlayers(String s, int n) {
		super(s);
		/////FRAME SIZE AND LOCATION/////
		Toolkit tk=Toolkit.getDefaultToolkit();
		Dimension screenSize=tk.getScreenSize();
		int sl=screenSize.width;
		int sa=screenSize.height;
		int x=sl/2-LARGURA/2;
		int y=sa/2-ALTURA/2;
		setBounds(x,y,LARGURA,ALTURA);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		/////////////////
		nPlayers = n;
		p= new JPanel();
//		p.setLayout(new FlowLayout());
		p.setLayout(null);
		getContentPane().add(p);
		JButton start = new JButton("Iniciar");
		for(int i=0; i<nPlayers; i++) {
			players[i].setSelectedIndex(i);
			players[i].addItemListener(this);
			players[i].setBounds(50+(100*i), 30, 80, 30);
			names[i].setBounds(50+(100*i), 90 , 80, 30);
			names[i].addKeyListener(new KeyAdapter() {
				int n=0;
			    public void keyTyped(KeyEvent e) {
			        char c = e.getKeyChar();
			        if ( ((c < '0') || (c > '9' && c< 'A')|| c> 'z') && (c != KeyEvent.VK_BACK_SPACE)) {
			            e.consume();  // if it's not a number, ignore the event
			        }else if(c == KeyEvent.VK_BACK_SPACE) {
			        	if(n>0)n--;
			        }else {
			        	n++;
			        }
			        if(n>8) {
			        	e.consume();
			        	n--;
			        }
			     }
			}
			);
			p.add(players[i]);
			p.add(names[i]);
		}
		start.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!isBlocked){
					setVisible(false);
					ListaImagens li = new ListaImagens();
					Frame f= new Frame("Banco Imobiliário", nPlayers);
					f.setVisible(true);
					li.novaImagem();
					f.repaint();
				}
			}
		});
		start.setBounds(580, 150, 80, 30);
		error.setBounds(15, 180, 300, 30);
		p.add(error);
		p.add(start);
		setVisible(true);	
	}
	
	public void itemStateChanged(ItemEvent e)
	{
		boolean entrou= false;
		// if the state combobox is changed
		for(int i=0; i<nPlayers; i++) {
			String a = (String) players[i].getSelectedItem();
			for(int j=0; j<nPlayers; j++) {
				String b = (String) players[j].getSelectedItem();
				if (i!=j && b==a) {
					entrou = true;
					isBlocked=true;
					error.setText("Nao pode ter 2 jogadores com a mesma cor");
					error.setForeground(Color.red);
				}
			}
		}
		if(isBlocked && !entrou) {
			error.setText("");
			isBlocked=false;
		}
		entrou=false;
	}

}
