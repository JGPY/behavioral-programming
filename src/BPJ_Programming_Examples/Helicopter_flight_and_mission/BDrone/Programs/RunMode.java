package BPJ_Programming_Examples.Helicopter_flight_and_mission.BDrone.Programs;

public enum RunMode {
	netWorkingMode, bpApiMode;

	public static RunMode fromString(String arg) {

		switch (arg) {
		case "netWorkingMode":
			return RunMode.netWorkingMode;

		case "bpApiMode":

			return RunMode.bpApiMode;
		default:
			return null;

		}
	}
}
