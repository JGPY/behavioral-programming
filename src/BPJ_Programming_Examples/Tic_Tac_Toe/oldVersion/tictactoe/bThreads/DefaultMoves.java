package BPJ_Programming_Examples.Tic_Tac_Toe.oldVersion.tictactoe.bThreads;

import static bpSourceCode.bp.eventSets.EventSetConstants.none;
import static BPJ_Programming_Examples.Tic_Tac_Toe.oldVersion.tictactoe.events.StaticEvents.gameOver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import BPJ_Programming_Examples.Tic_Tac_Toe.oldVersion.tictactoe.events.O;
import bpSourceCode.bp.BProgram;
import bpSourceCode.bp.BThread;
import bpSourceCode.bp.eventSets.EventSet;
import bpSourceCode.bp.eventSets.RequestableEventSet;
import bpSourceCode.bp.exceptions.BPJException;

/**
 * BThread that request all possible 9 moves - as default moves.
 * they "kick in" if no other strategy recommends anything.
 * 	 */



public class DefaultMoves extends BThread {

	private static final Logger logger = LoggerFactory.getLogger(DefaultMoves.class);

	@Override
	public void runBThread() throws BPJException {
		RequestableEventSet defaultMoves = new RequestableEventSet();
		RequestableEventSet corners = new RequestableEventSet();
		RequestableEventSet edges = new RequestableEventSet();
		interruptingEvents = new EventSet(gameOver);

		BProgram bp = getBProgram();

		defaultMoves.add(new O(1, 1));
		corners.add(new O(0, 0));
		corners.add(new O(0, 2));
		corners.add(new O(2, 0));
		corners.add(new O(2, 2));
		defaultMoves.add(corners);
		edges.add(new O(0, 1));
		edges.add(new O(1, 0));
		edges.add(new O(1, 2));
		edges.add(new O(2, 1));
		defaultMoves.add(edges);

		while (true) {
			logger.info("同步：bSync(defaultMoves,none,none)");
			bp.bSync(defaultMoves,none,none);
			logger.info("结束：bSync(defaultMoves,none,none)");
		}
	} // end runBThread method
	@Override
	public String toString() {
		return "DefaultMoves";
	}
} // end class
