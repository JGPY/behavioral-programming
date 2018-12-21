package bpSourceCode.bp;

import static bpSourceCode.bp.BProgramControls.bpjPackage;
import static bpSourceCode.bp.BProgramControls.continuationMode;
import static bpSourceCode.bp.BProgramControls.debugMode;
import static bpSourceCode.bp.BProgramControls.globalRunMode;
import static bpSourceCode.bp.BProgramControls.iterativeMode;
import static bpSourceCode.bp.BProgramControls.javaflowInstrumentation;
import static bpSourceCode.bp.BProgramControls.logMode;
import static bpSourceCode.bp.eventSets.EventSetConstants.none;

import java.io.File;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeMap;

import org.apache.commons.javaflow.Continuation;
import org.apache.commons.javaflow.ContinuationClassLoader;

import bpSourceCode.bApplication.BApplication;
import bpSourceCode.bp.eventSets.EventSetInterface;
import bpSourceCode.bp.eventSets.RequestableInterface;
import bpSourceCode.bp.exceptions.BPJConcurrencyException;
import bpSourceCode.bp.exceptions.BPJDuplicateBthreadException;
import bpSourceCode.bp.exceptions.BPJDuplicateJavaThreadException;
import bpSourceCode.bp.exceptions.BPJDuplicatePrioritiesException;
import bpSourceCode.bp.exceptions.BPJException;
import bpSourceCode.bp.exceptions.BPJInvalidParameterException;
import bpSourceCode.bp.exceptions.BPJJavaThreadEndException;
import bpSourceCode.bp.exceptions.BPJMissingBThreadException;
import bpSourceCode.bp.exceptions.BPJRequestableSetException;
import bpSourceCode.bp.exceptions.BPJSystemErrorException;
import bpSourceCode.bp.state.BPState;
import bpSourceCode.bp.state.Context;
import bpSourceCode.bp.state.LivenessVerification;

public class BProgram implements Serializable {

	/**
	 * sync class, used to identify all be-threads are finished
	 * 
	 */
	@SuppressWarnings("serial")
	private class Syncth_obj_class implements Serializable {
		transient public boolean finished;
	}

	transient static final int LEARNING = 1;

	transient static final int PRIORITY = 0;

	/**
	 *
	 */
	/**
	 * A set containing all the be-threads in the system. A be-thread adds
	 * itself to the list either - in its constructor and removes itself when
	 * its run() function finishes - or a Java thread adds itself and removes
	 * itself explicitly
	 */
	private transient TreeMap<Double, BThread> allBThreads;

	/**
	 * Indicates the maximal distance between two BThread priorities that may be
	 * considered as the same priority
	 */
	transient double bThreadEpsilon = 0;
	// Keep a pointer to the BT whose request for "lastEvent" is the one
	// that was triggered.
	transient private BThread bThreadOfLastEvent;

	/**
	 * A counter that counts how many of the be-thread in allBThreads are busy.
	 * B-Threads decrement this counter when they get into bSync just before
	 * they become dormant and wait to be awaken. When the counter gets to zero,
	 * the be-thread that decremented it from one to zero, awakes other
	 * be-threads and sets the counter to the number of be-threads that it
	 * awakes (the number of be-threads that are waiting to the next event)
	 */
	transient int busyBThreads = 0;

	/**
	 * A variable counting the number of events fired since the beginning of the
	 * execution
	 */
	// the index of the last event (= number of events -1)
	transient public int choicePointCounter = -1;

	/**
	 * Holds the data needed for iterative execution
	 */
	transient ArrayList<ChoicePoint> choicePoints = new ArrayList<ChoicePoint>();

	/**
	 * A semaphore for controlling concurrency. Uses: - during model checking -
	 * reduce thread-scheduling alternatives - compare performance to other
	 * programming approaches (makes BP synchronization look more like a
	 * case-statement with the added ease of continuation without needing to
	 * manage state.
	 * 
	 * The semaphore is acquired when a be-thread is awakened or started, and
	 * released when a be-thread is awakened, or ends.
	 * 
	 */
	transient public Concurrency concurrency;

	/**
	 * Indicates the number of the latest event in the current run for which
	 * there are more choices to execute
	 */
	transient int currentBranch = -1;

	static private Object error = null;

