package bpSourceCode.bp.state.unittest.ph;



import static bpSourceCode.bp.BProgram.bp;
import bpSourceCode.bApplication.BApplication;
import bpSourceCode.bp.Event;
import bpSourceCode.bp.eventSets.EventSet;

// moveCar.Main class
public class DiningPhilsBApp implements BApplication {
	
	transient static int max; // maximal number of phils that may attempt to eat
	// simultaneously
	static int nPhils; // number of philosophers
	static boolean leftyPhil = false; // Last Philosopher is left handed.
	static String parmString = "Application Parameters: "; 
	static {
		nPhils = Integer.parseInt(System.getProperty("nPhils", "3"));
		parmString += "nPhils=" + nPhils; 
		leftyPhil = (System.getProperty("leftyPhil","false").equals("true"));
		parmString += ", leftyPhil=" + leftyPhil;
		System.out.println(parmString);
	}
	static Event[] pickUpRightForkEvents = new Event[nPhils];
	static Event[] pickUpLeftForkEvents = new Event[nPhils];
	static EventSet[] pickMeUpSets = new EventSet[nPhils];

	static Event[] putDowns = new Event[nPhils];

	static Event NOEVENT = new Event("NOEVENT");

	static {
		for (int i = 0; i < nPhils; i++) {
			pickUpRightForkEvents[i] = new Event("PickUp-F" + i + "-by-P" + i);
			pickUpLeftForkEvents[i] = new Event("PickUp-F" + i + "-by-P" + ((i + 1) % nPhils));
			putDowns[i] = new Event("PutDown-F" + i);
			pickMeUpSets[i] = new EventSet(pickUpRightForkEvents[i],pickUpLeftForkEvents[i]);


		}

	}
	
	public void runBApplication() {
		double p = 1.0;
		bp.setBThreadEpsilon(100.0); 

		for (int i = 0; i < nPhils; i++) {
			bp.add(new Phil(i), p++);
			bp.add(new Fork(i), p++);

		}
		// sourceCode.bp.add(new LTL_Phil_0_Eat_Infinitely_Often(), 99.0);
		// sourceCode.bp.add(new LTL_DeadlockDetector(), 100.0);
		// sourceCode.bp.add(new LTL_Stam(), 100.0);
		
		try {
			// sourceCode.bp.add(new Logger(), 30.0);

			bp.startAll();
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(parmString);


	}
}
