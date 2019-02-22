package robotNavigationSimulation_v1.events;

import bpSourceCode.bp.Event;

import java.util.ArrayList;

public class AStarRoute extends Event {

    /**
     * Global route
     */
    private ArrayList<AStarNode> route;

    /**
     * Constructor.
     *
     * @param route
     *            Route after global path planning
     */
    public AStarRoute(ArrayList<AStarNode> route) {
        super();
        this.route = route;
        this.setName("AStarRoute(" + route + ")");
    }

    public ArrayList<AStarNode> getRoute() {
        return route;
    }

    /**
     * @see Object#toString()
     */
}
