package BPJ_Programming_Examples.Sudoku.bThreads;

import static bpSourceCode.bp.BProgram.labelNextVerificationState;
import static bpSourceCode.bp.eventSets.EventSetConstants.none;
import static BPJ_Programming_Examples.Sudoku.events.StaticEvents.pruning;
import bpSourceCode.bp.BProgram;
import bpSourceCode.bp.BThread;
import bpSourceCode.bp.exceptions.BPJException;

/**
 * A scenario that runs at a lower priority and prunes the search - means no solution. 
 */
@SuppressWarnings("serial")
public class PruneWhenNoSolution extends BThread {

	public void runBThread() throws BPJException {
		labelNextVerificationState("0");
		BProgram.bp.bSync(pruning, none, none);
		BProgram.bp.pruneSearchNow();   
		BProgram.bp.bSync(none, none, none);
		
	}
	
}