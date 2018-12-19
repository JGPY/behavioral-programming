package BPJ_Programming_Examples.Tic_Tac_Toe.tictactoe.bThreads.tactics;

import java.util.ArrayList;
import java.util.List;

import BPJ_Programming_Examples.Tic_Tac_Toe.tictactoe.events.O;
import BPJ_Programming_Examples.Tic_Tac_Toe.tictactoe.events.X;
import BPJ_Programming_Examples.Tic_Tac_Toe.tictactoe.externalApp.Square;
import bp.BThread;

/**
 * A scenario that tries to intercept a corner-edge fork of X by placing an O in
 * the junction corner (the base of the fork).
 */
@SuppressWarnings("serial")
public class PreventXFork extends PreventThirdX {

	/**
	 * @param firstSquare
	 * @param seconfSquare
	 * @param triggeredEvent
	 */
	public PreventXFork(X firstSquare, X secondSquare,
			O triggeredEvent) {
		super(firstSquare, secondSquare, triggeredEvent);
		this.setName("PreventXFork(" + firstSquare + ","
				+ secondSquare + "," + triggeredEvent + ")");
	}

	/**
	 * Construct all instances
	 */
	static public List<BThread> constructInstances() {

//		Set<BThread> set = new HashSet<BThread>();
		List<BThread> list = new ArrayList<BThread>();

		Square[] corners = { new Square(0,0), new Square(0,2),
				new Square(2,0), new Square(2,2)};

		for (int i=0; i<4; i++) {
			O junction = new O(corners[i].row,corners[i].col);
			
			X corner = new X(junction.row,2-junction.col);
			X edge = new X(Math.abs(junction.row-1),junction.col);
			list.add(new PreventXFork(corner,edge,junction));
			list.add(new PreventXFork(edge,corner,junction));

			corner = new X(2-junction.row,junction.col);
			edge = new X(junction.row,Math.abs(junction.col-1));
			list.add(new PreventXFork(corner,edge,junction));
			list.add(new PreventXFork(edge,corner,junction));
		}
		
		return list;
	}

}
