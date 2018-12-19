package BPJ_Programming_Examples.Tic_Tac_Toe.oldVersion.tictactoe.bThreads;

import static bpSourceCode.bp.eventSets.EventSetConstants.none;
import static BPJ_Programming_Examples.Tic_Tac_Toe.oldVersion.tictactoe.events.StaticEvents.gameOver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import BPJ_Programming_Examples.Tic_Tac_Toe.oldVersion.tictactoe.events.Move;
import BPJ_Programming_Examples.Tic_Tac_Toe.oldVersion.tictactoe.events.O;
import BPJ_Programming_Examples.Tic_Tac_Toe.oldVersion.tictactoe.events.X;
import bpSourceCode.bp.BProgram;
import bpSourceCode.bp.BThread;
import bpSourceCode.bp.eventSets.EventSet;
import bpSourceCode.bp.eventSets.EventsOfClass;
import bpSourceCode.bp.eventSets.RequestableEventSet;
import bpSourceCode.bp.exceptions.BPJException;
/**
 * BThread that finds a fork - two X's that are not in the same row and column,
 * and place an "O" in the corner of this intersection. This is a simplified
 * scenario - it assumes that the center is already taken, so diagonals can't
 * participate, and it intercepts the forks only in corners. It doesn't even
 * check where "O" or "X" locations are.
 *
 */
public class InterceptCornerEdgeFork extends BThread {

	private static final Logger logger = LoggerFactory.getLogger(InterceptCornerEdgeFork.class);

	@Override
	public void runBThread() throws BPJException {
		BProgram bp = getBProgram();  
		int i;
		int j;
		boolean[] candRows = { true, true, true }; // all rows are fork
		// candidates
		boolean[] candCols = { true, true, true }; // all cols are fork
		// candidates
		O[] forkPoints = { new O(0, 0), new O(0, 2), new O(2, 0), new O(2, 2) };
		Move move;
		// keep local copy of X's squares
		boolean[][] XSq = { { false, false, false }, { false, false, false }, { false, false, false } };
		interruptingEvents = new EventSet(gameOver);
		EventsOfClass moveFilter = new EventsOfClass(Move.class);
		RequestableEventSet intercepts = new RequestableEventSet();
		while (true) {
			logger.info("同步：bSync(intercepts,moveFilter,none)");
			bp.bSync(intercepts,moveFilter,none);
			logger.info("结束：bSync(intercepts,moveFilter,none)");
			move = (Move) bp.lastEvent;
			if (move instanceof X) {
				XSq[move.row][move.col] = true; // mark X's new location
				// check if previous, different, X's created candidate forks
				findFork: for (i = 0; i <= 2; i++) {
					for (j = 0; j <= 2; j++) {
						if (XSq[i][j] && i != move.row && j != move.col) {
							for (O p : forkPoints) {
								if ((p.row == move.row && p.col == j && candRows[p.row] && candCols[j])
										|| (p.col == move.col && p.row == i && candCols[p.col] && candRows[i])) {
									intercepts.add(p);
									break findFork;
								} // handled a fork point
							} // end loop of all possible fork points for this
								// other X
						} // end handling of this other X
					}
				} // loops on all other X's
			} // end of case of an X move
			else { // it was an O move - where O went, X can't fork
				candRows[move.row] = false;
				candCols[move.col] = false;
			} // end O move case
		} // end of infinite loop
	} // end runScenario method
} // end class
