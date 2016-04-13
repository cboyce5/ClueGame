package ClueGame;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Player {
	private String playerName;
	private int row;
	private int column;
	private Color color;
	private ArrayList<Card> cardsInHand;
	private Map<CardType,ArrayList<Card>> cardsNotSeen;
	private int pixelRow;
	private int pixelColumn;
	private int pixelHeight = 25;
	private int pixelWidth = 25;

	public Player(String playerName, int row, int column, Color color) {
		super();
		this.playerName = playerName;
		this.row = row;
		this.column = column;
		this.color = color;
		cardsInHand = new ArrayList<Card>();
		cardsNotSeen = new HashMap<CardType,ArrayList<Card>>();
		this.pixelColumn = column * 25;
		this.pixelRow = row * 25;
	}
	
	public Card disproveSuggestion(Solution suggestion) {
		ArrayList<Card> disprovedCards = new ArrayList<Card>();
		for (Card a:cardsInHand)
		{
			if (a.equals(new Card(suggestion.person,CardType.PERSON)) || 
				a.equals(new Card(suggestion.room,CardType.ROOM))||
				a.equals(new Card(suggestion.weapon,CardType.WEAPON)))
				disprovedCards.add(a);
				
		}
		if(disprovedCards.isEmpty()) return null;
		Random rd = new Random();
		return disprovedCards.get(rd.nextInt(disprovedCards.size()));
	}

	//Necessary getters and setters
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

	public void setCardsNotSeen(Map<CardType, ArrayList<Card>> cardsNotSeen) {
		this.cardsNotSeen = cardsNotSeen;
	}
	
	public Map<CardType, ArrayList<Card>> getCardsNotSeen() {
		return cardsNotSeen;
	}

	public String getPlayerName() {
		return playerName;
	}

	public int getPixelRow() {
		return pixelRow;
	}

	public int getPixelColumn() {
		return pixelColumn;
	}

	public int getPixelHeight() {
		return pixelHeight;
	}

	public int getPixelWidth() {
		return pixelWidth;
	}

	public void setRow(int row) {
		this.row = row;
		this.pixelRow = row * 25;
	}

	public void setColumn(int column) {
		this.column = column;
		this.pixelColumn = column * 25;
	}

	
}
