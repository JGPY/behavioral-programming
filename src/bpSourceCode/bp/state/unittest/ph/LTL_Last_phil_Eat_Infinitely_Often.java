package bpSourceCode.bp.state.unittest.ph;

import static bpSourceCode.bp.BProgram.bp;
import static bpSourceCode.bp.BProgram.markNextVerificationStateAsHot;
import static bpSourceCode.bp.eventSets.EventSetConstants.none;
import static bpSourceCode.bp.state.unittest.ph.DiningPhilsBApp.nPhils;
import static bpSourceCode.bp.state.unittest.ph.DiningPhilsBApp.putDowns;
import bpSourceCode.bp.BThread;
import bpSourceCode.bp.eventSets.EventSet;
import bpSourceCode.bp.exceptions.BPJRequestableSetException;

@SuppressWarnings("serial")
public class LTL_Last_phil_Eat_Infinitely_Often extends BThread {

	public void runBThread() throws InterruptedException, BPJRequestableSetException {
		EventSet postEat = new EventSet(putDowns[nPhils - 1], putDowns[0]);

		while (true) {
			markNextVerificationStateAsHot();
			// sourceCode.bp.bSync(none, pickUp[nPhils-1], none);
			// ***** Need to fix to have correct fork based on right or left
			System.out.println("LTL1: saw LAST eating");
			bp.bSync(none, postEat, none);
		}

	}

}
