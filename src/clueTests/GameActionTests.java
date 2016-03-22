package clueTests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import clueGame.Board;
import clueGame.Solution;

public class GameActionTests {
	private static Board board;
	Solution correctSolution;
	Solution badPerson;
	Solution badRoom;
	Solution badWeapon;
	
	@Before
	public void setUp() {
		board = new Board("layout.csv", "ClueLegend.txt");
		correctSolution = new Solution("Colonel Mustard","Hall","Knife");
		badPerson = new Solution("Professor Plum","Hall","Knife");
		badRoom = new Solution("Colonel Mustard","Kitchen","Knife");
		badWeapon = new Solution("Colonel Mustard","Hall","Revolver");
	}

	@Test
	public void testAccusation() {
		assertTrue(board.checkAccusation(correctSolution));
		assertFalse(board.checkAccusation(badPerson));
		assertFalse(board.checkAccusation(badRoom));
		assertFalse(board.checkAccusation(badWeapon));
	}

}
