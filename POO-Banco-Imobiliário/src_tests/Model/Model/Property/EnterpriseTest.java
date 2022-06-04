package Model.Property;

import static org.junit.Assert.*;

import org.junit.Test;

public class EnterpriseTest {
	private static final int TIMEOUT = 1000;

	@Test(timeout = TIMEOUT)
	private void testConstructor() {
		int aluguel[] = { 2, 5, 10 };
		Enterprise e = new Enterprise("Lagoa", aluguel, 100);
		assertNotNull("cria��o de enterprise falhou", e);
	}

	/////// Tests Get///////
	@Test(timeout = TIMEOUT)
	private void testGetNome() {
		int aluguel[] = { 2, 5, 10 };
		Enterprise e = new Enterprise("Lagoa", aluguel, 100);
		String expected = "Lagoa";
		String actual = e.getNome();
		assertNotNull("instacia��o do nome da empresa falhou", actual);
		assertEquals("atribui��o incorreta do nome", expected, actual);
	}

	@Test(timeout = TIMEOUT)
	private void testGetProprietario() {
		int aluguel[] = { 2, 5, 10 };
		Enterprise e = new Enterprise("Lagoa", aluguel, 100);
		int expected = -1;
		int actual = e.getProprietario();
		assertNotNull("instacia��o do propriet�rio da empresa falhou", actual);
		assertEquals("atribui��o incorreta do propriet�rio", expected, actual);
	}

	@Test(timeout = TIMEOUT)
	private void testGetAluguel() {
		int aluguel[] = { 2, 5, 10 };
		Enterprise e = new Enterprise("Lagoa", aluguel, 100);
		int[] expected = { 2, 5, 10 };
		int[] actual = e.getAluguel();
		assertNotNull("instacia��o do aluguel da empresa falhou", actual);
		for (int i = 0; i < 3; i++) {
			assertEquals("atribui��o incorreta do aluguel", expected[i], actual[i]);
		}
	}

	@Test(timeout = TIMEOUT)
	private void testGetValorCompra() {
		int aluguel[] = { 2, 5, 10 };
		Enterprise e = new Enterprise("Lagoa", aluguel, 100);
		int expected = 100;
		int actual = e.getValorCompra();
		assertNotNull("instacia��o do valor da empresa falhou", actual);
		assertEquals("atribui��o incorreta do valor de compra", expected, actual);
	}
}
