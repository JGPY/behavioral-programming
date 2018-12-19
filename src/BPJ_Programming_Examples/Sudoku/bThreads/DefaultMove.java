package BPJ_Programming_Examples.Sudoku.bThreads;

import static bpSourceCode.bp.BProgram.labelNextVerificationState;
import static bpSourceCode.bp.eventSets.EventSetConstants.none;
import static BPJ_Programming_Examples.Sudoku.externalApp.Sudoku.suLog;

import java.util.HashSet;
import java.util.Set;

import BPJ_Programming_Examples.Sudoku.events.Competitors;
import BPJ_Programming_Examples.Sudoku.events.Move;
import bpSourceCode.bp.BProgram;
import bpSourceCode.bp.BThread;
import bpSourceCode.bp.exceptions.BPJException;

/**
 * A scenario that tries to write a number in a square 
 * and when succeeds it blocks all other options.  
 * Its requested event may be unified with strategies - and it then provides the blocking
 */
public class DefaultMove extends BThread {

	private Move move;
	private Competitors competitors;

	public void runBThread() throws BPJException {
		// interruptingEvents = new EventSet(gameOver);

		labelNextVerificationState("0");

		BProgram.bp.bSync(move, none, none);
		suLog("by " + this); 
		
		labelNextVerificationState("1");
		BProgram.bp.bSync(none, none, competitors);

	}

	public DefaultMove(Move move) {
		super();
		this.move = move;
		this.competitors = new Competitors(move); 

		this.setName("MakeOneMove(" + move + ")");
	}

	/**
	 * Construct all instances
	 */

	static public Set<BThread> constructInstances() {

		Set<BThread> set = new HashSet<BThread>();

		for (int row = 0; row < 9; row++) {
			for (int col = 0; col < 9; col++) {
				for (int number = 1; number <= 9; number++)
					set.add(new DefaultMove(new Move(row, col, number)));
			}
		}
		return set;

	}

}