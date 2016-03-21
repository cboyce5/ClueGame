package clueGame;

import java.awt.Color;

public class Player {
	private String playerName;
	private int row;
	private int column;
	private Color color;
	
	public Card disproveSuggestion(Solution suggestion) {
		Card c = new Card();
		return c;
	}

	//For testing purposes only
	public String getPlayerName() {
		return playerName;
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
	
}
