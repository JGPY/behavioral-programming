package bpSourceCode.bp.state.unittest.ph;

import static bpSourceCode.bp.BProgram.bp;
import static bpSourceCode.bp.BProgram.labelNextVerificationState;
import static bpSourceCode.bp.eventSets.EventSetConstants.none;
import static bpSourceCode.bp.state.unittest.ph.DiningPhilsBApp.leftyPhil;
import static bpSourceCode.bp.state.unittest.ph.DiningPhilsBApp.nPhils;
import static bpSourceCode.bp.state.unittest.ph.DiningPhilsBApp.pickUpLeftForkEvents;
import static bpSourceCode.bp.state.unittest.ph.DiningPhilsBApp.pickUpRightForkEvents;
import static bpSourceCode.bp.state.unittest.ph.DiningPhilsBApp.putDowns;
import bpSourceCode.bp.BThread;
import bpSourceCode.bp.Event;
import bpSourceCode.bp.exceptions.BPJRequestableSetException;

@SuppressWarnings("serial")
public class Phil extends BThread {

	int id;
	Event leftUp;
	Event leftDown;
	Event rightUp;
	Event rightDown;
	Event firstUp;
	Event secondUp;

	public Phil(int id) {
		super("P-" + id);
		this.id = id;
		int right = id;
		int left = (id == 0) ? (nPhils - 1) : (id - 1);
		rightUp = pickUpRightForkEvents[right];
		rightDown = putDowns[right];
		leftUp = pickUpLeftForkEvents[left];
		leftDown = putDowns[left];
		firstUp = rightUp;
		secondUp = leftUp;
		if (leftyPhil) {
			if (id == (nPhils - 1)) {
				firstUp = leftUp;
				secondUp = rightUp;
				System.out.println("Left Handed: P-" + id + " " + firstUp + " then " + secondUp);
			}
		}
	}

	public void runBThread() throws InterruptedException, BPJRequestableSetException {
		while (true) {
			labelNextVerificationState("T");
			bp.bSync(firstUp, none, none);
			labelNextVerificationState("1");
			bp.bSync(secondUp, none, none);
			labelNextVerificationState("E");
			bp.bSync(rightDown, none, none);
			labelNextVerificationState("F");
			bp.bSync(leftDown, none, none);
		}

	}
}