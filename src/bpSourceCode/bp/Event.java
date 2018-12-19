package bpSourceCode.bp;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;

import bpSourceCode.bp.eventSets.EventSetInterface;
import bpSourceCode.bp.eventSets.RequestableInterface;
import bpSourceCode.bp.exceptions.BPJRequestableSetException;

/**
 * A base class for events
 */
public class Event implements EventSetInterface, RequestableInterface, Serializable {

	static int numerator = 0;
	int id = numerator++;
	
	private String name = this.getClass().getSimpleName();

	@Override
	public boolean contains(Object o) {
		return this.equals(o);
	}

	@Override
	public Iterator<RequestableInterface> iterator() {
		return new SingleEventIterator(this);
	}

	public Event() {
	}

	public Event(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return name + "(ID=" + id+  ")";
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public Event get(int index) {
		if (index == 0)
			return (this);
		throw (new ArrayIndexOutOfBoundsException());
	}

	public boolean add(RequestableInterface r) throws BPJRequestableSetException {
		throw new BPJRequestableSetException();

	}

	@Override
	public boolean isEvent() {
		return true;
	}

	@Override
	public int size() {
		return 1;
	}

	@Override
	public Event getEvent() throws BPJRequestableSetException {

		return this;
	}

	@Override
	public ArrayList<Event> getEventList() {
		ArrayList<Event> list = new ArrayList<Event>();
		this.addEventsToList(list);
		return list;
	}

	@Override
	public void addEventsToList(ArrayList<Event> list) {
		list.add(this); 
	}

	@Override
	public int hashCode() {
		return id;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Event other = (Event) obj;
		if (id != other.id)
			return false;
		return true;
	}
	
	
	
}

/**
 * An iterator over a single event object. Allows to view an event as a
 * (singleton) set.
 */
class SingleEventIterator implements Iterator<RequestableInterface> {
	Event e;

	public SingleEventIterator(Event e) {
		this.e = e;
	}

	@Override
	public boolean hasNext() {
		return e != null;
	}

	@Override
	public Event next() {
		Event tmp = e;
		e = null;
		return tmp;
	}

	@Override
	public void remove() {
		throw new UnsupportedOperationException();
	}
}
