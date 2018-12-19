package BPJ_Programming_Examples.Helicopter_flight_and_mission.BDrone.Programs;

public class Vars {
	public static RunMode runMode = RunMode.fromString(System
			.getProperty("runmode"));
	public static final long TIMEBETWEENSYNCS = 20;
	public static final int RIGHTWALL = 500; // x coordinate of right boundary (left boundary is x=0)
	public static final int UPPERWALL = 200; // y coordinate of top boundary (bottom boundary is y=0)
	public static final int STEPSBETWEENCOLORS = 50; // number of pixels between color changes
	public static final int ColWidth = 3; // distance in pixels between centers of up/down columns
}
