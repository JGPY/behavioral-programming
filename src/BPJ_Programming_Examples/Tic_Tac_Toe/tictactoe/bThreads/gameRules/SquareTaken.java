package BPJ_Programming_Examples.Tic_Tac_Toe.tictactoe.bThreads.gameRules;

import static bp.BProgram.bp;
import static bp.BProgram.labelNextVerificationState;
import static bp.eventSets.EventSetConstants.none;

import java.util.HashSet;
import java.util.Set;

import BPJ_Programming_Examples.Tic_Tac_Toe.tictactoe.events.Move;
import BPJ_Programming_Examples.Tic_Tac_Toe.tictactoe.events.X;
import bp.BThread;
import bp.exceptions.BPJException;

/**
 * BThread for not allowing two symbols in the same square.
 */
@SuppressWarnings("serial")
public class SquareTaken extends BThread {
	private int row;
	private int col;

	@Override
	public void runBThread() throws BPJException {

		labelNextVerificationState("?(" + row + "," + col + ")");
		Move move = new Move(row, col);
		move.setName("(" + row + "," + col + ")");

		// Wait for any move for a given square
		bp.bSync(none, move, none);

		Move lastMove = (Move) bp.lastEvent;

		if (lastMove instanceof X)
			labelNextVerificationState("X(" + row + "," + col + ")");
		else
			labelNextVerificationState("O(" + row + "," + col + ")");

		bp.bSync(none, none, move);

	}

	public SquareTaken(int row, int col) {
		super("SquareTaken(" + row + "," + col + ")");
		this.row = row;
		this.col = col;

	}

	static public Set<BThread> constructInstances() {

		Set<BThread> set = new HashSet<BThread>();

		for (int row = 0; row < 3; row++) {
			for (int col = 0; col < 3; col++) {
				set.add(new SquareTaken(row, col));
			}
		}
		return set;
	}
}
