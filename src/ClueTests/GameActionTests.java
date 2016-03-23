package ClueTests;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import ClueGame.Board;
import ClueGame.BoardCell;
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
		board.initialize();
		board.loadConfigFiles();
	}

	/*Testing an accusation
	 *Tests the following things:
	 *	-an accusation is correct when the cards match the answer
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
	
	/*Tests disproving suggestions
	 * Tests the following things:
	 * 	-Returning only possible card
	 * 	-Returning one card with multiple cards, randomly
	 * 	-Players are queried in order
	 * 	-Human player tests
	 * 	-Test current player turn does not return a card
	 */
	@Test
	public void testDisproveSuggestion() {
		
	}
	
	/*Tests selecting targets
	 *Tests the following things:
	 *	-Targets include room, haven't visited (Room preference tests)
	 *	-Targets that don't include a room, or a room that was just visited (Random choice tests)
	 */
	@Test
	public void testTargets() {
		//Room preference tests
		board.calcTargets(2, 14, 1);
		board.getComputerPlayers().get(0).pickLocation(board.getTargets());
		for (int i = 0; i < 50; i++ ) {
			assertEquals(board.getComputerPlayers().get(0).getNextCell(),board.getCellAt(2, 13));
		}
		
		//Random choice tests
		board.calcTargets(18, 15, 2);
		board.getComputerPlayers().get(0).pickLocation(board.getTargets());
		ArrayList<Boolean> cellsSelected = new ArrayList<Boolean>();
		for (int i = 0; i < 100; i++) {
			BoardCell selected = board.getComputerPlayers().get(0).pickLocation(board.getTargets());
			if (selected == board.getCellAt(20, 15))
				cellsSelected.add(true);
			else if (selected == board.getCellAt(19, 14))
				cellsSelected.add(true);
			else if (selected == board.getCellAt(18, 13))
				cellsSelected.add(true);
			else if (selected == board.getCellAt(17, 14))
				cellsSelected.add(true);
			else if (selected == board.getCellAt(16, 15))
				cellsSelected.add(true);
			else if (selected == board.getCellAt(17, 16))
				cellsSelected.add(true);
			else
				fail("Invaid target selected");
		}
		for (int i = 0;  i < cellsSelected.size(); i++) {
			boolean b = (boolean)cellsSelected.get(i);
			assertTrue(b);
		}
	}
	
	/*Tests a computer player making a suggestion
	 *Tests the following things:
	 *	-One suggestion is possible
	 *	-Multiple possibilities, randomly chooses
	 */
	@Test
	public void testComputerSuggestion() {
		
	}
}
