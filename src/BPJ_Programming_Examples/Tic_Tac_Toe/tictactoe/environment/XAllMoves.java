package BPJ_Programming_Examples.Tic_Tac_Toe.tictactoe.environment;

import static bp.BProgram.bp;
import static bp.eventSets.EventSetConstants.none;
import BPJ_Programming_Examples.Tic_Tac_Toe.tictactoe.events.X;
import bp.BThread;
import bp.eventSets.RequestableEventSet;
import bp.exceptions.BPJException;

/**
 * BThread that request all possible 9 moves - for X Player.
 * Used for model checking to explore all possible X games. 
 * (assumes X may be very smart - need to show that O never loses).
 * 	 */
/**
 *  */

@SuppressWarnings("serial")
public class XAllMoves extends BThread {

	@Override
	public void runBThread() throws BPJException {
		RequestableEventSet requests = new RequestableEventSet();
		RequestableEventSet xMoves = new RequestableEventSet();

		xMoves.add(new X(0, 0));
		xMoves.add(new X(0, 1));
		xMoves.add(new X(0, 2));
		xMoves.add(new X(1, 0));
		xMoves.add(new X(1, 1));
		xMoves.add(new X(1, 2));
		xMoves.add(new X(2, 0));
		xMoves.add(new X(2, 1));
		xMoves.add(new X(2, 2));
		requests.add(xMoves);

		while (true) {
			bp.bSync(requests,none,none);
		}
	} // end runBThread method
	
} // end class
