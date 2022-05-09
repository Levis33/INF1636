package Model;

import static org.junit.Assert.*;
import org.junit.*;

public class MatchTest {
	private static final int TIMEOUT = 1000;
	
	@Test(timeout= TIMEOUT)
	public void testConstructor() {
		Match m = new Match();
		assertNotNull("Erro na criação da partida", m);
	}

	// Tirar o Scanner de dentro da função getNumPlayers?
//	@Test()
//	public void testGetNumplayers() {
//		Match m = new Match();
//		int number =m.getNumPlayers();
//		assertNotNull("Erro em obter o número de jogadores");
//	}
	
//	@Test(timeout= TIMEOUT)
//	@Before()
//	public void testInitPlayers() {
//		Match m = new Match();
//		m.initPlayers(3);
//	}
	
//	@Test(timeout= TIMEOUT)
//	@After()
//	public void testFim() {
//		
//	}
}
