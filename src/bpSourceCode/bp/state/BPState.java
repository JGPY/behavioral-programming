package bpSourceCode.bp.state;

import static bpSourceCode.bp.BProgramControls.continuationMode;
import static bpSourceCode.bp.BProgramControls.estimatedStates;
import static bpSourceCode.bp.BProgramControls.globalDFS;
import static bpSourceCode.bp.BProgramControls.globalDisableStateHashing;
import static bpSourceCode.bp.BProgramControls.globalRunSubtree;
import static bpSourceCode.bp.BProgramControls.globalSafetyCheck;
import static bpSourceCode.bp.BProgramControls.suppressDeadlock;
import static bpSourceCode.bp.BProgramControls.globalLogBacktracking;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

import bpSourceCode.bp.BProgram;
import bpSourceCode.bp.BThread;
import bpSourceCode.bp.Event;
import bpSourceCode.bp.exceptions.VerificationFailedException;

/**
 * 
 * A class for capturing a state of a be-program.
 * 
 */
public class BPState {

	static int contWithCounter = 0;
	/**
	 * The set of states that where already processed
	 */
	static HashSet<BPState> processed = new HashSet<BPState>(estimatedStates);

	/**
	 * The set of states to be processed
	 */
	static LinkedList<BPState> queue = new LinkedList<BPState>();

	/**
	 * Keeps record of the queue in order to query membership in O(1)
	 */

	static HashSet<BPState> queueRec = new HashSet<BPState>(estimatedStates);

	static long tProcessed = System.currentTimeMillis();
	// prune is in effect
	public static boolean pruned = false;
	public static String immediatePruningMsg = null;
	// pruning pending
	public static boolean prunedAtNextState = false;
	public static String delayedPruningMsg = "";

	/**
	 * The number of times states are revisited.
	 */
	static int revisits = 0;
	static long tRevisits;

	/**
	 * The number of transitions taken in this run (sum over all states of
	 * fan-out degree).
	 */
	static int transitions = 0;

	/**
	 * The maximal fan-out degree over all states.
	 */
	static int maxOutDegree = 0;

	/**
	 * The maximal depth (distance from initial state) in the state graph.
	 */
	static int maxDepth = 0;

	/**
	 * Counts the number of distinct states visited so far
	 */
	static int numOfDistinctStates = 0;

	/**
	 * The be-program whose state is captured
	 */
	BProgram bp;

	/**
	 * The depth of this state in the state graph
	 */
	int depth;

	/**
	 * The BPState from which this BPState was initially reached
	 */
	BPState predecessorBPS = null;
	
	/**
	 * The event by which this BPState was initially reached
	 */
	Event triggeringEvent = null;
	
	/** The states of the relevant be-threads */
	List<BTState> btstates = new ArrayList<BTState>();

	/** The set of nondeterministic choices at the state */
	ArrayList<Event> enabledEvents;

	/**
	 * Start a be-program and return when all be-threads arrive at at a bsync.
	 * This function is intended to be invoked after all BThreads are created
	 * and added to the BProgram.
	 * 
	 * @param bp
	 *            A BProgram to start.
	 * @return A BPState object representing the state of the program at the
	 *         first event choice.
	 * @throws IOException
	 */
	static BPState startWith(BProgram bp) throws IOException {

		// Create an object for the next state.
		BPState bps = new BPState();

		// Store the be-program in the state object.
		bps.bp = bp;

		// Progress all be-threads to their next be-sync and store their new
		// state.
		for (BThread bt : bp.getAllBThreads().values()) {
			BTState bts = BTState.startWith(bt);
			bps.btstates.add(bts);
		}

		// Store the list of enabled events at the state, for future use.
		bps.enabledEvents = bps.bp.getAllEnabledEvents();
		// System.out.println("sourceCode.bp.getAllEnabledEvents()=" + bps.enabledEvents);

		// Return an object reflecting the state at the next be-sync.
		return bps;
	}

