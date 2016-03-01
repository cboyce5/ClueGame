package ClueGame;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class Board {
	private Map<BoardCell, LinkedList<BoardCell>> adjMtx;
	private Set<BoardCell> visited;
	private Set<BoardCell> targets;
	private BoardCell[][] board;
	private int numRows;
	private int numColumns;
	private static Map<Character,String> rooms;
	public final static int BOARD_SIZE = 50;
	private String boardConfigFile;
	private String roomConfigFile;
	private ArrayList<String> listBoard;
	
	
	
	public Board(String layout, String legend){
		super();
		this.boardConfigFile = layout;
		this.roomConfigFile = legend;

	}

	public Board(){
		super();
		this.boardConfigFile = "ClueLayout.csv";
		this.roomConfigFile = "ClueLEgend.txt";
	}
	
	public void calcAdjacencies(){
		adjMtx = new HashMap<BoardCell, LinkedList<BoardCell>>();
		for(int i = 0; i < numRows; i++){		
			for(int j = 0; j < numColumns; j++){
				LinkedList<BoardCell> list = new LinkedList<BoardCell>();

				if(board[i][j].getSecondInitial() != 'N'){
					if(board[i][j].getDoorDirection() == DoorDirection.UP){
						list.add(board[i-1][j]);
					}
					if(board[i][j].getDoorDirection() == DoorDirection.RIGHT){
						list.add(board[i][j+1]);
					}
					if(board[i][j].getDoorDirection() == DoorDirection.DOWN){
						list.add(board[i+1][j]);
					}
					if(board[i][j].getDoorDirection() == DoorDirection.LEFT){
						list.add(board[i][j-1]);
					}
					
				}
				else if(board[i][j].getInitial() != 'W'){
					list.clear();
				}
			
				else if(board[i][j].getInitial() == 'W'){
					if(j != numRows - 1 && (board[i][j+1].getDoorDirection() == DoorDirection.LEFT || board[i][j+1].getInitial() == 'W')){
						list.add(board[i][j+1]);
					}
					if(i != numRows - 1 && (board[i+1][j].getDoorDirection() == DoorDirection.UP|| board[i+1][j].getInitial() == 'W')){
						list.add(board[i+1][j]);
					}
					if(i != 0 && (board[i-1][j].getDoorDirection() == DoorDirection.DOWN || board[i-1][j].getInitial() == 'W')){
						list.add(board[i-1][j]);
					}
					if(j!=0 && (board[i][j-1].getDoorDirection() == DoorDirection.RIGHT || board[i][j-1].getInitial() == 'W')){
						list.add(board[i][j-1]);
					}
					
				}
				adjMtx.put(board[i][j], list);
			}	
		}			
	}
    public void calcTargets(BoardCell startCell, int pathLength){
    		visited = new HashSet<BoardCell>();
    		targets = new HashSet<BoardCell>();
    		visited.add(startCell);
    		findAllTargets(startCell, pathLength);
    }
    public void calcTargets(int x, int y, int pathLength){
		visited = new HashSet<BoardCell>();
		targets = new HashSet<BoardCell>();
		visited.add(board[x][y]);
		findAllTargets(board[x][y], pathLength);
    }
	public Set<BoardCell> getTargets(){
		return targets;
	}
	public LinkedList<BoardCell> getAdjList(BoardCell cell){
		return adjMtx.get(cell);
	}
	
	public LinkedList<BoardCell> getAdjList(int x, int y){
		return adjMtx.get(board[x][y]);
		
	}
	
	public BoardCell getCellAt(int x, int y){
		return board[x][y];
	}
	
	public void findAllTargets(BoardCell thisCell, int numSteps){
		LinkedList<BoardCell> adjacentCells = new LinkedList<BoardCell>(getAdjList(thisCell));
		for(BoardCell cell: adjacentCells)
		{
			if (visited.contains(cell)) continue;
			visited.add(cell);
			if(numSteps == 1) targets.add(cell);
			else findAllTargets(cell, numSteps - 1);
			visited.remove(cell);
		}
	}
	
	public void initialize() {
			try{
			
				loadRoomConfig();
				loadBoardConfig();
				calcAdjacencies();}
			catch(BadConfigFormatException e){
				System.out.println(e.getMessage());
			}
	}
	
	public void loadRoomConfig() throws BadConfigFormatException{
		FileReader reader = null;
		Scanner in = null;
		try{
			reader = new FileReader(roomConfigFile);
			in = new Scanner(reader);
		}catch(FileNotFoundException e){
			System.out.println(e.getMessage());
		}
		rooms = new HashMap<Character, String>();
		String input = "";
		while(in.hasNextLine()){
			input = in.nextLine();
			String[] array = input.split(",");
			if(!array[2].equals(" Card") && !array[2].equals(" Other") && array[1].length() != 1)
				throw new BadConfigFormatException(roomConfigFile);
			for(String s: array)
				rooms.put(array[0].charAt(0), array[1].trim());
		}		
	}
	
	public void loadBoardConfig() throws BadConfigFormatException{
		int rows = 0;
		int cols = 0;
		listBoard = new ArrayList<String>();
		FileReader reader = null;
		Scanner in = null;
		try{
			reader = new FileReader(boardConfigFile);
			in = new Scanner(reader);
		}catch(FileNotFoundException e){
			System.out.println(e.getMessage());
		}
		String input = "";
		while(in.hasNextLine()){
			input = in.nextLine();
			String[] array = input.split(",");
			if((cols != array.length) && cols != 0)
				throw new BadConfigFormatException(boardConfigFile);
			rows++;
			cols = array.length;
			for(String s: array){
				if(!rooms.containsKey(s.charAt(0)))
					throw new BadConfigFormatException(boardConfigFile);
				listBoard.add(s);
			}
		}
		numRows = rows;
		numColumns = cols;
		int count = 0;
		board = new BoardCell[numRows][numColumns];
		for(int i = 0; i < numRows; i++){
			for(int j = 0; j < numColumns; j++){
				board[i][j] = new BoardCell(i,j,listBoard.get(count).charAt(0));
				if(listBoard.get(count).length() == 2){
					board[i][j].setDoor(true);
					board[i][j].setSecondInitial(listBoard.get(count).charAt(1));
					board[i][j].setDirection(listBoard.get(count).charAt(1));
				}

				count++;
			}
		}
	}

	public static Map<Character, String> getRooms() {
		return rooms;
	}

	public int getRow() {
		return numRows;
	}

	public int getColumn() {
		return numColumns;
	}
	
}
