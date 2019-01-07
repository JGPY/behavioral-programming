package robotNavigationSimulation.constants;

import robotNavigationSimulation.events.Node;
import java.util.ArrayList;

/**
 * Store static data
 * @author L
 *
 */
public  class Constants {

	/**
	 * AStarMap size
	 */
	public static int mapSizewidth =600;
	public static int mapSize = 600;

	/**
	 * Initial position of the robot
	 */

	public static Node robotInitialPosition = new Node(40,40);

	/**
	 * End position of the robot
	 */

	public static Node robotEndPosition = new Node(500,500);

	/**
	 * Current position of the robot
	 */
	public static Node robotCurrentPosition = robotInitialPosition;

	/**
	 * Store robot walking track
	 */
	public static ArrayList<Node> listTrackFoot = new ArrayList();

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
