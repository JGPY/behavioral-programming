package BPJ_Programming_Examples.Tic_Tac_Toe.tictactoe.bThreads.tactics;

import static bp.BProgram.bp;
import static bp.eventSets.EventSetConstants.none;
import BPJ_Programming_Examples.Tic_Tac_Toe.tictactoe.events.O;
import bp.BThread;
import bp.eventSets.RequestableEventSet;
import bp.exceptions.BPJException;

/**
 * BThread that request all possible 9 moves - as default moves.
 * they "kick in" if no other strategy recommends anything.
 * 	 */
/**
 */

@SuppressWarnings("serial")
public class DefaultOMoves extends BThread {

	@Override
	public void runBThread() throws BPJException {
		RequestableEventSet defaultMoves = new RequestableEventSet();

		defaultMoves.add(new O(1, 1));
		defaultMoves.add(new O(0, 0));
		defaultMoves.add(new O(0, 2));
		defaultMoves.add(new O(2, 0));
		defaultMoves.add(new O(2, 2));
		defaultMoves.add(new O(0, 1));
		defaultMoves.add(new O(1, 0));
		defaultMoves.add(new O(1, 2));
		defaultMoves.add(new O(2, 1));
		
		while (true) {
			bp.bSync(defaultMoves, none, none);
		}
	} // end runBThread method

	@Override
	public String toString() {
		return "DefaultMoves";
	}
} // end class
