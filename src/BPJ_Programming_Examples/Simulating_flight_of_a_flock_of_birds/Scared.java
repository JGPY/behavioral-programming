package BPJ_Programming_Examples.Simulating_flight_of_a_flock_of_birds;

import bpSourceCode.bp.BThread;
import bpSourceCode.bp.eventSets.EventSet;

import static bpSourceCode.bp.BProgram.bp;
import static bpSourceCode.bp.eventSets.EventSetConstants.none;

@SuppressWarnings("serial")
public class Scared  extends BThread{
	@Override
	public void runBThread(){
		bp.bSync(none, Events.mouseReleased, new EventSet(Events.matchSpeed, Events.flyTowardsCenterOfMass));
	}

}
