package Model.Player;

import java.awt.Color;
import java.util.ArrayList;

public class Player {

	// Definition of variables

	private int number;
	private int money;
	private String name;

	private Color color;
	private Pawn pawn = new Pawn();

	private boolean saidaLivrePrisao = false;
	private boolean preso = false;
	private boolean falencia = false;

	private ArrayList<Integer> propriedades = new ArrayList<Integer>();

	// Random, Scanner etc

	// Constructors

	public Player(int playerNumb, int money, Color color) {
		this.number = playerNumb;
		this.money = money;
		this.color = color;
		// this.name = name;
	}

	// Methods

	public void movePawn(int somaDados) { // move o Pawn
		pawn.move(somaDados);
	}

	public void addPropriedade(int property) { // adiciona propriedade
		this.propriedades.add(property);
	}

	public void removePropriedade(int property) { // remove a propriedade
		this.propriedades.remove(propriedades.indexOf(property));
	}

	public boolean goToPrison() {
		pawn.goTo(10);
		preso = true;
		return true;
	}

	public void changeMoney(int value) {
		this.money += value;
		if (this.money <= 0) {
			this.falencia = true;
		}
	}

	public void changeStatusPreso() { // player fica preso ou deixa de ficar preso
		this.preso = !this.preso;
	}

	public void changeStatusFalencia() { // player fica falido
		this.falencia = true;
	}

	public void changeStatusSaidaPrisao() { // troca o status da carta para sair da prisao livremente
		this.saidaLivrePrisao = !this.saidaLivrePrisao;
	}

	public int getNumber() {
		return this.number;
	}

	public Color getColor() {
		return this.color;
	}

	public int getMoney() {
		return this.money;
	}

	public String getName() {
		return this.name;
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

	public ArrayList<Integer> getPropriedades() {
		ArrayList<Integer> props = new ArrayList<Integer>();
		int i;
		for (i = 0; i < this.propriedades.size(); i++) {
			props.add(propriedades.get(i));
		}

		return props;
	}

	public int getPawnPos() {
		return pawn.getPos();
	}
}