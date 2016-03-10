package clueTests;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import clueGame.*;

public class ClueTests {
	private static Board board;
	public static int NUM_ROOMS = 11;
	public static int NUM_ROWS = 25;
	public static int NUM_COLUMNS = 25;
	
	@Before
	public void setup() throws IOException{
		board = new Board();
		board.initialize();
		System.out.println("In @Before");
	}
	
	@Test
	public void testRoomLegend(){
		Map<Character, String> rooms = Board.getRooms();
		assertEquals(NUM_ROOMS, rooms.size());
		assertEquals("Kitchen", rooms.get('K'));
		assertEquals("Billiard room", rooms.get('R'));
		assertEquals("Closet", rooms.get('X'));
		assertEquals("Dining room", rooms.get('D'));
		assertEquals("Walkway", rooms.get('W'));
		
	}
	
	@Test
	public void testBoardDimensions() {
		// Ensure we have the proper number of rows and columns
		assertEquals(NUM_ROWS, board.getNumRows());
		assertEquals(NUM_COLUMNS, board.getNumColumns());		
	}
	
	@Test
	public void DoorDirections(){
		BoardCell room = board.getCellAt(13, 1);
		assertTrue(room.isDoorway());
		assertEquals(DoorDirection.DOWN, room.getDoorDirection());
		
		room = board.getCellAt(4, 5);
		assertTrue(room.isDoorway());
		assertEquals(DoorDirection.RIGHT, room.getDoorDirection());
		
		room = board.getCellAt(18, 5);
		assertTrue(room.isDoorway());
		assertEquals(DoorDirection.RIGHT, room.getDoorDirection());
		
		room = board.getCellAt(20, 7);
		assertTrue(room.isDoorway());
		assertEquals(DoorDirection.LEFT, room.getDoorDirection());
		
		room = board.getCellAt(16, 14);
		assertTrue(room.isDoorway());
		assertEquals(DoorDirection.UP, room.getDoorDirection());
		
	}
	@Test
	public void testRoomInitials(){
		assertEquals('X', board.getCellAt(10, 11).getInitial());
		assertEquals('C', board.getCellAt(20, 14).getInitial());
		assertEquals('W', board.getCellAt(22, 11).getInitial());
		assertEquals('R', board.getCellAt(1, 11).getInitial());
		assertEquals('D', board.getCellAt(9, 20).getInitial());
	}
	
	@Test (expected = BadConfigFormatException.class)
	public void testColumns() throws BadConfigFormatException, FileNotFoundException{
		Board board = new Board("ClueLayoutBadColumns.csv", "ClueLegend.txt");
		board.loadRoomConfig();
		board.loadBoardConfig();
		
	}
	@Test (expected = BadConfigFormatException.class)
	public void testBadRoomFormat() throws BadConfigFormatException, FileNotFoundException {
		Board board = new Board("ClueLayout.csv", "ClueLegendBadFormat.txt");
		board.loadRoomConfig();
	}
	@Test (expected = BadConfigFormatException.class)
	public void testBadRoom() throws BadConfigFormatException, FileNotFoundException {
		Board board = new Board("ClueLayoutBadRoom.csv", "ClueLegend.txt");
		board.loadRoomConfig();
		board.loadBoardConfig();
	}
		
}
