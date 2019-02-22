package robotNavigationSimulation_v1.bThreads;

import robotNavigationSimulation_v1.constants.AStarMap;
import robotNavigationSimulation_v1.constants.Constants;
import robotNavigationSimulation_v1.events.*;
import bpSourceCode.bp.BThread;
import bpSourceCode.bp.eventSets.EventsOfClass;
import bpSourceCode.bp.exceptions.BPJRequestableSetException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;

import static bpSourceCode.bp.BProgram.bp;
import static bpSourceCode.bp.eventSets.EventSetConstants.none;

/**
 *
 * @author L
 */
public class LocalPathPlanning extends BThread {

    private static final Logger logger = LoggerFactory.getLogger(LocalPathPlanning.class);

    @Override
    public synchronized void runBThread() throws InterruptedException, BPJRequestableSetException {
        logger.info("Entry behavior thread:" + "LocalPathPlanning.class");

        //Capture event
        logger.info("Capturing event:" + "GRoute.class");
        bp.bSync(none,new EventsOfClass(GPathEvent.class),none);
        GPathEvent bpEvent = (GPathEvent) bp.lastEvent;
        logger.info("Captured event:" + bpEvent.toString());

        ArrayList<Node> GlobalRoute = bpEvent.getRoute();
        for ( Node node: GlobalRoute) {

            Node robotNextPosition  = node;
            Constants.movingTrail.add(Constants.robotCurrentPosition);

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
            //根据GlobalRoute调整方向姿态
        }

        logger.info("requesting event:" + "MovingTrail.class");
        bp.bSync(new LPathEvent(Constants.movingTrail), none, none);
        logger.info("requested event:" + "MovingTrail.class");

                //fuzzy logic
                    //数据模糊化进行规则匹配
                    //如果规则不是TF和TF，
                        //就解模糊化
                        //行走位置
                        //如果位置与GlobalRoute偏差
                    //否则
                        //根据路线行走
                //
//        bp.bSync(none, StaticEvents.UIRefresh, all);
    }
}
