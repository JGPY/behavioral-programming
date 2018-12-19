package BPJ_Programming_Examples.Simulating_flight_of_a_flock_of_birds;

import bpSourceCode.bp.Event;

public class Events {
	public static Event mousePressed = new Event("Mouse Pressed");
	public static Event mouseReleased = new Event("Mouse Released");
	public static Event matchSpeed = new Event("Match Speed");
	public static Event keepSpeed = new Event("Keep Speed");
	public static Event flyTowardsCenterOfMass = new Event("Fly Towards Center Of Mass");
	public static Event flyAwayFromCenterOfMass = new Event("Fly Away From Center Of Mass");
}
