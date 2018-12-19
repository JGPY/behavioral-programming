package moveCar;

import static bpSourceCode.bp.eventSets.EventSetConstants.none;
import bpSourceCode.bp.BThread;
import bpSourceCode.bp.Event;
import static bpSourceCode.bp.BProgram.bp;

class HellowWorld {

	static Event goodMorning = new Event() {
		@Override
		public String toString() {
			return "Good Morning!";
		}
	};

	static Event goodEvening = new Event() {
		@Override
		public String toString() {
			return "Good Evening!";
		}
	};

	static class SayGoodMorning extends BThread {
		@Override
		public void runBThread() {
			while (true) {
				System.out.println("goodMorning_1");
				bp.bSync(goodMorning, none, none);
				System.out.println("goodMorning_2");
			}
		}
	}

	static class SayGoodEvening extends BThread {
		@Override
		public void runBThread() {
			while (true) {
				System.out.println("goodEvening_1");
				bp.bSync(goodEvening, none, none);
				System.out.println("goodEvening_2");
			}
		}
	}

	static class Interleave extends BThread {
		@Override
		public void runBThread() {
			while (true) {
				bp.bSync(none, goodMorning, goodEvening);
				bp.bSync(none, goodEvening, goodMorning);
			}
		}
	}


	public static void main(String[] args) {
		bp.add(new SayGoodMorning(), 1.0);
		bp.add(new SayGoodEvening(), 3.0);
		bp.add(new Interleave(), 4.0);
		bp.startAll();
	}
}