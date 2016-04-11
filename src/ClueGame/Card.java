package ClueGame;

public class Card {
	private String cardName;
	private CardType cardType;
	
	public Card(String cardName, CardType cardType) {
		super();
		this.cardName = cardName;
		this.cardType = cardType;
	}

	@Override
	public boolean equals(Object o) {
		boolean equal = false;
		if (o != null && o instanceof Card)
        {
           equal = this.cardName.equals(((Card)o).cardName) && 
           this.cardType == ((Card)o).cardType;
        }
		return equal;

	}
	
	@Override
	public String toString() {
		return "Card [cardName=" + cardName + ", cardType=" + cardType + "]";
	}

	//Necessary getters and setters
	public String getCardName() {
		return cardName;
	}

	public CardType getCardType() {
		return cardType;
	}
}
