package BPJ_Programming_Examples.Helicopter_flight_and_mission.BDrone.Programs;

import static bp.BProgram.bp;
import static bp.eventSets.EventSetConstants.none;
import bp.BThread;
import bp.exceptions.BPJRequestableSetException;

public class ClockThread extends Thread {
	public ClockThread() {
		while (true) {
			try {
				Thread.sleep(1000);
				BThread sc = new ClockBThread();
				bp.add(sc, 0.11);
				sc.startBThread();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}
}

@SuppressWarnings("serial")
class ClockBThread extends BThread {

	@Override
	public void runBThread() throws InterruptedException,
			BPJRequestableSetException {
		bp.bSync(WallPainting.tick, none, none);

	}

}