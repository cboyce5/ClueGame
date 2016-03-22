package clueGame;

public class Card {

	private String cardName;
	private CardType cardType;
	
	public Card(String cardName, CardType cardType) {
		super();
		this.cardName = cardName;
		this.cardType = cardType;
	}

	public boolean equals(Object o) {
		boolean equal = false;
		if (o != null && o instanceof Card)
        {
            equal = this.cardName == ((Card)o).cardName;
            equal = this.cardType == ((Card)o).cardType;
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
