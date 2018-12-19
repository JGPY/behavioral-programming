package BPJ_Programming_Examples.Helicopter_flight_and_mission.BDrone.events.Moves;

import bp.Event;

@SuppressWarnings("serial")
public class MoveUp extends Event {
	private boolean maintenace;

	public MoveUp(String name) {
		this.setName(name);
		this.maintenace = false;
	}

	public MoveUp(String name, boolean maintencace) {
		this.setName(name);
		this.maintenace = maintencace;
	}

	public boolean isMaintenace() {
		return maintenace;
	}
}
