package BPJ_Programming_Examples.Helicopter_flight_and_mission.BDrone.bThreads.moves;

import static bp.eventSets.EventSetConstants.none;
import BPJ_Programming_Examples.Helicopter_flight_and_mission.BDrone.Programs.WallPainting;
import bp.BThread;
import bp.eventSets.EventSet;
import bp.exceptions.BPJRequestableSetException;

@SuppressWarnings("serial")
public class MovingLeftMaint extends BThread {

	@Override
	public void runBThread() throws InterruptedException,
			BPJRequestableSetException {
		EventSet RegularMoves = new EventSet();
		RegularMoves.add(WallPainting.eMoveDownMi);
		RegularMoves.add(WallPainting.eMoveUpMi);
		RegularMoves.add(WallPainting.eMoveLeftMi);
		RegularMoves.add(WallPainting.eMoveRightMi);
		while (true) {
			bp.bSync(none, WallPainting.eMoveLeftMaint, none);

			bp.bSync(WallPainting.eMoveLeft, none, RegularMoves);

		}
	}
}
