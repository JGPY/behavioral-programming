package BPJ_Programming_Examples.Helicopter_flight_and_mission.BDrone.bThreads.Colors;

import static bp.eventSets.EventSetConstants.none;
import BPJ_Programming_Examples.Helicopter_flight_and_mission.BDrone.Programs.Vars;
import BPJ_Programming_Examples.Helicopter_flight_and_mission.BDrone.Programs.WallPainting;
import bp.BThread;
import bp.eventSets.EventSet;
import bp.exceptions.BPJRequestableSetException;

@SuppressWarnings("serial")
public class ColorControl extends BThread {
	public void runBThread() throws InterruptedException,
			BPJRequestableSetException {
		EventSet MovingSet = new EventSet();
		MovingSet.add(WallPainting.eMoveDownMi);
		MovingSet.add(WallPainting.eMoveRightMi);
		MovingSet.add(WallPainting.eMoveLeftMi);
		MovingSet.add(WallPainting.eMoveUpMi);
		int steps = 0;
		while (true) {
			bp.bSync(none, MovingSet, none);
			steps++;
			if (steps == Vars.STEPSBETWEENCOLORS) {
				double a = Math.random();
				if (a <= 0.25) {
					bp.bSync(WallPainting.eChangeColorBLACK, none, none);
				} else if (a <= 0.5 && a > 0.25) {
					bp.bSync(WallPainting.eChangeColorRED, none, none);
				} else if (a <= 0.75 && a > 0.5) {
					bp.bSync(WallPainting.eChangeColorGREEN, none, none);
				} else {
					bp.bSync(WallPainting.eChangeColorBLUE, none, none);
				}
				steps = 0;
			}
		}
	}

}
