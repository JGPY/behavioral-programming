package BPJ_Programming_Examples.Helicopter_flight_and_mission.HeliSim.ClientSide;

public class ClientPanel {

	public static void main(String[] args) {

		for (int i = 0; i < 180; i++) {
			try {
				Thread.sleep(50);
				SocketClient a = new SocketClient("Rel", 5, 3);
			} catch (Exception e) {

			}
		}
		// SocketClient a = new SocketClient((int) Math.random() * 500,
		// (int) Math.random() * 500);

	}
}
