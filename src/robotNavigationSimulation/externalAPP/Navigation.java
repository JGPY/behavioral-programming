package robotNavigationSimulation.externalAPP;

import robotNavigationSimulation.bThreads.GlobalPathPlanning;
import robotNavigationSimulation.bThreads.LocalPathPlanning;
import bpSourceCode.bApplication.BApplication;
import bpSourceCode.bp.BProgram;

import static bpSourceCode.bp.BProgram.bp;

public class Navigation implements BApplication {

    /**
     * Main program entry point
     *
     * @param args
     *            Command line parameters (ignored)
     */

    static public void main(String arg[]) {
        try {

            BProgram.startBApplication(Navigation.class, "robotNavigationSimulation");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void runBApplication() {


        bp.add(new GlobalPathPlanning(), 1.0);

        bp.add(new LocalPathPlanning(), 2.0);

        bp.startAll();
    }
}
