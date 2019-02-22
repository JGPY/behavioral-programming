package Robot_movement_simulation_v2;

import Robot_movement_simulation.bThreads.*;
import bpSourceCode.bApplication.BApplication;
import bpSourceCode.bp.BProgram;

import javax.swing.*;
import java.awt.*;

import static Robot_movement_simulation.bThreads.Constants.gui;
import static bpSourceCode.bp.BProgram.bp;

public class Main extends JApplet implements BApplication {



	/**
	 * Self tuning step.
	 */

	@Override
	public void init() {
		
		this.setSize(600,600);
		gui = new GUI();
		add(gui, BorderLayout.CENTER);

		bp.add(new Data(),1.0);
		bp.add(new AStar(),2.0);
		bp.add(new FuzzyRule(),3.0);
		bp.add(new Controller(),4.0);
		bp.add(new GUIRepaint(),5.0);

		try {

			BProgram.startBApplication(Main.class, "Flock");

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
		bp.startAll();
	}

}
