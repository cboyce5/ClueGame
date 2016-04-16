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
		JCheckBox scarletBox = new JCheckBox("Miss Scarlet");
		JCheckBox mustardBox = new JCheckBox("Colonel Mustard");
		JCheckBox whiteBox = new JCheckBox("Mrs White");
		JCheckBox greenBox = new JCheckBox("Mr Green");
		JCheckBox peacockBox = new JCheckBox("Mrs Peacock");
		JCheckBox skyBox = new JCheckBox("Mr Sky");
		JCheckBox boddyBox = new JCheckBox("Mr Boddy");
		JCheckBox plumBox = new JCheckBox("Professor Plum");
		JCheckBox ryanBox = new JCheckBox("Rusty Ryan");
		peoplePanel.add(scarletBox);
		peoplePanel.add(mustardBox);
		peoplePanel.add(whiteBox);
		peoplePanel.add(greenBox);
		peoplePanel.add(peacockBox);
		peoplePanel.add(skyBox);
		peoplePanel.add(boddyBox);
		peoplePanel.add(plumBox);
		peoplePanel.add(ryanBox);
		peoplePanel.setBorder(new TitledBorder(new EtchedBorder(), "People"));
		
		
		JPanel roomPanel = new JPanel();
		roomPanel.setLayout(new GridLayout(5,2));
		JCheckBox consBox = new JCheckBox("Conservatory");
		JCheckBox kitchenBox = new JCheckBox("Kitchen");
		JCheckBox ballBox = new JCheckBox("Ballroom");
		JCheckBox billiardBox = new JCheckBox("Billiard Room");
		JCheckBox libraryBox = new JCheckBox("Library");
		JCheckBox studyBox = new JCheckBox("Study");
		JCheckBox diningBox = new JCheckBox("Dining Room");
		JCheckBox loungeBox = new JCheckBox("Lounge");
		JCheckBox hallBox = new JCheckBox("Hall");
		roomPanel.add(consBox);
		roomPanel.add(kitchenBox);
		roomPanel.add(ballBox);
		roomPanel.add(billiardBox);
		roomPanel.add(libraryBox);
		roomPanel.add(studyBox);
		roomPanel.add(diningBox);
		roomPanel.add(loungeBox);
		roomPanel.add(hallBox);
		roomPanel.setBorder(new TitledBorder(new EtchedBorder(), "Rooms"));
		
		JPanel weaponPanel = new JPanel();
		weaponPanel.setLayout(new GridLayout(5,2));
		JCheckBox revolverBox = new JCheckBox("Revolver");
		JCheckBox candleBox = new JCheckBox("Candlestick");
		JCheckBox wrenchBox = new JCheckBox("Wrench");
		JCheckBox ropeBox = new JCheckBox("Rope");
		JCheckBox pipeBox = new JCheckBox("Lead Pipe");
		JCheckBox knifeBox = new JCheckBox("Knife");
		JCheckBox axeBox = new JCheckBox("Axe");
		JCheckBox poisonBox = new JCheckBox("Poison");
		JCheckBox swordBox = new JCheckBox("Sword");
		weaponPanel.add(revolverBox);
		weaponPanel.add(candleBox);
		weaponPanel.add(wrenchBox);
		weaponPanel.add(ropeBox);
		weaponPanel.add(pipeBox);
		weaponPanel.add(knifeBox);
		weaponPanel.add(axeBox);
		weaponPanel.add(poisonBox);
		weaponPanel.add(swordBox);
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