	/**
	 * Indicates the event choice policy Choose between the constants: PRIORITY
	 * (default), LEARNING
	 */
	transient int eventChoicePolicy = PRIORITY;

	transient public int eventCounter = 0;
	// int lastEventCounter = 0;

	/**
	 * Stores the strings of the events that occurred in this run
	 */
	transient ArrayList<String> eventLog = new ArrayList<String>();

	/**
	 * The number of event Strings to be saved in the list
	 */
	transient int eventLogSize = 100;

	transient int iter = 0;

	/**
	 * Indicates whether this program is executed in iterative mode
	 */

	/**
	 * Indicates the number of the latest event in the last run for which there
	 * are more choices to execute (An open non-det choice exists).
	 */
	transient int lastBranch = -1;

	/**
	 * A variable containing the last fired event.
	 */
	transient public Event lastEvent;

	// Default program name
	transient private String name = "BProgram";

	protected transient Syncth_obj_class syncth_obj = new Syncth_obj_class();

	transient HashMap<Thread, BThread> threadToBThreadMap = new HashMap<Thread, BThread>();

	/**
	 * Trace generator for generating the trace of the run.
	 */
	transient TraceGenerator tracegen;
	
	/**
	 * Trace generator for generating the trace of the run for tracer.
	 */
	transient TracerTraceGenerator tracerTraceGenerator;

	static// Track hot states also when not in liveness testing - for run time
	// alerts
	boolean hotStateInNonMCRun = false;
	int maxConsecutiveHotStates = 100;
	int actualConsecutiveHotStates = 0;

	// Constructor
	public BProgram() {

		tracegen = new TraceGenerator(this);
		tracerTraceGenerator = new TracerTraceGenerator(this);
		concurrency = new Concurrency();
		setAllBThreads(new TreeMap<Double, BThread>());
		System.out.println("BProgram instantiated");
	}

	/**
	 * Add a be-thread. Hereafter, the be-thread is counted in the list of all
	 * be-threads as busy. This allows, for example, to add a new be-thread
	 * dynamically (based on a precondition) in a way that assures that new
	 * events are not triggered before the thread of the new be-thread starts
	 * running.
	 * 
	 * The Java thread is created later - during start().
	 * 
	 * @return
	 * @throws BPJDuplicatePrioritiesException
	 */
	public BThread add(BThread bt, Double priority) throws BPJDuplicatePrioritiesException, BPJDuplicateBthreadException {
		synchronized (getAllBThreads()) {

			if (getAllBThreads().containsKey(priority)) {
				throw new BPJDuplicatePrioritiesException(getAllBThreads().get(priority), bt);
			}
			if (bt.getBProgram() != null) { // was already assigned, cancel
                throw new BPJDuplicateBthreadException(bt);
            }

			// Count the be-thread as busy
			busyBThreads++;

			// Add the be-thread to the be-threads set
			bt.priority = priority;
			bt.setBProgram(this);
			getAllBThreads().put(priority, bt);
			
			// Log this information for the static XML log of the tracer log.
			tracerTraceGenerator.logToStaticTrace(TracerTraceGenerator.Action.ADD, bt);

			if (bt.getThread() == null)
				bt.setThread(new JavaThreadForBThread(bt));

			// add the pair to the global table
			threadToBThreadMap.put(bt.getThread(), bt);
		}
		return bt;
	}

	// add the calling (currently running) Java thread as a be-thread.
	// returns the new be-thread;
	public BThreadForJavaThread add(Double priority) throws BPJException {
		synchronized (getAllBThreads()) {
			if (concurrency.control) {
				throw new BPJConcurrencyException(); // not supported (yet) with
				// ordinary Java threads
			}

			Thread th = Thread.currentThread();
			if (getAllBThreads().containsKey(priority)) {
				throw new BPJDuplicatePrioritiesException(getAllBThreads().get(priority), null);
			}
			if (threadToBThreadMap.get(th) != null) {
				throw new BPJDuplicateJavaThreadException(th);
			}
			BThreadForJavaThread bt = new BThreadForJavaThread(th);
			threadToBThreadMap.put(th, bt);
			// add this new bthread as usual
			add(bt, priority);

			return bt;
			// no need to start this be-Thread. It is already running.
		}

	}

