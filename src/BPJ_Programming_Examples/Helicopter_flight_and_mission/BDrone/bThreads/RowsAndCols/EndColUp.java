package BPJ_Programming_Examples.Helicopter_flight_and_mission.BDrone.bThreads.RowsAndCols;

import static bp.eventSets.EventSetConstants.none;
import BPJ_Programming_Examples.Helicopter_flight_and_mission.BDrone.Programs.WallPainting;
import bp.BThread;
import bp.exceptions.BPJRequestableSetException;

@SuppressWarnings("serial")
public class EndColUp extends BThread {
	@Override
	public void runBThread() throws InterruptedException,
			BPJRequestableSetException {
		while (true) {
			bp.bSync(none, WallPainting.eEndOfColUp, none);
			// bp.bSync(none, idle, none);
			bp.bSync(WallPainting.eDoRowRight, none, WallPainting.eMoveUpMi);
			bp.bSync(none, WallPainting.eEndOfRowRight, none);
			// bp.bSync(none, idle, none);
			bp.bSync(WallPainting.eDoColDown, none, none);
		}

	}
}