package bpSourceCode.bp;


// This class is a node in an execution trace.
// used initially in iterative execution - for 
// iterating through non-deterministic choices

public class ChoicePoint {
	// The first bthread that had a requested event that is not blocked 
	// (requester of first non-deterministic choice)
	// Epsilon of non-det requests may be counted from here. see parameter)
	double firstBThread = -1;

	// The event that was selected in THIS run. (of all the non-det choices).
	// Epsilon of non-det requests may be counted from this be-thread too
	// See parameter.
	
	EventChoice currentChoice = null; 
	
	// The next event in the list of non-det choices 
	
	EventChoice nextChoice = null ;

	
	public String toString() {
		return ("(" + "FirstBT=" + firstBThread + ", currentChoice=" + currentChoice + 
				", nextChoice=" + nextChoice+ ")"); 
	} 
}


