
package bpSourceCode.bp.state.unittest.ph;

import static bpSourceCode.bp.BProgram.bp;
import static bpSourceCode.bp.BProgram.markNextVerificationStateAsBad;
import static bpSourceCode.bp.eventSets.EventSetConstants.all;
import static bpSourceCode.bp.eventSets.EventSetConstants.none;
import bpSourceCode.bp.BThread;
import bpSourceCode.bp.exceptions.BPJException;

public class Logger extends BThread{

	int count = 0;
	int bound;
	

	@Override
	public void runBThread() throws BPJException {

		while (true){
			bp.bSync(none, all, none);
			System.out.println("LOG:" + bp.lastEvent);
				if (++count == bound) {
					markNextVerificationStateAsBad("Event count bound Exceeded = " + bound);
				}
		}			
	}
}
