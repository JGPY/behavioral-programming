package robotNavigationSimulation.constants;

import robotNavigationSimulation.map.Node;
import robotNavigationSimulation.UI.GUI;

import java.util.ArrayList;

/**
 * Store static data
 * @author L
 *
 */
public  class Constants {

	public static GUI gui;

	public static boolean uiMap = false;
	/**
	 * AStarMap size
	 */
	public static int mapSizewidth =600;
	public static int mapSize = 600;

	/**
	 * Initial position of the robot
	 */

	public static Node robotStartPosition = new Node(40,40);

	/**
	 * End position of the robot
	 */

	public static Node robotEndPosition = new Node(480,500);

	/**
	 * Current position of the robot
	 */
	public static Node robotCurrentPosition = robotStartPosition;

	/**
	 * Store robot walking track
	 */
	public static ArrayList<Node> movingTrail = new ArrayList();

    /**
     * Mobile robot angle
     *
     * Angle range [1, 360]
     * [90, 180, 270, 360] = [East, South, Weat, North]
     */
    public static int angle =0;

    /**
     * Robot movement speed
     */
    public static int robotSpeed =0;

	/**
	 * The acceleration for speed adjustment.
	 */
	
	public static int sleep = 10;

}
