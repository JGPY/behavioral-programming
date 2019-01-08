package robotNavigationSimulation.bThreads;

import bpSourceCode.bp.BThread;
import bpSourceCode.bp.exceptions.BPJRequestableSetException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import robotNavigationSimulation.aStar.AStarMap;
import robotNavigationSimulation.constants.Constants;
import robotNavigationSimulation.aStar.AStarNode;
import robotNavigationSimulation.events.GPathRoute;
import robotNavigationSimulation.map.Node;
import robotNavigationSimulation.events.StaticEvents;
import robotNavigationSimulation.aStar.AStar;

import java.util.ArrayList;

import static bpSourceCode.bp.BProgram.bp;
import static bpSourceCode.bp.eventSets.EventSetConstants.none;
import static java.lang.Thread.sleep;

/**
 *
 * @author L
 */
public class GlobalPathPlanning extends BThread {

    private static final Logger logger = LoggerFactory.getLogger(GlobalPathPlanning.class);

    @Override
    public void runBThread() throws InterruptedException, BPJRequestableSetException {
        logger.info("Entry behavior thread:" + "GlobalPathPlanning.class");

        ArrayList<Node> globalRoute = new ArrayList();
        while (true) {

            sleep(200);
            logger.info("requesting event:" + "GPathEvent.class");
            bp.bSync(StaticEvents.GPathEvent, none, none);
            logger.info("requested event:" + "GPathEvent.class");

            if(globalRoute.isEmpty()) {
                //获得初始位置 startNode，目标位置 endNode
                Node startNode = Constants.robotStartPosition;
                Node endNode = Constants.robotEndPosition;

                //获得栅格化后的地图 AStarMap
                AStarMap AStarMap = new AStarMap();

                //配置起点点和终点
                AStarMap.setStartNode(new AStarNode(startNode.getX()/20, startNode.getY()/20, "o", true));
                AStarMap.setEndNode(new AStarNode(endNode.getX()/20,endNode.getY()/20,"o", true));

                //计算全局路线图
                ArrayList<AStarNode> globalRouteAStar =  new AStar().search(AStarMap);

                //线路转换
                for ( AStarNode data : globalRouteAStar) {
                    globalRoute.add(new Node(data.getX(), data.getY()));
                }


                //发布全局路线
                logger.info("requesting event:" + "GPathRoute.class");
                bp.bSync(new GPathRoute(globalRoute), none, none);
                logger.info("requested event:" + "GPathRoute.class");

            }

        }
    }
}