	// Add with name
	public BThreadForJavaThread add(String btname, Double priority) throws BPJException {
		BThreadForJavaThread newbt = add(priority);
		newbt.setName(btname);
		return newbt;

	}

	/**
	 * Awake or interrupt all be-threads that are affected by lastEvent. The
	 * calling be-thread is a special case because it cannot be awaken (since it
	 * is not waiting yet). For the calling be-thread, we use the return value
	 * which indicates whether it has to wait for another be-thread to awake it
	 * or it is awaken by itself, i.e., it does not need to wait.
	 * 
	 * @return A flag that indicates whether this be-thread awakes itself, i.e.,
	 *         does not need to wait for another be-thread to awake it
	 */
	private boolean awakeWaitingBThreads(BThread thisBT) {

		boolean continueImmediately = false;

		// Increment the number of busy be-threads
		for (BThread bt : getAllBThreads().values()) {
			if (bt.watchedEvents.contains(lastEvent) || bt.interruptingEvents.contains(lastEvent) || bt.isRequested(lastEvent)) {
				busyBThreads++;
			}
		}

		// Interrupt and notify the be-threads that need to be awaken
		for (BThread bt : getAllBThreads().values()) {

			if (bt.interruptingEvents.contains(lastEvent)) {
				// BThread bt1 = ((JavaThreadForBThread)
				// Thread.currentThread()).bt;
				// debugPrint(bt + " interrupted in awake"); /**********/

				// if (bt.thread == null){
				// System.out.println(bt + " is null");
				// }else{
				bt.thread.interrupt();
				// }
			} else if (bt.watchedEvents.contains(lastEvent) || bt.isRequested(lastEvent)) {
				// if (bt.watchedEvents.contains(lastEvent) ) {

				if (bt == thisBT) {

					// Notifying the current be-thread is meaningless because it
					// is not waiting yet. Instead, we mark that there is no
					// need to wait.
					continueImmediately = true;

				} else {
					synchronized (bt) {
						bt.notify();
					}
				}
			} else if (bt.interruptingEvents.contains(lastEvent)) {
				bt.getThread().interrupt();
			}
		}

		if (thisBT.interruptingEvents.contains(lastEvent)) {
			// System.out.println(thisBT + " is going to bWait in awake");
			// /**********/
			// thisBT.thread.interrupt(); // unnecessary, depending on the above
			// "else"
			thisBT.bWait();
		}

		return continueImmediately;
	}

	public void bplog(String s) {
		if (logMode) {
            System.out.println(s);
        }
	}

