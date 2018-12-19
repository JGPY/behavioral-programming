package BPJ_Programming_Examples.Helicopter_flight_and_mission.BDrone.bThreads.StartAndFinish;

import static bp.eventSets.EventSetConstants.none;
import BPJ_Programming_Examples.Helicopter_flight_and_mission.BDrone.Programs.WallPainting;
import bp.BThread;
import bp.exceptions.BPJRequestableSetException;

@SuppressWarnings("serial")
public class IdlingThread extends BThread {
	@Override
	public void runBThread() throws InterruptedException,
			BPJRequestableSetException {
		int c = 0;
		while (true) {
			bp.bSync(WallPainting.idle, none, none);

			c++;
		}

	}

}