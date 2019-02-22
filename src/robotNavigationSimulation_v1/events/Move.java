package robotNavigationSimulation_v1.events;

import bpSourceCode.bp.Event;

public class Move extends Event {

    public Node node;

    public Move(Node node) {
        super();
        this.node = node;
    }
}
