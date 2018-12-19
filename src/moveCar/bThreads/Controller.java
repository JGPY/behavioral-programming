package moveCar.bThreads;

import bpSourceCode.bp.BProgram;
import bpSourceCode.bp.BThread;
import bpSourceCode.bp.exceptions.BPJRequestableSetException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static bpSourceCode.bp.eventSets.EventSetConstants.all;
import static bpSourceCode.bp.eventSets.EventSetConstants.none;

public class Controller extends BThread {

    private static final Logger logger = LoggerFactory.getLogger(Controller.class);

    @Override
    public void runBThread() throws InterruptedException, BPJRequestableSetException {

        BProgram bp = getBProgram();

        while (true) {
            logger.info("管理同步");
            bp.bSync(none, all, none);
            logger.info("管理结束");
        }
    }
}
