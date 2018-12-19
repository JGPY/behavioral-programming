package bpSourceCode.bp.state.unittest.ts;

import bpSourceCode.bp.eventSets.RequestableEventSet;

public class TSTransition {
	public TSState fromState;
	public RequestableEventSet transitionEvents;
	public TSState toState; 
	
	public TSTransition(TSState fromState, RequestableEventSet transitionEvents, TSState toState) {
		this.fromState = fromState; 
		this.transitionEvents = transitionEvents; 
		this.toState = toState;  
	}

}
