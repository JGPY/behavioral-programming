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
 * A scenario that tries to place a particular number in a given row it waits
 * till there is only one place to put it
 */
@SuppressWarnings("serial")
public class PlaceANumberInAColumn extends BThread {

	private Move myMoveTemplate;
	int remainingPossibilitiesCounter = 9;
	int col;
	int number;
	int boxCol;
	int[] possibleRows = { 0, 1, 2, 3, 4, 5, 6, 7, 8 }; // -1 means not
	// possible.

	private Move lastMove;

	public void runBThread() throws BPJException {
		int i;
		boxCol = col / 3;
		while (remainingPossibilitiesCounter > 1) {
			labelNextVerificationState("S" + getCurrentState());
			BProgram.bp.bSync(none, allMoves, none);
			lastMove = (Move) BProgram.bp.lastEvent;
			if (number != lastMove.number)
			{
				if (col == lastMove.col )
				{
					if (possibleRows[lastMove.row]>-1)
					{
						possibleRows[lastMove.row] = -1;
						remainingPossibilitiesCounter--;
					}
					continue;
				}
				else
					continue; 
			}
			//same number
			if (col == lastMove.col)
			{
				labelNextVerificationState("1");
				BProgram.bp.bSync(none, none, none);
				break; //just to make sure
			}

			//same number, check if on same box col
			if (lastMove.boxCol == boxCol) {
				for (i = lastMove.boxRow * 3; i < lastMove.boxRow * 3 + 3; i++)
					if (possibleRows[i] > -1) {
						possibleRows[i] = -1;
						remainingPossibilitiesCounter--;
					}
				continue;
			}

			//same number on different col
			if (possibleRows[lastMove.row] > -1) {
				possibleRows[lastMove.row] = -1;
				remainingPossibilitiesCounter--;
			}



		}
		for (i = 0; i < 9; i++) {
			if (possibleRows[i] != -1) {
				myMoveTemplate=new Move(possibleRows[i], col, number);
				break;
			}
		}
		labelNextVerificationState("0");
		BProgram.bp.bSync(myMoveTemplate, none, none); // Not followed by blocking -
		// rely
		// on others.
		suLog("by " + this);
		labelNextVerificationState("1");

		BProgram.bp.bSync(none, none, none);
	}

	public PlaceANumberInAColumn(int col, int number) {
		super();
		this.col = col;
		this.number = number;
		this.setName("PlaceANumberInAColumn(*," + col + ")/" + number + ")");
	}

	
	String getCurrentState()
	{
		String state="";
		for (int i=0; i<possibleRows.length; i++)
			if (possibleRows[i]>-1)
				state+=possibleRows[i];
		return state;
		
	}
	
	/**
	 * Construct all instances
	 */

	static public Set<BThread> constructInstances() {

		Set<BThread> set = new HashSet<BThread>();

		for (int col = 0; col < 9; col++) {
			for (int number = 1; number <= 9; number++) {

				set.add(new PlaceANumberInAColumn(col, number));
			}
		}
		return set;

	}

}