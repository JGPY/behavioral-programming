package BPJ_Programming_Examples.Tic_Tac_Toe.tictactoe.bThreads.gameRules;

import static bp.BProgram.bp;
import static bp.BProgram.labelNextVerificationState;
import static bp.BProgram.markNextVerificationStateAsBad;
import static bp.eventSets.EventSetConstants.all;
import static bp.eventSets.EventSetConstants.none;
import static BPJ_Programming_Examples.Tic_Tac_Toe.tictactoe.events.StaticEvents.XWin;

import java.util.HashSet;
import java.util.Set;

import BPJ_Programming_Examples.Tic_Tac_Toe.tictactoe.events.X;
import bp.BThread;
import bp.exceptions.BPJException;

/**
 * A scenario that identifies wins by player X
 */
@SuppressWarnings("serial")
public class DetectXWin extends BThread {

	private X firstSquare;
	private X secondSquare;
	private X thirdSquare;

	@Override
	public void runBThread() throws BPJException {
//		interruptingEvents = new EventSet(gameOver);

		labelNextVerificationState("0");
		// Wait for the first X
		bp.bSync(none, firstSquare, none);

		labelNextVerificationState("1");
		// Wait for the second X
		bp.bSync(none, secondSquare, none);

		labelNextVerificationState("2");
		// Wait for the third X
		bp.bSync(none, thirdSquare, none);

		labelNextVerificationState("3");
		markNextVerificationStateAsBad("X Won!");
		
		// Announce X win
		bp.bSync(XWin, none, none);		
//		bp.bSync(none, none, none);
		bp.bSync(none, none, all);		
	}

	/**
	 * @param x
	 * @param seconfSquare
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