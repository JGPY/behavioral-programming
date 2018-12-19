package BPJ_Programming_Examples.Helicopter_flight_and_mission.BDrone.bThreads.moves;

import static bp.eventSets.EventSetConstants.none;
import BPJ_Programming_Examples.Helicopter_flight_and_mission.BDrone.Programs.WallPainting;
import bp.BThread;
import bp.eventSets.EventSet;
import bp.exceptions.BPJRequestableSetException;

@SuppressWarnings("serial")
public class FixWind extends BThread {
	@Override
	public void runBThread() throws InterruptedException,
			BPJRequestableSetException {
		EventSet MiMoves = new EventSet();
		MiMoves.add(WallPainting.eMoveDownMi);
		MiMoves.add(WallPainting.eMoveUpMi);
		MiMoves.add(WallPainting.eMoveLeftMi);
		MiMoves.add(WallPainting.eMoveRightMi);
		while (true) {
			bp.bSync(none, WallPainting.eupdateArrived, none);
			int dx = WallPainting.BDrone.getRealX()
					- WallPainting.BDrone.getX();
			int dy = WallPainting.BDrone.getRealY()
					- WallPainting.BDrone.getY();
			while (dx != 0 || dy != 0) {

				if (dx > 0) {
					// bp.bSync(none, idle, MiMoves);
					bp.bSync(WallPainting.eMoveLeftMaint, none, MiMoves);
				} else if (dx < 0) {
					// bp.bSync(none, idle, MiMoves);
					bp.bSync(WallPainting.eMoveRightMaint, none, MiMoves);
				} else if (dy < 0) {
					// bp.bSync(none, idle, MiMoves);
					bp.bSync(WallPainting.eMoveUpMaint, none, MiMoves);
				} else if (dy > 0) {
					// bp.bSync(none, idle, MiMoves);
					bp.bSync(WallPainting.eMoveDownMaint, none, MiMoves);
				}
				bp.bSync(none, WallPainting.idle, MiMoves);

				dx = WallPainting.BDrone.getRealX()
						- WallPainting.BDrone.getX();
				dy = WallPainting.BDrone.getRealY()
						- WallPainting.BDrone.getY();

			}

		}
	}
}
