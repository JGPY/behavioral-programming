package bpSourceCode.bp.state.unittest.ph;

import bpSourceCode.bp.BProgram;

// moveCar.Main class
public class DiningPhilsMain {
	// ----------------------------------------
	static public void main(String arg[]) {
		try {
			BProgram.startBApplication(DiningPhilsBApp.class, "sourceCode.bp.state.unittest.ph");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
