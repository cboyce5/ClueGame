package clueTests;

import static org.junit.Assert.*;

import java.awt.Color;

import org.junit.Before;
import org.junit.Test;

import clueGame.Board;

public class GameSetupTests {
	private static Board board;
	public static int NUM_CARDS = 27;
	public static int NUM_PEOPLE = 9;
	public static int NUM_ROOMS = 9;
	public static int NUM_WEAPONS = 9;
	
	//Before method to set up board and a variety of cards for testing
	@Before
	public void setup() {
		board = new Board("layout.csv", "ClueLegend.txt");
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

}
