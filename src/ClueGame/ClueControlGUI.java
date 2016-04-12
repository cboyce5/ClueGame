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
	
	public ClueControlGUI() {
		setLayout();
		setLayout(new GridLayout(2,3));
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
		JTextField guessResult = new JTextField(10);
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
		nextPlayerButton.addActionListener(new NextPlayerListener());
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
		
		JPanel bottomPanel = new JPanel();
		bottomPanel.setLayout(new GridLayout(1,3));
		bottomPanel.add(diePanel);
		bottomPanel.add(guessPanel);
		bottomPanel.add(resultPanel);
		
		//Adding to JFrame
		add(topPanel);
		add(bottomPanel);

	}
	
	public void update() {
		
	}
	
	private class NextPlayerListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e) {
			ClueGame.nextPlayer();
		}
	}

}

