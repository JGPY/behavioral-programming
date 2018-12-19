package BPJ_Programming_Examples.Sudoku.externalApp;

/**
 * Class that represents an ID of a cell in a 9 by 9 matrix
 */
public class Square {
	public int row;
	public int col;

	public Square(int row, int col) {
		this.row = row;
		this.col = col;
	}

	@Override
	public String toString() {
		return "<" + row + "," + col + ">";
	}
}
