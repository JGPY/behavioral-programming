package bpSourceCode.bp.eventSets;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Iterator;

// moveCar.Main class
@SuppressWarnings("serial")
public class EventSet extends HashSet<EventSetInterface> implements EventSetInterface, Serializable {

	private String name = this.getClass().getSimpleName();

	public EventSet(EventSetInterface... eSetInterfaces) {
		super();

		for (EventSetInterface eSetInterface : eSetInterfaces) {
			add(eSetInterface);
		}
	}

	public EventSet(String name, EventSetInterface... eSetInterfaces) {
		this(eSetInterfaces);
		this.setName(name);
	}

	@Override
	public boolean contains(Object o) {
		Iterator<EventSetInterface> itr = this.iterator();

		while (itr.hasNext()) {
			EventSetInterface eSetInterface = itr.next();
			if (eSetInterface.contains(o)) {
				return true;
			}
		}

		return false;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return name;
	}
}