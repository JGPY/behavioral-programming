package BPJ_Programming_Examples.Tic_Tac_Toe.oldVersion.tictactoe.bThreads;

import static bpSourceCode.bp.eventSets.EventSetConstants.none;
import static BPJ_Programming_Examples.Tic_Tac_Toe.oldVersion.tictactoe.events.StaticEvents.OWin;
import static BPJ_Programming_Examples.Tic_Tac_Toe.oldVersion.tictactoe.events.StaticEvents.XWin;
import static BPJ_Programming_Examples.Tic_Tac_Toe.oldVersion.tictactoe.events.StaticEvents.draw;
import static BPJ_Programming_Examples.Tic_Tac_Toe.oldVersion.tictactoe.events.StaticEvents.gameOver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import BPJ_Programming_Examples.Tic_Tac_Toe.oldVersion.tictactoe.externalApp.TicTacToe_Main;
import bpSourceCode.bp.BProgram;
import bpSourceCode.bp.BThread;
import bpSourceCode.bp.eventSets.EventSet;
import bpSourceCode.bp.exceptions.BPJException;

/**
 * BThread that waits for a Win message and prints its message
 */
public class DeclareWinner extends BThread {

	private static final Logger logger = LoggerFactory.getLogger(DeclareWinner.class);

	@Override
	public void runBThread() throws BPJException {
		BProgram bp = getBProgram();
		logger.info("同步：bSync(none, new EventSet(\"WinnerDecided\", XWin, OWin, draw), none)");
		bp.bSync(none, new EventSet("WinnerDecided", XWin, OWin, draw), none);
		logger.info("结束：bSync(none, new EventSet(\"WinnerDecided\", XWin, OWin, draw), none)");
		String msg;
		if (bp.lastEvent == XWin) {
			msg = "X Wins";
		} else if (bp.lastEvent == OWin) {
			msg = "O Wins";
		} else{
			msg = "A Draw";
		}

		System.out.println(msg);
		TicTacToe_Main.gui.message.setText(msg);
		logger.info("同步：bSync(gameOver, none, none);");
		bp.bSync(gameOver, none, none);
		logger.info("结束：bSync(gameOver, none, none);");
	}
}
