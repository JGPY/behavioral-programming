package BPJ_Programming_Examples.Sudoku.events;

import java.io.Serializable;

import bpSourceCode.bp.eventSets.EventSetInterface;

/**
 * A set that contains all Move events that compete or conflict with a given
 * move event. i.e, all moves that try to put the same number in the same row,
 * column or box. AND cannot put any number (same or other) in the same square.
 */
@SuppressWarnings("serial")
public class Competitors implements EventSetInterface, Serializable {
	/**
	 * @see EventSetInterface#contains(Object)
	 */
	Move baseMove;

	public Competitors(Move baseMove) {
		super();
		this.baseMove = baseMove;

	}

	public boolean contains(Object o) {
		if (o == null) {
			return false;
		}
		if (!(o instanceof Move)) {
			return false;
		}
		Move other = (Move) o;

		// Current square is occupied - all moves there are competitors
		if ((baseMove.col == other.col) && (baseMove.row == other.row))
			return true;

		// We are now going for a different square.
		// if not same number - no competition.
		if (baseMove.number != other.number)
			return false;
		// Same number - can't be same row or same col, or same box,
		if (baseMove.intersect(other))
			return true;
		return false;
	}

	public String toString() {
		return ("Competitors(" + baseMove.toString() + ")");
	}

}
