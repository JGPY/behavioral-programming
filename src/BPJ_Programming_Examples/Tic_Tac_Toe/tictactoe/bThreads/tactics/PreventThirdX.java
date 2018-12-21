package BPJ_Programming_Examples.Tic_Tac_Toe.tictactoe.bThreads.tactics;

import static bp.BProgram.bp;
import static bp.BProgram.labelNextVerificationState;
import static bp.eventSets.EventSetConstants.none;

import java.util.ArrayList;
import java.util.List;

import BPJ_Programming_Examples.Tic_Tac_Toe.tictactoe.events.O;
import BPJ_Programming_Examples.Tic_Tac_Toe.tictactoe.events.X;
import BPJ_Programming_Examples.Tic_Tac_Toe.tictactoe.externalApp.Square;
import bp.BThread;
import bp.exceptions.BPJException;

/**
 * A scenario that tries to block a row/column/diagonal of Xs by putting an O in
 * the last remaining square of it.
 */
@SuppressWarnings("serial")
public class PreventThirdX extends BThread {

	Square[] sq;

	private X firstSquare;
	private X secondSquare;
	private O triggeredEvent;

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
		bp.bSync(triggeredEvent, none, none);

		labelNextVerificationState("3333"+firstSquare+secondSquare);
		bp.bSync(none, none, none);
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
	static public List<BThread> constructInstances() {

//		Set<BThread> set = new HashSet<BThread>();
		List<BThread> list = new ArrayList<BThread>();

		// All 6 permutations of three elements
		int[][] permutations = new int[][] { new int[] { 0, 1, 2 },
				new int[] { 0, 2, 1 }, new int[] { 1, 0, 2 },
				new int[] { 1, 2, 0 }, new int[] { 2, 0, 1 },
				new int[] { 2, 1, 0 } };

		for (int[] p : permutations) {
			// Run copies for each row
			for (int row = 0; row < 3; row++) {
				list.add(new PreventThirdX(new X(row, p[0]),
						new X(row, p[1]), new O(row, p[2])));
			}

			// Run copies for each column
			for (int col = 0; col < 3; col++) {

				list.add(new PreventThirdX(new X(p[0], col),
						new X(p[1], col), new O(p[2], col)));
			}

			// Run copies for the Main diagonal
			list.add(new PreventThirdX(new X(p[0], p[0]),
					new X(p[1], p[1]), new O(p[2], p[2])));

			// Run copies for the inverse diagonal
			list.add(new PreventThirdX(new X(p[0], 2 - p[0]),
					new X(p[1], 2 - p[1]), new O(p[2], 2 - p[2])));
		}

		return list;
	}

}