	/**
	 * Wait for the next event. Sleep until all be-threads call this function.
	 * 
	 * @param btID
	 *            The be-thread that called this function.
	 * 
	 * 
	 */
	public void bSync(RequestableInterface requestedEvents, EventSetInterface watchedEvents, EventSetInterface blockedEvents) throws BPJException {

		boolean continueImmediately = false;

		BThread bt;

		if (continuationMode) {

			bt = ((Context) Continuation.getContext()).bt;

			bt.requestedEvents = requestedEvents;
			bt.watchedEvents = watchedEvents;
			bt.blockedEvents = blockedEvents;
			Continuation.suspend();

			// The following loop may not be needed when we decide to implement
			// BTState.restore by caching the data.
			while (!((Context) Continuation.getContext()).continueRunning) {

				bt = ((Context) Continuation.getContext()).bt;

				bt.requestedEvents = requestedEvents;
				bt.watchedEvents = watchedEvents;
				bt.blockedEvents = blockedEvents;

				Continuation.suspend();
			}
			// ------------------------------------------------------------------

			return;
		}

		Thread currentThread = Thread.currentThread();
		// Go from running Java thread to be-thread.
		// The "if" below is for optimization. The "else" part should work for
		// all
		if (currentThread.getClass() == JavaThreadForBThread.class) {
			bt = ((JavaThreadForBThread) currentThread).bt;
		} else {
			bt = threadToBThreadMap.get(currentThread);
		}

		if (bt == null) {
			throw new BPJMissingBThreadException(currentThread);
		}
		synchronized (bt) {

			// The code is synchronized on allBThreads to make sure that only
			// one be-thread is in bSync at a time
			synchronized (getAllBThreads()) {

				// Store parameters in object variables for inspection by other
				// threads
				bt.requestedEvents = requestedEvents;
				bt.watchedEvents = watchedEvents;
				bt.blockedEvents = blockedEvents;

				// Remove this be-thread from the count of busy be-threads
				// because
				// it
				// is about to sleep or choose the next event
				busyBThreads--;

				// If end of be-thread, remove the be-thread from allBThreads
				// and
				// mark for immediate exit (don't wait at the end of the
				// function)
				if (bt.getThread() == null) {
					getAllBThreads().remove(bt.priority);
					tracerTraceGenerator.logToDynamicTrace(TracerTraceGenerator.Action.REMOVE, bt);
					threadToBThreadMap.remove(currentThread);
					continueImmediately = true;
					// if no more be-threads exist, also wake up
					// any thread that is waiting for this.
					// note that this doesn't really mean that the
					// be-program "finished" - more dynamic be-threads
					// may be added.
					if (getAllBThreads().size() == 0) {
						// System.out.println("Last BT: " + bt); /*************/
						// System.out.println("busyBThreads: "+busyBThreads);
						// /*************/
						getAllBThreads().notifyAll();
						return;
					}
				}

				// If this be-thread is the last to be counted as not busy, the
				// next event is chosen and the be-threads that wait for it are
				// awaken
				if (busyBThreads == 0) {
					// System.out.println("getAllEnabledEvents().size()=" +
					// getAllEnabledEvents().size());
					if (error == null && (BPState.pruned || BPState.prunedAtNextState)) {
                        setError("Run ended due to pruning during a non-MC run");
                    }

					if (hotStateInNonMCRun) {
						actualConsecutiveHotStates++;
						hotStateInNonMCRun = false; // Reset to cold default for
						// next bSync
						if (actualConsecutiveHotStates > maxConsecutiveHotStates) {
                            setError("Max consecutive hot states exceeded (" + maxConsecutiveHotStates + ")");
                        }

					} else {
                        actualConsecutiveHotStates = 0; // Cold state reached -
                    }
					// reset hot state
					// counter;

					if (error != null) {
						bplog("BProgram terminating: " + error);
						debugPrint(bt + " resetting program");
						/*************/
						// printEventLog();
						reset();
						if (bt.thread != null) {
                            bt.bWait(); // so the interrupt in reset() can take
                        // effect
						} else {
							// the bthread is already done
							// System.out.println(bt + " is null");
                            return;
                        }
						// throw new BPJInterruptingEventException();
					}

					// Are we in a hot state for too long?

					// Choose the next event and store it in the global variable
					// lastEvent
					chooseNextEvent();
					logTrace();
					String st;
					if (lastEvent != null) {
						eventCounter++;
						st = new String("Event #" + eventCounter + ": " + lastEvent + "  requested by  " + bThreadOfLastEvent);
						bplog(st);

						// Awake the waiting be-threads (returns a flag that
						// indicates if this be-thread is awaken, i.e., does not
						// need
						// to wait)
						continueImmediately |= awakeWaitingBThreads(bt);
					} else { // lastEvent == null -> deadlock?
						st = new String("No events chosen. " + bt + " stuck in bsync");
						debugPrint(st);
					}

					if (choicePointCounter < eventLogSize)
						eventLog.add(st); // at position eventCounter
					else
						eventLog.set(choicePointCounter % eventLogSize, st);

				}
			}

			// If the continueImmediately flag is not on, wait until another
			// be-thread chooses an event in this be-thread's watched events and
			// notifies it
			if (!continueImmediately) {

				// Don't count this be-thread as running for concurrency
				// purposes
				if (concurrency.control) {
					debugPrint("Permits=" + concurrency.semaphore.availablePermits() + " " + bt + " W1");
					concurrency.semaphore.release();

					debugPrint("Released - Waiting   " + bt + "\n" + "Permits= " + concurrency.semaphore.availablePermits() + " " + bt + " W2");
				}

				bt.bWait();

				// Count this be-thread as running for concurrency purposes
				if (concurrency.control) {
					debugPrint("Permits= " + concurrency.semaphore.availablePermits() + " " + bt + " B1");
					try {
						concurrency.semaphore.acquire();
					} catch (Exception e) {
						System.out.println("BPJ semaphore acquisition exception");
						throw new BPJSystemErrorException();
					}

					debugPrint("Acquired - Waking    " + bt + "\n" + "Permits= " + concurrency.semaphore.availablePermits() + " " + bt + " B2");

				}
			}
		}
	}

