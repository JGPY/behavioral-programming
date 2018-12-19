package bpSourceCode.bp.state.unittest.ts;

import bpSourceCode.bp.eventSets.EventSet;
import bpSourceCode.bp.eventSets.RequestableEventSet;

// This class contains all the externally provided data of the BT-State. 
// Perhaps a BT-State should inherit from it???

public class TSState {
	public String name;
	public boolean isHot;
	static public boolean HOT = true;
	static public boolean COLD = false;
	public boolean isBad;
	static public boolean BAD = true;
	static public boolean NOTBAD = false;
	public RequestableEventSet requestedEvents  = new RequestableEventSet(); 
	public EventSet watchedEvents = new EventSet() ;
	public EventSet blockedEvents  = new EventSet();

	public TSState(String name, boolean isHot, boolean isBad) {
		this.name = name;
		this.isHot = isHot; 
		this.isBad = isBad;
	}

	public String toString() {
		return name;
	}
	public boolean equals(TSState s) {
		return (this.name.equals(s.name));
	}
}
