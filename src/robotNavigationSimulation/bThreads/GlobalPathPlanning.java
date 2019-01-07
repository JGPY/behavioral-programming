package robotNavigationSimulation.bThreads;

import robotNavigationSimulation.constants.Constants;
import robotNavigationSimulation.constants.AStarMap;
import robotNavigationSimulation.events.AStarNode;
import robotNavigationSimulation.events.GPathEvent;
import robotNavigationSimulation.events.Node;
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

    @Override
    public void runBThread() throws InterruptedException, BPJRequestableSetException {

        //获得初始位置 startNode，目标位置 endNode
        Node startNode = Constants.robotInitialPosition;
        Node endNode = Constants.robotEndPosition;

        //获得栅格化后的地图 AStarMap
        AStarMap AStarMap = new AStarMap();

        //配置起点点和终点
        AStarMap.setStartNode(new AStarNode(startNode.getX()/20, startNode.getY()/20, "o", true));
        AStarMap.setEndNode(new AStarNode(endNode.getX()/20,endNode.getY()/20,"o", true));

        //计算全局路线图
        ArrayList<AStarNode> globalRoute =  new AStar().search(AStarMap);
        ArrayList<Node> globalRoutep = new ArrayList<Node>();

        //线路转换
        for ( AStarNode aStarNode : globalRoute) {
            globalRoutep.add(new Node(aStarNode.getX(), aStarNode.getY()));
        }

        bp.bSync(new GPathEvent(globalRoutep), none, none);

    }
}
