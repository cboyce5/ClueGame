package clueTests;

import static org.junit.Assert.*;

import java.util.LinkedList;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import clueGame.*;

public class TargetTests {
	private static Board board;

	@Before
	public void setUp() {
		board = new Board("ClueLayout.csv", "ClueLegend.txt");
		board.initialize();
	}

	@Test
	public void onlyWalkway(){
		LinkedList<BoardCell> testList = board.getAdjList(9,6);
		assertEquals(4, testList.size());
		assertTrue(testList.contains(board.getCellAt(9, 5)));
		assertTrue(testList.contains(board.getCellAt(9, 7)));
		assertTrue(testList.contains(board.getCellAt(8, 6)));
		assertTrue(testList.contains(board.getCellAt(10, 6)));
			
	}
	
	@Test
	public void edgeLocations(){
		LinkedList<BoardCell> testList = board.getAdjList(1,0);
		assertEquals(0, testList.size());
		
		testList = board.getAdjList(11,0);
		assertEquals(1,testList.size());
		assertTrue(testList.contains(board.getCellAt(12, 0)));
		
		testList = board.getAdjList(0,11);
		assertEquals(0,testList.size());
		
		testList = board.getAdjList(24,11);
		assertEquals(2,testList.size());
		assertTrue(testList.contains(board.getCellAt(24, 12)));
		assertTrue(testList.contains(board.getCellAt(23, 11)));
	}
	
	@Test
	public void besideRoomLocationsNotDoor(){
		LinkedList<BoardCell> testList = board.getAdjList(15, 13);
		assertEquals(3, testList.size());
		assertTrue(testList.contains(board.getCellAt(15, 12)));
		assertTrue(testList.contains(board.getCellAt(14, 13)));
		assertTrue(testList.contains(board.getCellAt(15, 14)));
		
		testList = board.getAdjList(14, 4);
		assertEquals(3, testList.size());
		assertTrue(testList.contains(board.getCellAt(14, 3)));
		assertTrue(testList.contains(board.getCellAt(14, 5)));
		assertTrue(testList.contains(board.getCellAt(15, 4)));
	}
	@Test
	public void nextToDoorway1(){
		LinkedList<BoardCell> testList = board.getAdjList(18,6);
		assertEquals(3, testList.size());
		assertTrue(testList.contains(board.getCellAt(18, 5)));
		assertTrue(testList.contains(board.getCellAt(19, 6)));
		assertTrue(testList.contains(board.getCellAt(17, 6)));
			
	}
	
	@Test
	public void nextToDoorway2(){
		LinkedList<BoardCell> testList = board.getAdjList(21,6);
		assertEquals(3, testList.size());
		assertTrue(testList.contains(board.getCellAt(21, 7)));
		assertTrue(testList.contains(board.getCellAt(20, 6)));
		assertTrue(testList.contains(board.getCellAt(22, 6)));
			
	}
	
	@Test
	public void nextToDoorway3(){
		LinkedList<BoardCell> testList = board.getAdjList(15,14);
		assertEquals(4, testList.size());
		assertTrue(testList.contains(board.getCellAt(15, 15)));
		assertTrue(testList.contains(board.getCellAt(15, 13)));
		assertTrue(testList.contains(board.getCellAt(14, 14)));
		assertTrue(testList.contains(board.getCellAt(16, 14)));			
	}
	
	@Test
	public void nextToDoorway4(){
		LinkedList<BoardCell> testList = board.getAdjList(4,10);
		assertEquals(4, testList.size());
		assertTrue(testList.contains(board.getCellAt(3, 10)));
		assertTrue(testList.contains(board.getCellAt(5, 10)));
		assertTrue(testList.contains(board.getCellAt(4, 11)));
		assertTrue(testList.contains(board.getCellAt(4, 9)));
			
	}
	
	@Test
	public void Doorway1(){
		LinkedList<BoardCell> testList = board.getAdjList(4,5);
		assertEquals(1, testList.size());
		assertTrue(testList.contains(board.getCellAt(4,6)));

			
	}
	
	@Test
	public void Doorway2(){
		LinkedList<BoardCell> testList = board.getAdjList(18,5);
		assertEquals(1, testList.size());
		assertTrue(testList.contains(board.getCellAt(18, 6)));
	
	}


	@Test
	public void targetAlongWalkway(){
		board.calcTargets(5,7, 1);
		Set<BoardCell> targets = board.getTargets();
		assertEquals(4, targets.size());
		assertTrue(targets.contains(board.getCellAt(5, 8)));
		assertTrue(targets.contains(board.getCellAt(6, 7)));	
		assertTrue(targets.contains(board.getCellAt(5, 6)));
		assertTrue(targets.contains(board.getCellAt(4, 7)));
		
		board.calcTargets(11,6,2);
		targets = board.getTargets();
		assertEquals(6, targets.size());
		assertTrue(targets.contains(board.getCellAt(9, 6)));
		assertTrue(targets.contains(board.getCellAt(10, 7)));	
		assertTrue(targets.contains(board.getCellAt(10, 5)));
		assertTrue(targets.contains(board.getCellAt(12, 7)));
		assertTrue(targets.contains(board.getCellAt(13, 6)));
		assertTrue(targets.contains(board.getCellAt(12, 5)));
		
		board.calcTargets(4,17,3);
		targets = board.getTargets();
		assertEquals(11, targets.size());
		assertTrue(targets.contains(board.getCellAt(1, 17)));
		assertTrue(targets.contains(board.getCellAt(2, 18)));	
		assertTrue(targets.contains(board.getCellAt(4, 20)));
		assertTrue(targets.contains(board.getCellAt(5, 19)));
		assertTrue(targets.contains(board.getCellAt(7, 17)));
		assertTrue(targets.contains(board.getCellAt(5, 15)));
		assertTrue(targets.contains(board.getCellAt(5, 15)));
		
	}
		
	@Test
	public void targetEnterRoom(){
		board.calcTargets(12,22, 1);
		Set<BoardCell> targets = board.getTargets();
		assertEquals(3, targets.size());
		assertTrue(targets.contains(board.getCellAt(11, 22)));
		assertTrue(targets.contains(board.getCellAt(12, 21)));	
		assertTrue(targets.contains(board.getCellAt(13, 22)));

		board.calcTargets(5,20, 2);
		targets = board.getTargets();
		assertEquals(5, targets.size());
		assertTrue(targets.contains(board.getCellAt(3, 20)));
		assertTrue(targets.contains(board.getCellAt(4, 19)));	
		assertTrue(targets.contains(board.getCellAt(4, 21)));
		assertTrue(targets.contains(board.getCellAt(5, 22)));
		assertTrue(targets.contains(board.getCellAt(5, 18)));
		
	}
	
	@Test
	public void targetLeaveRoom(){
		LinkedList<BoardCell> testList = board.getAdjList(19,5);
		assertEquals(1, testList.size());
		assertTrue(testList.contains(board.getCellAt(19, 6)));

		
		board.calcTargets(19,5, 1);		
		Set<BoardCell> targets = board.getTargets();
		assertEquals(1, targets.size());
		assertTrue(targets.contains(board.getCellAt(19, 6)));
		
		board.calcTargets(20,18, 2);
		targets = board.getTargets();
		assertEquals(2, targets.size());
		assertTrue(targets.contains(board.getCellAt(19, 17)));
		assertTrue(targets.contains(board.getCellAt(21, 17)));
	}

}
