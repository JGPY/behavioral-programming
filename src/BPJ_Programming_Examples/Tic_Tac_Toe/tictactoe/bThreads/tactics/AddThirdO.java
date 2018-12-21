package BPJ_Programming_Examples.Tic_Tac_Toe.tictactoe.bThreads.tactics;

import static bp.BProgram.bp;
import static bp.BProgram.labelNextVerificationState;
import static bp.eventSets.EventSetConstants.none;

import java.util.HashSet;
import java.util.Set;

import BPJ_Programming_Examples.Tic_Tac_Toe.tictactoe.events.O;
import bp.BThread;
import bp.exceptions.BPJException;
/**
 * A scenario that tries to complete a row/column/diagonal of Os
 */
@SuppressWarnings("serial")
public class AddThirdO extends BThread {

	private O firstSquare;
	private O secondSquare;
	private O triggeredEvent;

	@Override
	public void runBThread() throws BPJException {
//		interruptingEvents = new EventSet(gameOver);

		labelNextVerificationState("0");
		// Wait for the first O
		bp.bSync(none, firstSquare, none);

		labelNextVerificationState("1");
		// Wait for the second O
		bp.bSync(none, secondSquare, none);

		labelNextVerificationState("2");
		// Request the third O
		bp.bSync(triggeredEvent, none, none);

		labelNextVerificationState("3");
		bp.bSync(none, none, none);
		
	}

	/**
	 * @param firstSquare
	 * @param seconfSquare
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
				set.add(new AddThirdO(new O(row, p[0]), new O(row,
						p[1]), new O(row, p[2])));
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