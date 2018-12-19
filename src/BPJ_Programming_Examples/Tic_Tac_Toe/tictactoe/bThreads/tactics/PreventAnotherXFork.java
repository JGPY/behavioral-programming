package BPJ_Programming_Examples.Tic_Tac_Toe.tictactoe.bThreads.tactics;

import static bp.BProgram.bp;
import static bp.BProgram.labelNextVerificationState;
import static bp.eventSets.EventSetConstants.none;
import BPJ_Programming_Examples.Tic_Tac_Toe.tictactoe.events.Move;
import BPJ_Programming_Examples.Tic_Tac_Toe.tictactoe.events.O;
import BPJ_Programming_Examples.Tic_Tac_Toe.tictactoe.events.X;
import bp.BThread;
import bp.eventSets.EventsOfClass;
import bp.exceptions.BPJException;

// The scenario handles separately each of the first three steps of the game.
// If the game doesn't follow the moves prescribed by this strategy as risky,
// the scenario exits. If it does - it requests the above step.
// Note that this scenario does not keep track of the actual moves.

@SuppressWarnings("serial")
public class PreventAnotherXFork extends BThread {

	@Override
	public void runBThread() throws BPJException {
		// If the game ends - exit
		labelNextVerificationState("0");
		// Watch for X's first move
		// If not a corner - exit (both row and col must be 0 or 2)
		bp.bSync(none, new EventsOfClass(X.class), none);
		Move move1 = (Move) bp.lastEvent;
		if (move1.row == 1 || move1.col == 1)
			bp.bSync(none, none, none);

		labelNextVerificationState("1");

		// Watch O's first move (by another scenario)
		// If not center exit (both row and col must be 1)
		bp.bSync(none, new EventsOfClass(O.class), none);
		Move move2 = (Move) bp.lastEvent;
		if (move2.row != 1 || move2.col != 1)
			bp.bSync(none, none, none);

		labelNextVerificationState("2");
		// Watch for X's second move. If not a corner or not opposite - exit.
		bp.bSync(none, new EventsOfClass(X.class), none);
		move2 = (Move) bp.lastEvent;
		if (move2.row == 1 || move2.col == 1 || 						  // not a corner
				move1.row + move2.row != 2 || move1.col + move2.col != 2) // not opposite
			bp.bSync(none, none, none);

		labelNextVerificationState("3");

		// Conditions met - now request intercept and move on
		bp.bSync(new O(0,1), new EventsOfClass(O.class), none);
		
		labelNextVerificationState("4");
		bp.bSync(none, none, none);
	}
}