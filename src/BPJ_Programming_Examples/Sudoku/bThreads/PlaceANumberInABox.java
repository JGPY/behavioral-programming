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
public class PlaceANumberInABox extends BThread {

	private Move myMoveTemplate;
	int remainingPossibilitiesCounter = 9;
	int box;
	int number;
	int boxCol, boxRow;
	int[][] possiblePlacements = {	
			{0, 1, 2},
			{3, 4, 5},
			{6, 7, 8}}; // -1 means not
	// possible.	

	private Move lastMove;

	public void runBThread() throws BPJException {
		int i;
		boxCol = box % 3;
		boxRow = box / 3;
		while (remainingPossibilitiesCounter > 1) {
			labelNextVerificationState("S" + getCurrentState());
			BProgram.bp.bSync(none, allMoves, none);
			lastMove = (Move) BProgram.bp.lastEvent;
			if (lastMove.boxCol==boxCol && lastMove.boxRow == boxRow )	//same box
			{
				if (number == lastMove.number)//same number - no need for this BThread
				{
					labelNextVerificationState("1");
					BProgram.bp.bSync(none, none, none);
					break; //just to make sure
				}
				else	//different number- just flag
				{
					if (possiblePlacements[lastMove.row%3][lastMove.col%3]>-1)
					{
						possiblePlacements[lastMove.row%3][lastMove.col%3]=-1;
						remainingPossibilitiesCounter--;
					}
					continue;
				}
			}
			if (number == lastMove.number)
			{
				//not in the same box but same number
				if (lastMove.boxCol==boxCol)
				{
					int lastMoveCol=lastMove.col%3;
					for (i=0; i<3; i++)
					{
						if (possiblePlacements[i][lastMoveCol]>-1)
						{
							possiblePlacements[i][lastMoveCol]=-1;
							remainingPossibilitiesCounter--;
						}
					}
					continue;
				}


				if (lastMove.boxRow == boxRow)
				{
					int lastMoveRow=lastMove.row%3;

					for (i=0; i<3; i++)
					{
						if (possiblePlacements[lastMoveRow][i]>-1)
						{
							possiblePlacements[lastMoveRow][i]=-1;
							remainingPossibilitiesCounter--;
						}	
					}
					continue;
				}
			}
		}
		
		for (i=0; i<3; i++)
			for (int j=0; j<3; j++)
				if (possiblePlacements[i][j]>-1)
				{
					myMoveTemplate=new Move(i+3*boxRow, j+3*boxCol, number);
					break;
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
		for (int i=0; i<possiblePlacements.length; i++)
			for (int j=0; j<possiblePlacements[0].length; j++)
				if (possiblePlacements[i][j]>-1)
					state+=possiblePlacements[i][j];
		return state;
		
	}
	
	
	public PlaceANumberInABox(int box, int number) {
		super();
		this.box = box;
		this.number = number;
		this.setName("PlaceANumberInABox(*," + box + ")/" + number + ")");
	}

	/**
	 * Construct all instances
	 */

	static public Set<BThread> constructInstances() {

		Set<BThread> set = new HashSet<BThread>();

		for (int box = 0; box < 9; box++) {
			for (int number = 1; number <= 9; number++) {

				set.add(new PlaceANumberInABox(box, number));
			}
		}
		return set;

	}

}