package bpSourceCode.bp;

import bpSourceCode.bp.exceptions.BPJException;
import bpSourceCode.bp.exceptions.BPJJavaThreadStartException;

public class BThreadForJavaThread extends BThread {
	public BThreadForJavaThread(Thread th) {
		setThread(th);
	}

	public void runBThread() throws BPJException {
		throw new BPJJavaThreadStartException();
	}
}
