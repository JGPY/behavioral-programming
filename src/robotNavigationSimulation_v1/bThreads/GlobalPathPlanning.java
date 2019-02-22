package robotNavigationSimulation_v1.bThreads;

import bpSourceCode.bp.eventSets.EventsOfClass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import robotNavigationSimulation_v1.events.*;
import bpSourceCode.bp.BThread;
import bpSourceCode.bp.exceptions.BPJRequestableSetException;

import java.util.ArrayList;

import static bpSourceCode.bp.BProgram.bp;
import static bpSourceCode.bp.eventSets.EventSetConstants.none;

/**
 *
 * @author L
 */
public class GlobalPathPlanning extends BThread {

    private static final Logger logger = LoggerFactory.getLogger(GlobalPathPlanning.class);

    @Override
    public void runBThread() throws InterruptedException, BPJRequestableSetException {
        logger.info("Entry behavior thread:" + "GlobalPathPlanning.class");

        //Capture event
        logger.info("Capturing event:" + "AStarRoute.class");
        bp.bSync(none,new EventsOfClass(AStarRoute.class),none);
        AStarRoute aStarRoute = (AStarRoute) bp.lastEvent;
        logger.info("Captured event:" + aStarRoute.toString());

        //线路转换
        ArrayList<Node> globalRoute = new ArrayList();
        for ( AStarNode data : aStarRoute.getRoute()) {
            globalRoute.add(new Node(data.getX(), data.getY()));
        }

        logger.info("requesting event:" + "GRoute.class");
        bp.bSync(new GPathEvent(globalRoute), none, none);
        logger.info("requested event:" + "GRoute.class");

    }
}
