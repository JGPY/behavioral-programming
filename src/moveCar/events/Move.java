package moveCar.events;

import bpSourceCode.bp.Event;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * A base class for moves (X or O) in the tic-tac-toe game.
 */
public class Move extends Event {
	public int row;
	public int col;

	public Move(int row, int col) {
		super();
		this.row = row;
		this.col = col;
	}

	/**
	 * A string to display on the board to represent the occurrence of this
	 * event.
	 */
	public String displayString() {
		throw new NotImplementedException();
	}

	/*
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!getClass().isInstance(obj)) {
			return false;
		}
		Move other = (Move) obj;
		if (col != other.col) {
			return false;
		}
		if (row != other.row) {
			return false;
		}
		return true;
	}

}
