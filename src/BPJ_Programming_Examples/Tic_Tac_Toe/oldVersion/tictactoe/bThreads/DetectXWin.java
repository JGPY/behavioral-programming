package BPJ_Programming_Examples.Tic_Tac_Toe.oldVersion.tictactoe.bThreads;

import static bpSourceCode.bp.eventSets.EventSetConstants.none;
import static BPJ_Programming_Examples.Tic_Tac_Toe.oldVersion.tictactoe.events.StaticEvents.XWin;
import static BPJ_Programming_Examples.Tic_Tac_Toe.oldVersion.tictactoe.events.StaticEvents.gameOver;

import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import BPJ_Programming_Examples.Tic_Tac_Toe.oldVersion.tictactoe.events.X;
import bpSourceCode.bp.BProgram;
import bpSourceCode.bp.BThread;
import bpSourceCode.bp.eventSets.EventSet;
import bpSourceCode.bp.exceptions.BPJException;

/**
 * A scenario that identifies wins by player X
 */
public class DetectXWin extends BThread {

	private static final Logger logger = LoggerFactory.getLogger(DetectXWin.class);

	private X firstSquare;
	private X secondSquare;
	private X thirdSquare;

	@Override
	public void runBThread() throws BPJException {
		interruptingEvents = new EventSet(gameOver);
		BProgram bp = getBProgram();

		logger.info("同步：bSync(none, firstSquare, none)");
		bp.bSync(none, firstSquare, none); // Wait for the first X
		logger.info("结束：bSync(none, firstSquare, none)");

		logger.info("同步：bSync(none, secondSquare, none)");
		bp.bSync(none, secondSquare, none); // Wait for the second X
		logger.info("结束：bSync(none, secondSquare, none)");

		logger.info("同步：bSync(none, thirdSquare, none)");
		bp.bSync(none, thirdSquare, none); // Wait for the third X
		logger.info("结束：bSync(none, thirdSquare, none)");

		logger.info("同步：bSync(XWin, none, none)");
		bp.bSync(XWin, none, none); // Announce X win
		logger.info("结束：bSync(XWin, none, none)");

	}

	/**
	 * @param x
	 * @param x2
	 * @param x3
	 */
	public DetectXWin(X x, X x2, X x3) {
		super();
		this.firstSquare = x;
		this.secondSquare = x2;
		this.thirdSquare = x3;
		this.setName("DetectXWin(" + firstSquare + "," + secondSquare + ","
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
				set.add(new DetectXWin(new X(row, p[0]), new X(row, p[1]),
						new X(row, p[2])));
			}

			// Run copies for each column
			for (int col = 0; col < 3; col++) {

				set.add(new DetectXWin(new X(p[0], col), new X(p[1], col),
						new X(p[2], col)));
			}

			// Run copies for the Main diagonal
			set.add(new DetectXWin(new X(p[0], p[0]), new X(p[1], p[1]),
					new X(p[2], p[2])));

			// Run copies for the inverse diagonal
			set.add(new DetectXWin(new X(p[0], 2 - p[0]), new X(p[1],
					2 - p[1]), new X(p[2], 2 - p[2])));
		}

		return set;
	}
}