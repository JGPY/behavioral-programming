package bpSourceCode.bp.state.unittest.ph;

import static bpSourceCode.bp.BProgram.bp;
import static bpSourceCode.bp.BProgram.markNextVerificationStateAsHot;
import static bpSourceCode.bp.eventSets.EventSetConstants.none;
import static bpSourceCode.bp.state.unittest.ph.DiningPhilsBApp.nPhils;
import static bpSourceCode.bp.state.unittest.ph.DiningPhilsBApp.pickUpLeftForkEvents;
import static bpSourceCode.bp.state.unittest.ph.DiningPhilsBApp.putDowns;
import bpSourceCode.bp.BThread;
import bpSourceCode.bp.eventSets.EventSet;
import bpSourceCode.bp.exceptions.BPJRequestableSetException;

@SuppressWarnings("serial")
public class LTL_Phil_0_Eat_Infinitely_Often extends BThread {

	public void runBThread() throws InterruptedException, BPJRequestableSetException {
		EventSet postEat = new EventSet(putDowns[0], putDowns[nPhils - 1]);

		while (true) {
			markNextVerificationStateAsHot();
			bp.bSync(none, pickUpLeftForkEvents[0], none);
			// System.out.println("LTL1: saw 0 eating");
			bp.bSync(none, postEat, none);
		}

	}

}
