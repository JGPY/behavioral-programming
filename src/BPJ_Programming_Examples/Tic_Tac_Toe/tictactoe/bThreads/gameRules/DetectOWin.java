package BPJ_Programming_Examples.Tic_Tac_Toe.tictactoe.bThreads.gameRules;

import static bp.BProgram.bp;
import static bp.BProgram.labelNextVerificationState;
import static bp.BProgramControls.globalRunMode;
import static bp.eventSets.EventSetConstants.all;
import static bp.eventSets.EventSetConstants.none;
import static BPJ_Programming_Examples.Tic_Tac_Toe.tictactoe.events.StaticEvents.OWin;

import java.util.HashSet;
import java.util.Set;

import BPJ_Programming_Examples.Tic_Tac_Toe.tictactoe.events.O;
import bp.BThread;
import bp.RunMode;
import bp.exceptions.BPJException;

/**
 * A scenario that identifies wins by player O
 */
@SuppressWarnings("serial")
public class DetectOWin extends BThread {

	private O firstSquare;
	private O secondSquare;
	private O thirdSquare;

	@Override
	public void runBThread() throws BPJException {

		// Wait for the first O
		labelNextVerificationState("0");
		bp.bSync(none, firstSquare, none);

		// Wait for the second O
		labelNextVerificationState("1");
		bp.bSync(none, secondSquare, none);

		// Wait for the third O
		labelNextVerificationState("2");
		bp.bSync(none, thirdSquare, none);

		// Announce O win
		labelNextVerificationState("3");
		if (globalRunMode == RunMode.MCSAFETY || globalRunMode == RunMode.MCLIVENESS) {
			bp.pruneAtNextVerificationState("O Won!");
		}
		bp.bSync(OWin, none, none);
		bp.bSync(none, none, all);
	}

	/**
	 * @param firstSquare
	 * @param seconfSquare
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

			// Run copies for the Main diagonal
			set.add(new DetectOWin(new O(p[0], p[0]), new O(p[1], p[1]), new O(
					p[2], p[2])));

			// Run copies for the inverse diagonal
			set.add(new DetectOWin(new O(p[0], 2 - p[0]),
					new O(p[1], 2 - p[1]), new O(p[2], 2 - p[2])));
		}

		return set;
	}

}