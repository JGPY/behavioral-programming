package BPJ_Programming_Examples.Helicopter_flight_and_mission.BDrone.events.Moves;

import bp.Event;

@SuppressWarnings("serial")
public class MoveLeft extends Event {
	private boolean maintenace;

	public MoveLeft(String name) {
		this.setName(name);
		this.maintenace = false;
	}

	public MoveLeft(String name, boolean maintencace) {
		this.setName(name);
		this.maintenace = maintencace;
	}

	public boolean isMaintenace() {
		return maintenace;
	}
}