	/**
	 * Choose the next event to be fired. Save in lastEvent; Save also
	 * bThreadOfLastEvent
	 * 
	 * @throws BPJRequestableSetException
	 */
	private void chooseNextEvent() throws BPJRequestableSetException {

		// Reset the last event variables.
		lastEvent = null;
		// bThreadOfLastEvent = null;
		choicePointCounter++;

		switch (eventChoicePolicy) {

		case LEARNING:
			break;
		default:
			if (!iterativeMode)
				chooseNextEventDefault();
			else {
				chooseNextEventIterative();
			}
		}

	}

	public void chooseNextEventDefault() throws BPJRequestableSetException {

		EventChoice ec = getNextEventChoice(null, null);
		if (ec != null) {
			lastEvent = ec.getEvent(this);
			bThreadOfLastEvent = getAllBThreads().get(ec.btID);
		}
	}

	private void chooseNextEventIterative() throws BPJRequestableSetException {

		ChoicePoint p;

		if (choicePointCounter < lastBranch) {
			// The current event is not at the last branch (non-det choice) yet
			// Repeat previous choice.
			p = choicePoints.get(choicePointCounter);
			debugPrint("Event #:" + choicePointCounter + "(repeated): " + p.currentChoice);

		} else if (choicePointCounter == lastBranch) { // need to move to next
			// event
			// choice
			p = choicePoints.get(choicePointCounter);
			// move to next event choice
			p.currentChoice = p.nextChoice;
			// determine new next event choice
			p.nextChoice = getNextEventChoice(p.currentChoice, p.firstBThread);

			debugPrint("Event #" + choicePointCounter + "(next choice): " + p.currentChoice);

		} else { // eventCounter > lastBranch - explore a new path
			p = new ChoicePoint();
			// Choose an event that is requested but not blocked

			p.currentChoice = getNextEventChoice(null, null);
			if (p.currentChoice != null) {
				p.firstBThread = p.currentChoice.btID;
				p.nextChoice = getNextEventChoice(p.currentChoice, p.firstBThread);
				debugPrint("Event #" + choicePointCounter + "(new): " + p.currentChoice);

				if (choicePointCounter < choicePoints.size()) {
					choicePoints.set(choicePointCounter, p);
				} else {
					choicePoints.add(choicePointCounter, p);
				}
			}

		}
		// System.out.println("p: "+p); /*************/
		if (p.currentChoice != null) { // an event was found
			lastEvent = p.currentChoice.getEvent(this);
			bThreadOfLastEvent = allBThreads.get(p.currentChoice.btID);
		}

		if (p.nextChoice != null) { // there are more non-det choices here
			currentBranch = choicePointCounter;
			debugPrint("Another choice exists: " + p.nextChoice + ", currentBranch: " + currentBranch);
		}
	}

	public void debugPrint(String s) {
		if (debugMode)
			System.out.println("Debug: " + s);
	}

	public TreeMap<Double, BThread> getAllBThreads() {
		return allBThreads;
	}

	/**
	 * 
	 * @return an ArrayList of all enabled events that are requestable with the
	 *         same highest priority
	 */
	public ArrayList<Event> getAllEnabledEvents() {
		Double baseBT = null;
		Double recentBT = null;
		BThread bt;
		RequestableInterface set;
		ArrayList<Event> list = new ArrayList<Event>();

		for (Double key = allBThreads.firstKey(); key != null; key = allBThreads.higherKey(key)) {
			if (baseBT != null // first event was found
				&& key - baseBT > bThreadEpsilon) {
					// (key - recentBT > bThreadEpsilon)

                break;
            }

			bt = allBThreads.get(key);

			if (bt.getMonitorOnly() || bt.requestedEvents == null) {
                continue;
            }

			for (int i = 0, n = bt.requestedEvents.size(); i < n; i++) {
				set = bt.requestedEvents.get(i);
				for (int j = 0, m = set.size(); j < m; j++) {
					Event e = set.get(j).getEvent();
					if (!isBlocked(e)) {
						if (baseBT == null) {
                            baseBT = key;
                        }
						if (!list.contains(e)) {
                            list.add(e);
                        }
						recentBT = key;
					}
				}

				// When starting from another eventChoice,
				// we look only in the same set of events.
				// We don't look for the next event in another set of same
				// BThread. Move to next bthread.
				if (key.equals(recentBT)) {
                    break;
                }
			}

		}
		return list;
	}

