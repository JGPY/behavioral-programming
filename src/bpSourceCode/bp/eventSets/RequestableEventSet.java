package bpSourceCode.bp.eventSets;

import java.io.Serializable;
import java.util.ArrayList;

import bpSourceCode.bp.Event;
import bpSourceCode.bp.exceptions.BPJRequestableSetException;

@SuppressWarnings("serial")
public class RequestableEventSet extends ArrayList<RequestableInterface> implements EventSetInterface, RequestableInterface, Serializable {

	private String name = this.getClass().getSimpleName();

	public RequestableEventSet(RequestableInterface... reqs) {
		for (RequestableInterface r : reqs) {
			add(r);
		}
	}

	public RequestableEventSet(String name, RequestableInterface... reqs) {
		this(reqs);
		this.setName(name);
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return name;
	}

	@Override
	public boolean isEvent() {
		return (false);
	}

	@Override
    public boolean contains(Object o) {
		for (RequestableInterface r : this) {
			if (r.contains(o)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public Event getEvent() throws BPJRequestableSetException {
		throw new BPJRequestableSetException();
	}

	@Override
	public ArrayList<Event> getEventList() {
		ArrayList<Event> list = new ArrayList<Event>();
		this.addEventsToList(list);
		return list;
	}

	@Override
	public void addEventsToList(ArrayList<Event> list) {
		for (RequestableInterface ri : this) {
			ri.addEventsToList(list);
		}
	}
}
