package Model.Property;

import static org.junit.Assert.*;

import org.junit.Test;

public class EnterpriseTest {
	private static final int TIMEOUT = 1000;

	@Test(timeout = TIMEOUT)
	private void testConstructor() {
		int aluguel[] = { 2, 5, 10 };
		Enterprise e = new Enterprise("Lagoa", aluguel, 100, 10, 10, 1);
		assertNotNull("cria��o de enterprise falhou", e);
	}

	/////// Tests Get///////
	@Test(timeout = TIMEOUT)
	private void testGetNome() {
		int aluguel[] = { 2, 5, 10 };
		Enterprise e = new Enterprise("Lagoa", aluguel, 100, 10, 10, 1);
		String expected = "Lagoa";
		String actual = e.getNome();
		assertNotNull("instacia��o do nome da empresa falhou", actual);
		assertEquals("atribui��o incorreta do nome", expected, actual);
	}

	@Test(timeout = TIMEOUT)
	private void testGetProprietario() {
		int aluguel[] = { 2, 5, 10 };
		Enterprise e = new Enterprise("Lagoa", aluguel, 100, 10, 10, 1);
		int expected = -1;
		int actual = e.getProprietario();
		assertNotNull("instacia��o do propriet�rio da empresa falhou", actual);
		assertEquals("atribui��o incorreta do propriet�rio", expected, actual);
	}

	@Test(timeout = TIMEOUT)
	private void testGetValorCompra() {
		int aluguel[] = { 2, 5, 10 };
		Enterprise e = new Enterprise("Lagoa", aluguel, 100, 10, 10, 1);
		int expected = 100;
		int actual = e.getValorCompra();
		assertNotNull("instacia��o do valor da empresa falhou", actual);
		assertEquals("atribui��o incorreta do valor de compra", expected, actual);
	}

	@Test(timeout = TIMEOUT)
	private void testGetPosition() {
		int aluguel[] = { 2, 5, 10 };
		Enterprise e = new Enterprise("Lagoa", aluguel, 100, 10, 10, 1);
		int[] expected = {10, 10};
		int[] actual = e.getPos(0);
		assertNotNull("instacia��o do valor da empresa falhou", actual);
		assertEquals("atribui��o incorreta do valor de compra", expected, actual);
	}

	@Test(timeout = TIMEOUT)
	private void testGetCardNumber() {
		int aluguel[] = { 2, 5, 10 };
		Enterprise e = new Enterprise("Lagoa", aluguel, 100, 10, 10, 1);
		int expected = 1;
		int actual = e.getCardNumber();
		assertNotNull("instacia��o do valor da empresa falhou", actual);
		assertEquals("atribui��o incorreta do valor de compra", expected, actual);
	}
}
