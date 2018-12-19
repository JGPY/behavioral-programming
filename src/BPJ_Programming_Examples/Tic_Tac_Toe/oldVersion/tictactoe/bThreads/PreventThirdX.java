package BPJ_Programming_Examples.Tic_Tac_Toe.oldVersion.tictactoe.bThreads;

import static bpSourceCode.bp.eventSets.EventSetConstants.none;
import static BPJ_Programming_Examples.Tic_Tac_Toe.oldVersion.tictactoe.events.StaticEvents.gameOver;

import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import BPJ_Programming_Examples.Tic_Tac_Toe.oldVersion.tictactoe.events.O;
import BPJ_Programming_Examples.Tic_Tac_Toe.oldVersion.tictactoe.events.X;
import BPJ_Programming_Examples.Tic_Tac_Toe.oldVersion.tictactoe.externalApp.Square;
import bpSourceCode.bp.BProgram;
import bpSourceCode.bp.BThread;
import bpSourceCode.bp.eventSets.EventSet;
import bpSourceCode.bp.exceptions.BPJException;

/**
 * A scenario that tries to block a row/column/diagonal of Xs by putting an O in
 * the last remaining square of it.
 */
public class PreventThirdX extends BThread {

	private static final Logger logger = LoggerFactory.getLogger(PreventThirdX.class);

	Square[] sq;

	private X firstSquare;
	private X secondSquare;
	private O triggeredEvent;

	@Override
	public void runBThread() throws BPJException {
		interruptingEvents = new EventSet(gameOver);

		BProgram bp = getBProgram();

		// Wait for the first X
		logger.info("同步：bSync(none, firstSquare, none)");
		bp.bSync(none, firstSquare, none);
		logger.info("结束：bSync(none, firstSquare, none)");
		// Wait for the second X
		logger.info("同步：bSync(none, secondSquare, none)");
		bp.bSync(none, secondSquare, none);
		logger.info("结束：bSync(none, secondSquare, none)");
		logger.info("同步：bSync(triggeredEvent, none, none)");
		// Request the O
		bp.bSync(triggeredEvent, none, none);
		logger.info("结束：bSync(triggeredEvent, none, none)");
	}

	/**
	 * @param firstSquare
	 * @param seconfSquare
	 * @param triggeredEvent
	 */
	public PreventThirdX(X firstSquare, X secondSquare,
			O triggeredEvent) {
		super();
		this.firstSquare = firstSquare;
		this.secondSquare = secondSquare;
		this.triggeredEvent = triggeredEvent;
		this.setName("PreventThirdX(" + firstSquare + ","
				+ secondSquare + "," + triggeredEvent + ")");
	}

	/**
	 * Construct all instances
	 */
	static public Set<BThread> constructInstances() {

		Set<BThread> set = new HashSet<BThread>();

		// All 6 permutations of three elements
		int[][] permutations = new int[][] { new int[] { 0, 1, 2 },
				new int[] { 0, 2, 1 }, new int[] { 1, 0, 2 },
				new int[] { 1, 2, 0 }, new int[] { 2, 0, 1 },
				new int[] { 2, 1, 0 } };

		for (int[] p : permutations) {
			// Run copies for each row
			for (int row = 0; row < 3; row++) {
				set.add(new PreventThirdX(new X(row, p[0]),
						new X(row, p[1]), new O(row, p[2])));
			}

			// Run copies for each column
			for (int col = 0; col < 3; col++) {

				set.add(new PreventThirdX(new X(p[0], col),
						new X(p[1], col), new O(p[2], col)));
			}

			// Run copies for the main diagonal
			set.add(new PreventThirdX(new X(p[0], p[0]),
					new X(p[1], p[1]), new O(p[2], p[2])));

			// Run copies for the inverse diagonal
			set.add(new PreventThirdX(new X(p[0], 2 - p[0]),
					new X(p[1], 2 - p[1]), new O(p[2], 2 - p[2])));
		}

		return set;
	}

}
