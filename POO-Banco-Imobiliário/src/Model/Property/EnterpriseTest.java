package Model.Property;

import static org.junit.Assert.*;

import org.junit.Test;

public class EnterpriseTest {
	private static final int TIMEOUT = 1000;

	@Test(timeout= TIMEOUT)
	public void testConstructor() {
		int aluguel[]= {2, 5, 10};
		Enterprise e = new Enterprise("Lagoa", -1, aluguel, 100);
		assertNotNull("criação de enterprise falhou", e);
	}

	///////Tests Get///////
	@Test(timeout= TIMEOUT)
	public void testGetNome() {
		int aluguel[]= {2, 5, 10};
		Enterprise e = new Enterprise("Lagoa", -1, aluguel, 100);
		String expected = "Lagoa";
		String actual = e.getNome();
		assertNotNull("instaciação do nome da empresa falhou", actual);
		assertEquals("atribuição incorreta do nome", expected, actual);
	}
	
	@Test(timeout= TIMEOUT)
	public void testGetProprietario() {
		int aluguel[]= {2, 5, 10};
		Enterprise e = new Enterprise("Lagoa", -1, aluguel, 100);
		int expected = -1;
		int actual = e.getProprietario();
		assertNotNull("instaciação do proprietário da empresa falhou", actual);
		assertEquals("atribuição incorreta do proprietário", expected, actual);
	}
	
	@Test(timeout= TIMEOUT)
	public void testGetAluguel() {
		int aluguel[]= {2, 5, 10};
		Enterprise e = new Enterprise("Lagoa", -1, aluguel, 100);
		int[] expected = {2, 5, 10};
		int[] actual = e.getAluguel();
		assertNotNull("instaciação do aluguel da empresa falhou", actual);
		for(int i=0; i<3; i++) {
		assertEquals("atribuição incorreta do aluguel", expected[i], actual[i]);}
	}
	
	@Test(timeout= TIMEOUT)
	public void testGetValorCompra() {
		int aluguel[]= {2, 5, 10};
		Enterprise e = new Enterprise("Lagoa", -1, aluguel, 100);
		int expected = 100;
		int actual = e.getValorCompra();
		assertNotNull("instaciação do valor da empresa falhou", actual);
		assertEquals("atribuição incorreta do valor de compra", expected, actual);
	}
}
