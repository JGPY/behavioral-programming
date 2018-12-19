package BPJ_Programming_Examples.Helicopter_flight_and_mission.BDrone.bThreads.environment;

import static bp.eventSets.EventSetConstants.none;
import BPJ_Programming_Examples.Helicopter_flight_and_mission.BDrone.Programs.Vars;
import BPJ_Programming_Examples.Helicopter_flight_and_mission.BDrone.Programs.WallPainting;
import bp.BThread;
import bp.Event;
import bp.eventSets.EventSet;
import bp.exceptions.BPJRequestableSetException;

@SuppressWarnings("serial")
public class YAxis extends BThread {
	@Override
	public void runBThread() throws InterruptedException,
			BPJRequestableSetException {
		Event PrevEvent;
		int y = 0;
		EventSet moveUpOrDown = new EventSet();
		moveUpOrDown.add(WallPainting.eMoveUpMi);
		moveUpOrDown.add(WallPainting.eMoveDownMi);
		bp.bSync(none, moveUpOrDown, none);
		while (true) {

			if (bp.lastEvent.equals(WallPainting.eMoveDownMi)) {
				y--;

			} else if (bp.lastEvent.equals(WallPainting.eMoveUpMi)) {
				y++;

			}
			PrevEvent = bp.lastEvent;
			bp.bSync(none, WallPainting.idle, none);

			if (PrevEvent.equals(WallPainting.eMoveDownMi) && y <= 0) {
				bp.bSync(WallPainting.eEndOfColDown, none,
						WallPainting.eMoveDownMi);
				bp.bSync(none, WallPainting.eMoveUpMi, WallPainting.eMoveDownMi);
			} else if (PrevEvent.equals(WallPainting.eMoveUpMi)
					&& y >= Vars.UPPERWALL) {

				bp.bSync(WallPainting.eEndOfColUp, none, WallPainting.eMoveUpMi);
				bp.bSync(none, WallPainting.eMoveDownMi, WallPainting.eMoveUpMi);
			} else {
				bp.bSync(none, moveUpOrDown, none);
			}
		}

	}
}
