package bpSourceCode.bp.unittest;

import static bpSourceCode.bp.eventSets.EventSetConstants.all;
import static bpSourceCode.bp.eventSets.EventSetConstants.none;
import bpSourceCode.bp.BProgram;
import bpSourceCode.bp.BThread;
import bpSourceCode.bp.Event;
import bpSourceCode.bp.eventSets.EventSet;
import bpSourceCode.bp.eventSets.RequestableEventSet;
import bpSourceCode.bp.exceptions.BPJException;

class HelloWorldShortNames {

	static class GreetingEvent extends Event {

		public GreetingEvent(String name) {
			this.setName(name);
		}

	}

	static GreetingEvent goodMorning = new GreetingEvent("GM");
	static GreetingEvent goodEvening = new GreetingEvent("GE");

	static Event ttt = new Event() {
		@Override
        public String toString() {
			return "H";
		}
	};

	static class SayGoodMorning extends BThread {
		public void runBThread() throws BPJException {
			for (int i = 1; i <= 3; i++) {
				BProgram bp = getBProgram();
				bp.bSync(new RequestableEventSet("SET33",goodMorning, ttt), none, none);
			}
		}
	}

	static class SayGoodEvening extends BThread {
		public void runBThread() throws BPJException {
			for (int i = 1; i <= 3; i++) {
				BProgram bp = getBProgram();
				bp.bSync(goodEvening, none, none);
			}
		}
	}

	static class Interleave extends BThread {
		public void runBThread() throws BPJException {
			while (true) {
				BProgram bp = getBProgram();
				bp.bSync(none, goodMorning, new EventSet("SET11",goodEvening, ttt));
				bp.bSync(none, goodEvening, new EventSet("SET22",goodMorning, ttt));
			}
		}
	}

	static class DisplayEvents extends BThread {
		public void runBThread() throws BPJException {
			while (true) {
				BProgram bp = getBProgram();
				bp.bSync(none, all, none);
				System.out.println(getBProgram().lastEvent);
			}
		}
	}

	public static void main(String[] args) {
		BThread bt;
		BProgram hello = new BProgram();
		bt = new SayGoodMorning();
		bt.setName("BTM");
		hello.add(bt, 1.0);
		bt = new DisplayEvents();
		bt.setName("Disp");
		hello.add(bt, 2.0);
		bt = new SayGoodEvening();
		bt.setName("BTE");
		hello.add(bt, 3.0);
		bt = new Interleave();
		bt.setName("BTI");
		hello.add(bt, 4.0);

		hello.startAll();
	}

}