	/**
	 * Continue a system run from a given state until all be-threads arrive at
	 * the next bsync.
	 * 
	 * @param oldbps
	 *            A state to start from.
	 * @param event
	 *            The next event.
	 * @return The state on the next arrival of all be-threads to a bsync.
	 * @throws ClassNotFoundException
	 * @throws IOException
	 */
	static BPState continueWith(BPState oldbps, Event event) throws IOException, ClassNotFoundException {
		if (globalLogBacktracking)
			System.out.println("State=" + oldbps);

		// Create an object for the next state.
		BPState newbps = new BPState();

		// Copy the be-program to the new state object.
		newbps.bp = oldbps.bp;

		// Set the lastEvent object in the be-program.
		oldbps.bp.lastEvent = event;

		// Progress all be-threads to their next be-sync and store their new
		// state.
		for (BTState bts : oldbps.btstates) {
			BTState.restore(bts);

			if (bts.bt.isRequested(event) || bts.bt.watchedEvents.contains(event)) {
				BTState newbts = BTState.continueWith(bts);
				newbps.btstates.add(newbts);
				if (pruned)
					return null;
			} else {
				newbps.btstates.add(bts);
			}
		}

		// Store the list of enabled events at the state, for future use.
		newbps.enabledEvents = new ArrayList<Event>(newbps.bp.getAllEnabledEvents());
		// System.out.println("sourceCode.bp.getAllEnabledEvents()=" +
		// newbps.enabledEvents);

		// Return an object reflecting the state at the next be-sync.
		return newbps;
	}

