package BPJ_Programming_Examples.Simulating_flight_of_a_flock_of_birds;

import bpSourceCode.bp.BThread;
import bpSourceCode.bp.eventSets.EventSet;

import static bpSourceCode.bp.BProgram.bp;
import static bpSourceCode.bp.eventSets.EventSetConstants.none;

@SuppressWarnings("serial")
public class CheckCollision  extends BThread{
	Boid boid1, boid2;
	
	public CheckCollision(Boid b1, Boid b2){
		boid1 = b1;
		boid2 = b2;
	}
	@Override
	public void runBThread(){
		EventSet sleep = new EventSet(boid1.endSleep, boid2.endSleep);
		EventSet keepAwayEvent = new EventSet(boid1.keepAway[boid2.index], boid2.keepAway[boid1.index]);
		bp.bSync(none, sleep, keepAwayEvent);
		while(true) {
			double distance = boid1.location.distance(boid2.location);
			
			//boids are too close to one another
			if (distance < Constants.keepAwayNeibourthoodDistance) {
				bp.bSync(none, sleep, none);
			}
			else {
				bp.bSync(none, sleep, keepAwayEvent);
			}
			
		}
	}

}
