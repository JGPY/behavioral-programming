package BPJ_Programming_Examples.Helicopter_flight_and_mission.BDrone.bThreads.Colors;

import static bp.eventSets.EventSetConstants.none;
import BPJ_Programming_Examples.Helicopter_flight_and_mission.BDrone.Programs.WallPainting;
import bp.BThread;
import bp.eventSets.EventSet;
import bp.exceptions.BPJRequestableSetException;

@SuppressWarnings("serial")
public class ChangePaintingColor extends BThread {
	@Override
	public void runBThread() throws InterruptedException,
			BPJRequestableSetException {
		EventSet ChangingColor = new EventSet();
		ChangingColor.add(WallPainting.eChangeColorBLACK);
		ChangingColor.add(WallPainting.eChangeColorRED);
		ChangingColor.add(WallPainting.eChangeColorBLUE);
		ChangingColor.add(WallPainting.eChangeColorGREEN);
		while (true) {
			bp.bSync(none, ChangingColor, none);
			WallPainting.BDrone.setColor(bp.lastEvent.getName());

		}

	}
}