package BPJ_Programming_Examples.Tic_Tac_Toe.oldVersion.tictactoe.bThreads;

import static bpSourceCode.bp.eventSets.EventSetConstants.none;
import static BPJ_Programming_Examples.Tic_Tac_Toe.oldVersion.tictactoe.events.StaticEvents.gameOver;

import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import BPJ_Programming_Examples.Tic_Tac_Toe.oldVersion.tictactoe.events.Move;
import bpSourceCode.bp.BProgram;
import bpSourceCode.bp.BThread;
import bpSourceCode.bp.eventSets.EventSet;
import bpSourceCode.bp.exceptions.BPJException;

/**
 * BThread for not allowing two symbols in the same square.
 */
public class SquareTaken extends BThread {

	private static final Logger logger = LoggerFactory.getLogger(SquareTaken.class);

	private int row;
	private int col;

	@Override
	public void runBThread() throws BPJException {

		interruptingEvents = new EventSet(gameOver);
		BProgram bp = getBProgram();

		Move move = new Move(row, col);
		move.setName("(" + row + "," + col + ")");
		// Wait for any move for a given square
		logger.info("同步：bSync(none, move, none)");
		bp.bSync(none, move, none);
		logger.info("结束：bSync(none, move, none)");
		logger.info("同步：bSync(none, none, move");
		bp.bSync(none, none, move);
		logger.info("结束：bSync(none, none, move");

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
