package bpSourceCode.bp.state.unittest.br;

import static bpSourceCode.bp.BProgram.bp;
import static bpSourceCode.bp.eventSets.EventSetConstants.none;

import java.util.HashMap;

import bpSourceCode.bApplication.BApplication;
import bpSourceCode.bp.BProgram;
import bpSourceCode.bp.BThread;
import bpSourceCode.bp.Event;
import bpSourceCode.bp.eventSets.EventSet;
import bpSourceCode.bp.exceptions.BPJException;

public class BridgeSymbolic implements BApplication {

	static int nPersons = 4;
	static Event[] pGo = new Event[nPersons];
	static Event[] pRet = new Event[nPersons];
	static Event tGo = new Event("tGo");
	static Event tRet = new Event("tRet");
	static EventSet allPersonsGo = new EventSet();
	static EventSet allPersonsRet = new EventSet();
	static int[] personTime = { 5, 10, 20, 25 };
	static {
		for (int i = 0; i < nPersons; i++) {
			pGo[i] = new Event("p" + i + "Go");
			pRet[i] = new Event("p" + i + "Ret");
			allPersonsGo.add(pGo[i]);
			allPersonsRet.add(pRet[i]);
		}
	}

	static EventSet allTorchMoves = new EventSet(tGo, tRet);
	static EventSet allPersonsMoves = new EventSet(allPersonsGo, allPersonsRet);
	static EventSet allMoves = new EventSet(allPersonsMoves, allTorchMoves);
	static EventSet torchGoBlocks = new EventSet(tRet, allPersonsRet);
	static EventSet torchRetBlocks = new EventSet(tGo, allPersonsGo);

	static public void main(String arg[]) {
		try {
			BProgram.startBApplication(BridgeSymbolic.class, "sourceCode.bp.state.unittest.br");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void runBApplication() {
		bp.setBThreadEpsilon(100.0);
		System.out.println("Across the bridge in 60 minutes..    ");
		try {
			bp.add(new PersonCrossing(0), 201.0);
			bp.add(new PersonCrossing(1), 202.0);
			bp.add(new PersonCrossing(2), 203.0);
			bp.add(new PersonCrossing(3), 204.0);
			bp.add(new TorchCrossing(), 205.0);
			bp.add(new OneOrTwoPersonsWithTorch(), 206.0);
			bp.add(new Watcher(), 207.0);
			bp.startAll();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@SuppressWarnings("serial")
	static class PersonCrossing extends BThread {
		int id;

		public void runBThread() throws BPJException {

			for (;;) {
				bp.bSync(pGo[id], none, pRet[id]);

				bp.bSync(pRet[id], none, pGo[id]);

			}
		}

		public PersonCrossing(int id) {
			this.id = id;
		}
	}

	@SuppressWarnings("serial")
	static class TorchCrossing extends BThread {
		public void runBThread() throws BPJException {
			for (;;) {
				bp.bSync(tGo, none, torchGoBlocks);
				bp.bSync(tRet, none, torchRetBlocks);
			}
		}
	}

	@SuppressWarnings("serial")
	static class OneOrTwoPersonsWithTorch extends BThread {
		public void runBThread() throws BPJException {
			for (;;) {

				bp.bSync(none, allPersonsMoves, allTorchMoves);
				bp.bSync(none, allMoves, none);

				if (!allTorchMoves.contains(bp.lastEvent))
					bp.bSync(none, allTorchMoves, allPersonsMoves);
			}
		}
	}

	static int backtrackCount = 0;

	static HashMap<CurrentState, Integer> bestTimeForState = new HashMap<CurrentState, Integer>();

	@SuppressWarnings("serial")
	static class Watcher extends BThread {
		public void runBThread() throws BPJException {
			int moveTime = 0;
			int totalTime = 0;

			CurrentState state = new CurrentState();
			bestTimeForState.put(state, 0);

			String log = "LOG=";

			for (;;) {
				bp.bSync(none, allMoves, none);

				// System.out.println("Event #" + (c++) + ": " + sourceCode.bp.lastEvent);
				log += ":" + bp.lastEvent;

				if (bp.lastEvent.equals(tGo) || bp.lastEvent.equals(tRet)) {
					state.switchT();

					totalTime += moveTime;
					moveTime = 0;
					log += "[" + totalTime + ":" + state + "]";

					Integer best = bestTimeForState.get(state);

					if (state.allPersonsAtTheWestBank() && totalTime <= 60) {
						System.out.println(state);
						bp.markNextVerificationStateAsBad(log);
					} else {

						if (best != null && best <= totalTime) {
							System.out.println("Revisited before time limit: " + log + "(" + backtrackCount++ + ")");
							bp.pruneSearchNow();
						} else {
							bestTimeForState.put(state, totalTime);
						}

						if (totalTime >= 60) {
							System.out.println("Pruning after time limit: " + log + "(" + backtrackCount++ + ")");
							bp.pruneSearchNow();
						}
					}
				} else
					for (int i = 0; i < nPersons; i++) {

						if (bp.lastEvent.equals(pGo[i]) || bp.lastEvent.equals(pRet[i])) {
							moveTime = Math.max(moveTime, personTime[i]);
							state.switchP(i);
							break;
						}
					}

				 System.out.println("EVENT LOG: " + log);

			}
		}

	}

}
