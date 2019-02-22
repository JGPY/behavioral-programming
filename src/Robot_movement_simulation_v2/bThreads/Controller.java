package Robot_movement_simulation_v2.bThreads;

import bpSourceCode.bp.BThread;
import bpSourceCode.bp.exceptions.BPJRequestableSetException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static Robot_movement_simulation.events.StaticEvents.EventData;
import static Robot_movement_simulation.events.StaticEvents.EventFuzzy;
import static bpSourceCode.bp.eventSets.EventSetConstants.none;
import static bpSourceCode.bp.BProgram.bp;

public class Controller extends BThread {

    private static final Logger logger = LoggerFactory.getLogger(Controller.class);

    @Override
    public void runBThread() throws InterruptedException, BPJRequestableSetException {


        while (true) {
            logger.info("管理同步");
            bp.bSync(none,EventData, EventFuzzy);
            bp.bSync(none,EventFuzzy, EventData);
            logger.info("管理结束");
        }
    }
}
