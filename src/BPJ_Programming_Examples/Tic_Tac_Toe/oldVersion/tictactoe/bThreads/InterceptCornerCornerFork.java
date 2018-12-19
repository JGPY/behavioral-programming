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
import bpSourceCode.bp.exceptions.BPJException;

// The scenario handles separately each of the first three steps of the game.
// If the game doesn't follow the moves prescribed by this strategy as risky,
// the scenario exits. If it does - it requests the above step.
// Note that this scenario does not keep track of the actual moves.

public class InterceptCornerCornerFork extends BThread {

	private static final Logger logger = LoggerFactory.getLogger(InterceptCornerCornerFork.class);

	@Override
	public void runBThread() throws BPJException {
		Move move;

		BProgram bp = getBProgram();

		// If the game ends - exit
		interruptingEvents = new EventSet(gameOver);

		// Watch for X's first move
		// If not a corner - exit (both row and col must be 0 or 2)
		logger.info("同步：bSync(none, new EventsOfClass(X.class), none)");
		bp.bSync(none, new EventsOfClass(X.class), none);
		logger.info("结束：bSync(none, new EventsOfClass(X.class), none)");
		move = (Move) bp.lastEvent;
		if (move.row == 1 || move.col == 1) {
			return;
		}

		// Watch O's first move (by another scenario)
		// If not center exit (both row and col must be 1)
		logger.info("同步：bSync(none, new EventsOfClass(O.class), none)");
		bp.bSync(none, new EventsOfClass(O.class), none);
		logger.info("结束：bSync(none, new EventsOfClass(O.class), none)");
		move = (Move) bp.lastEvent;
		if (move.row != 1 || move.col != 1) {
			return; // continue. only if center;
		}

		// Watch for X's second move. If not a corner - exit.
		// Not checking specifically for opposite corner -
		// adjacent corners are handled by the simple defense scenario.
		logger.info("同步：bSync(none, new EventsOfClass(X.class), none)");
		bp.bSync(none, new EventsOfClass(X.class), none);
		logger.info("结束：bSync(none, new EventsOfClass(X.class), none)");
		move = (Move) bp.lastEvent;
		if (move.row == 1 || move.col == 1) {
			return;
		}

		// Conditions met - now recommend interception,
		// watch for just one move and exit scenario.
		logger.info("同步：bSync(new O(0, 1), none, none)");
		bp.bSync(new O(0, 1), none, none);
		logger.info("结束：bSync(new O(0, 1), none, none)");
	}
}