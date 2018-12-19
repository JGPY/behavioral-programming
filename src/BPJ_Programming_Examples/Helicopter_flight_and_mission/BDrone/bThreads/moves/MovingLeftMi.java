package BPJ_Programming_Examples.Helicopter_flight_and_mission.BDrone.bThreads.moves;

import static bp.eventSets.EventSetConstants.none;
import BPJ_Programming_Examples.Helicopter_flight_and_mission.BDrone.Programs.WallPainting;
import bp.BThread;
import bp.exceptions.BPJRequestableSetException;

@SuppressWarnings("serial")
public class MovingLeftMi extends BThread {

	@Override
	public void runBThread() throws InterruptedException,
			BPJRequestableSetException {

		while (true) {
			bp.bSync(none, WallPainting.eMoveLeftMi, none);
			WallPainting.BDrone.setPosition(WallPainting.BDrone.getX() - 1,
					WallPainting.BDrone.getRealY(), WallPainting.BDrone.getZ());
			bp.bSync(WallPainting.eMoveLeft, none, none);

		}
	}
}