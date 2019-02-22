package robotNavigationSimulation_v1.bThreads;

import bpSourceCode.bp.BThread;
import bpSourceCode.bp.eventSets.EventsOfClass;
import bpSourceCode.bp.exceptions.BPJRequestableSetException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import robotNavigationSimulation_v1.events.Move;

import static bpSourceCode.bp.BProgram.bp;
import static bpSourceCode.bp.eventSets.EventSetConstants.none;

public class RobotMove extends BThread {

    private static final Logger logger = LoggerFactory.getLogger(RobotMove.class);

    @Override
    public void runBThread() throws InterruptedException, BPJRequestableSetException {
        logger.info("Entry behavior thread:" + "RobotMove.class");

        while (true){
            //Capture event
            logger.info("Capturing event:" + "Move.class");
            bp.bSync(none,new EventsOfClass(Move.class),none);
            Move bpEvent = (Move) bp.lastEvent;
            logger.info("Captured event:" + bpEvent.toString());

            //更新UI移动
        }

    }
}
