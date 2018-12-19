package bpSourceCode.bp.state;

import static bpSourceCode.bp.BProgramControls.continuationMode;
import static bpSourceCode.bp.state.Fairness.fairCycle;

import java.io.IOException;
import java.util.HashSet;
import java.util.Stack;

import bpSourceCode.bp.BProgram;
import bpSourceCode.bp.Event;
import bpSourceCode.bp.exceptions.VerificationHotStateException;

public class LivenessVerification {
	public static int dfs1Counter = 0;
	public static int dfs2Counter = 0;

	// All reachable states (similar to R in Katoen book algorithm)
	static HashSet<BPState> reachableStates = new HashSet<BPState>();

	// Path to latest/current reachable (similar to U in alg.)
	static Stack<BPTransition> reachablesStack = new Stack<BPTransition>();

	// Current path hot cycle being evaluated (similar to V in alg.)
	static Stack<BPTransition> cycleStack = new Stack<BPTransition>();

	// All states visited in current search for hot cycle for one starting
	// state.
	// Used to avoid repetitions.
	static HashSet<BPState> cycleSearchStates = new HashSet<BPState>();

	// Events that were triggered in the presently evaluated cycle
	// (Events triggered infinitely often).

	static Stack<Event> triggeredEventsInCycle = new Stack<Event>();
	static HashSet<Event> pendingEventsInCycle = new HashSet<Event>();
	
	// Initial state; 
	static BPState init = null; 

	// --------------------------------------------------------------
	/**
	 * Check if there is any run that is "Eventually Always HOT". (negation of
	 * the liveness requirement that infinitely often cold). It looks for cycles
	 * in which all states are hot. First implementation is naive, and less
	 * efficient with the goal of avoiding bugs, and getting initial results;
	 */
	public static void verifyLiveness(BProgram bp) throws IOException, ClassNotFoundException {
		continuationMode = true;
		init = BPState.startWith(bp); // Get initial state.
		// Start nested search with dfs1 and dfs2.
		dfs1(init);
		System.out.println("DFS1counter=" + dfs1Counter + " DFS2counter=" + dfs2Counter);
	}

	// The outer dfs controls that we check cycles from each state only once,
	// and also keeps track of how one gets from initial state to the beginning
	// of the cycle
	private static void dfs1(BPState reachableState) throws IOException, ClassNotFoundException {
		// System.out.println("DFS1( " + reachableState + " )");

		// Remember newly visited states. If previously visited states - we
		// already know the answer
		// is "false".

		if (!reachableStates.add(reachableState))
			return;
		dfs1Counter++;
		System.out.println("dfs1 " + dfs1Counter);

	// 	reachablesStack.push(reachableState); // remember the path to it
		// - inclusive
		// now check if it starts a hot cycle (current-=candidate)
		// Reset list of visited states in the cycle search.
		cycleSearchStates.clear();

		if (reachableState.isHot()) {// only check hot cycles on hot BPStates.
			dfs2(reachableState, reachableState); // Check
		}
		// Not a hot cycle starter itself. Continue search for reachables.
		for (Event e : reachableState.enabledEvents) {
			BPState s = BPState.continueWith(reachableState, e); // get
			// successor and evaluate it
			reachablesStack.push(new BPTransition (reachableState, s, e)); 
			dfs1(s);
			reachablesStack.pop() ; 
			
		}
		return;

	}

	/**
	 * Returns TRUE if finds a back edge from currentState or after, to
	 * candidateCycleStarter
	 * 
	 * Optionally consider fairness assumptions - the cycle cannot include a
	 * choice where alternatives existed that were not subsequently taken.
	 * 
	 * In other words - for every state in the cycle - all its successors must
	 * appear exactly once.
	 * 
	 * This method is called only with hot states as parameters1.
	 */
	static void dfs2(BPState currentState, BPState candidateCycleStarter) throws IOException, ClassNotFoundException {

		BPTransition t;

		dfs2Counter++;
				System.out.println("dfs2 " + dfs2Counter);
		// Finite runs that end in hot state are bad.
		if (currentState.enabledEvents.isEmpty())
			throw (new VerificationHotStateException(buildLivenessCounterExample()));

		// Now trigger events one at a time to go to successor states
		for (Event e : currentState.enabledEvents) {
			BPState s = BPState.continueWith(currentState, e); // get successor
			// If we visited this state already - abandon. Remember new visit.
			if (!cycleSearchStates.add(s)) {
				continue;
			}
			if (!s.isHot()) {
				continue;
			}
			// New hot state in path from candidate.
			// (exclusive of the candidateCycleStarter)

			// Remember the event and the new state
			t = new BPTransition(currentState, s, e);
			cycleStack.push(t);

			// Does it appear to complete a cycle?
			if (s.equals(candidateCycleStarter) && fairCycle(candidateCycleStarter))

				throw (new VerificationHotStateException(buildLivenessCounterExample()));
			// s didn't complete a cycle. Is it PART of a cycle?
			dfs2(s, candidateCycleStarter);
			// s is NOT part of a cycle. Go check his sibling.
			cycleStack.pop();
		}
		// Examined all successors of current
		return;
	}

	static String buildLivenessCounterExample() {
		String st;
		BPTransition t = null;
		int i;
		st = "\n ---Reached Cycle/Finite Word: \n"; 
		st+= "Initial State: " + init + "\n"; 
		st += "States to cycle starter: \n";
		for (i = 0; i < reachablesStack.size(); i++) {
			t = reachablesStack.get(i); 
		st += " <" + t.event + "> " + t.toState + "\n";
		} 
		st += "Cycle/Part II of word:\n";
		for (i = 0; i < cycleStack.size(); i++) {
			t = cycleStack.get(i);
			st += " <" + t.event + "> " + t.toState + "\n";
		}
		st += "DFS1counter=" + dfs1Counter + " DFS2counter=" + dfs2Counter + "\n";

		return st;
	}

}
