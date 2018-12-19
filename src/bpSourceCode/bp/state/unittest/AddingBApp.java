package bpSourceCode.bp.state.unittest;

import static bpSourceCode.bp.BProgram.labelNextVerificationState;
import static bpSourceCode.bp.eventSets.EventSetConstants.all;
import static bpSourceCode.bp.eventSets.EventSetConstants.none;
import static bpSourceCode.bp.BProgram.bp;
import bpSourceCode.bApplication.BApplication;
import bpSourceCode.bp.BProgram;
import bpSourceCode.bp.BThread;
import bpSourceCode.bp.Event;
import bpSourceCode.bp.eventSets.EventSet;
import bpSourceCode.bp.exceptions.BPJException;

public class AddingBApp implements BApplication {

	static public void main(String arg[]) {
		try {
			BProgram.startBApplication(AddingBApp.class, "sourceCode.bp.state.unittest");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void runBApplication() {
		try {

			bp.setBThreadEpsilon(100.0);

			bp.add(new Logger(), 300.0);

			for (int i = 0; i < nProcs; i++)
				bp.add(new ParallelAdderProcess(i), 200.0 + i);

			bp.add(new Labeller(), 230.0);

			bp.startAll();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	transient static Event[] fetch;
	transient static Event[] inc;
	transient static Event[] store;
	
	final static int nProcs = 2; // number of processes
	final static int max = 20;
	final static int val = 17;

	{
		fetch = new Event[nProcs];
		inc = new Event[nProcs];
		store = new Event[nProcs];

		for (int i = 0; i < nProcs; i++) {
			fetch[i] = new Event("fetch[" + i + "]");
			inc[i] = new Event("inc[" + i + "]");
			store[i] = new Event("store[" + i + "]");
		}
	}

	@SuppressWarnings("serial")
	static class ParallelAdderProcess extends BThread {

		int i;

		public ParallelAdderProcess(int i) {
			super("ParallelAdderProcess" + "-" + i);
			this.i = i;
		}

		public void runBThread() throws BPJException {
			for (;;) {
				labelNextVerificationState("f");
				bp.bSync(fetch[i], none, none);
				labelNextVerificationState("i");
				bp.bSync(inc[i], none, none);
				labelNextVerificationState("s");
				bp.bSync(store[i], none, none);
			}
		}
	}

	@SuppressWarnings("serial")
	static class Labeller extends BThread {

		String label(int c, int[] x) {
			String lab = new String("c=" + c + ", x=[" + x[0]);
			for (int i = 1; i < nProcs; i++) {
				lab = lab.concat("," + x[i]);
			}
			lab = lab.concat("]");
			return lab;
		}

		public void runBThread() throws BPJException {

			int[] x = new int[nProcs];

			for (int i = 0; i < nProcs; i++) {
				x[i] = 0;
			}

			EventSet fetchEvents = new EventSet();
			for (int i = 0; i < nProcs; i++)
				fetchEvents.add(fetch[i]);

			int c = 1;

			for (;;) {

				if (c < max)
					bp.bSync(none, all, none);
				else
					// c >= max
					bp.bSync(none, all, fetchEvents);

				for (int i = 0; i < nProcs; i++) {
					if (bp.lastEvent.equals(fetch[i])) {
						x[i] = c;
					}

					if (bp.lastEvent.equals(inc[i])) {
						x[i] += c;
					}

					if (bp.lastEvent.equals(store[i])) {
						c = x[i];
					}
				}

				// labelNextVerificationState("c=" + c + ", x=" + x + ", cnt=" +
				// cnt);
				labelNextVerificationState(label(c, x));

				if (c == val) {
					BProgram.markNextVerificationStateAsBad(label(c, x));
				}

			}
		}
	}

	static int totalEventCount = 0;

	@SuppressWarnings("serial")
	static class Logger extends BThread {
		public void runBThread() throws BPJException {
			int i = 0;
			for (;;) {
				bp.bSync(none, all, none);
				System.out.println("Logger: Event #" + (i++) + " : " + bp.lastEvent + "             [" + totalEventCount++ + "]");
			}
		}
	}

}
