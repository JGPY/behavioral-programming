package bpSourceCode.bp.state.unittest;

/* Persons crossing bridge with a torch. 

 Four persons come to a river at night. There is a narrow bridge that
 can only hold two people at a time. A torch must be used when crossing
 the bridge. The first person can cross the bridge in 5 minutes, the
 second in 10, the third in 20, and the fourth in 25 minutes. When two
 people cross the bridge together, they must move at the slower
 person's pace, and the torch cannot be tossed over the bridge. The
 question is, can all four get across the bridge in 60 minutes or less?

 Note that the b-threads don't label the states, hence
 -DdisableStateHashing is required
 */
import static bpSourceCode.bp.eventSets.EventSetConstants.none;

import java.io.Serializable;
import java.util.HashMap;

import bpSourceCode.bApplication.BApplication;
import bpSourceCode.bp.BProgram;
import bpSourceCode.bp.BThread;
import bpSourceCode.bp.Event;
import bpSourceCode.bp.eventSets.EventSet;
import bpSourceCode.bp.eventSets.EventSetInterface;
import bpSourceCode.bp.eventSets.RequestableInterface;
import bpSourceCode.bp.exceptions.BPJException;

public class BridgeBApp implements BApplication {
	static public void main(String arg[]) {
		try {

			BProgram.startBApplication(BridgeBApp.class, "sourceCode.bp.state.unittest");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void runBApplication() {
		System.out.println("Across the bridge in 60 minutes..    ");
		bp = new BProgram();
		bp.setBThreadEpsilon(100.0);
		try {
			bp.add(new Person1Crossing(), 201.0);
			bp.add(new Person2Crossing(), 202.0);
			bp.add(new Person3Crossing(), 203.0);
			bp.add(new Person4Crossing(), 204.0);
			bp.add(new TorchCrossing(), 205.0);
			bp.add(new OneOrTwoPersonsWithTorch(), 206.0);
			bp.add(new Watcher(), 207.0);

			bp.startAll();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	transient static Event p1Go = new Event("p1Go");
	transient static Event p1Ret = new Event("p1Ret");
	transient static Event p2Go = new Event("p2Go");
	transient static Event p2Ret = new Event("p2Ret");
	transient static Event p3Go = new Event("p3Go");
	transient static Event p3Ret = new Event("p3Ret");
	transient static Event p4Go = new Event("p4Go");
	transient static Event p4Ret = new Event("p4Ret");
	transient static Event tGo = new Event("tGo");
	transient static Event tRet = new Event("tRet");
	transient static EventSet allPersonsGo = new EventSet(p1Go, p2Go, p3Go, p4Go);
	transient static EventSet allPersonsRet = new EventSet(p1Ret, p2Ret, p3Ret, p4Ret);
	transient static EventSet allPersonsMoves = new EventSet(allPersonsGo, allPersonsRet);
	transient static EventSet allTorchMoves = new EventSet(tGo, tRet);
	transient static EventSet allMoves = new EventSet(allPersonsMoves, allTorchMoves);

	transient static BProgram bp;

	@SuppressWarnings("serial")
	static class Person1Crossing extends BehaviorThread {
		public void runBThread() throws BPJException {
			for (;;) {
				requested = p1Go;
				waitedFor = none;
				blocked = p1Ret;
				bSync(requested, waitedFor, blocked);
				requested = p1Ret;
				waitedFor = none;
				blocked = p1Go;
				bSync(requested, waitedFor, blocked);

			}
		}
	}

	@SuppressWarnings("serial")
	static class Person2Crossing extends BehaviorThread {
		public void runBThread() throws BPJException {
			for (;;) {
				bSync(p2Go, none, p2Ret);
				bSync(p2Ret, none, p2Go);
			}
		}
	}

	@SuppressWarnings("serial")
	static class Person3Crossing extends BehaviorThread {
		public void runBThread() throws BPJException {
			for (;;) {
				bSync(p3Go, none, p3Ret);
				bSync(p3Ret, none, p3Go);
			}
		}
	}

	@SuppressWarnings("serial")
	static class Person4Crossing extends BehaviorThread {
		public void runBThread() throws BPJException {
			for (;;) {
				bSync(p4Go, none, p4Ret);
				bSync(p4Ret, none, p4Go);
			}
		}
	}

	@SuppressWarnings("serial")
	static class TorchCrossing extends BehaviorThread {
		public void runBThread() throws BPJException {
			for (;;) {
				requested = tGo;
				blocked = new EventSet(tRet, allPersonsRet);
				bSync(requested, none, blocked);

				requested = tRet;
				blocked = new EventSet(tGo, allPersonsGo);
				bSync(requested, none, blocked);
			}
		}
	}

	@SuppressWarnings("serial")
	static class OneOrTwoPersonsWithTorch extends BehaviorThread {
		public void runBThread() throws BPJException {
			for (;;) {

				bSync(none, allPersonsMoves, allTorchMoves);

				bSync(none, allMoves, none);

				if (!allTorchMoves.contains(bp.lastEvent))
					bp.bSync(none, allTorchMoves, allPersonsMoves);

			}
		}
	}

	static int d = 0;

	static HashMap<State, Integer> bestTimeForState = new HashMap<State, Integer>();

	@SuppressWarnings("serial")
	static class Watcher extends BThread {
		public void runBThread() throws BPJException {
			int moveTime = 0;
			int totalTime = 0;

			State state = new State();
			bestTimeForState.put(state, 0);

			String log = "";

			for (;;) {
				bp.bSync(none, new EventSet(p1Go, p1Ret, p2Go, p2Ret, p3Go, p3Ret, p4Go, p4Ret, tGo, tRet), none);

				// System.out.println("Event #" + (c++) + ": " + sourceCode.bp.lastEvent);
				log += ":" + bp.lastEvent;

				if (bp.lastEvent.equals(tGo) || bp.lastEvent.equals(tRet)) {
					state.switchT();

					totalTime += moveTime;
					moveTime = 0;
					log += "[" + totalTime + ":" + state + "]";

					Integer best = bestTimeForState.get(state);

					if (state.allSoldiersAtTheWestBank() && totalTime <= 60) {
						System.out.println(bestTimeForState.size());
						bp.markNextVerificationStateAsBad(log);
					} else {

						if (best != null && best <= totalTime) {
							System.out.println("Revisited: " + log + "(" + d++ + ")");
							bp.pruneSearchNow();
						} else {
							bestTimeForState.put(state, totalTime);
						}

						if (totalTime >= 60) {
							System.out.println("Pruning: " + log + "(" + d++ + ")");
							bp.pruneSearchNow();
						}
					}
				} else if (bp.lastEvent.equals(p1Go) || bp.lastEvent.equals(p1Ret)) {
					moveTime = Math.max(moveTime, 25);
					state.switchS1();
				} else if (bp.lastEvent.equals(p2Go) || bp.lastEvent.equals(p2Ret)) {
					moveTime = Math.max(moveTime, 20);
					state.switchS2();
				} else if (bp.lastEvent.equals(p3Go) || bp.lastEvent.equals(p3Ret)) {
					moveTime = Math.max(moveTime, 10);
					state.switchS3();
				} else if (bp.lastEvent.equals(p4Go) || bp.lastEvent.equals(p4Ret)) {
					moveTime = Math.max(moveTime, 5);
					state.switchS4();
				}

				// System.out.println("log: " + log);

			}
		}

	}

	@SuppressWarnings("serial")
	static abstract class BehaviorThread extends BThread {
		RequestableInterface requested;
		EventSetInterface waitedFor;
		EventSetInterface blocked;

		public void bSync(RequestableInterface r, EventSetInterface w, EventSetInterface b) {
			bp.bSync(r, w, b);
		}

	}
}

/**
 * A representation of the state of the system.
 */
@SuppressWarnings("serial")
class State implements Serializable {
	static public enum BANK {
		EAST, WEST
	};

	BANK s1 = BANK.EAST, s2 = BANK.EAST, s3 = BANK.EAST, s4 = BANK.EAST, t = BANK.EAST;

	void switchS1() {
		s1 = (s1 == BANK.EAST ? BANK.WEST : BANK.EAST);
	}

	public boolean allSoldiersAtTheWestBank() {
		return s1 == BANK.WEST && s2 == BANK.WEST && s3 == BANK.WEST && s4 == BANK.WEST;
	}

	void switchS2() {
		s2 = (s2 == BANK.EAST ? BANK.WEST : BANK.EAST);
	}

	void switchS3() {
		s3 = (s3 == BANK.EAST ? BANK.WEST : BANK.EAST);
	}

	void switchS4() {
		s4 = (s4 == BANK.EAST ? BANK.WEST : BANK.EAST);
	}

	void switchT() {
		t = (t == BANK.EAST ? BANK.WEST : BANK.EAST);
	}

	@Override
	public int hashCode() {
		int result = 0;

		if (s1 == BANK.WEST)
			result += 1;

		result *= 2;

		if (s2 == BANK.WEST)
			result += 1;

		result *= 2;

		if (s3 == BANK.WEST)
			result += 1;

		result *= 2;

		if (s4 == BANK.WEST)
			result += 1;

		result *= 2;

		if (t == BANK.WEST)
			result += 1;

		return result;
	}

	public boolean equals(Object obj) {
		return hashCode() == obj.hashCode();
	}

	@Override
	public String toString() {
		return (s1 == BANK.EAST ? "E" : "W") + (s2 == BANK.EAST ? "E" : "W") + (s3 == BANK.EAST ? "E" : "W") + (s4 == BANK.EAST ? "E" : "W") + "-" + (t == BANK.EAST ? "E" : "W");
	}

}
