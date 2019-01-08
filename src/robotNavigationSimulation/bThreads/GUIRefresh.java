package robotNavigationSimulation.bThreads;


import bpSourceCode.bp.BThread;
import bpSourceCode.bp.eventSets.EventsOfClass;
import bpSourceCode.bp.exceptions.BPJRequestableSetException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import robotNavigationSimulation.events.MovingTrail;

import static bpSourceCode.bp.BProgram.bp;
import static bpSourceCode.bp.eventSets.EventSetConstants.none;
import static robotNavigationSimulation_v1.constants.Constants.gui;

public class GUIRefresh extends BThread {

    private static final Logger logger = LoggerFactory.getLogger(GUIRefresh.class);

    @Override
    public void runBThread() throws InterruptedException, BPJRequestableSetException {
        logger.info("Entry behavior thread:" + "GUIRefresh.class");

        while (true) {
            try{
                gui.repaint();
                logger.info("Capturing event:" + "MovingTrail.class");
                bp.bSync(none,new EventsOfClass(MovingTrail.class),none);
                MovingTrail bpEvent = (MovingTrail) bp.lastEvent;
                logger.info("Captured event:" + bpEvent.toString());
            } catch ( Exception e){
                logger.info("Exception:" + e);
            }
   

        }
    }
}
