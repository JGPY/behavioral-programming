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
 * A scenario that tries to write a number in a square after  the same number was written in two 
 * other columns of this box. It relies on blocking by others. (MC)   
 */
@SuppressWarnings("serial")
public class EliminateTwoInABox extends BThread {

	private Move move;
	private int boxRowBrothersCounter = 3;
	private int boxColBrothersCounter = 3;
	private Move lastMove;
	public void runBThread() throws BPJException {

			while (boxRowBrothersCounter > 1
					&& boxColBrothersCounter > 1) {

				labelNextVerificationState("S" + boxRowBrothersCounter + boxColBrothersCounter);
				BProgram.bp.bSync(none, allMoves, none);
				
				lastMove = (Move) BProgram.bp.lastEvent;
				
				// If my event happened elsewhere - exit
				
				if ((move.number == lastMove.number) &&(move.intersect(lastMove)))
				{
					labelNextVerificationState("1");
					BProgram.bp.bSync(none, none, none);
					break; //just to make sure
				}
				
				if (lastMove.number == move.number) {
					if (lastMove.boxRow == move.boxRow)
						boxRowBrothersCounter--;
					if (lastMove.boxCol == move.boxCol)
						boxColBrothersCounter--;
				}

			}

			labelNextVerificationState("0");

			BProgram.bp.bSync(move, none, none);
			suLog("by " + this); 

		
		// 
		labelNextVerificationState("1");
		BProgram.bp.bSync(none, none, none);
	}

	public EliminateTwoInABox(Move move) {
		super();
		this.move = move;
		this.setName("EliminateTwoInABox(" + move +  ")");
	}

	/**
	 * Construct all instances
	 */

	static public Set<BThread> constructInstances() {

		Set<BThread> set = new HashSet<BThread>();

		for (int row = 0; row < 9; row++) {
			for (int col = 0; col < 9; col++) {
				for (int number = 1; number <= 9; number++)
					set.add(new EliminateTwoInABox(new Move(row, col, number)));			}
		}
		return set;

	}

}