	/**
	 * Get all the event choices that are possible at the current state.
	 * 
	 * @return The set of events.
	 */
	public Set<Event> getAllEventChoices() {
		HashSet<Event> set = new HashSet<Event>();

		EventChoice ec = getNextEventChoice(null, null);

		while (ec != null) {
			set.add(ec.getEvent(this));

			ec = getNextEventChoice(ec, null);
		}

		return set;
	}

	public Object getError() {
		return error;
	}

	/**
	 * 
	 * @return the given bprogram name
	 */
	public String getName() {
		return name;
	}

	/**
	 * This method is the Main event selection algorithm.
	 * 
	 * @param ec
	 *            null by default - start from the beginning. Otherwise
	 *            EventChoice object pointing to an event where the search
	 *            should start.
	 * 
	 * @param baseBT
	 *            null by default. Use in iterative mode to set the key of
	 *            Bthread with the first chosen event, such that all other
	 *            selections should be of "same" priority - calculated with
	 *            "epsilon" (since bthread id's must be unique).
	 * 
	 * @return an EventChoice to fire, or null if no event found
	 * 
	 */
	public EventChoice getNextEventChoice(EventChoice ec, Double baseBT) {
		BThread bt;
		RequestableInterface set;
		EventChoice startChoice = (ec != null) ? ec : new EventChoice(allBThreads.firstKey(), 0, -1);

		for (Double key = startChoice.btID; key != null; key = allBThreads.higherKey(key)) {
			if (baseBT != null) {
                if (key - baseBT > bThreadEpsilon) {
                    // (key - p.currentBThread > bThreadEpsilon)
                    return null;
                }
            }

			bt = allBThreads.get(key);

			if (bt.getMonitorOnly()) {
                continue;
            }

			for (int i = startChoice.eventSetSeq, n = bt.requestedEvents.size(); i < n; i++) {
				set = bt.requestedEvents.get(i);
				for (int j = startChoice.eventSeq + 1, m = set.size(); j < m; j++) {
					Event e = set.get(j).getEvent();
					if (!isBlocked(e)) {
						EventChoice newChoice = new EventChoice(key, i, j);
						return newChoice;
					}
				}

				// When starting from another eventChoice,
				// we look only in the same set of events.
				// We don't look for the next event in another set of same
				// BThread. Move to next bthread.
				if (ec != null && key.equals(ec.btID)) {
                    break;
                }
			}

		}
		return null;
	}

	public boolean hasMoreRuns() {
		debugPrint("currentBranch: " + currentBranch);
		return (currentBranch >= 0);
	}

	/**
	 * A function that checks if an event is blocked by some be-thread.
	 * 
	 * @param e
	 *            An event.
	 * @return true if the event is blocked by some be-thread.
	 */
	protected boolean isBlocked(Event e) {
		for (BThread bt : getAllBThreads().values()) {
			if (bt.blockedEvents.contains(e)) {
				return true;
			}
		}
		return false;
	}

	public void joinAll() {
		synchronized (getAllBThreads()) {
			if (getAllBThreads().size() == 0) {
                return;
            }
			try {
				getAllBThreads().wait();
			} catch (InterruptedException ex) {
				System.out.println(ex);
			}
		}
	}

	/**
	 * Start all added be-threads.
	 */
	// public void startAll() {
	// synchronized (syncth_obj) {
	// syncth_obj.finished = false;
	// }
	//
	// System.out.println("********* Starting " + allBThreads.size()
	// + " be-threads  **************");
	//
	// synchronized (allBThreads) {
	// for (BThread sc : allBThreads.values()) {
	// sc.startBThread();
	// }
	// }
	// }

    /**
    * logs the current trace
    */
    void logTrace() {
        if (lastEvent != null) {
            tracegen.logToVisualizationTrace();
        }

        if (lastEvent != null) {
            tracerTraceGenerator.logToDynamicTrace(TracerTraceGenerator.Action.CUT);
        }
    }

	static public void markNextVerificationStateAsBad(String str) {
		if (globalRunMode != RunMode.MCSAFETY && globalRunMode != RunMode.MCLIVENESS) {
			setError("Bad state reached during a non-MC run");
			return;
		}
		Context context = (Context) Continuation.getContext();
		context.bts.bad = true;
		context.bts.badInfo = str;

	}

