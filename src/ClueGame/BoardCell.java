package ClueGame;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;

public class BoardCell {
	private int row;
	private int column;
	private char initial;
	private char secondInitial = 'N';
	private boolean isDoor;
	private DoorDirection direction = DoorDirection.NONE;
	private int pixelRow;
	private int pixelCol;
	private int pixelHeight = 25;
	private int pixelWidth = 25;
	

	public BoardCell(int row, int column, char initial) {
		super();
		this.row = row;
		this.column = column;
		this.initial = initial;
		this.pixelRow = row * pixelHeight;
		this.pixelCol = column * pixelWidth;
	}
	
	public boolean containsClick(int x, int y) {
		Rectangle r = new Rectangle(pixelCol, pixelRow, pixelHeight, pixelWidth);
		if (r.contains(new Point(x,y)))
			return true;
		return false;
	}
	
	public void draw(Graphics g) {
		
		if (isWalkway()) {
			g.setColor(Color.PINK);
			g.fillRect(pixelCol, pixelRow, pixelWidth, pixelHeight);
			g.setColor(Color.BLACK);
			g.drawRect(pixelCol, pixelRow, pixelWidth, pixelHeight);
		}
		else {
			g.setColor(Color.LIGHT_GRAY);
			g.fillRect(pixelCol, pixelRow, pixelWidth, pixelHeight);
		}
		if (isDoorway()) {
			g.setColor(Color.BLUE);
			switch (this.direction) {
				case UP:
					g.fillRect(pixelCol, pixelRow, pixelWidth, 5);
					break;
				case DOWN:
					g.fillRect(pixelCol, pixelRow+20, pixelWidth, 5);
					break;
				case RIGHT:
					g.fillRect(pixelCol+20, pixelRow, 5, pixelHeight);
					break;
				case LEFT:
					g.fillRect(pixelCol, pixelRow, 5, pixelHeight);
					break;
				default:
					break;
				
			}
		}
		
	}

	
	public boolean isWalkway(){
		if(initial=='W')
			return true;
		return false;
	}
	
	public boolean isRoom(){
		if (initial == 'W' || initial == 'X') {
			return false;
		}
		return true;
	}
	
	public boolean isDoorway(){
		if (isRoom() && direction != DoorDirection.NONE) {
			return true;
		}
		return false;
	}
	
	public DoorDirection getDoorDirection(){
		return direction;
	}
	
	public char getInitial(){
		return initial;
	}

	public void setDoor(boolean isDoor) {
		this.isDoor = isDoor;
	}

	public char getSecondInitial() {
		return secondInitial;
	}

	public void setSecondInitial(char secondInitial) {
		this.secondInitial = secondInitial;
	}
	
	public int getRow() {
		return row;
	}

	public int getColumn() {
		return column;
	}

	public void setDirection(char secondInitial){
		switch(secondInitial){
		case 'R':
			this.direction = DoorDirection.RIGHT;
			break;
		case 'D':
			this.direction = DoorDirection.DOWN;
			break;
		case 'L':
			this.direction = DoorDirection.LEFT;
			break;
		case 'U':
			this.direction = DoorDirection.UP;
			break;
		default: 
			this.direction = DoorDirection.NONE;
			break;
		}
	}

	@Override
	public String toString() {
		return "BoardCell [row=" + row + ", column=" + column + ", initial=" + initial + ", secondInitial="
				+ secondInitial + ", isDoor=" + isDoor + ", direction=" + direction + "]";
	}
	
	@Override
	public boolean equals(Object o) {
		if (o instanceof BoardCell) {
			if (this.getRow() == ((BoardCell)o).getRow() && this.getColumn() == ((BoardCell)o).getColumn()) {
				return true;
			}
		}
		return false;
	}

	
}
