package BPJ_Programming_Examples.Helicopter_flight_and_mission.BDrone.bThreads.StartAndFinish;

import static bp.eventSets.EventSetConstants.all;
import static bp.eventSets.EventSetConstants.none;
import BPJ_Programming_Examples.Helicopter_flight_and_mission.BDrone.Programs.WallPainting;
import bp.BThread;
import bp.exceptions.BPJRequestableSetException;

@SuppressWarnings("serial")
public class StopPaintingTheWall extends BThread {
	@Override
	public void runBThread() throws InterruptedException,
			BPJRequestableSetException {

		bp.bSync(none, WallPainting.eStopPainting, none);
		System.out.println("FINISH");
		bp.bSync(none, none, all);
	}

}