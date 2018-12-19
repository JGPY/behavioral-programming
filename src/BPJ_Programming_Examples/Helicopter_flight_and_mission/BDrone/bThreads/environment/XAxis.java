package BPJ_Programming_Examples.Helicopter_flight_and_mission.BDrone.bThreads.environment;

import static bp.eventSets.EventSetConstants.none;
import BPJ_Programming_Examples.Helicopter_flight_and_mission.BDrone.Programs.Vars;
import BPJ_Programming_Examples.Helicopter_flight_and_mission.BDrone.Programs.WallPainting;
import bp.BThread;
import bp.eventSets.EventSet;
import bp.exceptions.BPJRequestableSetException;

@SuppressWarnings("serial")
public class XAxis extends BThread {
	@Override
	public void runBThread() throws InterruptedException,
			BPJRequestableSetException {
		int x = 0;

		EventSet MoveRightOrLeft = new EventSet();
		MoveRightOrLeft.add(WallPainting.eMoveLeftMi);
		MoveRightOrLeft.add(WallPainting.eMoveRightMi);

		bp.bSync(none, WallPainting.eDoRowRight, none);
		while (true) {

			if (bp.lastEvent.equals(WallPainting.eMoveRightMi)) {
				x++;
			} else if (bp.lastEvent.equals(WallPainting.eMoveLeftMi)) {
				x--;
			}
			if (x == Vars.RIGHTWALL) {
				// bp.bSync(eStopPainting, none, Moving);
			} else if (x % Vars.ColWidth == 0 && x != 0) {
				bp.bSync(WallPainting.eEndOfRowRight, none,
						WallPainting.eMoveRightMi);
				bp.bSync(none, WallPainting.eDoRowRight, none);

			}

			bp.bSync(none, MoveRightOrLeft, none);
		}
	}
}