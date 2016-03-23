package ClueGame;

public class Card {

	@Override
	public String toString() {
		return "Card [cardName=" + cardName + ", cardType=" + cardType + "]";
	}

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
            equal = this.cardName == ((Card)o).cardName 
            && this.cardType == ((Card)o).cardType;
        }
		return equal;

	}
	

	//Testing purposes only
	public String getCardName() {
		return cardName;
	}

	public CardType getCardType() {
		return cardType;
	}
}
