package robotNavigationSimulation_v1.bThreads;


import bpSourceCode.bp.BThread;
import bpSourceCode.bp.eventSets.EventsOfClass;
import bpSourceCode.bp.exceptions.BPJRequestableSetException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import robotNavigationSimulation_v1.events.LPathEvent;



import static bpSourceCode.bp.BProgram.bp;
import static bpSourceCode.bp.eventSets.EventSetConstants.none;
import static robotNavigationSimulation_v1.constants.Constants.gui;

public class GUIRefresh extends BThread {

    private static final Logger logger = LoggerFactory.getLogger(GUIRefresh.class);

    @Override
    public void runBThread() throws InterruptedException, BPJRequestableSetException {
        logger.info("Entry behavior thread:" + "GUIRefresh.class");

        while (true) {
            gui.repaint();
            logger.info("Capturing event:" + "MovingTrail.class");
            bp.bSync(none,new EventsOfClass(LPathEvent.class),none);
            LPathEvent bpEvent = (LPathEvent) bp.lastEvent;
            logger.info("Captured event:" + bpEvent.toString());
        }
    }
}
