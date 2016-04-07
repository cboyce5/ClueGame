package ClueGame;
import java.awt.Color;
import java.awt.Graphics;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

import javax.swing.JPanel;

public class Board extends JPanel{
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
	private ArrayList<Card> deck;
	
	
	private ArrayList<ComputerPlayer> computerPlayers;
	private HumanPlayer human;
	private String cardPlayerConfigFile = "people.txt";
	private String cardRoomsConfigFile = "rooms.txt";
	private String cardWeaponConfigFile = "weapons.txt";
	
	private String personSolution;
	private String roomSolution;
	private String weaponSolution;
	
	public Board(String layout, String legend){
		super();
		this.boardConfigFile = layout;
		this.roomConfigFile = legend;

	}

	public Board(){
		super();
		this.boardConfigFile = "ClueLayout.csv";
		this.roomConfigFile = "ClueLegend.txt";
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		for (int i = 0; i < numRows; i++) {
			for (int j = 0; j < numColumns; j++) {
				this.board[i][j].draw(g);
			}
		}
		g.setColor(Color.BLUE);
		g.drawString("Ballroom",70,70);
		g.drawString("Conservatory",490,710);
		g.drawString("Kitchen",70,350);
		g.drawString("Billiard Room",385,70);
		g.drawString("Library",280,710);
		g.drawString("Study",70,700);
		g.drawString("Dining Room",700,350);
		g.drawString("Lounge",735,70);
		g.drawString("Hall",750,710);
		
		g.setColor(human.getColor());
		g.fillOval(human.getPixelColumn(), human.getPixelRow(), human.getPixelHeight(), human.getPixelHeight());
		g.setColor(Color.BLACK);
		g.drawOval(human.getPixelColumn(), human.getPixelRow(), human.getPixelHeight(), human.getPixelHeight());
		
		
		for (int i = 0; i < computerPlayers.size(); i++) {
			g.setColor(computerPlayers.get(i).getColor());
			g.fillOval(computerPlayers.get(i).getPixelColumn(), computerPlayers.get(i).getPixelRow(), computerPlayers.get(i).getPixelHeight(), computerPlayers.get(i).getPixelHeight());
			g.setColor(Color.BLACK);
			g.drawOval(computerPlayers.get(i).getPixelColumn(), computerPlayers.get(i).getPixelRow(), computerPlayers.get(i).getPixelHeight(), computerPlayers.get(i).getPixelHeight());
		}
	}
	
	public boolean checkAccusation(Solution a) {
		if (a.person == personSolution && a.room == roomSolution && a.weapon == weaponSolution) {
			return true;
		}
		return false;
	}
	
	public void setSolution(Solution attempt) {
		this.personSolution = attempt.person;
		this.roomSolution = attempt.room;
		this.weaponSolution = attempt.weapon;
	}
	
