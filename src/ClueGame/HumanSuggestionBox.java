package ClueGame;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;

public class HumanSuggestionBox extends JDialog{
	private JComboBox<String> room;
	private JComboBox<String> person;
	private JComboBox<String> weapon;
	public Solution solution;
	
	public HumanSuggestionBox(String roomName) {
		setTitle("Suggestion");
		setSize(300,300);
		setLayout(new GridLayout(4,2));
		setUp(roomName);
	}
	
	public void setUp(String s) {
		JLabel roomLabel = new JLabel("Your Room");
		JLabel personLabel = new JLabel("Person");
		JLabel weaponLabel = new JLabel("Weapon");
		
		room = new JComboBox<String>();
		room.addItem(s);
		room.setEditable(false);
		
		person = new JComboBox<String>();
		person.addItem("Miss Scarlet");
		person.addItem("Colonel Mustard");
		person.addItem("Mrs White");
		person.addItem("Mr Green");
		person.addItem("Mrs Peacock");
		person.addItem("Mr Sky");
		person.addItem("Mr Boddy");
		person.addItem("Professor Plum");
		person.addItem("Rusty Ryan");
		
		weapon = new JComboBox<String>();
		weapon.addItem("Revolver");
		weapon.addItem("Candlestick");
		weapon.addItem("Wrench");
		weapon.addItem("Rope");
		weapon.addItem("Lead Pipe");
		weapon.addItem("Knife");
		weapon.addItem("Axe");
		weapon.addItem("Poison");
		weapon.addItem("Sword");
		
		JButton submitButton = new JButton("Submit");
		submitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				solution = new Solution(person.getSelectedItem().toString(),room.getSelectedItem().toString(),weapon.getSelectedItem().toString());
				setVisible(false);
			}
		});
		
		JButton cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(e -> setVisible(false));

		
		add(roomLabel);
		add(room);
		add(personLabel);
		add(person);
		add(weaponLabel);
		add(weapon);
		add(submitButton);
		add(cancelButton);
	}

	
}
