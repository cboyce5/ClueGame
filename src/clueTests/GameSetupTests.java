package clueTests;

import static org.junit.Assert.*;

import java.awt.Color;

import org.junit.Before;
import org.junit.Test;

import clueGame.Board;
import clueGame.Card;
import clueGame.CardType;

public class GameSetupTests {
	private static Board board;
	private Card mrGreen;
	private Card theHall;
	private Card theCandlestick;
	public static int NUM_CARDS = 27;
	public static int NUM_PEOPLE = 9;
	public static int NUM_ROOMS = 9;
	public static int NUM_WEAPONS = 9;
	
	//Before method to set up board and a variety of cards for testing
	@Before
	public void setup() {
		board = new Board("layout.csv", "ClueLegend.txt");
		mrGreen = new Card("Mr Green", CardType.PERSON);
		theHall = new Card("Hall", CardType.ROOM);
		theCandlestick = new Card("Candlestick", CardType.WEAPON);
		board.loadConfigFiles();
	}
	
	/*Testing that people are loaded correctly
	 *Tests the following things:
	 *	-human player and 2 computer players have correct name, color and starting location
	 *	 as specified by the config file
	 */
	@Test
	public void testLoadPeople() {
		//Test human player
		assertEquals(board.getHumanPlayer().getPlayerName(), "Human");
		assertEquals(board.getHumanPlayer().getColor(), Color.CYAN);
		assertEquals(board.getHumanPlayer().getRow(), 5);
		assertEquals(board.getHumanPlayer().getColumn(), 5);
		
		//Test 2 computer players (one from each end of config file)
		assertEquals(board.getComputerPlayers().get(0).getPlayerName(), "Miss Scarlet");
		assertEquals(board.getComputerPlayers().get(0).getColor(), Color.RED);
		assertEquals(board.getComputerPlayers().get(0).getRow(), 7);
		assertEquals(board.getComputerPlayers().get(0).getColumn(), 5);
		
		assertEquals(board.getComputerPlayers().get(8).getPlayerName(), "Ms Peach");
		assertEquals(board.getComputerPlayers().get(8).getColor(), Color.PINK);
		assertEquals(board.getComputerPlayers().get(8).getRow(), 15);
		assertEquals(board.getComputerPlayers().get(8).getColumn(), 5);
	}
	
	/*Testing that cards are all loaded correctly
	 *Tests the following things:
	 *	-Deck contains correct number of total cards
	 *	-Deck contains correct amount of each type of card
	 *	-Deck contains specific cards that we know should exist in deck
	 */
	@Test
	public void testLoadCards() {
		//Test the deck contains the correct total number of cards
		assertEquals(NUM_CARDS, board.getDeck().size());
		
		//Test deck has correct number of each type of card
		int roomCount = 0;
		int peopleCount = 0;
		int weaponCount = 0;
		for (int i = 0; i < board.getDeck().size(); i++) {
			switch (board.getDeck().get(i).getCardType()) {
				case ROOM:
					roomCount++;
					break;
				case PERSON:
					peopleCount++;
					break;
				case WEAPON:
					weaponCount++;
					break;
				default:
					break;
			}				
		}
		assertEquals(NUM_PEOPLE,peopleCount);
		assertEquals(NUM_ROOMS,roomCount);
		assertEquals(NUM_WEAPONS,weaponCount);
		
		//Test that deck contains specific cards (testing loading of names)
		assertTrue(board.getDeck().contains(mrGreen));
		assertTrue(board.getDeck().contains(theHall));
		assertTrue(board.getDeck().contains(theCandlestick));
		
		
	}
	
	/*Test that the deal is valid
	 *Tests the following things:
	 *	-All cards from the original deck are dealt
	 *	-All players have roughly the same number of cards (+/- 1)
	 *	-No card is in multiple players hand (no duplicates in dealing)
	 */
	@Test
	public void testDeal() {
		board.deal();
		//Test that all cards are dealt
		int cardsDealt = 0;
		for (int i = 0; i < board.getComputerPlayers().size(); i++) {
			cardsDealt += board.getComputerPlayers().get(i).getCardsInHand().size();
		}
		cardsDealt += board.getHumanPlayer().getCardsInHand().size();
		assertEquals(NUM_CARDS,cardsDealt);
		
		//Test that all players have roughly the same number of cards
		int min = board.getComputerPlayers().get(0).getCardsInHand().size();
		int max = board.getComputerPlayers().get(0).getCardsInHand().size();
		for (int i = 1; i < board.getComputerPlayers().size(); i++) {
			if (min > board.getComputerPlayers().get(i).getCardsInHand().size())
				min = board.getComputerPlayers().get(i).getCardsInHand().size();
			if (max < board.getComputerPlayers().get(i).getCardsInHand().size())
				max = board.getComputerPlayers().get(i).getCardsInHand().size();
		}
		assertTrue(max == min || max == min + 1);
		
		//Test that one card is not given to two different players (duplicate checking)
		int sameCard = 0;
		String c = board.getHumanPlayer().getCardsInHand().get(0).getCardName();
		String c1 = board.getHumanPlayer().getCardsInHand().get(1).getCardName();
		String c2 = board.getHumanPlayer().getCardsInHand().get(2).getCardName();
		for (int i = 0; i < board.getComputerPlayers().size(); i++) {
			for (int j = 0; j < board.getComputerPlayers().get(i).getCardsInHand().size(); j++) {
				if (board.getComputerPlayers().get(i).getCardsInHand().get(j).getCardName() == c) {
					sameCard++;
				}
				if (board.getComputerPlayers().get(i).getCardsInHand().get(j).getCardName() == c1) {
					sameCard++;
				}
				if (board.getComputerPlayers().get(i).getCardsInHand().get(j).getCardName() == c2) {
					sameCard++;
				}
			}
		}
		assertEquals(sameCard,0);
	}

}
