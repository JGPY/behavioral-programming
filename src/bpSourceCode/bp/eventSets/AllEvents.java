package bpSourceCode.bp.eventSets;

import java.io.Serializable;

/**
 * @author Bertrand Russell
 * 
 *         A set that contains everything.
 */
public class AllEvents implements EventSetInterface, Serializable {
	/**
	 * @see bpSourceCode.bp.eventSets.EventSetInterface#contains(Object)
	 */
	@Override
	public boolean contains(Object o) {
		return true;
	}

	@Override
	public String toString() {
		return ("All");
	}
}