	/**
	 * Run a be-program in a single thread using the BPState mechanism. Replaces
	 * BProgram.startAll().
	 * 
	 * @param bp
	 *            A BProgram with all be-threads added.
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public static void runInSingleThread(BProgram bp) throws IOException, ClassNotFoundException {
		continuationMode = true;

		BPState bps = BPState.startWith(bp);

		while (bps.enabledEvents.size() != 0) {
			bps = BPState.continueWith(bps, bps.enabledEvents.get(0));
		}
	}

	/**
	 * Scan all options for nondeterministic choices of running a be-program.
	 * Similar to running it in an iterative mode.
	 * 
	 * @param bp
	 *            A BProgram with all be-threads added.
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public static void verifyAllRuns(BProgram bp) throws IOException, ClassNotFoundException {
		continuationMode = true;
		DecimalFormat d = new DecimalFormat("#0.000");
		long time = System.currentTimeMillis();
		tRevisits = time;
		BPState initialState = BPState.startWith(bp);
		initialState.depth = 0;
		queue.add(initialState);
		queueRec.add(initialState);
		processed.add(initialState);
		numOfDistinctStates++;
		try {
			if (globalRunSubtree)
				runSubtree(initialState);
			else
				runBFSDFS();

			System.out.println("Verification successful");

		} catch (VerificationFailedException ex) {
			System.out.println("Verification failed:\n" +
					null + "->" + initialState + "\n" + ex.getMessage());
		}
		// Print results
		time = System.currentTimeMillis() - time;

//		if (!globalDisableStateHashing) {
//			if (globalRunSubtree) {
//				String durSec = d.format(time / 1000.0);
//				System.out.println("Duration (seconds) :    " + durSec);
//
//				System.out.println("--------------------------");
//				System.out.println("BTState cache hits:     " + BTState.cacheHit);
//				System.out.println("Num. of state revisits: " + revisits);
//				System.out.println("--------------------------");
//
//			} else {
				String averageOutDegree = d.format((float) transitions / (float) numOfDistinctStates);
				String searchType = globalDFS ? "DFS" : "BFS";
				if (globalRunSubtree)
					searchType = "Recursive - DFS";
				String durSec = d.format(time / 1000.0);
				String timePerState = d.format(time / (float) numOfDistinctStates);
				String timePerTransition = d.format(time / (float) transitions);
				System.out.println("Duration (seconds) :    " + durSec);
				System.out.println("Msec per state :        " + timePerState);
				System.out.println("Msec per Transition:    " + timePerTransition);
				System.out.println("Number of states:       " + numOfDistinctStates);
				System.out.println("Number of transitions:  " + transitions);
				System.out.println("Actual depth (" + searchType + "):     " + maxDepth);
				System.out.println("Maximal out-degree:     " + maxOutDegree);
				System.out.println("Average out-degree:     " + averageOutDegree);
				System.out.println("--------------------------");
				System.out.println("BTState cache hits:     " + BTState.cacheHit);
				System.out.println("Num. of state revisits: " + revisits);
				System.out.println("--------------------------");
//			}
//
//		} else
//			System.out.println("Duration (msec)=" + time);
	}

	/**
	 * A utility function for runAllOptions.
	 * 
	 * @param bps
	 *            A state at the top level of the options to scan.
	 * @throws ClassNotFoundException
	 * @throws IOException
	 */
	private static void runSubtree(BPState bps) throws IOException, ClassNotFoundException, VerificationFailedException {

		int outDegree = bps.enabledEvents.size();
		if (outDegree == 0) {
			System.out.println("Deadlock found at state" + bps);
			if (suppressDeadlock)
				return;
			// else
			throw new VerificationFailedException(bps + " is a deadlock state");
		}
		transitions += outDegree;
		if (outDegree > maxOutDegree)
			maxOutDegree = outDegree;

		for (Event e : bps.enabledEvents) {

			BPState newState = BPState.continueWith(bps, e);

			if (pruned) {
				pruned = false;
				if (immediatePruningMsg == null)
					System.out.println("continuation of " + bps + " with " + e + " - PRUNED!!!");
				else{
					System.out.println("continuation of " + bps + " with " + e + " - PRUNED: " +
							immediatePruningMsg);
					immediatePruningMsg = null;
				}
				prunedAtNextState = false;
				delayedPruningMsg = "";
				continue; // to the next enabled event
			}
			if (newState.isBad()) {
				System.out.println("runSubTree: "+ newState+ " is Bad");
				throw new VerificationFailedException(e.toString() + "->" + newState + "\n");
			}
			if (!globalDisableStateHashing && !processed.add(newState)) {
				revisits++;
				// System.out.println("-------------Revisited a state " + bps +
				// "------------");
				if (revisits % 100000 == 0) {
					long t = System.currentTimeMillis();
					long deltaT = t - tRevisits;
					System.out.println("Revisited=" + revisits + " DeltaT(msec) =" + deltaT);
					tRevisits = t;
				}
			} else {
				numOfDistinctStates++;
				if (numOfDistinctStates % 100000 == 0) {
					long tP = System.currentTimeMillis();
					long deltaTP = tP - tProcessed;
					System.out.println("------------------------------- number of states =" + numOfDistinctStates + " deltaT(Processed)(msec) =" + deltaTP);
					tProcessed = tP;
				}
				newState.depth = bps.depth + 1;
				if (newState.depth > maxDepth)
					maxDepth = newState.depth;

				if (prunedAtNextState) {
					prunedAtNextState = false;
					if (delayedPruningMsg.equals(""))
						System.out.println("runSubtree pruning at: " + newState);
					else{
						System.out.print("runSubtree pruning at: " + newState + ":\n" +
								delayedPruningMsg);
						delayedPruningMsg = "";
					}
					continue; // to the next enabled event
				}
				// else
				try {
					runSubtree(newState);
				} catch (VerificationFailedException ex) {
					throw new VerificationFailedException(e + "->" + newState + "\n" + ex.getMessage());
				}
			}
		}
	}
	
	/**
	 * Returns the trace to a given BPState.
	 * @param bps The given BPState.
	 */
	static String getTrace(BPState bps){
		BPState temp = bps;
		String trace = "";
		while (temp.triggeringEvent != null){
			trace = temp.triggeringEvent + "->" + temp + "\n" + trace;
			temp = temp.predecessorBPS;
		}
		return trace;
	}

