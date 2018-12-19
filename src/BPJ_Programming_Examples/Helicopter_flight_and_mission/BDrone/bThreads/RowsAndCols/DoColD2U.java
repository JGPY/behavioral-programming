package BPJ_Programming_Examples.Helicopter_flight_and_mission.BDrone.bThreads.RowsAndCols;

import static bp.eventSets.EventSetConstants.none;
import BPJ_Programming_Examples.Helicopter_flight_and_mission.BDrone.Programs.WallPainting;
import bp.BThread;
import bp.exceptions.BPJRequestableSetException;

@SuppressWarnings("serial")
public class DoColD2U extends BThread {
	@Override
	public void runBThread() throws InterruptedException,
			BPJRequestableSetException {
		boolean b;
		while (true) {
			b = true;
			bp.bSync(none, WallPainting.eDoColUp, none);
			while (b) {
				bp.bSync(none, WallPainting.idle, none);

				bp.bSync(WallPainting.eMoveUpMi, WallPainting.eEndOfColUp, none);
				if (bp.lastEvent.equals(WallPainting.eEndOfColUp))
					b = false;
			}

			System.out.println("finishedColD2U");
		}

	}
}