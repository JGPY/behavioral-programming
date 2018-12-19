package BPJ_Programming_Examples.Tic_Tac_Toe.tictactoe.bThreads.gameRules;

import static bp.BProgram.bp;
import static bp.BProgram.labelNextVerificationState;
import static bp.eventSets.EventSetConstants.none;
import BPJ_Programming_Examples.Tic_Tac_Toe.tictactoe.events.O;
import BPJ_Programming_Examples.Tic_Tac_Toe.tictactoe.events.X;
import bp.BThread;
import bp.eventSets.EventsOfClass;
import bp.exceptions.BPJException;

/**
 * BThread that blocks players from playing when its not their turn.
 */
@SuppressWarnings("serial")
public class EnforceTurns extends BThread {
	@Override
	public void runBThread() throws BPJException {
		EventsOfClass Xevents = new EventsOfClass(X.class);
		EventsOfClass Oevents = new EventsOfClass(O.class);
//		interruptingEvents = new EventSet(gameOver);
		while (true) {
			labelNextVerificationState("X's turn");
			bp.bSync(none, Xevents, Oevents);
			labelNextVerificationState("O's turn");
			bp.bSync(none, Oevents, Xevents);

		}
	}

}
