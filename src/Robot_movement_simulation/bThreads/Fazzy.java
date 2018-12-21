package Robot_movement_simulation.bThreads;

import bpSourceCode.bp.BThread;
import bpSourceCode.bp.exceptions.BPJRequestableSetException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static Robot_movement_simulation.events.StaticEvents.EventFazzy;
import static bpSourceCode.bp.eventSets.EventSetConstants.none;
import static bpSourceCode.bp.BProgram.bp;

public class Fazzy extends BThread {

    private static final Logger logger = LoggerFactory.getLogger(Data.class);
    @Override
    public void runBThread() throws InterruptedException, BPJRequestableSetException {

        while (true){
            logger.info("模糊处理");

            //读取传感器的数据


            // 模糊逻辑处理

            // 给出目标值

            Integer speed = 0;
            Integer angle = 0;
            logger.info("同步：bp.bSync(EventFazzy, none, none)");
            bp.bSync(EventFazzy, none, none);
            logger.info("结束：bp.bSync(EventFazzy, none, none)");
        }
    }
}
