package ClueTests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import ClueGame.Board;
import ClueGame.Solution;

public class GameActionTests {
	private static Board board;
	Solution correctSolution;
	Solution badPerson;
	Solution badRoom;
	Solution badWeapon;
	
	//Before method that sets up a board and a series of solutions that test accuracy of checkAccusation method
	@Before
	public void setUp() {
		board = new Board("layout.csv", "ClueLegend.txt");
		Solution actualAnswer = new Solution("Colonel Mustard", "Hall", "Knife");
		board.setSolution(actualAnswer);
		correctSolution = new Solution("Colonel Mustard","Hall","Knife");
		badPerson = new Solution("Professor Plum","Hall","Knife");
		badRoom = new Solution("Colonel Mustard","Kitchen","Knife");
		badWeapon = new Solution("Colonel Mustard","Hall","Revolver");
	}

	/*Testing an accusation
	 *Tests the following things:
	 *	-an accustaion is correct when the cards match the answer
	 *	-accusations are incorrect when one card does not match the answer
	 *		there is a test with one of each card incorrect to test each aspect of the answer is matched
	 */
	@Test
	public void testAccusation() {
		assertTrue(board.checkAccusation(correctSolution));
		assertFalse(board.checkAccusation(badPerson));
		assertFalse(board.checkAccusation(badRoom));
		assertFalse(board.checkAccusation(badWeapon));
	}

}
