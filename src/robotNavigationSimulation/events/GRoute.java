package robotNavigationSimulation.events;

import bpSourceCode.bp.Event;
import robotNavigationSimulation.map.Node;

import java.util.ArrayList;

/**
 * An event that is requested (with high priority)
 * @author L
 */
public class GRoute extends Event {

	/**
	 * Global route
	 */
	private  ArrayList<Node> route;

	/**
	 * Constructor.
	 *
	 * @param route
	 *            Route after global path planning
	 */
	public GRoute(ArrayList<Node> route) {
		super();
		this.route = route;
		this.setName("GRoute(" + route + ")");
	}

	public ArrayList<Node> getRoute() {
		return route;
	}

	/**
	 * @see Object#toString()
	 */

}
