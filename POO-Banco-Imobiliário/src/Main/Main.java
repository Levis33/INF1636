package Main;

import Model.*;
import Model.Property.CriaPropriedades;
import Model.Property.Property;

public class Main {

	public static void main(String[] args) {
		Match match = new Match();

		Property[] propriedade = CriaPropriedades.cria();

		System.out.println(propriedade[0].getNome());
		System.out.println(propriedade[0].getAluguel()[0]);

		int numPlayers = match.getNumPlayers();
		System.out.println(match.initPlayers(numPlayers)); // OK!
		match.endGame();

	}

}
