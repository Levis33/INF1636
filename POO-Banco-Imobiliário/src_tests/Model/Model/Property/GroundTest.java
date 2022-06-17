package Model.Property;

import java.awt.Color;

import static org.junit.Assert.*;

import org.junit.Test;

public class GroundTest {
	private static final int TIMEOUT = 1000;

	@Test(timeout = TIMEOUT)
	private void testConstructor() {
		int aluguel[] = { 2, 5, 10 };
		int price[] = { 100, 200, 300 };
		Ground g = new Ground("Lagoa", aluguel, price, Color.green);
		assertNotNull("cria��o de Ground falhou", g);
	}

	/////// Tests Get///////
	@Test(timeout = TIMEOUT)
	private void testGetNome() {
		int aluguel[] = { 2, 5, 10 };
		int price[] = { 100, 200, 300 };
		Ground g = new Ground("Lagoa", aluguel, price, Color.green);
		String expected = "Lagoa";
		String actual = g.getNome();
		assertNotNull("instacia��o do nome do terreno falhou", actual);
		assertEquals("atribui��o incorreta do nome", expected, actual);
	}

	@Test(timeout = TIMEOUT)
	private void testGetProprietario() {
		int aluguel[] = { 2, 5, 10 };
		int price[] = { 100, 200, 300 };
		Ground g = new Ground("Lagoa", aluguel, price, Color.green);
		int expected = -1;
		int actual = g.getProprietario();
		assertNotNull("instacia��o do propriet�rio do terreno falhou", actual);
		assertEquals("atribui��o incorreta do propriet�rio", expected, actual);
	}

	@Test(timeout = TIMEOUT)
	private void testGetValorCompra() {
		int aluguel[] = { 2, 5, 10 };
		int price[] = { 100, 200, 300 };
		Ground g = new Ground("Lagoa", aluguel, price, Color.green);
		int expected = 100;
		int actual = g.getValorCompra();
		assertNotNull("instacia��o do valor do terreno falhou", actual);
		assertEquals("atribui��o incorreta do valor de compra", expected, actual);
	}

}
