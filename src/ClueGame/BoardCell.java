package ClueGame;

public class BoardCell {
	private int row;
	private int column;
	private char initial;
	private char secondInitial = 'N';
	private boolean isDoor;
	private DoorDirection direction = DoorDirection.NONE;
	

	public BoardCell(int row, int column, char initial) {
		super();
		this.row = row;
		this.column = column;
		this.initial = initial;
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

	
}
