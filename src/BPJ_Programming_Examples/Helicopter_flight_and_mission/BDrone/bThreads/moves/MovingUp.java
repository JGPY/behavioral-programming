package BPJ_Programming_Examples.Helicopter_flight_and_mission.BDrone.bThreads.moves;

import static bp.eventSets.EventSetConstants.none;
import BPJ_Programming_Examples.Helicopter_flight_and_mission.BDrone.Programs.Vars;
import BPJ_Programming_Examples.Helicopter_flight_and_mission.BDrone.Programs.WallPainting;
import bp.BThread;
import bp.exceptions.BPJRequestableSetException;

@SuppressWarnings("serial")
public class MovingUp extends BThread {

	@Override
	public void runBThread() throws InterruptedException,
			BPJRequestableSetException {
		while (true) {
			bp.bSync(none, WallPainting.eMoveUp, none);
			WallPainting.BDrone.setRealPosition(WallPainting.BDrone.getRealX(),
					WallPainting.BDrone.getRealY() + 1,
					WallPainting.BDrone.getRealZ());

			WallPainting.BDrone
					.sendRealPosition(WallPainting.BDrone.getColor());
			try {
				Thread.sleep(Vars.TIMEBETWEENSYNCS);
			} catch (Exception e) {

			}

		}
	}
}