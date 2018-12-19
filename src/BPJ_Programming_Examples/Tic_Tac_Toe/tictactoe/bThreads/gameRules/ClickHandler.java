package BPJ_Programming_Examples.Tic_Tac_Toe.tictactoe.bThreads.gameRules;

import static bp.BProgram.bp;
import static bp.eventSets.EventSetConstants.none;
import BPJ_Programming_Examples.Tic_Tac_Toe.tictactoe.events.Click;
import bp.BThread;

/**
 * A scenario that handles click events
 */
@SuppressWarnings("serial")
public class ClickHandler extends BThread {
	private int row,col;
	public ClickHandler(int row,int col) {
		this.row = row;
		this.col = col;
	}
	@Override
	public void runBThread() {
		try {

			bp.bSync(new Click(row, col) , none ,none );

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public String toString() {
		return "ClickHandler(" + row + "," + col + ")";
	}
}