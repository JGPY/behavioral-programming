package robotNavigationSimulation_v1.externalAPP;

import robotNavigationSimulation_v1.bThreads.*;
import bpSourceCode.bApplication.BApplication;
import bpSourceCode.bp.BProgram;

import javax.swing.*;

import java.awt.*;

import static bpSourceCode.bp.BProgram.bp;
import static robotNavigationSimulation_v1.constants.Constants.gui;

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

        bp.add(new AStar(), 1.0);
        bp.add(new GlobalPathPlanning(), 1.1);

        bp.add(new LocalPathPlanning(), 2.0);

        bp.add(new GUIRefresh(), 3.1);

        bp.startAll();
    }
}
