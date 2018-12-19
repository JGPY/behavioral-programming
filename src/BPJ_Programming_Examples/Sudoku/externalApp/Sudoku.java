package BPJ_Programming_Examples.Sudoku.externalApp;

// Main program for running Sudoku for playing and for model-checking.
// model-checking run configuration
// -Dsearch=BFS
// 

import java.util.Date;

import BPJ_Programming_Examples.Sudoku.bThreads.*;
import bpSourceCode.bApplication.BApplication;
import bpSourceCode.bp.BProgram;
import bpSourceCode.bp.BThread;
import static bpSourceCode.bp.BProgram.*;

/**
 * The main entry point to the Sudoku program.
 */
public class Sudoku implements BApplication {
	// GUI for interactively playing the game
	public static long pace = 0;
	public static GUI playGUI;
	public static int globalEventCounter = 0;

	// Add GUI for watching the model-checking run.
	//public static SudokuDisplay modelCheckingMonitorGUI;
	public static GUI modelCheckingMonitorGUI;

	/**
	 * Main program entry point
	 * 
	 * @param args
	 *            Command line parameters (ignored)
	 */

	static public void main(String arg[]) {
		try {

			BProgram.startBApplication(Sudoku.class, "BPJ_Programming_Examples/Sudoku");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void runBApplication() {
		double pr; // priority;
		bp.setBThreadEpsilon(0.1);
		// Start the MC GUI - there is no "playing GUI"
		//modelCheckingMonitorGUI = new SudokuDisplay(sourceCode.bp);
		modelCheckingMonitorGUI = new GUI(bp);

		System.out.printf("%tc %s", new Date(), " - \n");
		// B-Threads
		bp.add(new UpdateMCMonitorGUI(), 1.0);
		bp.add(new DetectWin(), 2.0);
		bp.add(new MakeGivenMoves(), 3.0);

		pr = 1000.0;
		for (BThread sc : EliminateEight.constructInstances())
			bp.add(sc, pr += 1.0);

		
		pr = 2000.0;//don't neccesery for current boards
		for (BThread sc : PlaceANumberInARow.constructInstances())
			bp.add(sc, pr += 1.0);

		pr = 3000.0;//don't neccesery for current boards
		for (BThread sc : PlaceANumberInAColumn.constructInstances())
			bp.add(sc, pr += 1.0);
		
		pr = 4000.0;
		for (BThread sc : PlaceANumberInABox.constructInstances())
			bp.add(sc, pr += 1.000);
		
		
		
		pr = 10000.0;
		for (BThread sc : DefaultMove.constructInstances())
			bp.add(sc, pr += 0.0001);

		bp.add(new PruneWhenNoSolution(), 20000.0);

		// Start the scenarios

		bp.startAll();
	}

	public static void suLog(String s) {
		 System.out.println(s);
	}
}
