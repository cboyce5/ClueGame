package clueGame;

import java.awt.Color;
import java.util.HashSet;
import java.util.Set;

public class ComputerPlayer extends Player{
	public ComputerPlayer(String playerName, int row, int column, Color color) {
		super(playerName, row, column, color);
		// TODO Auto-generated constructor stub
	}
	public BoardCell pickLocation(Set<BoardCell> targets) {
		BoardCell b = new BoardCell(1,1,'c');	
		return b;
	}
	public void makeAccustation() {
		return;
	}
	public void makeSuggestion(Board board, BoardCell location) {
		return;
	}
}
