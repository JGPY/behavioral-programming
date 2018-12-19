package bpSourceCode.bp.state;

import static bpSourceCode.bp.state.LivenessVerification.cycleStack;

import java.util.ArrayList;
import java.util.HashSet;

import bpSourceCode.bp.Event;

// All fairness requirements in a verification
public class Fairness {
	public static boolean fairness = false; // Any fairness requirements at all?
	public static boolean unconditionalF = false; // Any unconditional fairness
	// requirements?
	public static boolean strongF = false; // Any strong fairness requirements?
	public static boolean weakF = false; // Any weak fairness requirements?
	public static ArrayList<HashSet<Event>> uncondFReq;
	public static ArrayList<HashSet<Event>> strongFReq;
	public static ArrayList<HashSet<Event>> weakFReq;
	static HashSet<Event> triggeredInCycle = new HashSet<Event>();
	static HashSet<Event> enabledInCycle = new HashSet<Event>();

	public static void initFairness(ArrayList<HashSet<Event>> uncondFReq, ArrayList<HashSet<Event>> strongFReq, ArrayList<HashSet<Event>> weakFReq) {
		Fairness.uncondFReq = uncondFReq;
		Fairness.strongFReq = strongFReq;
		Fairness.weakFReq = weakFReq;
		if (!uncondFReq.isEmpty())
			unconditionalF = true;
		if (!strongFReq.isEmpty())
			strongF = true;
		if (!weakFReq.isEmpty())
			weakF = true;
		if (unconditionalF || strongF || weakF)
			fairness = true;
	}

	// -----------------------------------------------------//
	// Fairness checking:
	// For each set of UNCONDITIONAL:
	// - Its intersection with the set of triggered - should be NON EMPTY -
	// thus, SOMETHING happened to answer the set
	// For each STRONG:
	// - If its intersection with ENABLED is empty - no need to check.
	// Otherwise, the intersection of the set and triggered, should be
	// not empty - something must answer the enabled.
	static boolean fairCycle(BPState starter) {

		HashSet<Event> temp;

		// If we don't care - say it is OK.
		if (!fairness)
			return true;
		// Collect all events in cycle;
		triggeredInCycle.clear();
		for (BPTransition t : cycleStack) {
			triggeredInCycle.add(t.event);
		}
		System.out.println("TRIGGERED IN CYCLE: " + triggeredInCycle);
		if (unconditionalF)
			// Check each uncond Fairness requirement
			for (HashSet<Event> u : uncondFReq) {
				temp = (HashSet<Event>) (u.clone());
				System.out.println("uncondU: " + temp);
				// requirement intersection with triggered
				temp.retainAll(triggeredInCycle);
				// if empty - none of the events in this set were triggered
				// in the cycle - unfair.
				if (temp.isEmpty()) {
					System.out.println("Candidate cycle failed Uncond Fairness test ");
					return false;
				}
			}

		if (strongF) {
			// Collect all enabled events;
			enabledInCycle.clear();
			for (BPTransition t : cycleStack) {
				enabledInCycle.addAll((t.fromState).enabledEvents);
			}
			System.out.println("ENABLED IN CYCLE: " + enabledInCycle);
			// See if there are pending events from the strong req
			for (HashSet<Event> u : strongFReq) {
				temp = (HashSet<Event>)(u.clone()); 
				System.out.println("U: " + temp);
				// See if strong req set was enabled in cycle
				temp.retainAll(enabledInCycle);
				System.out.println("U-AND-ENABLED: " + temp);
				if (!temp.isEmpty()) { // some strong req WERE enabled - check
					// if triggered
					// Intersection of enabled with triggered
					// Note that only enabled could be triggered.
					temp.retainAll(triggeredInCycle);
					System.out.println("U-AND-ENABLED-AND-TRIGGERED: " + temp);
					if (temp.isEmpty()) {
						System.out.println("Candidate cycle failed Strong Fairness test ");
						return false;
					}
				}
			}
		}
		if (weakF) {
			// Collect all continuously enabled events;
			// Initialize set;
			enabledInCycle.addAll(cycleStack.get(0).fromState.enabledEvents);
			// Intersect all enabled events sets.
			for (BPTransition t : cycleStack) {
				enabledInCycle.retainAll(t.fromState.enabledEvents);
			}
			System.out.println("CONTINUOUSLY ENABLED IN CYCLE: " + enabledInCycle);
			// See if there are pending events from the weak req'ts
			for (HashSet<Event> u : weakFReq) {

				temp = (HashSet<Event>)(u.clone()); 
				System.out.println("wU: " + temp);

				// See if weak req set was enabled in cycle
				temp.retainAll(enabledInCycle);
				System.out.println("wU and CONSTANTLY ENABLED: " + temp);
				if (!temp.isEmpty()) { // some weak req WERE CONTINUOUSLY
					// enabled - check if triggered
					// Note that only enabled could be triggered.
					// Intersection of enabled with triggered
					temp.retainAll(triggeredInCycle);
					System.out.println("wU and CONSTANTLY ENABLED AND TRIGGERED: " + temp);
					if (temp.isEmpty()) {
						System.out.println("Candidate cycle failed Weak Fairness test ");
						return false;
					}
				}
			}
		}
		return true;
	}
}
