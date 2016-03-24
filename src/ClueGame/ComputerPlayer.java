package ClueGame;

import java.awt.Color;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class ComputerPlayer extends Player{
	private BoardCell nextCell;
	public ComputerPlayer(String playerName, int row, int column, Color color) {
		super(playerName, row, column, color);
		// TODO Auto-generated constructor stub
	}
	public BoardCell getNextCell() {
		return nextCell;
	}
	public BoardCell pickLocation(Set<BoardCell> targets) {
		for (BoardCell b:targets) {
			if (b.isRoom()) {
				System.out.println(b);
				return b;
			}
		}
		Random rnd = new Random();
		BoardCell[] newTargets = targets.toArray(new BoardCell[targets.size()]);
		int index = rnd.nextInt(targets.size());
		return newTargets[index];
	}
	public Solution makeSuggestion(Board board, BoardCell location) {
		Solution s = new Solution("","","");
		return s;
	}
}
