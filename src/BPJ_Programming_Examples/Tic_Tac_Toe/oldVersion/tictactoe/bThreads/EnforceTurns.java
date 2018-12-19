package BPJ_Programming_Examples.Tic_Tac_Toe.oldVersion.tictactoe.bThreads;

import static bpSourceCode.bp.eventSets.EventSetConstants.none;
import static BPJ_Programming_Examples.Tic_Tac_Toe.oldVersion.tictactoe.events.StaticEvents.gameOver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import BPJ_Programming_Examples.Tic_Tac_Toe.oldVersion.tictactoe.events.O;
import BPJ_Programming_Examples.Tic_Tac_Toe.oldVersion.tictactoe.events.X;
import bpSourceCode.bp.BProgram;
import bpSourceCode.bp.BThread;
import bpSourceCode.bp.eventSets.EventSet;
import bpSourceCode.bp.eventSets.EventsOfClass;
import bpSourceCode.bp.exceptions.BPJException;

/**
 * BThread that blocks players from playing when its not their turn.
 */
public class EnforceTurns extends BThread {

	private static final Logger logger = LoggerFactory.getLogger(EnforceTurns.class);

	@Override
	public void runBThread() throws BPJException {
		BProgram bp = getBProgram();
		EventsOfClass Xevents = new EventsOfClass(X.class);
		EventsOfClass Oevents = new EventsOfClass(O.class);
		interruptingEvents = new EventSet(gameOver);
		while (true) {
			logger.info("同步：bSync(none, Xevents, Oevents)");
			bp.bSync(none, Xevents, Oevents);
			logger.info("结束：bSync(none, Xevents, Oevents)");
			logger.info("同步：bSync(none, Oevents, Xevents)");
			bp.bSync(none, Oevents, Xevents);
			logger.info("结束：bSync(none, Oevents, Xevents)");

		}
	}

}
