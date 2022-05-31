package Main;

import Model.*;
import Model.Property.CriaPropriedades;
import Model.Property.Property;
import JFrame.*;

public class Main {

	public static void main(String[] args) {
		Match match = new Match();

		Property[] propriedade = CriaPropriedades.cria();

		System.out.println(propriedade[0].getNome());
		System.out.println(propriedade[0].getAluguel()[0]);

		//////// Frames///////////////
		
		 GameIntro f2= new GameIntro("Banco Imobili·rio");
		 f2.setVisible(true);
		 f2.repaint();

		//Frame f= new Frame("Banco Imobili√°rio", 6);
//		f.setVisible(true);

		/////////////////////////////////////


		int numPlayers = match.getNumPlayers();
		System.out.println(match.initPlayers(numPlayers)); // OK!
		match.endGame();

	}

}
