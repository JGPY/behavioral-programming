package robotNavigationSimulation.bThreads;

import bpSourceCode.bp.BThread;
import bpSourceCode.bp.eventSets.EventsOfClass;
import bpSourceCode.bp.exceptions.BPJRequestableSetException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import robotNavigationSimulation.aStar.AStarMap;
import robotNavigationSimulation.aStar.AStarNode;
import robotNavigationSimulation.constants.Constants;
import robotNavigationSimulation.events.*;
import robotNavigationSimulation.map.Node;

import java.util.ArrayList;

import static bpSourceCode.bp.BProgram.bp;
import static bpSourceCode.bp.eventSets.EventSetConstants.none;
import static java.lang.Thread.sleep;
import static robotNavigationSimulation.constants.Constants.gui;

/**
 *
 * @author L
 */
public class LocalPathPlanning extends BThread {

    private static final Logger logger = LoggerFactory.getLogger(LocalPathPlanning.class);

    @Override
    public synchronized void runBThread() throws InterruptedException, BPJRequestableSetException {
        logger.info("Entry behavior thread:" + "LocalPathPlanning.class");

        while (true) {

            bp.bSync(StaticEvents.LPathEvent, none, none);

            //Capture event
            logger.info("Capturing event:" + "GPathRoute.class");
            bp.bSync(none,new EventsOfClass(GPathRoute.class),none);
            logger.info("Captured event:" + "GPathRoute.class");

            GPathRoute bpEvent = (GPathRoute) bp.lastEvent;

            ArrayList<Node> GlobalRoute = bpEvent.getRoute();

            for ( Node node: GlobalRoute) {
                sleep(100);

                Node robotNextPosition  = node;
                Constants.movingTrail.add(Constants.robotCurrentPosition);

                new Thread(new Runnable(){
                    @Override
                    public void run() {
                        gui.repaint();
                    }
                }).start();


                //模拟 通过传感器获得环境数据
                AStarNode[][] map= new AStarMap().getMap();
                if (!map[node.getX()][node.getY()].isReachable()) {
                    Constants.robotCurrentPosition = robotNextPosition;
                } else {

                    Node node1 = robotNextPosition;
                    robotNextPosition.setX(node1.getX()+1);
                    robotNextPosition.setY(node1.getY()+1);

                    Constants.robotCurrentPosition = robotNextPosition;
                }
            }



            //发布轨迹
            logger.info("requesting event:" + "MovingTrail.class");
            bp.bSync(new MovingTrail(Constants.movingTrail), none, none);
            logger.info("requested event:" + "MovingTrail.class");


        }
    }
}
