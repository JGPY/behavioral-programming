package BPJ_Programming_Examples.Tic_Tac_Toe.oldVersion.tictactoe.bThreads;

import static bpSourceCode.bp.eventSets.EventSetConstants.none;
import static BPJ_Programming_Examples.Tic_Tac_Toe.oldVersion.tictactoe.events.StaticEvents.draw;
import static BPJ_Programming_Examples.Tic_Tac_Toe.oldVersion.tictactoe.events.StaticEvents.gameOver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import BPJ_Programming_Examples.Tic_Tac_Toe.oldVersion.tictactoe.events.Move;
import bpSourceCode.bp.BProgram;
import bpSourceCode.bp.BThread;
import bpSourceCode.bp.eventSets.EventSet;
import bpSourceCode.bp.eventSets.EventsOfClass;
import bpSourceCode.bp.exceptions.BPJException;

public class DetectDraw extends BThread {

	private static final Logger logger = LoggerFactory.getLogger(DetectDraw.class);

	@Override
	public void runBThread() throws BPJException {
		interruptingEvents = new EventSet(gameOver);

		BProgram bp = getBProgram();

		// Wait for 9 events
		for (int count = 0; count < 9; count++) {
			logger.info("同步：bSync(none, new EventsOfClass(Move.class), none)");
			bp.bSync(none, new EventsOfClass(Move.class), none);
			logger.info("结束：bSync(none, new EventsOfClass(Move.class), none)");
		}
		logger.info("同步：bSync(draw, none, none)");
		bp.bSync(draw, none, none);
		logger.info("结束：bSync(draw, none, none)");
	}

	@Override
	public String toString() {
		return "DetectDraw";
	}
}
