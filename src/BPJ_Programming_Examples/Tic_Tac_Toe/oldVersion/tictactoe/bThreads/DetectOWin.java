package BPJ_Programming_Examples.Tic_Tac_Toe.oldVersion.tictactoe.bThreads;

import static bpSourceCode.bp.eventSets.EventSetConstants.none;
import static BPJ_Programming_Examples.Tic_Tac_Toe.oldVersion.tictactoe.events.StaticEvents.OWin;
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
 * A scenario that identifies wins by player O
 */
public class DetectOWin extends BThread {

	private static final Logger logger = LoggerFactory.getLogger(DetectOWin.class);

	private O firstSquare;
	private O secondSquare;
	private O thirdSquare;

	@Override
	public void runBThread() throws BPJException {
		interruptingEvents = new EventSet(gameOver);

		BProgram bp = getBProgram();
		logger.info("同步：bSync(none, firstSquare, none)");
		bp.bSync(none, firstSquare, none);  // Wait for the first O
		logger.info("结束：bSync(none, firstSquare, none)");

		logger.info("同步：bSync(none, secondSquare, none)");
		bp.bSync(none, secondSquare, none);  // Wait for the second O
		logger.info("结束：bSync(none, secondSquare, none)");

		logger.info("同步：bSync(none, thirdSquare, none)");
		bp.bSync(none, thirdSquare, none); // Wait for the third O
		logger.info("结束：bSync(none, thirdSquare, none)");

		logger.info("同步：bSync(OWin, none, none)");
		bp.bSync(OWin, none, none);    // Announce O win
		logger.info("结束：bSync(OWin, none, none)");
	}

	/**
	 * @param firstSquare
	 * @param secondSquare
	 * @param thirdSquare
	 */
	public DetectOWin(O firstSquare, O secondSquare, O thirdSquare) {
		super();
		this.firstSquare = firstSquare;
		this.secondSquare = secondSquare;
		this.thirdSquare = thirdSquare;
		this.setName("DetectOWin(" + firstSquare + "," + secondSquare + ","
				+ thirdSquare + ")");
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
				set.add(new DetectOWin(new O(row, p[0]), new O(row, p[1]),
						new O(row, p[2])));
			}

			// Run copies for each column
			for (int col = 0; col < 3; col++) {

				set.add(new DetectOWin(new O(p[0], col), new O(p[1], col),
						new O(p[2], col)));
			}

			// Run copies for the main diagonal
			set.add(new DetectOWin(new O(p[0], p[0]), new O(p[1], p[1]),
					new O(p[2], p[2])));

			// Run copies for the inverse diagonal
			set.add(new DetectOWin(new O(p[0], 2 - p[0]), new O(p[1],
					2 - p[1]), new O(p[2], 2 - p[2])));
		}

		return set;
	}

}