package Model.Player;

import static org.junit.Assert.*;
import java.awt.Color;
import org.junit.*;

public class PlayerTest {
	private static final int TIMEOUT = 1000;

	@Test(timeout = TIMEOUT)
	@Before
	public void testConstructor() {
		Player p1 = new Player(1, 4000, Color.red, "Luiza");
		assertNotNull("Player Inv�lido", p1);
	}

	////// Tests Get//////
	@Test(timeout = TIMEOUT)
	public void testGetPlayerStatusPreso() {
		Player p1 = new Player(1, 4000, Color.red, "Luiza");
		assertNotNull("Status preso n�o instanciado", p1.getPlayerPreso());
	}

	@Test(timeout = TIMEOUT)
	public void testGetPlayerStatusFalencia() {
		Player p1 = new Player(1, 4000, Color.red, "Luiza");
		assertNotNull("Status falencia n�o instanciado", p1.getPlayerFalencia());
	}

	@Test(timeout = TIMEOUT)
	public void testGetPlayerSaidaLivrePrisao() {
		Player p1 = new Player(1, 4000, Color.red, "Luiza");
		assertNotNull("Status saida livre prisao n�o instanciado", p1.getSaidaLivrePrisao());
	}

	@Test(timeout = TIMEOUT)
	public void testGetmoney() {
		Player p1 = new Player(1, 4000, Color.red, "Luiza");
		assertEquals("Erro ao declarar o dinheiro do jogador", 4000, p1.getMoney());
	}

	@Test(timeout = TIMEOUT)
	public void testGetcolor() {
		Player p1 = new Player(1, 4000, Color.red, "Luiza");
		assertEquals("Erro ao declarar a cor do jogador", Color.red, p1.getColor());
	}

	@Test(timeout = TIMEOUT)
	public void testGetnumber() {
		Player p1 = new Player(1, 4000, Color.red, "Luiza");
		assertEquals("Erro ao declarar o n�mero do jogador", 1, p1.getNumber());
	}

	///// Tests ChangeStatus/////
	@Test(timeout = TIMEOUT)
	public void testChangeStatusPreso() {
		Player p1 = new Player(1, 4000, Color.red, "Luiza");
		boolean original = p1.getPlayerPreso();
		boolean expected = !original;
		p1.changeStatusPreso();
		boolean actual = p1.getPlayerPreso();
		assertEquals("fun�ao changeStatusPreso n�o est� funcionando corretamente", expected, actual);
	}

	@Test(timeout = TIMEOUT)
	public void testChangeStatusSaidaLivrePrisao() {
		Player p1 = new Player(1, 4000, Color.red, "Luiza");
		boolean original = p1.getSaidaLivrePrisao();
		boolean expected = !original;
		p1.changeStatusSaidaPrisao();
		boolean actual = p1.getSaidaLivrePrisao();
		assertEquals("fun�ao changeStatusSaidaLivrePrisao n�o est� funcionando corretamente", expected, actual);
	}

	@Test(timeout = TIMEOUT)
	public void testChangeStatusFalencia() {
		Player p1 = new Player(1, 4000, Color.red, "Luiza");
		boolean expected = true;
		p1.changeStatusFalencia();
		boolean actual = p1.getPlayerFalencia();
		assertEquals("fun�ao changeStatusFalencia n�o est� funcionando corretamente", expected, actual);
	}
}
