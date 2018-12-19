package bpSourceCode.bp;

import java.math.BigDecimal;

//This class is used to point to a particular request among
//many non-deterministic choices, in a way that persists across
//iterative runs.
public class EventChoice {
	double btID =-1; 			// bthread id
	int eventSetSeq =-1;	// The sequence # of the event set within the
							// requested events set of this be-thread.
	int eventSeq = -1; 		// The seq # of the event within above set
	// String eventString - a toString of the event itself for later printing
	
	public String toString() {
		
		BigDecimal bd = new BigDecimal(btID);
	    bd = bd.setScale(3,BigDecimal.ROUND_HALF_UP);
		return("<" + bd.doubleValue() + "/" + eventSetSeq + "/" + eventSeq + ">");
	}
	
	public EventChoice(double btID, int eventSetSeq, int eventSeq) {
		this.btID = btID;
		this.eventSetSeq = eventSetSeq;
		this.eventSeq = eventSeq;
	}
	
	public EventChoice() {
		}
	public Event getEvent(BProgram bp) {
		BThread bt = (bp.getAllBThreads()).get(btID);
		return ((bt.requestedEvents.get(eventSetSeq)).get(eventSeq)).getEvent();
	}
}

