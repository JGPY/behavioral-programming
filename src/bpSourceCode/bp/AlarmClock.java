package bpSourceCode.bp;

import bpSourceCode.bp.exceptions.BPJRequestableSetException;
import static bpSourceCode.bp.eventSets.EventSetConstants.none;

/**
 * This class simulates an alarm clock. It allows a bThread to sleep and other
 * bThreads to go on as usual.
 * 
 * TODO: get the wakeup event to create in the constructor in order for
 * different bThreads to set up different alarm clocks. Currently will wake up
 * all bThreads that have set up (different) alarm clocks.
 */
public class AlarmClock extends Thread {

	private long t;
	private double priority;
	private BProgram bp; // 

	/**
	 * @param t
	 *            : The time duration to sleep
	 * @param priority
	 *            : The priority of the wakeup event
	 * @param bp
	 *            : The BProgram that this clock belongs to, should be the
	 *            BProgram of the bThread that started this clock.
	 */
	public AlarmClock(BProgram bp, long t, double priority) {
		this.t = t;
		this.priority = priority;
		this.bp = bp;
	}

	public void run() {
		try {
			Thread.sleep(t);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		//AMIR: removed- this is a bug, see sourceCode.bp in constructor.
		//final BProgram sourceCode.bp = new BProgram();
		
		BThread bt = new BThread() {
			public void runBThread() throws InterruptedException, BPJRequestableSetException {
				bp.bSync(wakeup, none, none);
			}

			@Override
			public String toString() {
				return "AlarmClock";
			}
		};

		bp.add(bt, priority);
		bt.startBThread();
	}

	public Event wakeup = new Event() {
		@Override
		public String toString() {
			return "wakeup";
		}
	};
}
