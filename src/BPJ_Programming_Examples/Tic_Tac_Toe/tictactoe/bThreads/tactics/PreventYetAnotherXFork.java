package BPJ_Programming_Examples.Tic_Tac_Toe.tictactoe.bThreads.tactics;

import java.util.ArrayList;
import java.util.List;

import BPJ_Programming_Examples.Tic_Tac_Toe.tictactoe.events.O;
import BPJ_Programming_Examples.Tic_Tac_Toe.tictactoe.events.X;
import bp.BThread;

/**
 * A scenario that tries to intercept a edge-edge fork of X by placing an O in
 * the junction corner (the base of the fork).
 */
@SuppressWarnings("serial")
public class PreventYetAnotherXFork extends PreventThirdX {

	/**
	 * @param firstSquare
	 * @param seconfSquare
	 * @param triggeredEvent
	 */
	public PreventYetAnotherXFork(X firstSquare, X secondSquare,
			O triggeredEvent) {
		super(firstSquare, secondSquare, triggeredEvent);
		this.setName("PreventYetAnotherXFork(" + firstSquare + ","
				+ secondSquare + "," + triggeredEvent + ")");
	}

	/**
	 * Construct all instances
	 */
	static public List<BThread> constructInstances() {

//		Set<BThread> set = new HashSet<BThread>();
		List<BThread> list = new ArrayList<BThread>();

		X[] edgeXs = {new X(1,0), new X(0,1), new X(1,2), new X(2,1)}; 
		O[] cornerOs = {new O(0,0), new O(0,2), new O(2,2), new O(2,0)};

		for (int i=0; i<4; i++) {			
			list.add(new PreventYetAnotherXFork(edgeXs[i],edgeXs[(i+1)%4],cornerOs[i]));
			list.add(new PreventYetAnotherXFork(edgeXs[(i+1)%4],edgeXs[i],cornerOs[i]));
		}
		
		return list;
	}

}
