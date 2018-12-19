package moveCar.bThreads;

import bpSourceCode.bp.BProgram;
import bpSourceCode.bp.BThread;
import bpSourceCode.bp.exceptions.BPJRequestableSetException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static bpSourceCode.bp.eventSets.EventSetConstants.none;
import static moveCar.events.StaticEvents.EventData;

public class Advance extends BThread {

    private static final Logger logger = LoggerFactory.getLogger(Advance.class);

    @Override
    public void runBThread() throws InterruptedException, BPJRequestableSetException {

        BProgram bp = getBProgram();

        while (true) {
            logger.info("前进同步");
            bp.bSync(EventData, none, none);
            logger.info("前进结束");
        }
    }
}
