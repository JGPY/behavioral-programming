package robotNavigationSimulation.bThreads;

import robotNavigationSimulation.events.GPathEvent;
import robotNavigationSimulation.events.Node;
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
    public void runBThread() throws InterruptedException, BPJRequestableSetException {
        //Capture event
        logger.info("Capturing event:" + "GPathEvent.class");
        bp.bSync(none,new EventsOfClass(GPathEvent.class),none);
        GPathEvent bpEvent = (GPathEvent) bp.lastEvent;
        logger.info("Captured event:" + bpEvent.toString());

        ArrayList<Node> GlobalRoute = bpEvent.getRoute();

        System.out.println("GlobalRoute.size():"+GlobalRoute.size());

        //根据GlobalRoute调整方向姿态

                //通过传感器获得环境数据
                //fuzzy logic
                    //数据模糊化进行规则匹配
                    //如果规则不是TF和TF，
                        //就解模糊化
                        //行走位置
                        //如果位置与GlobalRoute偏差
                    //否则
                        //根据路线行走
                //


        //AStar计算全局路线图
        //bpSyc(线路数据,,,)
    }
}
