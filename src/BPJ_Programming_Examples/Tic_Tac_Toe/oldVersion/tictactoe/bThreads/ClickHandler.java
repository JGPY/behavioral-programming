package BPJ_Programming_Examples.Tic_Tac_Toe.oldVersion.tictactoe.bThreads;

import static bpSourceCode.bp.eventSets.EventSetConstants.none;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import BPJ_Programming_Examples.Tic_Tac_Toe.oldVersion.tictactoe.events.Click;
import bpSourceCode.bp.BProgram;
import bpSourceCode.bp.BThread;

/**
 * A scenario that handles click events
 */
public class ClickHandler extends BThread {

	private static final Logger logger = LoggerFactory.getLogger(ClickHandler.class);

	private int row,col;

	public ClickHandler(int row,int col) {
		this.row = row;
		this.col = col;
	}

	@Override
	public void runBThread() {
		Click p;
		BProgram bp1 = getBProgram();  
		try {
			System.out.println("我的：ClickHandler.runBThread（）");
			logger.info("同步：bSync(p = new rawData(row, col) , none ,none )");
			bp1.bSync(p = new Click(row, col) , none ,none );
			logger.info("结束：bSync(p = new rawData(row, col) , none ,none )");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public String toString() {
		return "ClickHandler(" + row + "," + col + ")";
	}
}