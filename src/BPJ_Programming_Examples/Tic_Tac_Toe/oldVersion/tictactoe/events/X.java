package BPJ_Programming_Examples.Tic_Tac_Toe.oldVersion.tictactoe.events;

/**
 * An event that is fired when player X makes a move.
 */
public class X extends Move {

	/**
	 * Constructor.
	 */
	public X(int row, int col) {
		super(row, col);
		this.setName("X(" + row + "," + col + ")");
	}

	/**
	 * @see BPJ_Programming_Examples.Tic_Tac_Toe.oldVersion.tictactoe.events.Move#displayString()
	 */
	@Override
	public String displayString() {
		return "X";
	}

}
