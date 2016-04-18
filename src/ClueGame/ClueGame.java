package ClueGame;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class ClueGame extends JFrame{
	private static Board board;
	private ArrayList<Card> humanCards;
	private ArrayList<Card> peopleCards;
	private ArrayList<Card> roomCards;
	private ArrayList<Card> weaponCards;
	
	private static ClueControlGUI controlGUI;
	private DetectiveNotesDialog dialog;
	public static int NUM_ROOMS = 11;
	public static int NUM_ROWS = 25;
	public static int NUM_COLUMNS = 25;
	
	private static int playerCount = 0;
	
	public ClueGame() {
		board = new Board("layout.csv", "ClueLegend.txt");
		board.initialize();
		board.deal();
		
		
		
		controlGUI = new ClueControlGUI();
		setTitle("Clue Game");
		peopleCards = new ArrayList<Card>();
		roomCards = new ArrayList<Card>();
		weaponCards = new ArrayList<Card>();
		humanCards = new ArrayList<Card>();
		humanCards = board.getHumanPlayer().getCardsInHand();
		
		for(int i = 0; i < humanCards.size(); i++){
			if(humanCards.get(i).getCardType() == CardType.PERSON){
				peopleCards.add(humanCards.get(i));
			}
				
			if(humanCards.get(i).getCardType() == CardType.ROOM){
				roomCards.add(humanCards.get(i));
			}
					
			if(humanCards.get(i).getCardType() == CardType.WEAPON){
				weaponCards.add(humanCards.get(i));
				
			}
		}
		
		setSize(new Dimension(800,800));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		menuBar.add(createFileMenu());
		JPanel cardPanel = myCardPanel();
		add(cardPanel, BorderLayout.EAST);
		add(board, BorderLayout.CENTER);
		add(controlGUI,BorderLayout.SOUTH);
	}
	
	private JMenu createFileMenu() {
		JMenu menu = new JMenu("File");
		menu.add(createNotesItem());
		menu.add(createFileExitItem());
		return menu;
	}
	
	private JPanel myCardPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(6,1));
		panel.setBorder(new TitledBorder(new EtchedBorder(), "My Cards"));
		
		JPanel people = new JPanel();
		people.setLayout(new GridLayout(peopleCards.size(),1));
		people.setBorder(new TitledBorder(new EtchedBorder(), "People"));
		JTextField peopleCard;
		String s;
		for (int i = 0; i < peopleCards.size(); i++) {
			peopleCard = new JTextField();
			s = peopleCards.get(i).getCardName();
			peopleCard.setText(s);
			people.add(peopleCard);
		}
		
		JPanel room = new JPanel();
		room.setLayout(new GridLayout(roomCards.size(),1));
		room.setBorder(new TitledBorder(new EtchedBorder(), "Rooms"));
		for (int i = 0; i < roomCards.size(); i++) {
			JTextField roomCard = new JTextField();
			s = roomCards.get(i).getCardName();
			roomCard.setText(s);
			room.add(roomCard);
		}
		
		JPanel weapon = new JPanel();
		weapon.setLayout(new GridLayout(weaponCards.size(),1));
		weapon.setBorder(new TitledBorder(new EtchedBorder(), "Weapons"));
		for (int i = 0; i < weaponCards.size(); i++) {
			JTextField weaponCard = new JTextField();
			s = weaponCards.get(i).getCardName();
			weaponCard.setText(s);
			weapon.add(weaponCard);
		}
		panel.add(people);
		panel.add(room);
		panel.add(weapon);
		return panel;
	}
	
	private JMenuItem createNotesItem() {
		JMenuItem item = new JMenuItem("Show Notes");
		DetectiveNotesDialog dialog = new DetectiveNotesDialog();
		class MenuItemListener implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				dialog.setVisible(true);
				
			}
		}
		item.addActionListener(new MenuItemListener());
		return item;
	}
	
	private JMenuItem createFileExitItem() {
		JMenuItem item = new JMenuItem("Exit");
		class MenuItemListener implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		}
		item.addActionListener(new MenuItemListener());
		return item;
	}
	
	public static void nextPlayer() {
		if (!board.humanMustFinish) {
			Random rn = new Random();
			int roll = rn.nextInt(6) + 1;
			if (playerCount % 9 == 0) {
				board.beginningHumanTurn = true;
				HumanPlayer human = board.getHumanPlayer();
				board.humanMustFinish = true;
				human.makeMove(board, roll);
				System.out.println(board.getHumanSolution());
				
				if (board.getHumanSolution() != null) {
					
					if (board.returnCard !=  null) {
						controlGUI.update(board.getHumanPlayer(), roll, board.getHumanSolution().toString(),board.returnCard.getCardName());
					}
					else {
						controlGUI.update(board.getHumanPlayer(), roll,board.getHumanSolution().toString(),"No New Clue");
					}
					if (board.getHumanSolution().person == human.getPlayerName()) {
						board.getHumanPlayer().setColumn(board.getRoomDoorCell().get(board.getHumanSolution().room).getColumn());
						board.getHumanPlayer().setRow(board.getRoomDoorCell().get(board.getHumanSolution().room).getRow());
						board.repaint();
					}
					else {
						int index = 0;
						for (int i= 0; i < board.getComputerPlayers().size(); i++) {
							if (board.getHumanSolution().person == board.getComputerPlayers().get(i).getPlayerName()) {
								index = i;
								break;
							}
						}
						board.getComputerPlayers().get(index).setColumn(board.getRoomDoorCell().get(board.getHumanSolution().room).getColumn());
						board.getComputerPlayers().get(index).setRow(board.getRoomDoorCell().get(board.getHumanSolution().room).getRow());
						board.repaint();
					}
					
					
				}
				else {
					controlGUI.update(board.getHumanPlayer(), roll,"","");
				}
				

			} else {
				
				ComputerPlayer player = board.getComputerPlayers().get(playerCount % 8);
				player.setLastCell(board.getCellAt(player.getColumn(), player.getRow()));
				player.makeMove(board, roll);
				if (player.getNextCell().isRoom()) {
					Solution playerSolution = player.makeSuggestion(board, player.getNextCell());
					Card c = board.handleSuggestion(playerSolution, player.getPlayerName(), player.getNextCell());
					if (c != null) {
						player.updateCardsNotSeen(c);
						controlGUI.update(player, roll, playerSolution.toString(), c.getCardName());
					}
					else {
						controlGUI.update(player, roll, playerSolution.toString(), "No New Clue");
					}
					if (playerSolution.person == board.getHumanPlayer().getPlayerName()) {
						board.getHumanPlayer().setColumn(board.getRoomDoorCell().get(playerSolution.room).getColumn());
						board.getHumanPlayer().setRow(board.getRoomDoorCell().get(playerSolution.room).getRow());
						board.repaint();
					}
					else {
						int index = 0;
						for (int i= 0; i < board.getComputerPlayers().size(); i++) {
							if (playerSolution.person == board.getComputerPlayers().get(i).getPlayerName()) {
								index = i;
								break;
							}
						}
						board.getComputerPlayers().get(index).setColumn(board.getRoomDoorCell().get(playerSolution.room).getColumn());
						board.getComputerPlayers().get(index).setRow(board.getRoomDoorCell().get(playerSolution.room).getRow());
						board.repaint();
					}
				}
				else {
					controlGUI.update(player, roll, "","");
				}
				
			}
			playerCount++;
		}
		else {
			JOptionPane.showMessageDialog(board, "You must finish your turn", "Error",JOptionPane.INFORMATION_MESSAGE);
		}
		
		
		
	}

	
	public static void accusation() {
		System.out.println(playerCount);
		System.out.println(board.beginningHumanTurn);
		if (playerCount % 9 == 1 && board.beginningHumanTurn) {
			HumanSuggestionBox box = new HumanSuggestionBox(board.getAllRooms());
			box.setVisible(true);
			if (board.checkAccusation(box.solution)) {
				JOptionPane.showMessageDialog(board, "You are correct!", "Game Over",JOptionPane.INFORMATION_MESSAGE);
			}
			else {
				JOptionPane.showMessageDialog(board, "Sorry, that is not correct", "",JOptionPane.INFORMATION_MESSAGE);
			}
		}
		else {
			JOptionPane.showMessageDialog(board, "It is not your turn", "Error",JOptionPane.INFORMATION_MESSAGE);
		}
	}
	
	public static void main(String[] args) {
		ClueGame game = new ClueGame();
		game.setVisible(true);
		JOptionPane.showMessageDialog(game, "You are Miss Scarlet, press 'Next Player' to begin play", "Welcome to Clue",JOptionPane.INFORMATION_MESSAGE);
	}

}
