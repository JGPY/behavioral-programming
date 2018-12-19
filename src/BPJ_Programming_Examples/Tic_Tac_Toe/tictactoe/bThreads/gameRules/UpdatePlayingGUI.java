package BPJ_Programming_Examples.Tic_Tac_Toe.tictactoe.bThreads.gameRules;

import static bp.BProgram.bp;
import static bp.eventSets.EventSetConstants.none;
import static BPJ_Programming_Examples.Tic_Tac_Toe.tictactoe.events.StaticEvents.gameOver;

import javax.swing.JButton;

import BPJ_Programming_Examples.Tic_Tac_Toe.tictactoe.events.Move;
import BPJ_Programming_Examples.Tic_Tac_Toe.tictactoe.externalApp.TicTacToe;
import bp.BThread;
import bp.eventSets.EventSet;
import bp.eventSets.EventsOfClass;
import bp.exceptions.BPJException;

/**
 * BThread for updating the labels of the buttons.
 */
@SuppressWarnings("serial")
public class UpdatePlayingGUI extends BThread {

	@Override
	public void runBThread() throws BPJException {
		interruptingEvents = new EventSet(gameOver);



		while (true) {

			// Wait for an event
			bp.bSync(none, new EventsOfClass(Move.class), none);

			// Update the board
			Move move = (Move) bp.lastEvent;
			JButton btt = TicTacToe.playGUI.buttons[move.row][move.col];
			btt.setText(move.displayString());
//			btt.setEnabled(false);
		}
	}
}
