package BPJ_Programming_Examples.Sudoku.bThreads;

import static bpSourceCode.bp.eventSets.EventSetConstants.none;
import static BPJ_Programming_Examples.Sudoku.events.StaticEvents.initialBox;
import static BPJ_Programming_Examples.Sudoku.externalApp.Sudoku.suLog;
import BPJ_Programming_Examples.Sudoku.events.Move;
import bpSourceCode.bp.BProgram;
import bpSourceCode.bp.BThread;
import bpSourceCode.bp.exceptions.BPJException;

/**
 * A scenario that identifies wins by player O
 */
@SuppressWarnings("serial")
public class MakeGivenMoves extends BThread {

	@SuppressWarnings("static-access")
	public void runBThread() throws BPJException {

		Move move;
		for (int i=0; i<9; i++)
			for (int j=0; j<9; j++)
				if (initialBox[i][j]>0)
				{
					move=new Move(i,j,initialBox[i][j]);
					BProgram.bp.bSync(move, none, none);
					suLog("by " + this); 
					
				}
//		for (Move move : givens) {
//			
//			BProgram.sourceCode.bp.labelNextVerificationState(move.toString());
//			BProgram.sourceCode.bp.bSync(move, none, none);
//			suLog("by " + this); 
//		}

		BProgram.bp.bSync(none, none, none);

	}

}