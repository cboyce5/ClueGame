package ClueGame;

import java.awt.Color;
import java.awt.Graphics;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class ComputerPlayer extends Player{
	private BoardCell lastCell;
	private BoardCell nextCell;
	
	public ComputerPlayer(String playerName, int row, int column, Color color) {
		super(playerName, row, column, color);
		// TODO Auto-generated constructor stub
	}

	public BoardCell pickLocation(Set<BoardCell> targets) {
		for (BoardCell b:targets) {
			if (b.isRoom()) {
				if (b != lastCell && b.getInitial() != lastCell.getInitial()) {
					return b;
				}
			}
		}
		Random rnd = new Random();
		BoardCell[] newTargets = targets.toArray(new BoardCell[targets.size()]);
		int index = rnd.nextInt(targets.size());
		return newTargets[index];
	}
	
	public Solution makeSuggestion(Board board, BoardCell location) {
		String room = "";
		String person = "";
		String weapon = "";
		room = board.getRooms().get(location.getInitial());
		Random rnd = new Random();
		int index = rnd.nextInt(this.getCardsNotSeen().get(CardType.PERSON).size());
		person = this.getCardsNotSeen().get(CardType.PERSON).get(index).getCardName();
		index = rnd.nextInt(this.getCardsNotSeen().get(CardType.WEAPON).size());
		weapon = this.getCardsNotSeen().get(CardType.WEAPON).get(index).getCardName();
		Solution s = new Solution(person,room,weapon);
		return s;
	}
	
	public BoardCell getNextCell() {
		return nextCell;
	}
	
	public void makeMove(Board b, int r) {
		b.repaint();
		b.calcTargets(b.getCellAt(this.getRow(), this.getColumn()), r);
		this.nextCell = pickLocation(b.getTargets());
		this.setRow(nextCell.getRow());
		this.setColumn(nextCell.getColumn());
		b.repaint();
	}

	public BoardCell getLastCell() {
		return lastCell;
	}

	public void setLastCell(BoardCell lastCell) {
		this.lastCell = lastCell;
	}
	
	
}
