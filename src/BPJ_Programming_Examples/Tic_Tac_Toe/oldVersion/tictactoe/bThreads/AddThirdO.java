package BPJ_Programming_Examples.Tic_Tac_Toe.oldVersion.tictactoe.bThreads;

import static bpSourceCode.bp.eventSets.EventSetConstants.none;
import static BPJ_Programming_Examples.Tic_Tac_Toe.oldVersion.tictactoe.events.StaticEvents.gameOver;

import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import BPJ_Programming_Examples.Tic_Tac_Toe.oldVersion.tictactoe.events.O;
import bpSourceCode.bp.BProgram;
import bpSourceCode.bp.BThread;
import bpSourceCode.bp.eventSets.EventSet;
import bpSourceCode.bp.exceptions.BPJException;

/**
 * A scenario that tries to complete a row/column/diagonal of Os
 */
public class AddThirdO extends BThread {

    private static final Logger logger = LoggerFactory.getLogger(AddThirdO.class);

	private O firstSquare;
	private O secondSquare;
	private O triggeredEvent;

	@Override
	public void runBThread() throws BPJException {
		BProgram bp = getBProgram();
		interruptingEvents = new EventSet(gameOver);

        logger.info("同步:sourceCode.bp.bSync(none, firstSquare, none);");
		// Wait for the first O
		bp.bSync(none, firstSquare, none);
        logger.info("结束:sourceCode.bp.bSync(none, firstSquare, none);");

        logger.info("同步:sourceCode.bp.bSync(none, secondSquare, none);");
		// Wait for the second O
		bp.bSync(none, secondSquare, none);
        logger.info("结束:sourceCode.bp.bSync(none, secondSquare, none);");

        logger.info("同步:sourceCode.bp.bSync(triggeredEvent, none, none);");
		// Request the third O
		bp.bSync(triggeredEvent, none, none);
        logger.info("结束:sourceCode.bp.bSync(triggeredEvent, none, none);");
	}

	/**
	 * @param firstSquare
	 * @param secondSquare
	 * @param triggeredEvent
	 */
	public AddThirdO(O firstSquare, O secondSquare, O triggeredEvent) {
		super();
		this.firstSquare = firstSquare;
		this.secondSquare = secondSquare;
		this.triggeredEvent = triggeredEvent;
		this.setName("AddThirdO(" + firstSquare + ","
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
				set.add(
							new AddThirdO(new O(row, p[0]), new O(row,p[1]), new O(row, p[2]))
						);
			}

			// Run copies for each column
			for (int col = 0; col < 3; col++) {

				set.add(new AddThirdO(new O(p[0], col), new O(p[1],
						col), new O(p[2], col)));
			}

			// Run copies for the Main diagonal
			set.add(new AddThirdO(new O(p[0], p[0]), new O(p[1],
					p[1]), new O(p[2], p[2])));

			// Run copies for the inverse diagonal
			set.add(new AddThirdO(new O(p[0], 2 - p[0]), new O(
					p[1], 2 - p[1]), new O(p[2], 2 - p[2])));
		}

		return set;
	}

}