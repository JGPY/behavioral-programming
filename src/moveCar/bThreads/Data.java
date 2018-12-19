package moveCar.bThreads;

import bpSourceCode.bp.BThread;
import bpSourceCode.bp.exceptions.BPJRequestableSetException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static bpSourceCode.bp.BProgram.bp;
import static bpSourceCode.bp.eventSets.EventSetConstants.none;
import static moveCar.events.StaticEvents.EventData;

public class Data extends BThread {

    private static final Logger logger = LoggerFactory.getLogger(Data.class);

    @Override
    public void runBThread() throws InterruptedException, BPJRequestableSetException {



        while (true) {
            logger.info("处理数据同步");
            logger.info("处理数据同步:"+bp.lastEvent);
            bp.bSync(EventData, none, none);
            logger.info("处理数据结束");
        }
    }
}
