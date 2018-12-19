package BPJ_Programming_Examples.Tic_Tac_Toe.oldVersion.tictactoe.events;

/**
 * An event that is executed when player O makes a move.
 */
public class O extends Move {
	/**
	 * Constructor.
	 */
	public O(int row, int col) {
		super(row, col);
		this.setName("O(" + row + "," + col + ")");
	}

	/**
	 * @see Object#toString()
	 */
	/**
	 * @see BPJ_Programming_Examples.Tic_Tac_Toe.oldVersion.tictactoe.events.Move#displayString()
	 */
	@Override
	public String displayString() {
		return "O";
	}

}
