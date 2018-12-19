package BPJ_Programming_Examples.Helicopter_flight_and_mission.BDrone.bThreads.StartAndFinish;

import static bp.eventSets.EventSetConstants.none;
import BPJ_Programming_Examples.Helicopter_flight_and_mission.BDrone.Programs.WallPainting;
import bp.BThread;
import bp.exceptions.BPJRequestableSetException;

@SuppressWarnings("serial")
public class BeginPaintingTheWall extends BThread {
	@Override
	public void runBThread() throws InterruptedException,
			BPJRequestableSetException {
		// bp.bSync(eBeginPainting, none, none);
		// bp.bSync(none, eBeginPainting, none);
		bp.bSync(WallPainting.eDoColUp, none, none);
	}

}
