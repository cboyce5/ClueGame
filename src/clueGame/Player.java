package clueGame;

import java.awt.Color;
import java.util.ArrayList;

public class Player {
	private String playerName;
	private int row;
	private int column;
	private Color color;
	private ArrayList<Card> cardsInHand;
	
	public Card disproveSuggestion(Solution suggestion) {
		Card c = new Card("",CardType.ROOM);
		return c;
	}

	//For testing purposes only
	public String getPlayerName() {
		return playerName;
	}

	public Player(String playerName, int row, int column, Color color) {
		super();
		this.playerName = playerName;
		this.row = row;
		this.column = column;
		this.color = color;
	}

	public int getRow() {
		return row;
	}

	public int getColumn() {
		return column;
	}

	public Color getColor() {
		return color;
	}
	
	public ArrayList<Card> getCardsInHand() {
		return cardsInHand;
	}

	
}
