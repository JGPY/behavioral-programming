package bpSourceCode.bp.state.unittest.ts;

import static bpSourceCode.bp.BProgram.bp;
import static bpSourceCode.bp.eventSets.EventSetConstants.none;
import static bpSourceCode.bp.state.unittest.ts.TSState.COLD;
import static bpSourceCode.bp.state.unittest.ts.TSState.NOTBAD;
import bpSourceCode.bApplication.BApplication;
import bpSourceCode.bp.BProgram;
import bpSourceCode.bp.Event;
import bpSourceCode.bp.eventSets.RequestableEventSet;

// moveCar.Main class
public class TSMain implements BApplication {

	static Event NOEVENT = new Event("NOEVENT");

	// ----------------------------------------
	static public void main(String arg[]) {
		try {
			BProgram.startBApplication(TSMain.class, "sourceCode.bp.state.unittest");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// The real "MAIN" program is below - under RUN (for Javaflow).
	double p = 1.0;

	public void runBApplication() {

		// The set of events
		RequestableEventSet goodMorningEvent = new RequestableEventSet(new Event("GoodMorningEvent"));
		RequestableEventSet goodEveningEvent = new RequestableEventSet(new Event("GoodEveningEvent"));

		TSBThread goodEveningBT = new TSBThread("GOOD_EVENING_BT");
		TSBThread displayEventsBT = new TSBThread("DISPLAY_EVENTS_BT");
		TSBThread interleaveBT = new TSBThread("INTERLEAVE_BT");

		// B-THREAD goodMorningBT
		TSBThread goodMorningBT = new TSBThread("GOOD_MORNING_BT");
		// Define States
		TSState gmS1 = new TSState("GM-S1", COLD, NOTBAD);
		TSState gmS2 = new TSState("GM-S2", COLD, NOTBAD);
		TSState gmS3 = new TSState("GM-S3", COLD, NOTBAD);
		TSState gmS4 = new TSState("GM-S4", COLD, NOTBAD);
		// Requested
		gmS1.requestedEvents.add(goodMorningEvent);
		gmS2.requestedEvents.add(goodMorningEvent);
		gmS3.requestedEvents.add(goodMorningEvent);
		gmS4.requestedEvents = new RequestableEventSet(none);
		// Add states
		goodMorningBT.states.add(gmS1);
		goodMorningBT.states.add(gmS2);
		goodMorningBT.states.add(gmS3);
		goodMorningBT.states.add(gmS4);
		// Transitions
		goodMorningBT.transitions.add(new TSTransition(gmS1, goodMorningEvent, gmS2));
		goodMorningBT.transitions.add(new TSTransition(gmS2, goodMorningEvent, gmS3));
		goodMorningBT.transitions.add(new TSTransition(gmS3, goodMorningEvent, gmS4));
		goodMorningBT.initialState = gmS1;

		// B-Thread goodEveningBT
		// Define states
		TSState geS1 = new TSState("GE-S1", COLD, NOTBAD);
		TSState geS2 = new TSState("GE-S2", COLD, NOTBAD);
		TSState geS3 = new TSState("GE-S3", COLD, NOTBAD);
		TSState geS4 = new TSState("GE-S4", COLD, NOTBAD);
		// Add states
		goodEveningBT.states.add(geS1);
		goodEveningBT.states.add(geS2);
		goodEveningBT.states.add(geS3);
		goodEveningBT.states.add(geS4);
		// Requested
		geS1.requestedEvents.add(goodEveningEvent);
		geS2.requestedEvents.add(goodEveningEvent);
		geS3.requestedEvents.add(goodEveningEvent);
		geS4.requestedEvents = new RequestableEventSet(none);
		// Transitions
		goodEveningBT.transitions.add(new TSTransition(geS1, goodEveningEvent, geS2));
		goodEveningBT.transitions.add(new TSTransition(geS2, goodEveningEvent, geS3));
		goodEveningBT.transitions.add(new TSTransition(geS3, goodEveningEvent, geS4));
		goodEveningBT.initialState = geS1;

		// B-THREAD DisplayEventsBT
		// Define States
		TSState deS1 = new TSState("DE-S1", COLD, NOTBAD);
		// Add states
		displayEventsBT.states.add(deS1);
		// Requested
		deS1.requestedEvents = new RequestableEventSet(none);
		// Transitions
		displayEventsBT.transitions.add(new TSTransition(deS1, new RequestableEventSet(goodMorningEvent, goodEveningEvent), deS1));
		displayEventsBT.initialState = deS1;

		// B-THREAD INTERLEAVE
		// Define States
		TSState interleaveS1 = new TSState("INTERLEAVE-S1", COLD, NOTBAD);
		TSState interleaveS2 = new TSState("INTERLEAVE-S2", COLD, NOTBAD);
		interleaveBT.states.add(interleaveS1);
		interleaveBT.states.add(interleaveS2);
		// Requested
		interleaveS1.requestedEvents = new RequestableEventSet(none);
		interleaveS2.requestedEvents = new RequestableEventSet(none);
		// Blocked
		interleaveS1.blockedEvents.add(goodEveningEvent);
		interleaveS2.blockedEvents.add(goodMorningEvent);
		// Transitions
		interleaveBT.transitions.add(new TSTransition(interleaveS1, goodMorningEvent, interleaveS2));
		interleaveBT.transitions.add(new TSTransition(interleaveS2, goodEveningEvent, interleaveS1));
		interleaveBT.initialState = interleaveS1;

		try {

			bp.add(goodMorningBT, 1.0);
			bp.add(goodEveningBT, 2.0);
			bp.add(displayEventsBT, 3.0);
			bp.add(interleaveBT, 4.0);
			bp.add(new Logger(), 5.0);
			bp.startAll();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
