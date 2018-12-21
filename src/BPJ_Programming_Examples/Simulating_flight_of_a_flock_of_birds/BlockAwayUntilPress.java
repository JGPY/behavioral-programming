package BPJ_Programming_Examples.Simulating_flight_of_a_flock_of_birds;

import bpSourceCode.bp.BThread;

import static bpSourceCode.bp.BProgram.bp;
import static bpSourceCode.bp.eventSets.EventSetConstants.none;

@SuppressWarnings("serial")
public class BlockAwayUntilPress  extends BThread{
	@Override
	public void runBThread(){
		bp.bSync(none, Events.mousePressed, Events.flyAwayFromCenterOfMass);
	}

}
