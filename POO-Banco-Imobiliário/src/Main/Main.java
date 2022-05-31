package Main;

import Model.*;
import Model.Property.CriaPropriedades;
import Model.Property.Property;
import JFrame.*;

public class Main {

	public static void main(String[] args) {
		Match match = new Match();

		Property[] propriedade = CriaPropriedades.cria();



		//////// Frames///////////////
		
//		 GameIntro f2= new GameIntro("Banco Imobili�rio");
//		 f2.setVisible(true);
//		 f2.repaint();

		Frame f= new Frame("Banco Imobiliário", 6);
		f.setVisible(true);

		/////////////////////////////////////

		Property[] propriedades = CriaPropriedades.cria();
		
		for(int i =0;i < propriedades.length; i++){
			System.out.println(propriedades[i].getNome());
			System.out.println(propriedade[i].getValorCompra());
		}

//		int numPlayers = match.getNumPlayers();
//		System.out.println(match.initPlayers(numPlayers)); // OK!
		match.endGame();

	}

}
