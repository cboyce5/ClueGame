package ClueGame;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


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
	private static ClueControlGUI controlGUI;
	private DetectiveNotesDialog dialog;
	public static int NUM_ROOMS = 11;
	public static int NUM_ROWS = 25;
	public static int NUM_COLUMNS = 25;
	
	public ClueGame() {
		board = new Board("layout.csv", "ClueLegend.txt");
		board.initialize();
		controlGUI = new ClueControlGUI();
		setTitle("Clue Game");
		setSize(new Dimension(800,800));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		menuBar.add(createFileMenu());
		JPanel cardPanel = myCardPanel(board);
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
	
	private JPanel myCardPanel(Board b) {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(6,1));
		panel.setBorder(new TitledBorder(new EtchedBorder(), "My Cards"));
		
		JPanel people = new JPanel();
		people.setLayout(new GridLayout(1,1));
		people.setBorder(new TitledBorder(new EtchedBorder(), "People"));
		JTextField peopleCard = new JTextField();
		String s = "mr green";
		peopleCard.setText(s);
		people.add(peopleCard);
		
		JPanel room = new JPanel();
		room.setLayout(new GridLayout(1,1));
		room.setBorder(new TitledBorder(new EtchedBorder(), "Rooms"));
		JTextField roomCard = new JTextField();
		s = "dining room";
		roomCard.setText(s);
		room.add(roomCard);
		
		JPanel weapon = new JPanel();
		weapon.setLayout(new GridLayout(1,1));
		weapon.setBorder(new TitledBorder(new EtchedBorder(), "Weapons"));
		JTextField weaponCard = new JTextField();
		s = "pipe";
		weaponCard.setText(s);
		weapon.add(weaponCard);
		
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

	public static void main(String[] args) {
		ClueGame game = new ClueGame();
		game.setVisible(true);
		JOptionPane.showMessageDialog(game, "You are Miss Scarlet, press 'Next Player' to begin play", "Welcome to Clue",JOptionPane.INFORMATION_MESSAGE);
	}

}
