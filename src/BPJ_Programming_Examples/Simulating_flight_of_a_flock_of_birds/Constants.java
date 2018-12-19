package BPJ_Programming_Examples.Simulating_flight_of_a_flock_of_birds;

public class Constants {

	/**
	 * Conversion between the model time and real time.
	 */
	public static double stepTime = 0.1;

	/**
	 * The "fly towards center" factor.
	 */
	public static double holdFlockCenter = 0.001;

	public static double avoidNeibourthood = 0.01;

	/**
	 * The number of boids in the BPJ_Programming_Examples.flock.
	 */
	public static int count = 8;

	/**
	 * The keep away zone around boids
	 */
	public static double keepAwayDistance = 150;
	public static double keepAwayNeibourthoodDistance = 50;

	/**
	 * Minimal speed (accelerate if slower).
	 */
	public static double minSpeed = 1;

	/**
	 * Max speed (slow down if faster).
	 */
	public static double maxSpeed = minSpeed * 2;

	/**
	 * The acceleration for speed adjustment.
	 */
	public static double speedAdjust = 0.005;
	
	public static double flockAwayFromCenter = 0.00001;
	
	public static int sleep = 10;

}
