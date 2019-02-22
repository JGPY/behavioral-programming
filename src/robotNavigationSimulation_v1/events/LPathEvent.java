package robotNavigationSimulation_v1.events;

import bpSourceCode.bp.Event;

import java.util.ArrayList;

public class LPathEvent extends Event {

  /** Global route */
  private ArrayList<Node> movingTrail;

  /**
   * Constructor.
   *
   * @param movingTrail
   *                robot moving trail
   */
  public LPathEvent(ArrayList<Node> movingTrail) {
    super();
    this.movingTrail = movingTrail;
    this.setName("GRoute(" + movingTrail + ")");
  }

  public ArrayList<Node> getRoute() {
    return movingTrail;
  }

  /** @see Object#toString() */

}
