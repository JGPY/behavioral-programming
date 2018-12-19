package bpSourceCode.bp.state.unittest;

/* 
 * Nine I/O buffers are connected to memory via a shared bus. Each buffer is connected to an input stream
 or an output stream, which transfers a fixed number of bytes (1 or 2
 eight-bit bytes) to or from the buffer with every clock tick. All 
 the data in each input buffer needs to be stored in memory before an
 overflow can occur, and the data in each output buffer needs to be
 retrieved from memory before the buffer is exhausted and an underflow
 occurs. (We limit our discussion below to input buffers; the
 handling of output buffers is symmetric.) Data from an input buffer
 is moved in 4-byte words into a 64-byte (16-word) register.
 One at a time, buffer registers are transferred to memory by 
 commands issued by an arbiter component. In~\cite{weisstr} it is shown that when there is no interference, a schedule exists that requires only 4-byte buffers. 
 With our tool, we were able to reproduce the results of~\cite{weisstr}, originally obtained using SMV. 
 In~\cite{ernits} the model includes a complicating interference by a
 memory-refresh cycle every certain large number of clock ticks.
 With our tool, we were able to show that occasional delays in the
 schedule eventually lead to an overflow. 

 */

import static bpSourceCode.bp.BProgram.bp;
import static bpSourceCode.bp.eventSets.EventSetConstants.none;
import bpSourceCode.bApplication.BApplication;
import bpSourceCode.bp.BProgram;
import bpSourceCode.bp.BThread;
import bpSourceCode.bp.Event;
import bpSourceCode.bp.eventSets.EventsOfClass;
import bpSourceCode.bp.eventSets.RequestableEventSet;
import bpSourceCode.bp.eventSets.RequestableInterface;
import bpSourceCode.bp.exceptions.BPJException;

