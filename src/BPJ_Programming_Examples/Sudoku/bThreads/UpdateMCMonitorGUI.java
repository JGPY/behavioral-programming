package BPJ_Programming_Examples.Sudoku.bThreads;


import static bpSourceCode.bp.eventSets.EventSetConstants.none;

import java.awt.Font;

import BPJ_Programming_Examples.Sudoku.events.Move;
import BPJ_Programming_Examples.Sudoku.externalApp.Sudoku;
import bpSourceCode.bp.BProgram;
import bpSourceCode.bp.BThread;
import bpSourceCode.bp.eventSets.EventsOfClass;
import bpSourceCode.bp.exceptions.BPJException;

/**
 * BThread for updating the labels of the buttons.
 */
@SuppressWarnings("serial")
public class UpdateMCMonitorGUI extends BThread {

//	Move[][] recordedMoves;
	char[][] recordedMoves;
	
	public void runBThread() throws BPJException {
//		interruptingEvents = new EventSet(gameOver);

//		HashSet<Move> recorderMoves = new HashSet<Move>();
		recordedMoves = new char[9][9];
		for(int i=0; i<9; i++)
			for(int j=0; j<9; j++)
//				recordedMoves[i][j] = null;
				recordedMoves[i][j] = ' ';
		while (true) {

			// Wait for an event
			BProgram.bp.bSync(none, new EventsOfClass(Move.class), none);

			// Update the board
			Move move = (Move) BProgram.bp.lastEvent;
			recordedMoves[move.row][move.col] = move.displayString().charAt(0);
			// if ( pace++ % 500 == 0) { 
			 refreshDisplay();
		// 	} 
			
//			JButton btt = Sudoku.playGUI.buttons[move.row][move.col];
//			btt.setText(move.displayString());
//			btt.setEnabled(false);
		}
	}
	
	void refreshDisplay(){
		for(int i=0; i<9; i++)
			for(int j=0; j<9; j++) {
				Font myFont = new Font("Courier", Font.BOLD ,20);        
				Sudoku.modelCheckingMonitorGUI.buttons[i][j].setFont(myFont);        

				Sudoku.modelCheckingMonitorGUI.buttons[i][j].setText(String.valueOf(recordedMoves[i][j]));
			}
//				if (recordedMoves[i][j] == null)
//					Sudoku.playGUI.buttons[i][j].setText("");
//				else
//					Sudoku.playGUI.buttons[i][j].setText(recordedMoves[i][j].displayString());
	}
}