	static public void markNextVerificationStateAsHot() {
		if (globalRunMode != RunMode.MCSAFETY && globalRunMode != RunMode.MCLIVENESS) {
			hotStateInNonMCRun = true;
		}
		Context context = (Context) Continuation.getContext();
		context.bts.hot = true;
	}

	static public void labelNextVerificationState(Object obj) {
		// For non-MC runs this is a no-op
		if ((globalRunMode != RunMode.MCSAFETY) && (globalRunMode != RunMode.MCLIVENESS)) {
            return;
        }
		// For MC runs:
		Context context = (Context) Continuation.getContext();
		context.bts.label = obj;
	}

	/**
	 * Utility function (for debugging purposes) that prints the ordered list of
	 * active be-threads.
	 */
	public void printAllBThreads() {
		int c = 0;
		for (BThread bt : getAllBThreads().values()) {
			bplog("\t" + (c++) + ":" + bt);
		}
	}

	public void printEventLog() {

		System.out.println("\n ***** Printing last " + eventLog.size() + " choice points out of " + (choicePointCounter + 1) + ":");

		if (choicePointCounter < eventLogSize) {
			for (String eventString : eventLog) {
				System.out.println(eventString);
			}
		} else {
			for (int i = 1; i <= eventLogSize; i++) {
				System.out.println(eventLog.get((choicePointCounter + i) % eventLogSize));
			}
		}

		System.out.println("***** end event log ******");
	}

	// Immediate pruning.
	public void pruneSearchNow() {
		BPState.pruned = true; // Indicate immediate pruning
//		BPState.prunedAtNextState = true; // Indicate delayed pruning too
	}

	// Immediate pruning.
	public void pruneSearchNow(String msg) {
		BPState.pruned = true; // Indicate immediate pruning
//		BPState.prunedAtNextState = true; // Indicate delayed pruning too
		BPState.immediatePruningMsg = msg;		
	}

	// Delayed pruning.
	public void pruneAtNextVerificationState() {
//		System.out.println("Calling prunedAtNextVerificationState");
		BPState.prunedAtNextState = true;
	}

	// Delayed pruning.
	public void pruneAtNextVerificationState(String msg) {
//		System.out.println("Calling prunedAtNextVerificationState");
		BPState.prunedAtNextState = true;
		BPState.delayedPruningMsg = BPState.delayedPruningMsg + "delayed pruning info: " +
		msg + "\n";		
	}

	// Remove the running Java thread that had added itself as a be-thread
	// during
	// run
	public void remove() throws BPJException {
		Thread th = Thread.currentThread();
		BThread bt = threadToBThreadMap.get(th);
		if (bt == null) {
			throw new BPJMissingBThreadException(th);
		}
		if (bt.getClass() != BThreadForJavaThread.class) {
            throw new BPJJavaThreadEndException();
        }
		// indicate you are on your way out
		bt.setThread(null);
		bt.getBProgram().bSync(none, none, none);

	}

	public void reset() {
		// busyBThreads = allBThreads.size();
		synchronized (allBThreads) {
			for (BThread bt : allBThreads.values()) {
				// Thread t = bt.thread;
				// if (t != null){ // this check seems unnecessary, since
				// if t==null then the BThread shouldn't be in allBThreads
				busyBThreads++;
				// debugPrint("resetting " + bt);
				// t.interrupt();
				bt.thread.interrupt();
				// }
				// else
				// System.out.println(bt + " is null");
			}
		}
	}

	public void setAllBThreads(TreeMap<Double, BThread> allBThreads) {
		this.allBThreads = allBThreads;
	}

	public void setBThreadEpsilon(double eps) {
		this.bThreadEpsilon = eps;
	}

	public void setDebugMode(boolean mode) {
		debugMode = mode;
	}

	/**
	 * Sets the error that occurred during the run, to make BProgram terminate
	 * at the next bSync and print the error.
	 * 
	 * @param error
	 *            An Object of the error occurred during the run - better have
	 *            an informative toString().
	 */
	public static void setError(Object error) {
		BProgram.error = error;
	}

	public void setIterativeMode(boolean mode) {
		iterativeMode = mode;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return name;
	}

