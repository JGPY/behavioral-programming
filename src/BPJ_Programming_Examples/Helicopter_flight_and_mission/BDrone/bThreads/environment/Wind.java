package BPJ_Programming_Examples.Helicopter_flight_and_mission.BDrone.bThreads.environment;

import static bp.eventSets.EventSetConstants.none;
import BPJ_Programming_Examples.Helicopter_flight_and_mission.BDrone.Programs.WallPainting;
import bp.BThread;
import bp.eventSets.EventSet;
import bp.exceptions.BPJRequestableSetException;

@SuppressWarnings("serial")
public class Wind extends BThread {
	@Override
	public void runBThread() throws InterruptedException,
			BPJRequestableSetException {
		EventSet Moves = new EventSet();
		Moves.add(WallPainting.eMoveDownMi);
		Moves.add(WallPainting.eMoveUpMi);
		Moves.add(WallPainting.eMoveRightMi);
		Moves.add(WallPainting.eMoveLeftMi);

		while (true) {
			int steps = (int) (Math.random() * 100 + 75);
			for (int i = 0; i < steps; i++) {
				bp.bSync(none, Moves, none);
			}
			bp.bSync(none, WallPainting.idle, none);
			int x = (int) (Math.random() * 150 - 75);
			int y = (int) (Math.random() * 150 - 75);
			WallPainting.BDrone.setRealPosition(WallPainting.BDrone.getRealX()
					+ x, WallPainting.BDrone.getRealY() + y, 0);

			WallPainting.BDrone
					.sendRealPosition(WallPainting.BDrone.getColor());

			bp.bSync(WallPainting.eupdateArrived, none, none);
		}
	}

}
