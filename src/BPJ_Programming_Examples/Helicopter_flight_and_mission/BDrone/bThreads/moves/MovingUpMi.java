package BPJ_Programming_Examples.Helicopter_flight_and_mission.BDrone.bThreads.moves;

import static bp.eventSets.EventSetConstants.none;
import BPJ_Programming_Examples.Helicopter_flight_and_mission.BDrone.Programs.WallPainting;
import bp.BThread;
import bp.exceptions.BPJRequestableSetException;

@SuppressWarnings("serial")
public class MovingUpMi extends BThread {

	@Override
	public void runBThread() throws InterruptedException,
			BPJRequestableSetException {

		while (true) {
			bp.bSync(none, WallPainting.eMoveUpMi, none);
			WallPainting.BDrone.setPosition(WallPainting.BDrone.getX(),
					WallPainting.BDrone.getRealY() + 1,
					WallPainting.BDrone.getZ());
			bp.bSync(WallPainting.eMoveUp, none, none);

		}
	}
}
