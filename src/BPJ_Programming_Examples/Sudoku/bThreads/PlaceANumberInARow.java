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
public class PlaceANumberInARow extends BThread {

	private Move myMoveTemplate;
	int remainingPossibilitiesCounter = 9;
	int row;
	int number;
	int boxRow;
	int[] possibleColumns = { 0, 1, 2, 3, 4, 5, 6, 7, 8 }; // -1 means not
	// possible.

	private Move lastMove;

	public void runBThread() throws BPJException {
		int i;
		boxRow = row / 3;
		while (remainingPossibilitiesCounter > 1) {
			labelNextVerificationState("S" + getCurrentState());
			BProgram.bp.bSync(none, allMoves, none);
			lastMove = (Move) BProgram.bp.lastEvent;
			if (number != lastMove.number)
			{
				if (row == lastMove.row)
				{
					if (possibleColumns[lastMove.col] > -1)
					{
						possibleColumns[lastMove.col] = -1;
						remainingPossibilitiesCounter--;
					}
					continue;
				}
				else
					continue; 
				

			}
			
			//same number and same row- finish
			if (row == lastMove.row)
			{
				labelNextVerificationState("1");
				BProgram.bp.bSync(none, none, none);

				break; //just to make sure
			}

			
			if (lastMove.boxRow == boxRow) {
				for (i = lastMove.boxCol * 3; i < lastMove.boxCol * 3 + 3; i++)
					if (possibleColumns[i] > -1) {
						possibleColumns[i] = -1;
						remainingPossibilitiesCounter--;
					}
				continue;
			}
			
			//same number but not in the same box row
			if (possibleColumns[lastMove.col] > -1) {
				possibleColumns[lastMove.col] = -1;
				remainingPossibilitiesCounter--;
			}



		}

		for (i = 0; i < 9; i++) {
			if (possibleColumns[i] != -1) {
				myMoveTemplate = new Move(row,possibleColumns[i],number);
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
	
	String getCurrentState()
	{
		String state="";
		for (int i=0; i<possibleColumns.length; i++)
			if (possibleColumns[i]>-1)
				state+=possibleColumns[i];
		return state;
		
	}
	

	public PlaceANumberInARow(int row, int number) {
		super();
		this.row = row;
		this.number = number;
		this.setName("PlaceANumberInARow(" + row + ",*)/" + number + ")");
	}

	/**
	 * Construct all instances
	 */

	static public Set<BThread> constructInstances() {

		Set<BThread> set = new HashSet<BThread>();

		for (int row = 0; row < 9; row++) {
			for (int number = 1; number <= 9; number++) {

				set.add(new PlaceANumberInARow(row, number));
			}
		}
		return set;

	}

}