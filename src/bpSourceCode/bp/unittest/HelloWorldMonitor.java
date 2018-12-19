package bpSourceCode.bp.unittest;

import static bpSourceCode.bp.eventSets.EventSetConstants.all;
import static bpSourceCode.bp.eventSets.EventSetConstants.none;
import bpSourceCode.bp.BProgram;
import bpSourceCode.bp.BThread;
import bpSourceCode.bp.Event;
import bpSourceCode.bp.exceptions.BPJException;

class HelloWorldMonitor {
	static class GreetingEvent extends Event {

		public GreetingEvent(String name) {
			this.setName(name);
		}

	}

	static GreetingEvent goodMorning = new GreetingEvent("Good Morning!");
	static GreetingEvent goodEvening = new GreetingEvent("Good Evening!");
	static Event displayMessage = new Event("displayMessage");


	static class SayGoodMorning extends BThread {
		public void runBThread() throws BPJException {
			for (int i = 1; i <= 3; i++) {
				BProgram bp = getBProgram();
				bp.bSync(goodMorning, none, none);
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
				bp.bSync(none, goodMorning, goodEvening);
				bp.bSync(none, goodEvening, goodMorning);
			}
		}
	}

	static class DisplayEvents extends BThread {
		public void runBThread() throws BPJException {
			while (true) {
				BProgram bp = getBProgram();
				bp.bSync(none, all, none);
				System.out.println(toString()+ " processed:" + bp.lastEvent);
				bp.bSync(displayMessage, none, none);
				System.out.println(toString()+ " saw displayMessage");

			}
		}
	}

	public static void main(String[] args) {
		BProgram hello = new BProgram();
		DisplayEvents displayEvents1 = new DisplayEvents();
		displayEvents1.setName("DisplayEvents1"); 
		DisplayEvents displayEvents2 = new DisplayEvents();
		displayEvents2.setName("DisplayEvents2");
		hello.add(displayEvents1, 0.5);
		hello.add(displayEvents2, 0.6);
		hello.add(new SayGoodMorning(), 1.0);
		hello.add(new SayGoodEvening(), 3.0);
		hello.add(new Interleave(), 4.0);
		//can test with next line:
		// Make both or just one of displayEvents monitor-only. 
		displayEvents1.setMonitorOnly(true);
		

		hello.startAll();
	}

}
