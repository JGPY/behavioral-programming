package robotNavigationSimulation.APP;

import bpSourceCode.bApplication.BApplication;
import bpSourceCode.bp.BProgram;
import robotNavigationSimulation.UI.GUI;
import robotNavigationSimulation.bThreads.GlobalPathPlanning;
import robotNavigationSimulation.bThreads.LocalPathPlanning;
import robotNavigationSimulation.bThreads.Navigation;

import javax.swing.*;
import java.awt.*;

import static bpSourceCode.bp.BProgram.bp;
import static robotNavigationSimulation.constants.Constants.gui;

public class NavigationAPP extends JApplet implements BApplication {


    /**
     * Self tuning step.
     */

    @Override
    public void init() {

        this.setSize(600,600);
        gui = new GUI();
        add(gui, BorderLayout.CENTER);

        try {

            BProgram.startBApplication(NavigationAPP.class, "robotNavigationSimulation_v1");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    @Override
    public void runBApplication() {

        try {
            Thread.sleep(100);
        } catch (InterruptedException e1) {
            e1.printStackTrace();
        }

        bp.add(new Navigation(), 1.0);

        bp.add(new GlobalPathPlanning(), 2.0);

        bp.add(new LocalPathPlanning(), 3.0);



        bp.startAll();
    }
}
