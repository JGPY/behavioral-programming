package bpSourceCode.bp.eventSets;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;

import bpSourceCode.bp.Event;
import bpSourceCode.bp.exceptions.BPJRequestableSetException;

/**
 * A filter that doesn't match any object.
 */
public class EmptyEventSet implements EventSetInterface, RequestableInterface, Serializable {
	/**
	 * @see bpSourceCode.bp.eventSets.EventSetInterface#contains(Object)
	 */
	@Override
	public boolean contains(Object o) {
		return false;
	}

	@Override
	public Iterator<RequestableInterface> iterator() {
		return new EmptyEventIterator();
	}

	@Override
	public String toString() {
		return this.getClass().getSimpleName();
	}

	@Override
	public RequestableInterface get(int index) {
		throw new ArrayIndexOutOfBoundsException();
	}

	@Override
	public int size() {
		return (0);
	}

	@Override
	public void addEventsToList(ArrayList<Event> list) {
		// Just return
	}

	@Override
	public Event getEvent() throws BPJRequestableSetException {
		throw new BPJRequestableSetException();
	}

	@Override
	public ArrayList<Event> getEventList() {
		return (new ArrayList<Event>()); 
	}

	@Override
	public boolean isEvent() {
		return false;
	}
}

/**
 * An iterator over an empty set of events.
 */
class EmptyEventIterator implements Iterator<RequestableInterface> {
	@Override
	public boolean hasNext() {
		return false;
	}

	@Override
	public Event next() {
		throw new UnsupportedOperationException();
	}

	@Override
	public void remove() {
		throw new UnsupportedOperationException();
	}
}
