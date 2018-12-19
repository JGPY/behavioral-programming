package BPJ_Programming_Examples.Sudoku.bThreads;

import bpSourceCode.bp.BThread;
import bpSourceCode.bp.exceptions.BPJException;

/**
 * BThread for updating the labels of the buttons.
 */
@SuppressWarnings("serial")
public class UpdatePlayingGUIOLD extends BThread {

	public void runBThread() throws BPJException {
//		interruptingEvents = new EventSet(gameOver);



	//	while (true) {

//			// Wait for an event
//			BProgram.sourceCode.bp.bSync(none, new EventsOfClass(Move.class), none);
//
//			// Update the board
//			Move move = (Move) BProgram.sourceCode.bp.lastEvent;
//			System.out.println("BP=" + sourceCode.bp + " LastEvent="+ BProgram.sourceCode.bp.lastEvent + " MOVE=" + move);
//			
//			JButton btt = Sudoku.playGUI.buttons[move.row][move.col];
//			btt.setText(move.displayString());
////			btt.setEnabled(false);
//		}
	}
}
