package bpSourceCode.bp.state;

import bpSourceCode.bp.BThread;

public class Context {

	public BThread bt;
	public boolean continueRunning;
	public BTState bts;

	public void set(BThread bt, boolean continueRunning, BTState bts) {
		this.bt = bt;
		this.continueRunning = continueRunning;
		this.bts = bts;
	}
}
