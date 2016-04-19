package ClueGame;

import java.awt.Color;

public class HumanPlayer extends Player{

	public HumanPlayer(String playerName, int row, int column, Color color) {
		super(playerName, row, column, color);
	}
	
	public void makeMove(Board b, int r) {
		b.calcTargets(b.getCellAt(this.getRow(), this.getColumn()), r);
		b.setHighlight(true);
		b.repaint();
		b.setHumanTurn(true);
	}

	
}
