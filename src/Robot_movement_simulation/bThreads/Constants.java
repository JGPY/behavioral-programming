package Robot_movement_simulation.bThreads;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Constants {

	public static GUI gui;
	/**
	 * Map size
	 */
	public static int mapSizewidth =600;
	public static int mapSize = 600;

	/**
	 * Initial position of the robot
	 */
	public static Point robotInitialPosition = new Point(40,40);

	/**
	 * Current position of the robot
	 */
	public static Point robotCurrentPosition = robotInitialPosition;

	/**
	 * Robot walking track storage
	 */
	public static ArrayList<Point> listTrackFoot = new ArrayList();

	/**
	 * Robot sensor acquisition data
	 */
	public static Map<String,Integer> sensorData = new HashMap<>(9);


	/**
	 * Robot movement angle and speed
	 */

	public static int robotSpeed =0;
	public static int robotAngle =0;


	/**
	 * The acceleration for speed adjustment.
	 */
	
	public static int sleep = 10;

}
