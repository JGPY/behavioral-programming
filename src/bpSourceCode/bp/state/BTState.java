package bpSourceCode.bp.state;

import static bpSourceCode.bp.BProgramControls.globalShallow;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;

import org.apache.commons.javaflow.Continuation;

import bpSourceCode.bp.BThread;
import bpSourceCode.bp.Event;
import bpSourceCode.bp.eventSets.EventSetInterface;
import bpSourceCode.bp.eventSets.RequestableInterface;

/**
 *
 * A class for capturing be-thread states.
 *
 */
public class BTState {

	
	static HashMap<CacheKey, BTState> cache = new HashMap<CacheKey, BTState>();

	static int cacheHit;

	static Context context = new Context();
	
	static CacheKey cacheKey = new CacheKey();
	static BTState cacheResult = null;



	/** The be-thread whose state is captured */
	BThread bt;

	/**
	 * A serialization of a continuation object used to store the state of the
	 * be-thread
	 */
	byte[] serialization;

	/**
	 * Object that stores the label that the corresponding be-thread assigned to
	 * the state. This object is null until the be-thread calls
	 * BPRogram.labelVerificationState.
	 */
	public Object label = null;

	
	/**
	 * Object storing the continuation object in case of shallow continuation
	 * (otherwise, a full serialization of the continuation is stored in the
	 * variable serialization and this variable is not used.
	 */
	private Continuation cont;

	RequestableInterface requestedEvents;
	public EventSetInterface watchedEvents;
	EventSetInterface blockedEvents;

	// Flag for safety verification
	public boolean bad = false;
	
	// Information about the reached bad state
	public String badInfo;

	// Flag for liveness.
	public boolean hot = false;

	/**
	 * Run a be-thread to its first be-sync.
	 *
	 * @param bt
	 *            A be-thread that was not yet started.
	 * @return A BPState object representing the state at the first be-sync.
	 * @throws IOException
	 */
	static BTState startWith(BThread bt) throws IOException {
		// Create a state object to be returned.
		BTState st = new BTState();

		// Store a pointer to the be-thread in the state object.
		st.bt = bt;

		// Run the be-thread to its first be-sync.
		context.set(bt, true, st);
		Continuation cont = Continuation.startWith(bt.getThread(), context);
		st.save(cont);

		st.requestedEvents = bt.requestedEvents;
		st.watchedEvents = bt.watchedEvents;
		st.blockedEvents = bt.blockedEvents;

		// Return an object representing the state of the be-thread at its
		// first be-sync.
		return st;
	}

	/**
	 * Continue the run of a be-thread to its next be-sync.
	 *
	 * @param oldst
	 *            A state of a be-thread.
	 * @return The state of the be-thread at its next be-sync.
	 * @throws ClassNotFoundException
	 * @throws IOException
	 */
	static BTState continueWith(BTState oldst) throws IOException, ClassNotFoundException {
		//BTState bts = cacheEnabledcontWith(oldst);
		//restore(bts);

		BTState bts = cachedDisabledcontWith(oldst);

		return bts;
	}



	
	/**
	 * Continue the run of a be-thread to its next be-sync.
	 *
	 * @param oldst
	 *            A state of a be-thread.
	 * @return The state of the be-thread at its next be-sync.
	 * @throws ClassNotFoundException
	 * @throws IOException
	 */
	static BTState cachedDisabledcontWith(BTState oldst) throws IOException, ClassNotFoundException {
		// Create a state object to be returned.
		BTState newst = new BTState();

		// Copy the reference to the be-thread and its id.
		newst.bt = oldst.bt;

		// Run the be-thread to its next be-sync.
		Continuation oldcont = oldst.load();

		context.set(oldst.bt, true, newst);
		Continuation newcont = Continuation.continueWith(oldcont, context);

		newst.save(newcont);

		newst.requestedEvents = oldst.bt.requestedEvents;
		newst.watchedEvents = oldst.bt.watchedEvents;
		newst.blockedEvents = oldst.bt.blockedEvents;

		// Return an object representing the state of the be-thread at its
		// next be-sync.
		return newst;
	}


	static BTState cacheEnabledcontWith(BTState oldst) throws IOException, ClassNotFoundException {
		// Fetch the cached result from the cache if there is a non-empty label
		// to the state
		if (oldst.label != null) {
			cacheKey.set(oldst);
			cacheResult = cache.get(cacheKey);
		}
		// If the needed result is in the cache return it, otherwise compute it.
		if (cacheKey != null && cacheResult != null) {
			cacheHit++;
			return cacheResult;
		} else {

			// Run the be-thread to its net be-sync
			BTState newst = cachedDisabledcontWith(oldst);

			// Put the result about to be returned in the cache
			if (cacheKey != null) {
				cache.put(cacheKey, newst);
			}

			// Return an object representing the state of the be-thread at its
			// next be-sync.
			return newst;
		}
	}

	/**
	 * Restore the state of the be-thread without running it to its next be-sync..
	 *
	 * @param st
	 *            An object representing the state of a be-thread.
	 * @throws ClassNotFoundException
	 * @throws IOException
	 */
	static void restore(BTState st) throws IOException, ClassNotFoundException {
		st.bt.requestedEvents = st.requestedEvents;
		st.bt.watchedEvents = st.watchedEvents;
		st.bt.blockedEvents = st.blockedEvents;
	}

	private void save(final Continuation c) throws IOException {

		if (globalShallow) {
			cont = c;
			return;
		}

		ByteArrayOutputStream outs = new ByteArrayOutputStream();

		final ObjectOutputStream oos = new ObjectOutputStream(outs);

		oos.writeObject(c);

		oos.close();

		serialization = outs.toByteArray();
	}

	private Continuation load() throws IOException, ClassNotFoundException {
		if (globalShallow) {
			return cont;
		}

		final ObjectInputStream ois = new ObjectInputStreamExt(new ByteArrayInputStream(serialization), this.getClass().getClassLoader());

		final Continuation o = (Continuation) ois.readObject();

		ois.close();

		return o;
	}

	/** States are identified by their labels */
	public int hashCode() {
		return ((label == null) ? 0 : label.hashCode());
	}

	/** States are identified by their labels */
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		// if (getClass() != obj.getClass())
		// return false;
		BTState other = (BTState) obj;
		if (label == null) {
			if (other.label != null)
				return false;
		} else if (!label.equals(other.label))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return label == null ? "-" : label.toString();
	}

}

// Key for the cache.
class CacheKey {
	BThread bt;
	BTState bts;
	Event event;

	public void set(BTState bts) {
		this.bt = bts.bt;
		this.bts = bts;
		this.event = bts.bt.getBProgram().lastEvent;
	}


	@Override
	public String toString() {
		return "CacheKey [bt=" + bt + ", event=" + event + ", nts=" + bts + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((bt == null) ? 0 : bt.hashCode());
		result = prime * result + ((event == null) ? 0 : event.hashCode());
		result = prime * result + ((bts == null) ? 0 : bts.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CacheKey other = (CacheKey) obj;
		if (bt == null) {
			if (other.bt != null)
				return false;
		} else if (!bt.equals(other.bt))
			return false;
		if (event == null) {
			if (other.event != null)
				return false;
		} else if (!event.equals(other.event))
			return false;
		if (bts == null) {
			if (other.bts != null)
				return false;
		} else if (!bts.equals(other.bts))
			return false;
		return true;
	}

}
