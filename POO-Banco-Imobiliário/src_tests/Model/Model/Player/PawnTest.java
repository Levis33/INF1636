package Model.Player;

import static org.junit.Assert.*;

import org.junit.Test;

public class PawnTest {
	private static final int TIMEOUT = 1000;

	@Test(timeout = TIMEOUT)
	private void testConstructor() {
		Pawn p = new Pawn();
		assertNotNull("Erro na cria��o do pe�o", p);
	}

	/// TEST functions ///
	@Test(timeout = TIMEOUT)
	private void testGetPositionWithMove() {
		Pawn p = new Pawn();
		int loc = p.getPos();
		p.move(3);
		p.move(5);
		int expected = loc + 3 + 5;
		int actual = p.getPos();
		assertEquals("erro na fun��o de movimenta��o do peao", expected, actual);
	}

	@Test(timeout = TIMEOUT)
	private void testGetPositionWithSetPosition() {
		Pawn p = new Pawn();
		p.setPosition(15);
		int expected = 15;
		int actual = p.getPos();
		assertEquals("erro na fun��o de posicionamento do peao", expected, actual);
	}

	@Test(timeout = TIMEOUT)
	private void testGetPositionWithBothMovingFunctions() {
		Pawn p = new Pawn();
		p.setPosition(15);
		p.move(4);
		int expected = 15 + 4;
		int actual = p.getPos();
		assertEquals("erro na combina��o das fun��es do peao", expected, actual);
	}

	@Test(timeout = TIMEOUT)
	private void testGetCoordenatesWithSetCoordenates() {
		Pawn p = new Pawn();
		p.setCoordenates(10, 15);
		int[] expected = {10, 15};
		int[] actual = p.getCoordenates();
		assertEquals("erro na funcao de coordenadas do peao", expected[0], actual[0]);
		assertEquals("erro na funcao de coordenadas do peao", expected[1], actual[1]);
	}
}
