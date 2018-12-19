package BPJ_Programming_Examples.Tic_Tac_Toe.tictactoe.bThreads.gameRules;

import static bp.BProgram.bp;
import static bp.eventSets.EventSetConstants.none;

import java.util.HashSet;
import java.util.Set;

import BPJ_Programming_Examples.Tic_Tac_Toe.tictactoe.events.Move;
import bp.BThread;
import bp.exceptions.BPJException;

/**
 * BThread for not allowing two markings of the same square.
 */
@SuppressWarnings("serial")
public class SquareCanBeMarkedOnlyOnce extends BThread {
	private int row;
	private int col;

	@Override
	public void runBThread() throws BPJException {

		Move anyMoveInTheSquare = new Move(row, col);

		// Wait for any move for the given square
		bp.bSync(none, anyMoveInTheSquare, none);

		// Block further markings of the same square
		bp.bSync(none, none, anyMoveInTheSquare);

	}

	public SquareCanBeMarkedOnlyOnce(int row, int col) {
		super("SquareCanBeMarkedOnlyOnce(" + row + "," + col + ")");
		this.row = row;
		this.col = col;

	}

	static public Set<BThread> constructInstances() {

		Set<BThread> set = new HashSet<BThread>();

		for (int row = 0; row < 3; row++) {
			for (int col = 0; col < 3; col++) {
				set.add(new SquareCanBeMarkedOnlyOnce(row, col));
			}
		}
		return set;
	}
}
