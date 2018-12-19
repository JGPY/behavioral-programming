package BPJ_Programming_Examples.Tic_Tac_Toe.oldVersion.tictactoe.bThreads;

import static bpSourceCode.bp.eventSets.EventSetConstants.none;
import static BPJ_Programming_Examples.Tic_Tac_Toe.oldVersion.tictactoe.events.StaticEvents.gameOver;

import javax.swing.JButton;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import BPJ_Programming_Examples.Tic_Tac_Toe.oldVersion.tictactoe.events.Move;
import BPJ_Programming_Examples.Tic_Tac_Toe.oldVersion.tictactoe.externalApp.TicTacToe_Main;
import bpSourceCode.bp.BProgram;
import bpSourceCode.bp.BThread;
import bpSourceCode.bp.eventSets.EventSet;
import bpSourceCode.bp.eventSets.EventsOfClass;
import bpSourceCode.bp.exceptions.BPJException;

/**
 * BThread for updating the labels of the buttons.
 */
public class UpdateDisplay extends BThread {

	private static final Logger logger = LoggerFactory.getLogger(UpdateDisplay.class);

	@Override
	public void runBThread() throws BPJException {
		interruptingEvents = new EventSet(gameOver);

		BProgram bp = getBProgram();

		while (true) {

			// Wait for an event
			logger.info("同步：bSync(none, new EventsOfClass(Move.class), none)");
			bp.bSync(none, new EventsOfClass(Move.class), none);
			logger.info("结束：bSync(none, new EventsOfClass(Move.class), none)");

			// Update the board
			Move move = (Move) bp.lastEvent;
			JButton btt = TicTacToe_Main.gui.buttons[move.row][move.col];
			btt.setText(move.displayString());
			btt.setEnabled(false);
		}
	}
}
