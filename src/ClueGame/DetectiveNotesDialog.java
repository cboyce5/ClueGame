package ClueGame;

import java.awt.GridLayout;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class DetectiveNotesDialog extends JDialog{
	
	public DetectiveNotesDialog() {
		setTitle("Detective Notes");
		setSize(500,600);
		setLayout(new GridLayout(3,2));
		setUp();
	}
	
	public void setUp() {
		JPanel peoplePanel = new JPanel();
		peoplePanel.setLayout(new GridLayout(5,2));
		JCheckBox box1 = new JCheckBox("Miss Scarlet");
		JCheckBox box2 = new JCheckBox("Colonel Mustard");
		JCheckBox box3 = new JCheckBox("Mrs White");
		JCheckBox box4 = new JCheckBox("Mr Green");
		JCheckBox box5 = new JCheckBox("Mrs Peacock");
		JCheckBox box6 = new JCheckBox("Mr Sky");
		JCheckBox box7 = new JCheckBox("Mr Boddy");
		JCheckBox box8 = new JCheckBox("Professor Plum");
		JCheckBox box9 = new JCheckBox("Rusty Ryan");
		peoplePanel.add(box1);
		peoplePanel.add(box2);
		peoplePanel.add(box3);
		peoplePanel.add(box4);
		peoplePanel.add(box5);
		peoplePanel.add(box6);
		peoplePanel.add(box7);
		peoplePanel.add(box8);
		peoplePanel.add(box9);
		peoplePanel.setBorder(new TitledBorder(new EtchedBorder(), "People"));
		
		
		JPanel roomPanel = new JPanel();
		roomPanel.setLayout(new GridLayout(5,2));
		JCheckBox box11 = new JCheckBox("Conservatory");
		JCheckBox box21 = new JCheckBox("Kitchen");
		JCheckBox box31 = new JCheckBox("Ballroom");
		JCheckBox box41 = new JCheckBox("Billiard Room");
		JCheckBox box51 = new JCheckBox("Library");
		JCheckBox box61 = new JCheckBox("Study");
		JCheckBox box71 = new JCheckBox("Dining Room");
		JCheckBox box81 = new JCheckBox("Lounge");
		JCheckBox box91 = new JCheckBox("Hall");
		roomPanel.add(box11);
		roomPanel.add(box21);
		roomPanel.add(box31);
		roomPanel.add(box41);
		roomPanel.add(box51);
		roomPanel.add(box61);
		roomPanel.add(box71);
		roomPanel.add(box81);
		roomPanel.add(box91);
		roomPanel.setBorder(new TitledBorder(new EtchedBorder(), "Rooms"));
		
		JPanel weaponPanel = new JPanel();
		weaponPanel.setLayout(new GridLayout(5,2));
		JCheckBox box12 = new JCheckBox("Revolver");
		JCheckBox box22 = new JCheckBox("Candlestick");
		JCheckBox box32 = new JCheckBox("Wrench");
		JCheckBox box42 = new JCheckBox("Rope");
		JCheckBox box52 = new JCheckBox("Lead Pipe");
		JCheckBox box62 = new JCheckBox("Knife");
		JCheckBox box72 = new JCheckBox("Axe");
		JCheckBox box82 = new JCheckBox("Poison");
		JCheckBox box92 = new JCheckBox("Sword");
		weaponPanel.add(box12);
		weaponPanel.add(box22);
		weaponPanel.add(box32);
		weaponPanel.add(box42);
		weaponPanel.add(box52);
		weaponPanel.add(box62);
		weaponPanel.add(box72);
		weaponPanel.add(box82);
		weaponPanel.add(box92);
		weaponPanel.setBorder(new TitledBorder(new EtchedBorder(), "Weapons"));

		JPanel peopleGuess = new JPanel();
		peopleGuess.setLayout(new GridLayout(0,2));
		JComboBox<String> people = new JComboBox<String>();
		people.addItem("Miss Scarlet");
		people.addItem("Colonel Mustard");
		people.addItem("Mrs White");
		people.addItem("Mr Green");
		people.addItem("Mrs Peacock");
		people.addItem("Mr Sky");
		people.addItem("Mr Boddy");
		people.addItem("Professor Plum");
		people.addItem("Rusty Ryan");
		people.addItem("Unsure");
		peopleGuess.add(people);
		peopleGuess.setBorder(new TitledBorder(new EtchedBorder(), "People Guess"));
		
		JPanel roomGuess = new JPanel();
		roomGuess.setLayout(new GridLayout(0,2));
		JComboBox<String> rooms = new JComboBox<String>();
		rooms.addItem("Conservatory");
		rooms.addItem("Kitchen");
		rooms.addItem("Ballroom");
		rooms.addItem("Billiard Room");
		rooms.addItem("Library");
		rooms.addItem("Study");
		rooms.addItem("Dining Room");
		rooms.addItem("Lounge");
		rooms.addItem("Hall");
		rooms.addItem("Unsure");
		roomGuess.add(rooms);
		roomGuess.setBorder(new TitledBorder(new EtchedBorder(), "Room Guess"));
		
		JPanel weaponGuess = new JPanel();
		weaponGuess.setLayout(new GridLayout(0,2));
		JComboBox<String> weapon = new JComboBox<String>();
		weapon.addItem("Revolver");
		weapon.addItem("Candlestick");
		weapon.addItem("Wrench");
		weapon.addItem("Rope");
		weapon.addItem("Lead Pipe");
		weapon.addItem("Knife");
		weapon.addItem("Axe");
		weapon.addItem("Poison");
		weapon.addItem("Sword");
		weapon.addItem("Unsure");
		weaponGuess.add(weapon);
		weaponGuess.setBorder(new TitledBorder(new EtchedBorder(), "Weapon Guess"));
		
		add(peoplePanel);
		add(peopleGuess);
		add(roomPanel);
		add(roomGuess);
		add(weaponPanel);
		add(weaponGuess);
		
		
		
		
		
	}

}
