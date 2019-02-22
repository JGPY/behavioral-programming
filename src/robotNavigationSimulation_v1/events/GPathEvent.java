package robotNavigationSimulation_v1.events;

import bpSourceCode.bp.Event;

import java.util.ArrayList;

/**
 * An event that is requested (with high priority)
 * @author L
 */
public class GPathEvent extends Event {

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
	public GPathEvent(ArrayList<Node> route) {
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
