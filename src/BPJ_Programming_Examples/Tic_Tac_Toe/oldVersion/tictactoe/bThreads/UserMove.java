package BPJ_Programming_Examples.Tic_Tac_Toe.oldVersion.tictactoe.bThreads;

import static bpSourceCode.bp.eventSets.EventSetConstants.none;
import static BPJ_Programming_Examples.Tic_Tac_Toe.oldVersion.tictactoe.events.StaticEvents.gameOver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import BPJ_Programming_Examples.Tic_Tac_Toe.oldVersion.tictactoe.events.Click;
import BPJ_Programming_Examples.Tic_Tac_Toe.oldVersion.tictactoe.events.X;
import bpSourceCode.bp.BProgram;
import bpSourceCode.bp.BThread;
import bpSourceCode.bp.eventSets.EventSet;
import bpSourceCode.bp.eventSets.EventsOfClass;
import bpSourceCode.bp.exceptions.BPJException;

/**
 * BThread that fires X when a button is clicked (in response to a P event)
 */
public class UserMove extends BThread {

	private static final Logger logger = LoggerFactory.getLogger(UserMove.class);

	@Override
	public void runBThread() throws BPJException {
		interruptingEvents = new EventSet(gameOver);
		BProgram bp = getBProgram();

		while (true) {
			// Wait for a P event
			logger.info("同步：bSync(none,new EventsOfClass(Click.class),none)");
			bp.bSync(none,new EventsOfClass(Click.class),none);
			logger.info("结束：bSync(none,new EventsOfClass(Click.class),none)");

			// Put an X
			Click p = (Click) bp.lastEvent;
			logger.info("同步：bSync(new X(p.row, p.col),none,none)");
			bp.bSync(new X(p.row, p.col),none,none);
			logger.info("结束：bSync(new X(p.row, p.col),none,none)");

		}
	}

}