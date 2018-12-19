package BPJ_Programming_Examples.Tic_Tac_Toe.oldVersion.tictactoe.externalApp;

import BPJ_Programming_Examples.Tic_Tac_Toe.oldVersion.tictactoe.bThreads.AddThirdO;
import BPJ_Programming_Examples.Tic_Tac_Toe.oldVersion.tictactoe.bThreads.DeclareWinner;
import BPJ_Programming_Examples.Tic_Tac_Toe.oldVersion.tictactoe.bThreads.DefaultMoves;
import BPJ_Programming_Examples.Tic_Tac_Toe.oldVersion.tictactoe.bThreads.DetectDraw;
import BPJ_Programming_Examples.Tic_Tac_Toe.oldVersion.tictactoe.bThreads.DetectOWin;
import BPJ_Programming_Examples.Tic_Tac_Toe.oldVersion.tictactoe.bThreads.DetectXWin;
import BPJ_Programming_Examples.Tic_Tac_Toe.oldVersion.tictactoe.bThreads.SquareTaken;
import BPJ_Programming_Examples.Tic_Tac_Toe.oldVersion.tictactoe.bThreads.EnforceTurns;
import BPJ_Programming_Examples.Tic_Tac_Toe.oldVersion.tictactoe.bThreads.InterceptCornerCornerFork;
import BPJ_Programming_Examples.Tic_Tac_Toe.oldVersion.tictactoe.bThreads.InterceptCornerEdgeFork;
import BPJ_Programming_Examples.Tic_Tac_Toe.oldVersion.tictactoe.bThreads.UpdateDisplay;
import BPJ_Programming_Examples.Tic_Tac_Toe.oldVersion.tictactoe.bThreads.PreventThirdX;
import BPJ_Programming_Examples.Tic_Tac_Toe.oldVersion.tictactoe.bThreads.UserMove;

import bpSourceCode.bp.BProgram;
import bpSourceCode.bp.BThread;

/**
 * The main entry point to the TicTacToe_Main program.
 */
public class TicTacToe_Main {
	public static GUI gui;

	/**
	 * moveCar.Main program entry point
	 *
	 * @param args
	 *            Command line parameters (ignored)
	 */
	public static void main(String[] args) {

		BProgram bp = new BProgram(); //初始化行为线程
		// Register scenarios

		bp.add(new UserMove(), 0.5); //添加用户移动
		bp.add(new UpdateDisplay(), 0.6); //添加显示更新

		double pr = 1.0;
		for (BThread sc : DetectXWin.constructInstances()) {
			bp.add(sc, pr += 0.001);
		}
		
		pr = 2.0; 
		for (BThread sc : DetectOWin.constructInstances()) {
			bp.add(sc, pr += 0.001);
		}
		
		bp.add(new DeclareWinner(), 2.5);
		bp.add(new EnforceTurns(), 3.0);
		
		pr = 4.0;  
		for (BThread sc : SquareTaken.constructInstances()) {
			bp.add(sc, pr += 0.001);
		}


		bp.add(new DetectDraw(), 5.0);

		pr = 6.0;
		for (BThread sc : AddThirdO.constructInstances()) {
			bp.add(sc, pr += 0.001);
		}

		pr = 7.0;
		for (BThread sc : PreventThirdX.constructInstances()) {
			bp.add(sc, pr += 0.001);
		}

		bp.add(new InterceptCornerCornerFork(), 8.0);
		bp.add(new InterceptCornerEdgeFork(), 9.0);

		bp.add(new DefaultMoves(), 10.0);

		// Start the graphical user interface
		gui = new GUI(bp);

		// Start the scenarios

		bp.startAll();
}

}
