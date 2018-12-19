package BPJ_Programming_Examples.Tic_Tac_Toe.tictactoe.bThreads.gameRules;

import static bp.BProgram.bp;
import static bp.eventSets.EventSetConstants.none;
import BPJ_Programming_Examples.Tic_Tac_Toe.tictactoe.events.Move;
import BPJ_Programming_Examples.Tic_Tac_Toe.tictactoe.externalApp.TicTacToe;
import bp.BThread;
import bp.eventSets.EventsOfClass;
import bp.exceptions.BPJException;

/**
 * BThread for updating the labels of the buttons.
 */
@SuppressWarnings("serial")
public class UpdateMCMonitorGUI extends BThread {

//	Move[][] recordedMoves;
	char[][] recordedMoves;
	
	@Override
	public void runBThread() throws BPJException {
//		interruptingEvents = new EventSet(gameOver);

//		HashSet<Move> recorderMoves = new HashSet<Move>();
		recordedMoves = new char[3][3];
		for(int i=0; i<3; i++)
			for(int j=0; j<3; j++)
//				recordedMoves[i][j] = null;
				recordedMoves[i][j] = ' ';

		while (true) {

			// Wait for an event
			bp.bSync(none, new EventsOfClass(Move.class), none);

			// Update the board
			Move move = (Move) bp.lastEvent;
			recordedMoves[move.row][move.col] = move.displayString().charAt(0);
			refreshDisplay();
//			JButton btt = TicTacToe.disp.buttons[move.row][move.col];
//			btt.setText(move.displayString());
//			btt.setEnabled(false);
		}
	}
	
	void refreshDisplay(){
		for(int i=0; i<3; i++)
			for(int j=0; j<3; j++)
				TicTacToe.modelCheckingMonitorGUI.buttons[i][j].setText(String.valueOf(recordedMoves[i][j]));
//				if (recordedMoves[i][j] == null)
//					TicTacToe.disp.buttons[i][j].setText("");
//				else
//					TicTacToe.disp.buttons[i][j].setText(recordedMoves[i][j].displayString());
	}
}
