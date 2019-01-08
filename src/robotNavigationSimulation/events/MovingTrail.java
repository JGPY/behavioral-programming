package robotNavigationSimulation.events;

import bpSourceCode.bp.Event;
import robotNavigationSimulation.map.Node;

import java.util.ArrayList;

public class MovingTrail extends Event {

  /** Global route */
  private ArrayList<Node> movingTrail;

  /**
   * Constructor.
   *
   * @param movingTrail
   *                robot moving trail
   */
  public MovingTrail(ArrayList<Node> movingTrail) {
    super();
    this.movingTrail = movingTrail;
    this.setName("GPathRoute(" + movingTrail + ")");
  }

  public ArrayList<Node> getRoute() {
    return movingTrail;
  }

  /** @see Object#toString() */

}
