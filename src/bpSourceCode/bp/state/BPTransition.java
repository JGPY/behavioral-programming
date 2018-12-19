package bpSourceCode.bp.state;

//A triple reflecting a run-time actual transion - needed initially for fairness
//TODO - we dont really need the fromState. But it seems clearer this way
//and may be useful for other things too. Check if needed later. 
import bpSourceCode.bp.Event;

public class BPTransition {
	public BPState fromState;
	public BPState toState;
	public Event event;

	public BPTransition(BPState fromState, BPState toState, Event event) {
		this.fromState = fromState;
		this.toState = toState;
		this.event = event;
	}

	// TODO - do we need this equals?
	public boolean equals(BPTransition o) {
		if (fromState == o.fromState && toState == o.toState && event == o.event) {
			return true;
		} else {
			return false;
		}

	}

}
