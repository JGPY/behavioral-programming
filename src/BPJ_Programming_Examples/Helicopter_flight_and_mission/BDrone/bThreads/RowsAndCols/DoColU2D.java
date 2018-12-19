package BPJ_Programming_Examples.Helicopter_flight_and_mission.BDrone.bThreads.RowsAndCols;

import static bp.eventSets.EventSetConstants.none;
import BPJ_Programming_Examples.Helicopter_flight_and_mission.BDrone.Programs.WallPainting;
import bp.BThread;
import bp.exceptions.BPJRequestableSetException;

@SuppressWarnings("serial")
public class DoColU2D extends BThread {
	@Override
	public void runBThread() throws InterruptedException,
			BPJRequestableSetException {
		boolean b;
		while (true) {
			bp.bSync(none, WallPainting.eDoColDown, none);
			b = true;
			while (b) {
				bp.bSync(none, WallPainting.idle, none);
				if (bp.lastEvent.equals(WallPainting.eEndOfColDown))
					b = false;
				bp.bSync(WallPainting.eMoveDownMi, WallPainting.eEndOfColDown,
						none);
				if (bp.lastEvent.equals(WallPainting.eEndOfColDown))
					b = false;
			}

			System.out.println("finishedColU2D");
		}

	}
}