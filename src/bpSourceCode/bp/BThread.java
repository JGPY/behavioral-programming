package bpSourceCode.bp;

//import gov.nasa.jpf.jvm.Verify;

import static bpSourceCode.bp.eventSets.EventSetConstants.none;

import java.io.Serializable;

import bpSourceCode.bp.eventSets.EventSetInterface;
import bpSourceCode.bp.eventSets.RequestableInterface;
import bpSourceCode.bp.exceptions.BPJInterruptingEventException;
import bpSourceCode.bp.exceptions.BPJRequestableSetException;
import bpSourceCode.bp.exceptions.BPJUnregisteredBThreadException;

/**
 * A base class for behavior thread
 */
public abstract class BThread implements Serializable {
	transient Double priority;

	/* transient */private String name = this.getClass().getSimpleName();

	transient private BProgram bp = null;
	transient private boolean monitorOnly = false;

	/**
	 * Temporary storage for bpSync parameters
	 */
	public transient RequestableInterface requestedEvents;
	public transient EventSetInterface watchedEvents;
	public transient EventSetInterface blockedEvents;

	/**
	 * The set of events that will interrupt this scenario.
	 */
	transient protected EventSetInterface interruptingEvents = none;

	/**
	 * The thread that executes this scenario
	 */
	transient Thread thread;

	public BThread() {
	}

	public BThread(String name) {
		this.setName(name);
	}

	public void setName(String name) {
		this.name = name;
	}

	/**
	 * The function that implements the BThread. Subclasses of BThread should
	 * override this method.
	 * 
	 * @throws BPJRequestableSetException
	 */

	public abstract void runBThread() throws InterruptedException, BPJRequestableSetException;

	/**
	 * @see Thread#start()
	 */

	public void startBThread() {
		getThread().start();
	}

	public boolean isRequested(Event event) {
		return (requestedEvents.contains(event));
	}

	@Override
	public String toString() {
		return name;
	}

	public BProgram getBProgram() {
		return bp;
	}

	public void setBProgram(BProgram bp) {
		this.bp = bp;
	}

	public void bWait() throws BPJInterruptingEventException {
		try {
			wait();
		} catch (Exception e) {
			throw new BPJInterruptingEventException();

		}
	}

	public void setMonitorOnly(boolean flag) {

		if (bp == null) {
			throw new BPJUnregisteredBThreadException();
		}
		synchronized (bp.getAllBThreads()) {
			monitorOnly = flag;
		}

	}

	public boolean getMonitorOnly() {
		return monitorOnly;
	}

	public void setThread(Thread thread) {
		this.thread = thread;
	}

	public Thread getThread() {
		return thread;
	}

	// The code below makes sure that we get the same hashCode and equals for
	// copies that come from serialization and then deserialization of the same
	// object.

	static int numerator = 0;
	int hash = numerator++;

	@Override
	public int hashCode() {
		return hash;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		BThread other = (BThread) obj;
		if (hash != other.hash) {
			return false;
		}
		return true;
	}

}
