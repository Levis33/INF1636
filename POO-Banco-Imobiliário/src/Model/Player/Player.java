package Model.Player;

import java.awt.Color;

public class Player {

	// Definition of variables

	private int playerNumber;
	private int playerMoney;

	private Color playerColor;
	// private Pawn pawn = new Pawn();

	private boolean saidaLivrePrisao = false;
	private boolean preso = false;
	private boolean falencia = false;

	// Random, Scanner etc

	// Constructors

	public Player(int playerNumb, int money, Color color) {
		this.playerNumber = playerNumb;
		this.playerMoney = money;
		this.playerColor = color;
	}

	// Methods

	public void changeStatusPreso() { // player fica preso ou deixa de ficar preso
		this.preso = !this.preso;
	}

	public void changeStatusFalencia() { // player fica falido
		this.falencia = true;
	}

	public void changeStatusSaidaPrisao() { // troca o status da carta para sair da prisao livremente
		this.saidaLivrePrisao = !this.saidaLivrePrisao;
	}

	public int getPlayerNumber() {
		return this.playerNumber;
	}

	public Color getPlayerColor() {
		return this.playerColor;
	}

	public int getPlayerMoney() {
		return this.playerMoney;
	}

	public boolean getSaidaLivrePrisao() {
		return this.saidaLivrePrisao;
	}

	public boolean getPlayerPreso() {
		return this.preso;
	}

	public boolean getPlayerFalencia() {
		return this.falencia;
	}

}