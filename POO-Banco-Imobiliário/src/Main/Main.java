package Main;

import Model.*;
import Model.Property.CriaPropriedades;
import Model.Property.Property;
import JFrame.*;
import Regras.CtrlRegras;

public class Main {

	public static void main(String[] args) {
		CtrlRegras match = new CtrlRegras();

		Property[] propriedade = CriaPropriedades.cria();

		//////// Frames///////////////

		GameIntro f2 = new GameIntro("Banco Imobiliario");
		f2.setVisible(true);
		f2.repaint();

		// Frame f= new Frame("Banco Imobiliario", 6);
		// f.setVisible(true);

		/////////////////////////////////////

		Property[] propriedades = CriaPropriedades.cria();

		for (int i = 0; i < propriedades.length; i++) {
			System.out.println(propriedades[i].getNome());
			System.out.println(propriedade[i].getValorCompra());
		}

	}

}
