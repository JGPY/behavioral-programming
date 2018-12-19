package BPJ_Programming_Examples.Sudoku.bThreads;

import static bpSourceCode.bp.BProgram.labelNextVerificationState;
import static bpSourceCode.bp.eventSets.EventSetConstants.none;
import static BPJ_Programming_Examples.Sudoku.events.StaticEvents.allMoves;
import static BPJ_Programming_Examples.Sudoku.externalApp.Sudoku.suLog;

import java.util.HashSet;
import java.util.Set;

import BPJ_Programming_Examples.Sudoku.events.Move;
import bpSourceCode.bp.BProgram;
import bpSourceCode.bp.BThread;
import bpSourceCode.bp.exceptions.BPJException;
/**
 * A scenario that eliminates possibilities for a given square and then when
 * reaches ONE possibility - requests it with high priority.
 */
@SuppressWarnings("serial")
public class EliminateEight extends BThread {

	private Move myMoveTemplate;
	int remainingPossibilitiesCounter = 9;

	int[] possibleNumbers = { 1, 2, 3, 4, 5, 6, 7, 8, 9 }; // 0 means not possible.

	private Move lastMove;

	public void runBThread() throws BPJException {

		while (remainingPossibilitiesCounter > 1) {
			labelNextVerificationState("S" + getCurrentState());
			BProgram.bp.bSync(none, allMoves, none);
			lastMove = (Move) BProgram.bp.lastEvent;

			if (myMoveTemplate.intersect(lastMove)) {
				if (possibleNumbers[(lastMove.number - 1)] > -1){ 
				possibleNumbers[(lastMove.number - 1)] = -1;
				remainingPossibilitiesCounter--;
				} 
			}
		}
		for (int i = 0; i < 9; i++) {
			if (possibleNumbers[i] > -1){ 
				myMoveTemplate.number = possibleNumbers[i];
			break;
			} 
		}
		labelNextVerificationState("0");
		BProgram.bp.bSync(myMoveTemplate, none, none); // Not followed by blocking - rely
		// on others.
		suLog("by " + this); 
		labelNextVerificationState("1");
		BProgram.bp.bSync(none, none, none);
	}

	public EliminateEight(Move myMoveTemplate) {
		super();
		this.myMoveTemplate = myMoveTemplate;
		this.setName("EliminateEight(" + myMoveTemplate.row + "," +myMoveTemplate.col + ")");
	}

	/**
	 * Construct all instances
	 */
	
	String getCurrentState()
	{
		String state="";
		for (int i=0; i<possibleNumbers.length; i++)
			if (possibleNumbers[i]>-1)
				state+=possibleNumbers[i];
		return state;
		
	}

	static public Set<BThread> constructInstances() {

		Set<BThread> set = new HashSet<BThread>();

		for (int row = 0; row < 9; row++) {
			for (int col = 0; col < 9; col++) {
				set.add(new EliminateEight(new Move(row, col, 0)));
			}
		}
		return set;

	}

}