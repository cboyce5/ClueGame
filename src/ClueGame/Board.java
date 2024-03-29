package ClueGame;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
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

import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Board extends JPanel implements MouseListener{
	private Map<BoardCell, LinkedList<BoardCell>> adjMtx;
	private Set<BoardCell> visited;
	private Set<BoardCell> targets;
	private BoardCell[][] board;
	private int numRows;
	private int numColumns;
	private static Map<Character,String> rooms;
	private Map<String,BoardCell> roomDoorCell;
	public final static int BOARD_SIZE = 50;
	private String boardConfigFile;
	private String roomConfigFile;
	private ArrayList<String> listBoard;
	private ArrayList<Card> deck;
	private ArrayList<String> allRooms;
	
	private ArrayList<ComputerPlayer> computerPlayers;
	private HumanPlayer human;
	private String cardPlayerConfigFile = "people.txt";
	private String cardRoomsConfigFile = "rooms.txt";
	private String cardWeaponConfigFile = "weapons.txt";
	
	private String personSolution;
	private String roomSolution;
	private String weaponSolution;
	
	private boolean highlight;
	private boolean humanTurn = false;
	private boolean humanMustFinish = false;
	private boolean beginningHumanTurn = false;
	
	private int roll;
	


	private BoardCell humanTarget;
	private Solution humanSolution;
	private HumanSuggestionBox box;
	public Card returnCard;
	private ClueControlGUI controlGUI;
	private static int playerCount = 0;


	public Board(String layout, String legend){
		super();
		this.boardConfigFile = layout;
		this.roomConfigFile = legend;
		addMouseListener(this);
	}

	public Board(){
		super();
		this.boardConfigFile = "ClueLayout.csv";
		this.roomConfigFile = "ClueLegend.txt";
		addMouseListener(this);
	}
	
	public void nextPlayer() {
		if (!isHumanMustFinish()) {
			Random rn = new Random();
			roll = rn.nextInt(6) + 1;
			if (playerCount % 9 == 0) {
				setBeginningHumanTurn(true);
				HumanPlayer human = this.getHumanPlayer();
				setHumanMustFinish(true);
				human.makeMove(this, roll);
				
				if (this.getHumanSolution() != null) {
					
					
					if (this.getHumanSolution().person == human.getPlayerName()) {
						this.getHumanPlayer().setColumn(this.getRoomDoorCell().get(this.getHumanSolution().room).getColumn());
						this.getHumanPlayer().setRow(this.getRoomDoorCell().get(this.getHumanSolution().room).getRow());
						this.repaint();
					}
					else {
						int index = 0;
						for (int i= 0; i < getComputerPlayers().size(); i++) {
							if (this.getHumanSolution().person == this.getComputerPlayers().get(i).getPlayerName()) {
								index = i;
								break;
							}
						}
						this.getComputerPlayers().get(index).setColumn(this.getRoomDoorCell().get(this.getHumanSolution().room).getColumn());
						this.getComputerPlayers().get(index).setRow(this.getRoomDoorCell().get(this.getHumanSolution().room).getRow());
						this.repaint();
					}
					
					
				}
				else {
					controlGUI.update(this.getHumanPlayer(), roll,"","");
				}
				

			} else {
				
				ComputerPlayer player = this.getComputerPlayers().get(playerCount % 8);
				player.setLastCell(this.getCellAt(player.getColumn(), player.getRow()));
				if (player.knowAnswer) {				
					JOptionPane.showMessageDialog(this, "Computer Accusation: " + player.finalAnswer + ", Result: " + this.checkAccusation(player.finalAnswer), "Computer Solution Result",JOptionPane.INFORMATION_MESSAGE);					
				}
				player.makeMove(this, roll);
				if (player.getNextCell().isRoom()) {
					Solution playerSolution = player.makeSuggestion(this, player.getNextCell());
					Card c = this.handleSuggestion(playerSolution, player.getPlayerName(), player.getNextCell());
					if (c != null) {
						player.updateCardsNotSeen(c);
						controlGUI.update(player, roll, playerSolution.toString(), c.getCardName());
						for (int i = 0; i < this.getComputerPlayers().size(); i++) {
							System.out.println(this.getComputerPlayers().get(i).getCardsNotSeen());
						}
					}
					else {
						controlGUI.update(player, roll, playerSolution.toString(), "No New Clue");
						player.finalAnswer = playerSolution;
						player.knowAnswer = true;
					}
					if (playerSolution.person == this.getHumanPlayer().getPlayerName()) {
						this.getHumanPlayer().setColumn(this.getRoomDoorCell().get(playerSolution.room).getColumn());
						this.getHumanPlayer().setRow(this.getRoomDoorCell().get(playerSolution.room).getRow());
						this.repaint();
					}
					else {
						int index = 0;
						for (int i= 0; i < this.getComputerPlayers().size(); i++) {
							if (playerSolution.person == this.getComputerPlayers().get(i).getPlayerName()) {
								index = i;
								break;
							}
						}
						this.getComputerPlayers().get(index).setColumn(this.getRoomDoorCell().get(playerSolution.room).getColumn());
						this.getComputerPlayers().get(index).setRow(this.getRoomDoorCell().get(playerSolution.room).getRow());
						this.repaint();
					}
				}
				else {
					controlGUI.update(player, roll, "","");
				}
				
			}
			playerCount++;
		}
		else {
			JOptionPane.showMessageDialog(this, "You must finish your turn", "Error",JOptionPane.INFORMATION_MESSAGE);
		}
		
		
		
	}
	
	public void accusation() {
		if (playerCount % 9 == 1 && this.beginningHumanTurn) {
			HumanSuggestionBox box = new HumanSuggestionBox(this.getAllRooms());
			box.setVisible(true);
			if (this.checkAccusation(box.solution)) {
				JOptionPane.showMessageDialog(this, "You are correct!", "Game Over",JOptionPane.INFORMATION_MESSAGE);
			}
			else {
				this.humanTurn = false;
				this.humanMustFinish = false;
				this.beginningHumanTurn = false;
				this.repaint();
				JOptionPane.showMessageDialog(this, "Sorry, that is not correct", "",JOptionPane.INFORMATION_MESSAGE);
			}
		}
		else {
			JOptionPane.showMessageDialog(this, "It is not your turn", "Error",JOptionPane.INFORMATION_MESSAGE);
		}
	}
	
	
	public void mouseClicked(MouseEvent e) {	
		
		if (humanTurn == true) {
			beginningHumanTurn = false;
			humanTarget = null;
			for (BoardCell a: targets) {
				if (a.containsClick(e.getX(), e.getY())){
					humanTarget = a;
					break;
				}
			
			}
			if (humanTarget != null) {
				this.getHumanPlayer().setRow(humanTarget.getRow());
				this.getHumanPlayer().setColumn(humanTarget.getColumn());
				
				
				if (this.getCellAt(human.getRow(), human.getColumn()).isRoom()) {
					ArrayList<String> temp = new ArrayList<String>();
					temp.add((this.getRooms().get(this.getCellAt(human.getRow(), human.getColumn()).getInitial())));
					box = new HumanSuggestionBox(temp);
					box.setVisible(true);
					this.humanSolution = box.solution;
					returnCard = this.handleSuggestion(this.humanSolution, human.getPlayerName(), this.getCellAt(human.getRow(), human.getColumn()));
					if (this.returnCard !=  null) {
						controlGUI.update(this.getHumanPlayer(), roll, this.getHumanSolution().toString(),this.returnCard.getCardName());
					}
					else {
						controlGUI.update(this.getHumanPlayer(), roll,this.getHumanSolution().toString(),"No New Clue");
					}
				}
				
				
				this.repaint();
				humanTurn = false; 
				humanMustFinish = false;
				
			}
			else
				JOptionPane.showMessageDialog(this, "Invalid Target.", "Error", JOptionPane.INFORMATION_MESSAGE);
				
			
		}
		
		
	}
	
	public void mouseEntered(MouseEvent arg0) {}
	public void mouseExited(MouseEvent arg0) {}
	public void mousePressed(MouseEvent arg0) {}
	public void mouseReleased(MouseEvent arg0) {}
		
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		for (int i = 0; i < numRows; i++) {
			for (int j = 0; j < numColumns; j++) {
				this.board[i][j].draw(g);
			}
		}
		g.setColor(Color.BLUE);
		g.drawString("Ballroom",50,50);
		g.drawString("Conservatory",335,500);
		g.drawString("Kitchen",50,250);
		g.drawString("Billiard Room",275,50);
		g.drawString("Library",200,500);
		g.drawString("Study",50,500);
		g.drawString("Dining Room",500,250);
		g.drawString("Lounge",525,50);
		g.drawString("Hall",525,500);
		
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
		if (this.highlight == true) {
			g.setColor(Color.CYAN);
			for (BoardCell a: targets) {
				g.fillRect(a.getColumn()*25, a.getRow()*25, 25, 25);
			}
			this.highlight = false;
		}
	}
	
	public boolean checkAccusation(Solution a) {
		if (personSolution.equals(a.person) && roomSolution.equals(a.room) && weaponSolution.equals(a.weapon)) {
			System.out.println("true");
			return true;
		}
		System.out.println("false");
		return false;
	}
	
	public void deal() {
		ArrayList<Card> dealDeck = deck;
		int indexOne;
		int indexTwo;
		int indexThree;
		int count = 0;
		
		Random rn = new Random();
		
		ArrayList<Card> r = new ArrayList<Card>();
		ArrayList<Card> p = new ArrayList<Card>();
		ArrayList<Card> w = new ArrayList<Card>();
		for (int i = 0; i < 9; i++) {
			p.add(deck.get(i));
		}
		for (int i = 9; i < 18; i++) {
			r.add(deck.get(i));
		}
		for (int i = 18; i < 27; i++) {
			w.add(deck.get(i));
		}
		HashMap<CardType,ArrayList<Card>> t = new HashMap<CardType,ArrayList<Card>>();
		t.put(CardType.PERSON, p);
		t.put(CardType.ROOM, r);
		t.put(CardType.WEAPON, w);
		
		for (int i = 0; i< this.getComputerPlayers().size(); i++) {
			this.getComputerPlayers().get(i).setCardsNotSeen(t);
		}
		
		indexOne = rn.nextInt(9);
		indexTwo = rn.nextInt(9) + 8;
		indexThree = rn.nextInt(9) + 16;
		
		personSolution = dealDeck.get(indexOne).getCardName();
		dealDeck.remove(indexOne);
		roomSolution = dealDeck.get(indexTwo).getCardName();
		dealDeck.remove(indexTwo);
		weaponSolution = dealDeck.get(indexThree).getCardName();
		dealDeck.remove(indexThree);
		
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
		int index = 0;
		
		for (int i = 0; i < computerPlayers.size(); i++) {
			if (computerPlayers.get(i).getPlayerName() == accusingPlayer) {
				index = i;
			}
		}
		
		for(int i = index + 1; i < computerPlayers.size(); i++){
			
			if(computerPlayers.get(i).disproveSuggestion(suggestion) != null && !computerPlayers.get(i).getPlayerName().equals(accusingPlayer))
				holder.add(computerPlayers.get(i).disproveSuggestion(suggestion));
		}
		
		if (human.disproveSuggestion(suggestion) != null && !human.getPlayerName().equals(accusingPlayer)){
			holder.add(human.disproveSuggestion(suggestion));
		}
		
		for (int i = 0; i < index; i++){
			if(computerPlayers.get(i).disproveSuggestion(suggestion) != null && !computerPlayers.get(i).getPlayerName().equals(accusingPlayer))
				holder.add(computerPlayers.get(i).disproveSuggestion(suggestion));
		}
		
		if(holder.isEmpty()) 
			return null;
		return holder.get(0);
	}
	
	public void loadCardConfig() {
		//arraylist creation(s)
		allRooms = new ArrayList<String>();
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
			allRooms.add(newCard.getCardName());
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
		controlGUI = new ClueControlGUI();
		this.highlight = false;
		try {
			loadRoomConfig();
			loadBoardConfig();
			loadCardConfig();
			calcAdjacencies();
			
		}
		catch(BadConfigFormatException e){
			System.out.println(e.getMessage());
		}
		this.roomDoorCell = new HashMap<String,BoardCell>();
		this.roomDoorCell.put("Ballroom",this.board[4][5]);
		this.roomDoorCell.put("Billiard room",this.board[3][10]);
		this.roomDoorCell.put("Lounge",this.board[3][20]);
		this.roomDoorCell.put("Kitchen", this.board[13][2]);
		this.roomDoorCell.put("Dining room", this.board[11][21]);
		this.roomDoorCell.put("Study",this.board[18][5]);
		this.roomDoorCell.put("Library",this.board[20][7]);
		this.roomDoorCell.put("Conservatory",this.board[16][14]);
		this.roomDoorCell.put("Hall", this.board[20][18]);
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
    
    //Necessary getters and setters
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
	
	public void setSolution(Solution attempt) {
		this.personSolution = attempt.person;
		this.roomSolution = attempt.room;
		this.weaponSolution = attempt.weapon;
	}
	
	public void setPlayers(HumanPlayer human, ArrayList<ComputerPlayer> computerPlayers) {
		this.human = human;
		this.computerPlayers = computerPlayers;
	}
	
	public boolean isHighlight() {
		return highlight;
	}

	public void setHighlight(boolean highlight) {
		this.highlight = highlight;
	}

	public BoardCell getHumanTarget() {
		return humanTarget;
	}

	public Solution getHumanSolution() {
		return humanSolution;
	}

	
	public void setHumanSolution(Solution humanSolution) {
		this.humanSolution = humanSolution;
	}

	public Map<String, BoardCell> getRoomDoorCell() {
		return roomDoorCell;
	}

	public HumanSuggestionBox getBox() {
		return box;
	}

	public ArrayList<String> getAllRooms() {
		return allRooms;
	}

	public ClueControlGUI getControlGUI() {
		return controlGUI;
	}
	
	public boolean isHumanTurn() {
		return humanTurn;
	}

	public void setHumanTurn(boolean h) {
		humanTurn = h;
	}

	public boolean isHumanMustFinish() {
		return humanMustFinish;
	}

	public void setHumanMustFinish(boolean h) {
		humanMustFinish = h;
	}

	public boolean isBeginningHumanTurn() {
		return beginningHumanTurn;
	}

	public void setBeginningHumanTurn(boolean b) {
		beginningHumanTurn = b;
	}
}
