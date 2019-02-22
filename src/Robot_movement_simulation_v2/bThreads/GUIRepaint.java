package Robot_movement_simulation_v2.bThreads;


import bpSourceCode.bp.BThread;
import bpSourceCode.bp.exceptions.BPJRequestableSetException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static bpSourceCode.bp.BProgram.bp;
import static Robot_movement_simulation.bThreads.Constants.gui;
import static bpSourceCode.bp.eventSets.EventSetConstants.all;
import static bpSourceCode.bp.eventSets.EventSetConstants.none;

public class GUIRepaint extends BThread {

    private static final Logger logger = LoggerFactory.getLogger(GUIRepaint.class);

    @Override
    public void runBThread() throws InterruptedException, BPJRequestableSetException {

        while (true) {
            logger.info("刷新UI");
            gui.repaint();
            Thread.sleep(500);
            logger.info("同步：bp.bSync(none, all, none);");
            bp.bSync(none, all, none);
            logger.info("结束：bp.bSync(none, all, none);");
        }
    }
}
