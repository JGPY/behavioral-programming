package BPJ_Programming_Examples.Tic_Tac_Toe.tictactoe.events;

/**
 * An event that is executed when player O makes a move.
 */
@SuppressWarnings("serial")
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
	 * @see tictactoe.events.Move#displayString()
	 */
	@Override
	public String displayString() {
		return "O";
	}

}
