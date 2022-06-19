package Model.Player;

import java.awt.Color;
import java.util.ArrayList;

public class Player {

	// Definition of variables

	private int number;
	private int money;
	private String cor;
	private String name;

	private Color color;
	private Pawn pawn;

	private boolean saidaLivrePrisao = false;
	private boolean preso = false;
	private boolean falencia = false;
	private boolean saiuDoJogo = false;

	private ArrayList<Integer> propriedades = new ArrayList<Integer>();

	// Random, Scanner etc

	// Constructors

	public Player(int playerNumb, int money, Color color, String cor, int pin, String name) {
		this.number = playerNumb;
		this.money = money;
		this.color = color;
		this.cor = cor;
		this.name = name;
		this.pawn = new Pawn(pin);
	}

	// Methods
	public int getPin() {
		return this.pawn.getPawnNumber();
	}

	public String getName() {
		return this.name;
	}

	public String getCor() {
		return this.cor;
	}

	public void movePawn(int somaDados) { // move o Pawn
		pawn.move(somaDados);
	}

	public void addPropriedade(int propriedade) { // adiciona propriedade
		this.propriedades.add(propriedade);
	}

	public void removePropriedade(int property) { // remove a propriedade
		this.propriedades.remove(propriedades.indexOf(property));
	}

	public boolean goToPrison() {
		preso = true;
		return true;
	}

	public void changeMoney(int value) {
		this.money += value;
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

	public int[] getPawnCoordenates() {
		return pawn.getCoordenates();
	}

	public void setPosition(int x) {
		pawn.setPosition(x);
		return;
	}

	public void setCoordenates(int x, int y) {
		pawn.setCoordenates(x, y);
		return;
	}

	public String genSaveString() {
		String playerData = "";

		playerData += String.format(
				"\n\tPosicao: %d\n\tCor: %s\n\tColor: %s\n\tMoney: %d\n\tSaidaLivrePrisao: %b\n\tPreso: %b\n\tFalencia: %b\n\tSaiu do jogo: %b\n\tnome: %s\n",
				pawn.getPos(), cor, color, money, saidaLivrePrisao, preso, falencia, saiuDoJogo, name);

		playerData += "\tPropriedades: " + propriedades.toString();

		return playerData;
	}

	public boolean getSaiuDoJogo() {
		return saiuDoJogo;
	}

	public boolean setSaiuDoJogo() {
		saiuDoJogo = !saiuDoJogo;
		return saiuDoJogo;
	}

}