	/**
	 * Start all added scenarios. In default mode this method returns while the
	 * PBrogram is still running. In iterative mode this method returns only
	 * after all BThreads of this BProgram terminate (do not use the method
	 * joinAll() in this mode).
	 */
	public void startAll() {
		// TODO - replace iterativeMode with pure usage of RunMode.
		// Includes changing of those who set iterativeMode;
		if (globalRunMode == RunMode.ITER) {
            iterativeMode = true;
        }

		if (iterativeMode) {
            startIterative();
        } else if (globalRunMode == RunMode.DET || globalRunMode == RunMode.RANDOM) {
            startDefault();
        } else if (globalRunMode == RunMode.MCSAFETY) {
			// TODO move verifyAllRuns and other methods from BPState to
			// BProgram
			try {
				BPState.verifyAllRuns(this);
			} catch (Exception e) {
				e.printStackTrace();

			}

		} else if (globalRunMode == RunMode.MCLIVENESS) {
			try {
				// TODO move verifyLiveness and more from LivenessVerification
				// to
				// BProgram

				LivenessVerification.verifyLiveness(this);
			} catch (Exception e) {
				e.printStackTrace();

			}
		} else if (globalRunMode == RunMode.MCDET) {
			try {
				BPState.runInSingleThread(this);
			} catch (Exception e) {
				e.printStackTrace();

			}
		} else {
            throw new BPJInvalidParameterException("Unknown Run Mode encountered at run time");
        }

	}

	// this method returns while the BProgram is still running
	private void startDefault() {

		bplog("********* Starting " + getAllBThreads().size() + " scenarios  **************");

		synchronized (getAllBThreads()) {
			for (BThread sc : getAllBThreads().values()) {
				sc.startBThread();
			}

			// try {
			// allBThreads.wait();
			// } catch (InterruptedException ex) {
			// System.out.println(ex);
			// }
		}
	}

	// This method returns only after all BThreads of this BProgram terminate
	// Do not use the method joinAll() together with this method (2 'wait', 1
	// 'notify')
	private void startIterative() {

		// do{

		// lastEventCounter = eventCounter;
		choicePointCounter = -1;
		eventCounter = 0;

		eventLog.clear();
		error = null;

		// save the deepest level of branching in the last run
		lastBranch = currentBranch;
		currentBranch = -1;

		bplog("\n  ********* Iteration " + ++iter + ": Starting " + getAllBThreads().size() + " scenarios  **************");

		synchronized (getAllBThreads()) {
			for (BThread sc : getAllBThreads().values()) {
				sc.startBThread();
			}

			try {
				getAllBThreads().wait();
			} catch (InterruptedException ex) {
				System.out.println(ex);
			}
		}
		// } while (currentBranch >= 0);
	}

	/**
	 * Wait for the run to finish
	 * 
	 */
	public void waitForFinish() {
		synchronized (syncth_obj) {
			try {
				while (!syncth_obj.finished) {
                    syncth_obj.wait();
                }
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}

	// Define "equal priorities": See definition of bThreadEpsilon
	public void setNondeterminism(double bThreadEpsilon) {
		this.bThreadEpsilon = bThreadEpsilon;
	}
	
	// GW: Get all waited-for events
	public Collection<EventSetInterface> getWatchedEventSets() {
		Collection<EventSetInterface> ret = new ArrayList<EventSetInterface>();
		for (BThread bt : allBThreads.values()) {
			ret.add(bt.watchedEvents);
		}
		return ret;
	}

	static public void startBApplication(Class<?> classToLoad, String packageRoot) throws MalformedURLException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		BApplication m;
		if (javaflowInstrumentation) {
			System.out.println("Invoking javaflow instrumentation");
			ContinuationClassLoader cl1 = new ContinuationClassLoader(new URL[] { new File("bin/").toURI().toURL() }, classToLoad.getClassLoader());

			cl1.addLoaderPackageRoot(packageRoot);
			cl1.addLoaderPackageRoot(bpjPackage);

			m = (BApplication) cl1.loadClass(classToLoad.getName()).newInstance();
		} else {
            m = (BApplication) classToLoad.newInstance();
        }

		System.out.println("Instantiated: " + m.getClass());
		m.runBApplication();

	}

	public static BProgram bp = new BProgram();
}
