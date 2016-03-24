package ClueTests;

import static org.junit.Assert.*;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import ClueGame.Board;
import ClueGame.BoardCell;
import ClueGame.Card;
import ClueGame.CardType;
import ClueGame.ComputerPlayer;
import ClueGame.HumanPlayer;
import ClueGame.Player;
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
	
	/*Tests selecting targets
	 *Tests the following things:
	 *	-Targets include room, haven't visited (Room preference tests)
	 *	-Targets that don't include a room, or a room that was just visited (Random choice tests)
	 */
	@Test
	public void testTargets() {
		//Room preference tests
		board.calcTargets(14, 2, 1);
		//for (int i = 0; i < 50; i++ ) {
		System.out.println(board.getCellAt(13, 2));
		assertEquals(board.getComputerPlayers().get(0).pickLocation(board.getTargets()),board.getCellAt(13, 2));
		//}
		
		//Random choice tests
		board.calcTargets(15, 18, 2);
		board.getComputerPlayers().get(0).pickLocation(board.getTargets());
		ArrayList<Boolean> cellsSelected = new ArrayList<Boolean>();
		for (int i = 0; i < 100; i++) {
			BoardCell selected = board.getComputerPlayers().get(0).pickLocation(board.getTargets());
			if (selected == board.getCellAt(15, 20))
				cellsSelected.add(true);
			else if (selected == board.getCellAt(14, 19))
				cellsSelected.add(true);
			else if (selected == board.getCellAt(13, 18))
				cellsSelected.add(true);
			else if (selected == board.getCellAt(14, 17))
				cellsSelected.add(true);
			else if (selected == board.getCellAt(15, 16))
				cellsSelected.add(true);
			else if (selected == board.getCellAt(16, 17))
				cellsSelected.add(true);
			else
				fail("Invaid target selected");
		}
		for (int i = 0;  i < cellsSelected.size(); i++) {
			boolean b = (boolean)cellsSelected.get(i);
			assertTrue(b);
		}
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
		//Returning only possible card
		Player disprover = new Player("Disprover",0,0,Color.red);
		disprover.getCardsInHand().add(new Card("Mr Green",CardType.PERSON));
		disprover.getCardsInHand().add(new Card("Mrs Peacock",CardType.PERSON));
		disprover.getCardsInHand().add(new Card("Kitchen",CardType.ROOM));
		disprover.getCardsInHand().add(new Card("Ballroom",CardType.ROOM));
		disprover.getCardsInHand().add(new Card("Wrench",CardType.WEAPON));
		disprover.getCardsInHand().add(new Card("Rope",CardType.WEAPON));
		assertEquals(disprover.disproveSuggestion(new Solution("Mr Green","Library","Poison")),new Card("Mr Green",CardType.PERSON));
		assertEquals(disprover.disproveSuggestion(new Solution("Mr Boddy","Kitchen","Poison")),new Card("Kitchen",CardType.ROOM));
		assertEquals(disprover.disproveSuggestion(new Solution("Mr Boddy","Library","Wrench")),new Card("Wrench",CardType.WEAPON));
		assertEquals(disprover.disproveSuggestion(new Solution("Mr Boddy","Library","Poison")),null);
		//Returning one card with multiple cards, randomly
		int countPerson = 0, countRoom = 0, countWeapon = 0;
		Card person = new Card("Mr Green",CardType.PERSON);
		Card room = new Card("Kitchen",CardType.ROOM);
		Card weapon = new Card("Rope",CardType.WEAPON);
		boolean nullExist = true;
		for(int i = 0; i < 60; i++)
		{
			nullExist = true;
			Card checkedCard = disprover.disproveSuggestion(new Solution("Mr Green","Kitchen","Rope"));
			if (checkedCard.equals(person)){
				countPerson++;
				nullExist = false;}
			if (checkedCard.equals(room)){
				countRoom++;
				nullExist = false;}
			if (checkedCard.equals(weapon)){
				countWeapon++;
				nullExist = false;}
			assertFalse(nullExist);
		}
		assertTrue(countPerson > 10);
		assertTrue(countRoom > 10);
		assertTrue(countWeapon > 10);
		//Players are queried in order
		ArrayList<ComputerPlayer> players = new ArrayList<ComputerPlayer>();
		HumanPlayer disprover1 = new HumanPlayer("Disprover",0,0,Color.red);
		disprover1.getCardsInHand().add(new Card("Mrs Peacock",CardType.PERSON));
		disprover1.getCardsInHand().add(new Card("Library",CardType.ROOM));
		disprover1.getCardsInHand().add(new Card("Lead pipe",CardType.WEAPON));
		players.add(new ComputerPlayer("A",0,0,Color.green));
		players.add(new ComputerPlayer("B",0,0,Color.gray));
		players.add(new ComputerPlayer("C",0,0,Color.black));
		players.add(new ComputerPlayer("D",0,0,Color.red));
		players.get(0).getCardsInHand().add(new Card ("Miss Scarlet",CardType.PERSON));
		players.get(0).getCardsInHand().add(new Card ("Conservatory",CardType.ROOM));
		players.get(0).getCardsInHand().add(new Card ("Revolver",CardType.WEAPON));
		players.get(1).getCardsInHand().add(new Card ("Colonel Mustard",CardType.PERSON));
		players.get(1).getCardsInHand().add(new Card ("Kitchen",CardType.ROOM));
		players.get(1).getCardsInHand().add(new Card ("Candlestick",CardType.WEAPON));
		players.get(2).getCardsInHand().add(new Card ("Mrs White",CardType.PERSON));
		players.get(2).getCardsInHand().add(new Card ("Ballroom",CardType.ROOM));
		players.get(2).getCardsInHand().add(new Card ("Wrench",CardType.WEAPON));
		players.get(3).getCardsInHand().add(new Card ("Mr Green",CardType.PERSON));
		players.get(3).getCardsInHand().add(new Card ("Billiard room",CardType.ROOM));
		players.get(3).getCardsInHand().add(new Card ("Rope",CardType.WEAPON));
		//No players can disprove, including human player.
		for(int i = 0; i < players.size(); i++)
		{
			assertEquals(players.get(i).disproveSuggestion(new Solution("Ms Peach","Dining room","Sword")),null);
		}
		assertEquals(disprover1.disproveSuggestion(new Solution("Ms Peach","Dining room","Sword")),null);
		//Only human can disprove
		for(int i = 0; i < players.size(); i++)
		{
			assertEquals(players.get(i).disproveSuggestion(new Solution("Mrs Peacock","Dining room","Sword")),null);
		}
		assertEquals(disprover1.disproveSuggestion(new Solution("Mrs Peacock","Dining room","Sword")),new Card("Mrs Peacock",CardType.PERSON));
		//Test current player turn does not return a card
		board.setPlayers(disprover1, players);
		Solution testHumanSolution = new Solution("Mrs Peacock","Dining room","Sword");
		assertEquals(board.handleSuggestion(testHumanSolution, disprover1.getPlayerName(), new BoardCell(0,0,'W')), null);
		assertFalse(board.handleSuggestion(testHumanSolution, disprover1.getPlayerName(), new BoardCell(0,0,'W')) == new Card("Mrs Peacock",CardType.PERSON));
		Solution testComputerSolution = new Solution("Mrs Peach","Kitchen","Sword");
		assertEquals(board.handleSuggestion(testComputerSolution, players.get(1).getPlayerName(), new BoardCell(0,0,'W')), null);
		assertFalse(board.handleSuggestion(testComputerSolution, disprover1.getPlayerName(), new BoardCell(0,0,'W')) == new Card("Kitchen",CardType.ROOM));
		//Test people disprove orderly
		Solution doubleDisprove = new Solution("Miss Scarlet","Ballroom","Sword");
		assertEquals(board.handleSuggestion(doubleDisprove, disprover1.getPlayerName(),  new BoardCell(0,0,'W')), new Card("Miss Scarlet",CardType.PERSON));
		Solution furthestDisprove = new Solution("Mr Green","Dining","Sword");
		assertEquals(board.handleSuggestion(furthestDisprove, players.get(0).getPlayerName(),  new BoardCell(0,0,'W')), new Card("Mr Green",CardType.PERSON));
	}
	/*Tests a computer player making a suggestion
	 *Tests the following things:
	 *	-One suggestion is possible
	 *	-Multiple possibilities, randomly chooses
	 */
	@Test
	public void testComputerSuggestion() {
		//Only one suggestion is possible based on card than have been seen
		ComputerPlayer comp1 = new ComputerPlayer("",1,1,Color.BLACK);
		Solution onlyOne = new Solution("Colonel Mustard","Hall","Knife");
		Map<CardType,Card> one = new HashMap<CardType,Card>();
		one.put(CardType.PERSON, new Card("Colonel Mustard",CardType.PERSON));
		one.put(CardType.WEAPON, new Card("Knife",CardType.WEAPON));
		one.put(CardType.ROOM, new Card("Hall",CardType.ROOM));
		comp1.setCardsNotSeen(one);
		assertEquals(onlyOne,comp1.makeSuggestion(board, board.getCellAt(comp1.getRow(), comp1.getColumn())));
		
		//Multiple suggestions are possible by having multiple cards of same type not seen
		one.put(CardType.PERSON, new Card("Professor Plum", CardType.PERSON));
		comp1.setCardsNotSeen(one);
		boolean seenColonel = false;
		boolean seenProfessor = false;
		for (int i = 0; i < 50; i++) {
			Solution s = comp1.makeSuggestion(board, board.getCellAt(comp1.getRow(), comp1.getColumn()));
			if (s.person == "Colonel Mustard")
				seenColonel = true;
			if (s.person == "Professor Plum")
				seenProfessor = true;
		}
		assertTrue(seenColonel);
		assertTrue(seenProfessor);
	}
}
