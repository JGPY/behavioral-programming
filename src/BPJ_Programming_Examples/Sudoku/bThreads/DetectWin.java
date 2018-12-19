package BPJ_Programming_Examples.Sudoku.bThreads;
import static bpSourceCode.bp.BProgram.labelNextVerificationState;
import static bpSourceCode.bp.eventSets.EventSetConstants.none;
import static BPJ_Programming_Examples.Sudoku.events.StaticEvents.allMoves;
import static BPJ_Programming_Examples.Sudoku.events.StaticEvents.finished;
import static BPJ_Programming_Examples.Sudoku.externalApp.Sudoku.globalEventCounter;
import static BPJ_Programming_Examples.Sudoku.externalApp.Sudoku.suLog;
import bpSourceCode.bp.BProgram;
import bpSourceCode.bp.BThread;
import bpSourceCode.bp.exceptions.BPJException;

/**
 * A scenario that identifies wins by player O
 */
@SuppressWarnings("serial")
public class DetectWin extends BThread {

	private int counter = 0; 

	@SuppressWarnings("static-access")
	public void runBThread() throws BPJException {
//		interruptingEvents = new EventSet(gameOver);

	 
while (counter < 81) { 
		labelNextVerificationState(""+counter);
		
		// Wait for next event; 
		BProgram.bp.bSync(none, allMoves, none);
		counter++; 
		globalEventCounter++; 
		suLog("MYLOG:"+ BProgram.bp.lastEvent + " local=" + counter + " global=" + globalEventCounter ); 
} 
	
		labelNextVerificationState("Finished");
		BProgram.bp.markNextVerificationStateAsBad("Finished!");  
		BProgram.bp.bSync(finished,none,none); 
		BProgram.bp.bSync(none, none, none);
		

	}

	/**
	 * @param firstSquare
	 * @param seconfSquare
	 * @param thirdSquare
	 */
	
	/**
	 * Construct all instances
	 */
	
}