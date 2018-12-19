package BPJ_Programming_Examples.Helicopter_flight_and_mission.BDrone.bThreads.RowsAndCols;

import static bp.eventSets.EventSetConstants.none;
import BPJ_Programming_Examples.Helicopter_flight_and_mission.BDrone.Programs.WallPainting;
import bp.BThread;
import bp.eventSets.EventSet;
import bp.exceptions.BPJRequestableSetException;

@SuppressWarnings("serial")
public class DoRowL2R extends BThread {

	@Override
	public void runBThread() throws InterruptedException,
			BPJRequestableSetException {

		EventSet MoveDownOrUp = new EventSet();
		MoveDownOrUp.add(WallPainting.eMoveDownMi);
		MoveDownOrUp.add(WallPainting.eMoveUpMi);
		while (true) {
			bp.bSync(none, WallPainting.eDoRowRight, none);
			while (!bp.lastEvent.equals(WallPainting.eEndOfRowRight)) {
				// bp.bSync(none, idle, MoveDownOrUp);
				bp.bSync(WallPainting.eMoveRightMi,
						WallPainting.eEndOfRowRight, MoveDownOrUp);

			}
			System.out.println("finishedRowL2R");
		}

	}
}