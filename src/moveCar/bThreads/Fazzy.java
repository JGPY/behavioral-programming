package moveCar.bThreads;

import bpSourceCode.bp.BThread;
import bpSourceCode.bp.exceptions.BPJRequestableSetException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static bpSourceCode.bp.eventSets.EventSetConstants.none;
import static bpSourceCode.bp.BProgram.bp;
import static moveCar.events.StaticEvents.EventFazzy;

public class Fazzy extends BThread {

    private static final Logger logger = LoggerFactory.getLogger(Data.class);
    @Override
    public void runBThread() throws InterruptedException, BPJRequestableSetException {

        while (true){
            logger.info("模糊处理同步");
            bp.bSync(EventFazzy, none, none);
            logger.info("模糊处理结束");
        }
    }
}
