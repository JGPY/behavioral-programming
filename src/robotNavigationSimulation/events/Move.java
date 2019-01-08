package robotNavigationSimulation.events;

import bpSourceCode.bp.Event;
import robotNavigationSimulation.map.Node;

public class Move extends Event {

    public Node node;

    public Move(Node node) {
        super();
        this.node = node;
    }
}
