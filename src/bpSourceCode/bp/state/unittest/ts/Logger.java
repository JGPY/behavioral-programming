
package bpSourceCode.bp.state.unittest.ts;

import static bpSourceCode.bp.eventSets.EventSetConstants.all;
import static bpSourceCode.bp.eventSets.EventSetConstants.none;
import static bpSourceCode.bp.BProgram.bp;
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
				
		}			
	}
}
