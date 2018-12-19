package bpSourceCode.bp.state.unittest.br;

/**
 * A representation of the state of the system.
 */

import java.io.Serializable;

import static bpSourceCode.bp.state.unittest.br.BridgeSymbolic.*;

@SuppressWarnings("serial")
class CurrentState implements Serializable {

	BANK[] side = new BANK[nPersons];
	BANK tSide = BANK.E;

	public CurrentState() {
		for (int i = 0; i < nPersons; i++) {
			side[i] = BANK.E;
		}
	}

	void switchP(int i) {
		side[i] = (side[i] == BANK.E ? BANK.W : BANK.E);
		
	}

	public boolean allPersonsAtTheWestBank() {
		for (int i = 0; i < nPersons; i++) {
			if (!(side[i] == BANK.W))
				return false;
		}
		return true;
	}

	void switchT() {
		tSide = ((tSide == BANK.E) ? BANK.W : BANK.E);
	}

	@Override
	public int hashCode() {
		int result = 0;
		for (int i = 0; i < nPersons; i++) {

			if (side[i] == BANK.W)
				result += 1;

			result *= 2;
		}
		if (tSide == BANK.W)
			result += 1;
		return result;
	}

	public boolean equals(Object obj) {
		return hashCode() == obj.hashCode();
	}

	@Override
	public String toString() {

		String s = "";
		for (int i = 0; i < nPersons; i++) {
			s += side[i];
		}
		s += "-" + tSide;
		return s;
	}

}
