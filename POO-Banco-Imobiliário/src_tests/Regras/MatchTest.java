package Model;

import static org.junit.Assert.*;
import org.junit.*;

public class MatchTest {
	private static final int TIMEOUT = 1000;

	@Test(timeout = TIMEOUT)
	private void testConstructor() {
		Match m = new Match();
		assertNotNull("Erro na cria��o da partida", m);
	}

	// Tirar o Scanner de dentro da fun��o getNumPlayers?
	// @Test()
	// public void testGetNumplayers() {
	// Match m = new Match();
	// int number =m.getNumPlayers();
	// assertNotNull("Erro em obter o n�mero de jogadores");
	// }

	// @Test(timeout= TIMEOUT)
	// @Before()
	// public void testInitPlayers() {
	// Match m = new Match();
	// m.initPlayers(3);
	// }

	// @Test(timeout= TIMEOUT)
	// @After()
	// public void testFim() {
	//
	// }
}
