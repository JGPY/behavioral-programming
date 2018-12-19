package moveCar;

import bpSourceCode.bp.BThread;
import static bpSourceCode.bp.BProgram.bp;
import moveCar.bThreads.Data;
import moveCar.bThreads.Fazzy;

import static bpSourceCode.bp.eventSets.EventSetConstants.none;
import static moveCar.events.StaticEvents.*;

public class Main {


    @SuppressWarnings("serial")
    static class Interleave extends BThread {
        @Override
        public void runBThread() throws InterruptedException {
            while (true) {
                System.out.println("1!");
//                sourceCode.bp.bSync(none, addHot, addCold);
//                sourceCode.bp.bSync(none, addCold, addHot);
                bp.bSync(none, EventData, EventFazzy);
                bp.bSync(none, EventFazzy, EventData);
                System.out.println("2!");
//                sourceCode.bp.bSync(none, all, none);
//                System.out.println("3!");
            }
        }
    }

    public static void main(String[] args) {

        System.out.println("Hello World!");

        // Register scenarios
        bp.add(new Data(),1.0);
        bp.add(new Fazzy(),2.0);

//        sourceCode.bp.add(new RequestFiveAddColdEvents(), 1.1);
//
//        sourceCode.bp.add(new RequestFiveAddHotEvents(), 2.1);

        bp.add(new Interleave(),3.0);

        bp.startAll();
    }
}
