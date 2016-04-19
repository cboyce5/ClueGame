package ClueGame;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class ClueControlGUI extends JPanel{
	
	private JPanel diePanel;
	private JLabel dieLabel;
	private JTextField rollResult;
	private JPanel guessPanel;
	private JTextField guessResult;
	private JLabel guessLabel;
	private JTextField turnField;
	private JPanel resultPanel;
	private JLabel guessResultLabel;
	private JTextField guessResultText;
	private JButton nextPlayerButton;
	private JButton accusationButton;
	
	public ClueControlGUI() {
		setLayout();
		setLayout(new GridLayout(2,1));
	}
	
	private void setLayout() {
		//Die panel
		diePanel = new JPanel();
		dieLabel = new JLabel("Roll");
		rollResult = new JTextField(5);
		rollResult.setEditable(false);
		diePanel.add(dieLabel);
		diePanel.add(rollResult);
		diePanel.setBorder(new TitledBorder(new EtchedBorder(), "Die"));
		
		//Guess panel
		guessPanel = new JPanel();
		guessLabel = new JLabel("Guess");
		guessResult = new JTextField(30);
		guessResult.setEditable(false);
		guessPanel.add(guessLabel);
		guessPanel.add(guessResult);
		guessPanel.setBorder(new TitledBorder(new EtchedBorder(), "Guess"));
		
		//Guess Result panel
		resultPanel = new JPanel();
		guessResultLabel = new JLabel("Guess Result");
		guessResultText = new JTextField(10);
		guessResultText.setEditable(false);
		resultPanel.add(guessResultLabel);
		resultPanel.add(guessResultText);
		resultPanel.setBorder(new TitledBorder(new EtchedBorder(), "Guess Result"));
		
		//Top panel with label, text and 2 buttons
		JPanel topPanel = new JPanel();
		topPanel.setLayout(new GridLayout(1,3));
		nextPlayerButton = new JButton("Next Player");
		accusationButton = new JButton("Make an Accusation");
		JLabel whoseTurn = new JLabel("Whose Turn?");
		turnField = new JTextField(20);
		turnField.setEditable(false);
		JPanel turnPanel = new JPanel();
		turnPanel.setLayout(new GridLayout(2,1));
		turnPanel.add(whoseTurn);
		turnPanel.add(turnField);
		topPanel.add(turnPanel);
		topPanel.add(nextPlayerButton);
		topPanel.add(accusationButton);
		
		JPanel bottomPanel = new JPanel();;
		bottomPanel.add(diePanel);
		bottomPanel.add(guessPanel);
		bottomPanel.add(resultPanel);
		
		//Adding to JFrame
		add(topPanel);
		add(bottomPanel);

	}
	
	public void update(Player p,int roll, String solution, String card) {
		turnField.setText(p.getPlayerName());
		rollResult.setText(Integer.toString(roll));
		guessResult.setText(solution);
		guessResultText.setText(card);
		
	}

	public JButton getNextPlayerButton() {
		return nextPlayerButton;
	}

	public JButton getAccusationButton() {
		return accusationButton;
	}
	
	

}

