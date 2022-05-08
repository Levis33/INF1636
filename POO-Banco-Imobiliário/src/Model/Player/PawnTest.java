package Model.Player;

import static org.junit.Assert.*;

import org.junit.Test;

public class PawnTest {
	private static final int TIMEOUT = 1000;
	
	@Test(timeout= TIMEOUT)
	public void testConstructor() {
		Pawn p = new Pawn();
		assertNotNull("Erro na criação do peão", p);
	}
	
	/// TEST functions ///
	@Test(timeout= TIMEOUT)
	public void testGetPositionWithMove() {
		Pawn p = new Pawn();
		int loc = p.getPos();
		p.move(3);
		p.move(5);
		int expected = loc+3+5;
		int actual = p.getPos();
		assertEquals("erro na função de movimentação do peao", expected, actual);
	}
	
	@Test(timeout= TIMEOUT)
	public void testGetPositionWithGoTo() {
		Pawn p = new Pawn();
		p.goTo(15);
		int expected = 15;
		int actual = p.getPos();
		assertEquals("erro na função de posicionamento do peao", expected, actual);
	}
	
	@Test(timeout= TIMEOUT)
	public void testGetPositionWithBothMovingFunctions() {
		Pawn p = new Pawn();
		p.goTo(15);
		p.move(4);
		int expected = 15+4;
		int actual = p.getPos();
		assertEquals("erro na combinação das funções do peao", expected, actual);
	}
}