public class TermaCaseBApp implements BApplication {
	static public void main(String arg[]) {
		try {
			BProgram.startBApplication(TermaCaseBApp.class, "sourceCode.bp.state.unittest");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void runBApplication() {
		bp.setBThreadEpsilon(100.0);
		try {

			bp.add(new Gen(), -2.0);
			bp.add(new FactorP1(), -1.9);
			bp.add(new FactorP2(), -1.8);

			bp.add(new Scheduler(), -1.0);

			for (int i = 0; i < 9; i++) {
				bp.add(new Buf(i), 0.1 * i);
			}

			bp.add(new FailureBlocker(), 2.0);

			bp.add(new DefaultEventGenerator(), 200.0);

			bp.startAll();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@SuppressWarnings("serial")
	static class BufferAppEvent extends Event {
		public BufferAppEvent(String name) {
			super(name);
		}
	}

	static BufferAppEvent[] bufEvent = new BufferAppEvent[9];
	// Each of these B[i] events indicates that the i-th register was emptied
	// into memory.
	static {
		for (int i = 0; i < 9; i++) {
			bufEvent[i] = new BufferAppEvent("B" + i);

		}
	}

	static BufferAppEvent nobEvent = new BufferAppEvent("nob");
	static BufferAppEvent failure = new BufferAppEvent("failure");

	static Event nop = new Event("NOP");

	static EventsOfClass allB = new EventsOfClass(BufferAppEvent.class);

	// Buffer types: 1 byte or 2 bytes are transfered in each time-tick.
	static public int[] type = new int[] { 16, 16, 16, 16, 8, 8, 8, 8, 8 };

	static final int maxB = 20;
	static final int minFailureDelay = 64;

	// Buffer sizes
	static int[] max_b = new int[] { maxB, maxB, maxB, maxB, maxB, maxB, maxB, maxB, maxB };

	// number of words in the register associated with this buffer.
	static int[] max_r = new int[] { 16, 16, 16, 16, 16, 16, 16, 16, 16 };

	// number of data bytes already in the buffer when the system starts (cursor
	// location)
	static int[] init_b = new int[] { 1, 2, 1, 2, 1, 2, 3, 4, 1 };

	// number of data words already in the register when the system starts
	// (cursor location)
	static int[] init_r = new int[] { 16, 14, 13, 11, 13, 12, 11, 10, 10 };

	static RequestableInterface[] detSchedule = { bufEvent[0], none, none, bufEvent[1], none, none, bufEvent[2], none, none, bufEvent[3], none, none, bufEvent[4], none, none, bufEvent[5], none, none,
			bufEvent[6], none, none, bufEvent[7], none, none, bufEvent[8], none, none, none, none, none, none, none, bufEvent[0], none, none, bufEvent[1], none, none, bufEvent[2], none, none,
			bufEvent[3], none, none, none, none, none, none, none, none, none, none, none, none, none, none, none, none, none, none, none, none, none, none };

	// Construct a copy of the schedule for nondeterministic choice for
	// failures.
	static RequestableInterface[] schedule = new RequestableInterface[detSchedule.length];
	static {
		for (int i = 0; i < schedule.length; i++) {
			if (detSchedule[i].equals(none)) {
				schedule[i] = none;
			} else {
				// schedule[i] = new RequestableEventSet(new
				// RequestableEventSet(detSchedule[i], failure));
				schedule[i] = detSchedule[i];
			}
		}
	}

	// Buffer behavior thread. One instance for each buffer.

	@SuppressWarnings("serial")
	static class Buf extends BThread {

		String label;

		// The identification of the buffer.
		int buffNum;

		// Number of data units that are moved together to the register
		private int Db;
		// Threshold of when to move data from buffer to register
		private int Tb;

		public Buf(int buffNum) {
			super();
			this.buffNum = buffNum;

			if (type[buffNum] == 8) {
				Db = 4;
				Tb = 4;
			} else {
				Db = 2;
				Tb = 2;
			}

		}

		public void runBThread() throws BPJException {

			// A state variable that models the current number of bytes in the
			// buffer.
			Integer b = init_b[buffNum];

			// A state variable that models the current number of words in the
			// register.
			Integer r = init_r[buffNum];

			// TODO is this variable on the stack a problem ????

			EventsOfClass AllExceptMe = new EventsOfClass(BufferAppEvent.class) {
				@Override
                public boolean contains(Object o) {
					if (o.equals(bufEvent[buffNum]))
						return false;
					else
						return super.contains(o);
				}
			};

			for (;;) {
				// The buffer's state is the number of bytes in the buf and in
				// the register.
				label = "b" + buffNum + "=" + b + " r" + buffNum + "=" + r;

				BProgram.labelNextVerificationState(label);
				bp.bSync(none, allB, none);

				if (!bp.lastEvent.equals(bufEvent[buffNum])) {
					// The last event (requested by the scheduler)
					// WAS NOT an emptying of my register.
					if (b >= Tb && r < 16) {
						// Threshold exceeded - move data to register.
						// indicate bytes that moved out;
						b -= Db;
						// indicated a word that was added to the register
						r += 1;

						// With every event/tick - more data is moved to the
						// buffer
						b = increment(b);
					} else {
						// No need to move data to register, but
						// With every event/tick - more data is moved to the
						// buffer
						b = increment(b);
					}
				} else {
					// My register was now emptied into memory

					// System.out.println("------------   Buffer " + buffNum +
					// " reset --------------");
					if (r != 16) {

						BProgram.markNextVerificationStateAsBad("Reset with r=" + r + "(resets are allowed only when r=16)");
					} else {
						// A time tick - add the bytes to the buffer.
						// TODO it will be more readable if the increment could
						// occur before the reset. (can it?)
						b = increment(b);
						// Reset the register.
						r = 0;

						// A transfer takes 3 time ticks. The next two events
						// also be my transfers.
						label = "XFER-Part2 b" + buffNum + "=" + b;
						BProgram.labelNextVerificationState(label);
						bp.bSync(bufEvent[buffNum], none, AllExceptMe);
						b = increment(b);

						label = "XFER-Part3 b" + buffNum + "=" + b;
						BProgram.labelNextVerificationState(label);
						bp.bSync(bufEvent[buffNum], none, AllExceptMe);
						b = increment(b);

					}
				}

			}
		}

		/**
		 * @param b
		 * @return
		 */
		// TODO add comments and rename to incrementAndWaitForxxxx
		private Integer increment(Integer b) {
			b++;

			BProgram.labelNextVerificationState(label);
			bp.bSync(none, allBS, none);
			if (bp.lastEvent.equals(notBlindSpot)) {
				if (b > max_b[buffNum]) {
					BProgram.markNextVerificationStateAsBad("Buffer " + buffNum + " overflow");
				}
			}

			return b;
		}
	}

	// This b-thread tries to trigger the emptying of registers with the B[i] events, 
	// per prescribed schedule.
	@SuppressWarnings("serial")
	static class Scheduler extends BThread {

		public void runBThread() throws BPJException {
			for (;;) {
				for (int s = 0; s < schedule.length; s++) {
					BProgram.labelNextVerificationState("s=" + s);
					// Try to trigger per schedule - but anything may happen.
					bp.bSync(schedule[s], allB, none);
					// If 
					// TODO need to be more careful that only B[i] or failure could occur.
					// Do we need to check?  also none could be requested. 
					if (bp.lastEvent.equals(failure)) {
						// After failure we retry the last schedule
						s--;
					}
				}
			}
		}
	}

	// This b-threads waits for a failure event, and after one
	// happens prevents a failure for a a prescribed number of next events. 
	@SuppressWarnings("serial")
	static class FailureBlocker extends BThread {
		static int c = 0;

		public void runBThread() throws BPJException {
			for (;;) {

				BProgram.labelNextVerificationState("wait for F");
				bp.bSync(none, failure, none);

				for (int s = 0; s < minFailureDelay; s++) {
					BProgram.labelNextVerificationState("blocking F" + s);
					bp.bSync(none, allB, failure);
				}
			}
		}
	}

	/**
	 * A b-thread that injects "nob" in low priority.
	 */
	@SuppressWarnings("serial")
	static class DefaultEventGenerator extends BThread {
		static int c = 0;

		public void runBThread() throws BPJException {
			for (;;) {
				bp.bSync(nobEvent, allB, none);
			}
		}
	}

	// %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
	// %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
	// %%%%% Property blind spot language (analogous to L_n language in proof).
	// %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
	// %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
	@SuppressWarnings("serial")
	static class BS extends Event {
		public BS(String name) {
			super(name);
		}
	}

	static BS isBlindSpot = new BS("isBlSp");
	static BS notBlindSpot = new BS("notBlSp");

	static RequestableEventSet allBS = new RequestableEventSet(isBlindSpot, notBlindSpot);

	// The B-Thread "Gen" repeatedly tries to generate
	// both isBlindSpot and notBlindSpot.
	// The two FACTOR b-threads control which of the two will be triggered.
	// As in L_n language.
	@SuppressWarnings("serial")
	static class Gen extends BThread {
		public void runBThread() throws BPJException {
			int c = 0;
			for (;;) {

				BProgram.labelNextVerificationState("B");
				bp.bSync(none, allB, allBS);

				if (((c++) % 1) == 0)
					System.out.println("Event #" + c + " = " + bp.lastEvent);

				BProgram.labelNextVerificationState("BS");
				bp.bSync(allBS, none, allB);

				if (((c++) % 1) == 0)
					System.out.println("Event #" + c + " = " + bp.lastEvent);

				if (c > 67 * 2 * 23) {
					bp.pruneSearchNow();
				}

			}
		}
	}

	// TODO create one FactorP class with multiple instances.
	@SuppressWarnings("serial")
	static class FactorP1 extends BThread {
		static final int p1 = 67;

		public void runBThread() throws BPJException {
			for (;;) {

				BProgram.labelNextVerificationState("0 % " + p1);
				// When modulo is zero - all events are allowed
				bp.bSync(none, allBS, none);

				// When modulo is not zero - it cannot be a blindSpot. must be
				// notBlindSpot
				for (int i = 1; i < p1; i++) {
					BProgram.labelNextVerificationState(i + " % " + p1);
					bp.bSync(none, allBS, isBlindSpot);
				}
			}
		}
	}

	// See comments for FactorP1
	@SuppressWarnings("serial")
	static class FactorP2 extends BThread {
		static final int p2 = 23;

		public void runBThread() throws BPJException {
			for (;;) {

				BProgram.labelNextVerificationState("0 % " + p2);
				bp.bSync(none, allBS, none);

				for (int i = 1; i < p2; i++) {
					BProgram.labelNextVerificationState(i + " % " + p2);
					bp.bSync(none, allBS, isBlindSpot);
				}
			}
		}
	}

}