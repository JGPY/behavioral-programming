package robotNavigationSimulation_v1.bThreads;

import bpSourceCode.bp.BThread;
import bpSourceCode.bp.exceptions.BPJRequestableSetException;
import robotNavigationSimulation_v1.events.StaticEvents;
import robotNavigationSimulation_v1.externalAPP.GUI;

import static bpSourceCode.bp.BProgram.bp;
import static bpSourceCode.bp.eventSets.EventSetConstants.none;
import static robotNavigationSimulation_v1.constants.Constants.gui;

public class UI extends BThread {

    @Override
    public void runBThread() throws InterruptedException, BPJRequestableSetException {
        gui = new GUI();
        System.out.println("gui");
        bp.bSync(none, StaticEvents.UIRefresh, none);
        System.out.println("gui");
    }
}
