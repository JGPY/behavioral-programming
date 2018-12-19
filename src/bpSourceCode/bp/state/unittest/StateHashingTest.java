package bpSourceCode.bp.state.unittest;

/* A very simple test. 
 * Two bthreads issue a pair of events repeatedly.
 * no blocking, no bad, no hot.
 * they label their states  - so after all possible ordering the MC run stops.
 * A Det run allows the first BT to run forever. 
 * 
 */

import static bpSourceCode.bp.BProgram.labelNextVerificationState;
import static bpSourceCode.bp.eventSets.EventSetConstants.all;
import static bpSourceCode.bp.eventSets.EventSetConstants.none;
import bpSourceCode.bApplication.BApplication;
import static bpSourceCode.bp.BProgram.bp;
import bpSourceCode.bp.BProgram;
import bpSourceCode.bp.BThread;
import bpSourceCode.bp.Event;
import bpSourceCode.bp.exceptions.BPJException;

public class StateHashingTest implements BApplication {

	static public void main(String arg[]) {
		try {

			BProgram.startBApplication(StateHashingTest.class, "sourceCode.bp.state.unittest");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void runBApplication() {
		try {

			bp.setBThreadEpsilon(100.0);
			bp.add(new S1(), 201.0);
			bp.add(new S2(), 202.0);
			bp.add(new logger(), 203.0);

			bp.startAll();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	transient static Event E1 = new Event("E1");
	transient static Event E2 = new Event("E2");
	transient static Event E3 = new Event("E3");
	transient static Event E4 = new Event("E4");

	static class S1 extends BThread {
		public void runBThread() throws BPJException {
			for (;;) {
				labelNextVerificationState("Person1");
				bp.bSync(E1, none, none);
				labelNextVerificationState("Person2");
				bp.bSync(E2, none, none);
			}
		}
	}

	static class S2 extends BThread {
		public void runBThread() throws BPJException {
			for (;;) {
				labelNextVerificationState("Person1");
				bp.bSync(E3, none, none);
				labelNextVerificationState("Person2");
				bp.bSync(E4, none, none);
			}
		}
	}

	static class logger extends BThread {
		public void runBThread() throws BPJException {
			int c = 0;
			for (;;) {
				bp.bSync(none, all, none);
				System.out.println("Event #" + (c++) + " : " + bp.lastEvent);
			}
		}
	}

}
