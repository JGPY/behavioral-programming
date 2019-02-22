package robotNavigationSimulation.bThreads;

import bpSourceCode.bp.BThread;
import bpSourceCode.bp.exceptions.BPJRequestableSetException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import robotNavigationSimulation.events.StaticEvents;

import static bpSourceCode.bp.BProgram.bp;
import static bpSourceCode.bp.eventSets.EventSetConstants.none;

/**
 *
 * @author L
 */
public class NavigationCtrl extends BThread {

    private static final Logger logger = LoggerFactory.getLogger(NavigationCtrl.class);

    @Override
    public void runBThread() throws InterruptedException, BPJRequestableSetException {
        while (true) {
            bp.bSync(none, StaticEvents.LPathEvent, StaticEvents.GPathEvent);
            bp.bSync(none, StaticEvents.GPathEvent, StaticEvents.LPathEvent);
        }
    }
}