	public void selectAnswer() {
		
	}
	public void setPlayers(HumanPlayer human, ArrayList<ComputerPlayer> computerPlayers)
	{
		this.human = human;
		this.computerPlayers = computerPlayers;
	}
	public void deal() {
		ArrayList<Card> dealDeck = deck;
		int indexOne;
		int indexTwo;
		int indexThree;
		int count = 0;
		
		Random rn = new Random();
		
		indexOne = rn.nextInt(9);
		indexTwo = rn.nextInt(9) + 9;
		indexThree = rn.nextInt(9) + 18;
		
		personSolution = dealDeck.get(indexOne).getCardName();
		dealDeck.remove(indexOne);
		personSolution = dealDeck.get(indexOne).getCardName();
		dealDeck.remove(indexOne);
		personSolution = dealDeck.get(indexOne).getCardName();
		dealDeck.remove(indexOne);
		
		while (dealDeck.size() != 0) {
			int index = rn.nextInt(dealDeck.size());
			if (count % 9 == 0) {
				human.getCardsInHand().add(dealDeck.get(index));
				dealDeck.remove(index);
			}
			else {
				computerPlayers.get((count-1) % 9).getCardsInHand().add(dealDeck.get(index));
				dealDeck.remove(index);
			}
			count++;
		}
	}
	public Card handleSuggestion(Solution suggestion, String accusingPlayer,BoardCell clicked) {
		ArrayList<Card> holder = new ArrayList<Card>();
		for (Player a: computerPlayers)
		{
			if(a.disproveSuggestion(suggestion) != null && !a.getPlayerName().equals(accusingPlayer))
				holder.add(a.disproveSuggestion(suggestion));
		}
		if (human.disproveSuggestion(suggestion) != null && !human.getPlayerName().equals(accusingPlayer)){
			holder.add(human.disproveSuggestion(suggestion));
		}
			
		if(holder.isEmpty()) return null;
		return holder.get(0);
		
	}
	public void loadConfigFiles() {
		//arraylist creation(s)
		computerPlayers = new ArrayList<ComputerPlayer>();
		deck = new ArrayList<Card>();
		FileReader reader = null;
		Scanner in = null;
		
		//People objects and people card creation
		try{
			reader = new FileReader(cardPlayerConfigFile);
			in = new Scanner(reader);
		}catch(FileNotFoundException e){
			System.out.println(e.getMessage());
		}
		String input = "";
		int count = 0;
		while (in.hasNextLine()) {
			input = in.nextLine();
			String[] array = input.split(",");
			Card newCard = new Card(array[0], CardType.PERSON);
			deck.add(newCard);
			String name = array[0];
			int row = Integer.parseInt(array[2]);
			int col = Integer.parseInt(array[3]);
			Color color;
			try {
			    Field field = Class.forName("java.awt.Color").getField(array[1]);
			    color = (Color)field.get(null);
			    if (count == 0) {
			    	human = new HumanPlayer(name,row,col,color);
			    }
			    else {
			    	ComputerPlayer tempPlayer = new ComputerPlayer(name,row,col,color);
				    computerPlayers.add(tempPlayer);
			    }
			} catch (Exception e) {
			    System.out.println("Color not valid on people config file.");
			}
			count++;
		}
		
		//Room cards added to deck
		try{
			reader = new FileReader(cardRoomsConfigFile);
			in = new Scanner(reader);
		}catch(FileNotFoundException e){
			System.out.println(e.getMessage());
		}
		while (in.hasNextLine()) {
			input = in.nextLine();
			Card newCard = new Card(input, CardType.ROOM);
			deck.add(newCard);
		}
		
		//Weapon cards added to deck
		try{
			reader = new FileReader(cardWeaponConfigFile);
			in = new Scanner(reader);
		}catch(FileNotFoundException e){
			System.out.println(e.getMessage());
		}
		while (in.hasNextLine()) {
			input = in.nextLine();
			Card newCard = new Card(input, CardType.WEAPON);
			deck.add(newCard);
		}
		
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
					if (j != 0) {
						if((board[i][j-1].getDoorDirection() == DoorDirection.RIGHT || board[i][j-1].getInitial() == 'W')){
							list.add(board[i][j-1]);
						}
						
					}
					if (j != numColumns-1) {
						if((board[i][j+1].getDoorDirection() == DoorDirection.LEFT || board[i][j+1].getInitial() == 'W')){
							list.add(board[i][j+1]);
						}
					}
					if (i != 0) {
						if((board[i-1][j].getDoorDirection() == DoorDirection.DOWN || board[i-1][j].getInitial() == 'W')){
							list.add(board[i-1][j]);
						}
					}
					if (i != numRows-1) {
						if((board[i+1][j].getDoorDirection() == DoorDirection.UP|| board[i+1][j].getInitial() == 'W')){
							list.add(board[i+1][j]);
						}
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
			if(numSteps == 1 || cell.isDoorway()) targets.add(cell);
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

	public int getNumRows() {
		return numRows;
	}

	public int getNumColumns() {
		return numColumns;
	}
	
	public HumanPlayer getHumanPlayer() {
		return human;
	}
	
	public ArrayList<Card> getDeck() {
		return deck;
	}
	
	public ArrayList<ComputerPlayer> getComputerPlayers() {
		return computerPlayers;
	}
	
}
