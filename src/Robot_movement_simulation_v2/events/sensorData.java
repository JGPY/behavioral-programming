package Robot_movement_simulation_v2.events;

import bpSourceCode.bp.Event;

import static Robot_movement_simulation.bThreads.Constants.sensorData;
import java.util.Map;

/**
 * @author l
 */
public  class sensorData extends Event {


	public sensorData(Map<String,Integer> map) {
		super();
		sensorData = map;
		this.setName("sensorData");
	}

}