package BPJ_Programming_Examples.Sudoku.events;

import bpSourceCode.bp.Event;

/**
 * A base class for moves in the Sudoku game
 */
public class Move extends Event {
	public int row;
	public int col;
	public int boxRow;
	public int boxCol;
	public int number; 

	public Move(int row, int col, int number) {
		super();
		this.row = row;
		this.col = col;
		this.number = number;
		boxRow = row / 3;
		boxCol = col / 3; 
		
		
		
	}

	/**
	 * A string to display on the board to represent the occurrence of this
	 * event.
	 */
	public String displayString() {
		
		  
		return (""+number); 
	}

	
	@Override
    public String toString() {
		
		  
		return ("Move(" + row + "," + col + ")/" +number);
	}
	
	/*
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (this == obj) {	
			return true;
		}

		if (!getClass().isInstance(obj)) {
			return false;
		}
		Move other = (Move) obj;
		if (col != other.col) {
			return false;
		}
		if (row != other.row) {
			return false;
		}
		if (number != other.number){ 
			return false;
		}
		return true;
	}
	public boolean intersect(Move other) {
		if ((this.col == other.col)
				|| (this.row == other.row)
				|| ((this.boxRow == other.boxRow) && (this.boxCol == other.boxCol)))
			return true;
		else
			return false;
	} 

}