	/**
	 * Traverses the state graph in a BFS manner, assuming the initial BPState
	 * is in the queue.
	 * 
	 * @throws IOException
	 * @throws ClassNotFoundException
	 * @throws VerificationFailedException
	 */
	private static void runBFSDFS() throws IOException, ClassNotFoundException, VerificationFailedException {

		while (!queue.isEmpty()) {

			BPState bps = queue.poll();
			queueRec.remove(bps);

			if (!globalDisableStateHashing) {
				processed.add(bps);
			}

			int outDegree = bps.enabledEvents.size();
			if (outDegree == 0) {
				System.out.println("Deadlock found at state" + bps);
				if (!suppressDeadlock)
					throw new VerificationFailedException(getTrace(bps) + bps + " is a deadlock state");
				continue;
			}
			transitions += outDegree;
			if (outDegree > maxOutDegree)
				maxOutDegree = outDegree;

			for (Event e : bps.enabledEvents) {

				BPState newState = BPState.continueWith(bps, e);

				if (pruned) {
					pruned = false;
					if (immediatePruningMsg == null)
						System.out.println("continuation of " + bps + " with " + e + " - PRUNED!!!");
					else{
						System.out.println("continuation of " + bps + " with " + e + " - PRUNED: " +
								immediatePruningMsg);
						immediatePruningMsg = null;
					}
					prunedAtNextState = false;
					delayedPruningMsg = "";
					continue; // to the next enabled event
				}

				newState.predecessorBPS = bps;
				newState.triggeringEvent = e;
				
				if (newState.isBad()) {
					System.out.println("runBFSDFS: " + newState + " is Bad");
					throw new VerificationFailedException(getTrace(newState));
				} 
				//else {
				if (!globalDisableStateHashing && (processed.contains(newState) || queueRec.contains(newState))) {
					revisits++;
					if (revisits % 100000 == 0) {
						long t = System.currentTimeMillis();
						long deltaT = t - tRevisits;
						System.out.println("Revisited=" + revisits + " DeltaT(msec) =" + deltaT);
						tRevisits = t;
					}
				} else {
					numOfDistinctStates++;
					if (numOfDistinctStates % 100000 == 0) {
						long tP = System.currentTimeMillis();
						long deltaTP = tP - tProcessed;
						System.out.println("------------------------------- number of states =" + numOfDistinctStates + " deltaT(Processed)(msec) =" + deltaTP);
						tProcessed = tP;
					}
					newState.depth = bps.depth + 1;
					if (newState.depth > maxDepth)
						maxDepth = newState.depth;

					// react to delayed or not delayed pruning
					if (prunedAtNextState) {
						prunedAtNextState = false;
						if (delayedPruningMsg.equals(""))
							System.out.println("runBFSDFS pruning at: " + newState);
						else{
							System.out.print("runBFSDFS pruning at: " + newState + ":\n" +
									delayedPruningMsg);
							delayedPruningMsg = "";
						}
						processed.add(newState);
						continue; // to the next enabled event
					} // else
					if (globalDFS) // DFS OR BFS?
						queue.addFirst(newState); // DFS
					else
						queue.add(newState); // BFS

					queueRec.add(newState);
				}
				// }
			}
			// SAVE MEMORY - DATA NOT NEEDED!
			bps.enabledEvents = null;

		}
	}

	/**
	 * Check if the state is bad by checking if one of its be-threads is in a
	 * bad state
	 * 
	 * TODO store the flag in BPS
	 */
	boolean isBad() {
		if (!globalSafetyCheck)
			return false;
		for (BTState bts : btstates) {
			if (bts.bad){
				System.out.println("Bad state info: " + bts.badInfo);
				return true;
			}
		}
		return false;
	}

	int hashCode = 0;

	/** States are identified by the constituent be-thread states */
	public int hashCode() {
		if (hashCode != 0)
			return hashCode;
		else
			return hashCode = ((btstates == null) ? 0 : btstates.hashCode());
	}

	/** States are identified by the constituent be-thread states */
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		BPState other = (BPState) obj;
		if (btstates == null) {
			if (other.btstates != null)
				return false;
		} else if (!btstates.equals(other.btstates))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return (btstates.toString() + (this.isHot() ? "-HOT" : "-COLD"));
	}

	/**
	 * Check if the state is bad by checking if one of its be-threads is in a
	 * bad state
	 */
	boolean isHot() {
		for (BTState bts : btstates) {
			if (bts.hot)
				return true;
		}
		return false;
	}

}
