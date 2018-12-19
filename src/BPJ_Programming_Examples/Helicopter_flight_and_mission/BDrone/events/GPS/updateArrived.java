package BPJ_Programming_Examples.Helicopter_flight_and_mission.BDrone.events.GPS;

import bp.Event;

@SuppressWarnings("serial")
public class updateArrived extends Event {

	public updateArrived(String name) {
		this.setName(name);
	}
}
