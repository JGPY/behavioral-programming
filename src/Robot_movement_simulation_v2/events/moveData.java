package Robot_movement_simulation_v2.events;

import bpSourceCode.bp.Event;

import static Robot_movement_simulation.bThreads.Constants.robotSpeed;
import static Robot_movement_simulation.bThreads.Constants.robotAngle;

/**
 * @author l
 */
public class moveData extends Event {

	public moveData(int speed, int angle) {
		super();
		robotSpeed = speed;
		robotAngle = angle;
		this.setName("moveData");
	}

}