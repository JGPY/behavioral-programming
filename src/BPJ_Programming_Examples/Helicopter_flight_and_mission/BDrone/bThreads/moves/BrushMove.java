package BPJ_Programming_Examples.Helicopter_flight_and_mission.BDrone.bThreads.moves;

import static bp.eventSets.EventSetConstants.none;
import BPJ_Programming_Examples.Helicopter_flight_and_mission.BDrone.Programs.WallPainting;
import bp.BThread;
import bp.eventSets.EventSet;
import bp.exceptions.BPJRequestableSetException;

@SuppressWarnings("serial")
public class BrushMove extends BThread {
	@Override
	public void runBThread() throws InterruptedException,
			BPJRequestableSetException {
		EventSet Moving = new EventSet();
		Moving.add(WallPainting.eMoveDownMi);
		Moving.add(WallPainting.eMoveUpMi);
		Moving.add(WallPainting.eDoColDown);
		Moving.add(WallPainting.eDoColUp);

		EventSet Blocked = new EventSet();
		Blocked.add(WallPainting.eEndOfColDown);
		Blocked.add(WallPainting.eEndOfColUp);
		Blocked.add(WallPainting.eEndOfRowLeft);
		Blocked.add(WallPainting.eEndOfRowRight);
		Blocked.add(Moving);

		while (true) {
			bp.bSync(none, Moving, none);
			bp.bSync(none, WallPainting.idle, Blocked);
			bp.bSync(WallPainting.eMoveRightMi, none, Blocked);
			bp.bSync(none, WallPainting.idle, Blocked);
			bp.bSync(WallPainting.eMoveLeftMi, none, Blocked);
			bp.bSync(none, WallPainting.idle, Blocked);
			bp.bSync(WallPainting.eMoveLeftMi, none, Blocked);
			bp.bSync(none, WallPainting.idle, Blocked);
			bp.bSync(WallPainting.eMoveRightMi, none, Blocked);
		}

	}

}