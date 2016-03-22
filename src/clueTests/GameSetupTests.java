package clueTests;

import static org.junit.Assert.*;

import java.awt.Color;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import clueGame.Board;
import clueGame.Card;
import clueGame.CardType;

public class GameSetupTests {
	private static Board board;
	private static Card mrGreen;
	private static Card theHall;
	private static Card theCandlestick;
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
	
	//Testing that people are loaded correctly
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

}
