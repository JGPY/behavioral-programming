package moveCar.events;

import bpSourceCode.bp.Event;


public class StaticEvents {

	static public Event addHot = new Event("AddHot") {

		@Override
		public String toString() {
			System.out.println("addHot事件!");
			return "addHot事件";
		}
	};

	static public Event addCold = new Event("AddCold") {

	};

	static public Event EventData = new Event("数据") {


	};

	static public Event EventFazzy = new Event("模糊") {
	};

	static public Event eventAdvance = new Event("Advance") {
		@Override
		public String toString() {
			System.out.println("Advance事件!");
			return super.toString();
		}
	};

	static public Event eventRetreat = new Event("Retreat") {
		@Override
		public String toString() {
			System.out.println("Retreat事件!");
			return super.toString();
		}
	};


}
