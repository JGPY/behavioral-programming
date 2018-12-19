package BPJ_Programming_Examples.Tic_Tac_Toe.tictactoe.events;

import bp.Event;

@SuppressWarnings({ "serial" })
public class StaticEvents {
	static public Event draw = new Event("Draw") {
	};

	static public Event XWin = new Event("XWin") {
	};

	static public Event OWin = new Event("OWin") {
	};

	static public Event gameOver = new Event("GameOver") {
	};
}
