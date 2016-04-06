package ClueGame;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class ClueControlGUI extends JFrame{
	
	public ClueControlGUI() {
		setTitle("Clue Control");
		setSize(700,200);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout();
	}
	
	private void setLayout() {
		//Die panel
		JPanel diePanel = new JPanel();
		JLabel dieLabel = new JLabel("Roll");
		JTextField rollResult = new JTextField(5);
		rollResult.setEditable(false);
		diePanel.add(dieLabel);
		diePanel.add(rollResult);
		diePanel.setBorder(new TitledBorder(new EtchedBorder(), "Die"));
		
		//Guess panel
		JPanel guessPanel = new JPanel();
		JLabel guessLabel = new JLabel("Guess");
		JTextField guessResult = new JTextField(30);
		guessResult.setEditable(false);
		guessPanel.add(guessLabel);
		guessPanel.add(guessResult);
		guessPanel.setBorder(new TitledBorder(new EtchedBorder(), "Guess"));
		
		//Guess Result panel
		JPanel resultPanel = new JPanel();
		JLabel guessResultLabel = new JLabel("Guess Result");
		JTextField guessResultText = new JTextField(10);
		guessResultText.setEditable(false);
		resultPanel.add(guessResultLabel);
		resultPanel.add(guessResultText);
		resultPanel.setBorder(new TitledBorder(new EtchedBorder(), "Guess"));
		
		//Top panel with label, text and 2 buttons
		JPanel topPanel = new JPanel();
		topPanel.setLayout(new GridLayout(1,3));
		JButton nextPlayerButton = new JButton("Next Player");
		JButton accusationButton = new JButton("Make an Accusation");
		JLabel whoseTurn = new JLabel("Whose Turn?");
		JTextField turnField = new JTextField(20);
		turnField.setEditable(false);
		JPanel turnPanel = new JPanel();
		turnPanel.setLayout(new GridLayout(2,1));
		turnPanel.add(whoseTurn);
		turnPanel.add(turnField);
		topPanel.add(turnPanel);
		topPanel.add(nextPlayerButton);
		topPanel.add(accusationButton);
		
		//Adding to JFrame
		add(topPanel,BorderLayout.NORTH);
		add(diePanel,BorderLayout.WEST);
		add(guessPanel, BorderLayout.CENTER);
		add(resultPanel, BorderLayout.EAST);

	}

}

