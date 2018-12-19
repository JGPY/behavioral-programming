package BPJ_Programming_Examples.Tic_Tac_Toe.tictactoe.bThreads.gameRules;

import static bp.BProgram.bp;
import static bp.BProgram.labelNextVerificationState;
import static bp.BProgramControls.globalRunMode;
import static bp.eventSets.EventSetConstants.all;
import static bp.eventSets.EventSetConstants.none;
import static BPJ_Programming_Examples.Tic_Tac_Toe.tictactoe.events.StaticEvents.draw;
import BPJ_Programming_Examples.Tic_Tac_Toe.tictactoe.events.Move;
import bp.BThread;
import bp.RunMode;
import bp.eventSets.EventsOfClass;
import bp.exceptions.BPJException;


@SuppressWarnings("serial")
public class DetectDraw extends BThread {
	@Override
	public void runBThread() throws BPJException {
//		interruptingEvents = new EventSet(gameOver);


		// Wait for 9 events
		for (int count = 0; count < 9; count++) {
			labelNextVerificationState(count);
			bp.bSync(none, new EventsOfClass(Move.class), none);
//			try {
//				Thread.sleep(500);
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
		}
		labelNextVerificationState("9");
		if (globalRunMode == RunMode.MCSAFETY || globalRunMode == RunMode.MCLIVENESS) {
			bp.pruneAtNextVerificationState("Draw");
		}
		bp.bSync(draw, none, none);
		
//		bp.bSync(none, none, none);
		bp.bSync(none, none, all);

	}

	@Override
	public String toString() {
		return "DetectDraw";
	}
}
