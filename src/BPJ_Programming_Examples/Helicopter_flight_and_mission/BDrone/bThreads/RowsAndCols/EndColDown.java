package BPJ_Programming_Examples.Helicopter_flight_and_mission.BDrone.bThreads.RowsAndCols;

import static bp.eventSets.EventSetConstants.none;
import BPJ_Programming_Examples.Helicopter_flight_and_mission.BDrone.Programs.WallPainting;
import bp.BThread;
import bp.exceptions.BPJRequestableSetException;

@SuppressWarnings("serial")
public class EndColDown extends BThread {
	@Override
	public void runBThread() throws InterruptedException,
			BPJRequestableSetException {
		while (true) {

			bp.bSync(none, WallPainting.eEndOfColDown, none);
			// bp.bSync(none, idle, none);
			bp.bSync(WallPainting.eDoRowRight, none, WallPainting.eMoveDownMi);
			bp.bSync(none, WallPainting.eEndOfRowRight, none);
			// bp.bSync(none, idle, none);
			bp.bSync(WallPainting.eDoColUp, none, none);
		}

	}
}
