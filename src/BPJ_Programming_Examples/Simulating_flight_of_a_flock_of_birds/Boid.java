package BPJ_Programming_Examples.Simulating_flight_of_a_flock_of_birds;
 

import static bpSourceCode.bp.eventSets.EventSetConstants.none;

import java.awt.geom.Point2D;

import bpSourceCode.bp.BThread;
import bpSourceCode.bp.Event;
import bpSourceCode.bp.eventSets.EventSet;
import bpSourceCode.bp.exceptions.BPJException;

import static bpSourceCode.bp.BProgram.bp;

@SuppressWarnings("serial")
public class Boid extends BThread{

	/**
	 * The boid location (fields are modified the the object is never reallocated hence final)
	 */
	final Point2D.Double location = new Point2D.Double(700 * Math.random(),
			500 * Math.random());

	/**
	 * The boid flight vector
	 */
	final Point2D.Double speed = new Point2D.Double(Math.random(), Math
			.random());

	/**
	 * The BPJ_Programming_Examples.flock where this boid belongs. The BPJ_Programming_Examples.flock is not final, can theoretically change.
	 */
	Flock flock;
	
	public Event endSleep = new Event("End Sleep");
	public static Event softBounceFromUpperWall = new Event("Soft Bounce From Upper Wall");
	public static Event softBounceFromLowerWall = new Event("Soft Bounce From Lower Wall");
	public static Event softBounceFromLeftWall = new Event("Soft Bounce From Left Wall");
	public static Event softBounceFromRightWall = new Event("Soft Bounce From Right Wall");
	public static Event hardBounceFromUpperWall = new Event("Hard Bounce From Upper Wall");
	public static Event hardBounceFromLowerWall = new Event("Hard Bounce From Lower Wall");
	public static Event hardBounceFromLeftWall = new Event("Hard Bounce From Left Wall");
	public static Event hardBounceFromRightWall = new Event("Hard Bounce From Right Wall");
	public Event[] keepAway;
	
	
	public MatchSpeed match = new MatchSpeed(this);
	public KeepSpeed keep = new KeepSpeed();
	public FlyTowardsCenterOfMass towards = new FlyTowardsCenterOfMass();
	public FlyAwayFromCenterOfMass away = new FlyAwayFromCenterOfMass();
	public SoftBounceFromUpperWall softUpper = new SoftBounceFromUpperWall();
	public SoftBounceFromLowerWall softLower = new SoftBounceFromLowerWall();
	public SoftBounceFromLeftWall softLeft = new SoftBounceFromLeftWall();
	public SoftBounceFromRightWall softRight = new SoftBounceFromRightWall();
	public HardBounceFromUpperWall hardUpper = new HardBounceFromUpperWall();
	public HardBounceFromLowerWall hardLower = new HardBounceFromLowerWall();
	public HardBounceFromLeftWall hardLeft = new HardBounceFromLeftWall();
	public HardBounceFromRightWall hardRight = new HardBounceFromRightWall();
	public CheckUpperWall checkUpper = new CheckUpperWall();
	public CheckLowerWall checkLower = new CheckLowerWall();
	public CheckLeftWall checkLeft = new CheckLeftWall();
	public CheckRightWall checkRight = new CheckRightWall();
	public KeepAway awayArray[];

	public int index;
	
	long t1,t2,time;

	public Boid(int n) {
		index = n;
		keepAway = new Event[Constants.count];
		awayArray = new KeepAway[Constants.count];
		for (int i = 0; i < Constants.count; i++) {
			if(i != n) {
				keepAway[i] = new Event(Integer.toString(n)+" Keep Away From "+Integer.toString(i));
				awayArray[i] = new KeepAway(i);
			}
		}
	}



	/**
	 * Try to keep the speed the same as speed of other boids.
	 */
	public class MatchSpeed extends BThread{
		Boid boid;
		public MatchSpeed(Boid b) {
			boid = b;
		}
		@Override
		public void runBThread() {
			while(true) {
				bp.bSync(none, boid.endSleep, none);
				bp.bSync(Events.matchSpeed, none, none);

				double x = 0;
				double y = 0;

				//Calculates average velocity
				int n = flock.size() - 1;
				for (Boid b : flock)
					if (b != boid) {
						x += b.speed.x;
						y += b.speed.y;
					}
				x /= n;
				y /= n;

				double vx = x - boid.speed.x;
				double vy = y - boid.speed.y;

				//adjusts velocity
				boid.speed.x += vx * Constants.speedAdjust * time;
				boid.speed.y += vy * Constants.speedAdjust * time;
				
			}
		}
	}

	/**
	 * Fly towards the perceived center of mass
	 */
	public class FlyTowardsCenterOfMass extends BThread{

		@Override
		public void runBThread(){

			while(true) {
				bp.bSync(none, endSleep, none);
				bp.bSync(Events.flyTowardsCenterOfMass, none, none);
				
				Point2D.Double center = vectorCenter(); //calculates center of mass
				
				//moves bird towards center of mass
				move(time, center.x, center.y, Constants.holdFlockCenter);
			}
		}
	}
	
	
	/**
	 * Fly away from the perceived center of mass
	 */
	public class FlyAwayFromCenterOfMass extends BThread{

		@Override
		public void runBThread(){
			while(true) {
				bp.bSync(none, endSleep, none);
				bp.bSync(Events.flyAwayFromCenterOfMass, none, none);
				
				Point2D.Double center = vectorCenter();//calculates center of mass
				
				//update velocity to be away from center of mass
				speed.x -= center.x * Constants.flockAwayFromCenter * time;
				speed.y -= center.y * Constants.flockAwayFromCenter * time;
			}
		}
	}

	
	Point2D.Double vectorCenter() {
		// Compute the perceive center.
		double x = 0;
		double y = 0;

		// Do not count self
		int n = flock.size() - 1;
		for (Boid boid : flock)
			if (boid != this) {
				x += boid.location.x;
				y += boid.location.y;
			}

		x /= n;
		y /= n;

		// Compute the vector toward center
		double vx = x - location.x;
		double vy = y - location.y;
		return new Point2D.Double(vx,vy);
	}

	/**
	 * Accelerate if too slow.
	 */
	public class KeepSpeed extends BThread{
		@Override
		public void runBThread() {
			while(true) {
				bp.bSync(none, endSleep, none);
				bp.bSync(Events.keepSpeed, none, none);
				
				double v = Math.sqrt(speed.x * speed.x + speed.y * speed.y);
				double r = Constants.minSpeed - v;

				if (r > 0 && v < Constants.maxSpeed)
					r = 1 + Constants.speedAdjust; //speed should be increased
				else
					r = 1 - Constants.speedAdjust; //speed should be decreased

				//adjusts velocity
				speed.x *= r;
				speed.y *= r;
			}
			
		}
		
	}

	/**
	 * Service method to move
	 * @param time the time quant
	 * @param vx x speed vector
	 * @param vy y speed vector
	 * @param weight the weight of this move
	 */
	private void move(double time, double vx, double vy, double weight) {
		location.x += vx * weight * time;
		location.y += vy * weight * time;
	}

	/**
	 * Keep away from walls and other boids
	 */
	public class KeepAway extends BThread{
		int indexOfOtherBoid;
		public KeepAway(int i) {
			indexOfOtherBoid = i;
		}

		@Override
		public void runBThread() {
			Boid otherBoid = flock.get(indexOfOtherBoid);
			while(true) {
				bp.bSync(none, endSleep, none);
				bp.bSync(keepAway[indexOfOtherBoid], none, none);
				
				double distance = otherBoid.location.distance(location);
					
				// birds are in the same location
				if (distance < 1)
					distance = 1;

				double s = 1 / distance;
				double vx = s * (location.x - otherBoid.location.x);
				double vy = s * (location.y - otherBoid.location.y);
				
				//adjusts coordinates coordinates
				location.x += vx * Constants.avoidNeibourthood*time; 
				location.y += vy * Constants.avoidNeibourthood*time; 					
			}
			
		}
		
	}
	
	
	/**
	 * Bounce from the boundaries of the modeling space (like bats in the cave). To avoid sticking on the boundary
	 * (altering vector on every iteration), this must be the last method to call.
	 */
	
	public class SoftBounceFromLeftWall extends BThread{

		@Override
		public void runBThread() {
			while(true) {
				bp.bSync(none, endSleep, none);
				bp.bSync(softBounceFromLeftWall, none, none);
				speed.x += Constants.avoidNeibourthood;
			}
			
		}
	
	}
	public class SoftBounceFromRightWall extends BThread{

		@Override
		public void runBThread() {
			while(true) {
				bp.bSync(none, endSleep, none);
				bp.bSync(softBounceFromRightWall, none, none);
				speed.x -= Constants.avoidNeibourthood;
			}
			
		}
	
	}
	public class SoftBounceFromUpperWall extends BThread{

		@Override
		public void runBThread() {
			while(true) {
				bp.bSync(none, endSleep, none);
				bp.bSync(softBounceFromUpperWall, none, none);
				speed.y += Constants.avoidNeibourthood;
			}
			
		}
	
	}
	public class SoftBounceFromLowerWall extends BThread{

		@Override
		public void runBThread() {
			while(true) {
				bp.bSync(none, endSleep, none);
				bp.bSync(softBounceFromLowerWall, none, none);
				speed.y -= Constants.avoidNeibourthood;
			}
			
		}
	
	}
	
	//HARD BOUNCE
	public class HardBounceFromLeftWall extends BThread{

		@Override
		public void runBThread() {
			while(true) {
				bp.bSync(none, endSleep, none);
				bp.bSync(hardBounceFromLeftWall, none, none);
				location.x = 0;
				speed.x = -speed.x;
			}
			
		}
	
	}
	public class HardBounceFromRightWall extends BThread{

		@Override
		public void runBThread() {
			while(true) {
				bp.bSync(none, endSleep, none);
				bp.bSync(hardBounceFromRightWall, none, none);
				location.x = flock.size.width;
				speed.x = -speed.x;
			}
			
		}
	
	}
	public class HardBounceFromUpperWall extends BThread{

		@Override
		public void runBThread() {
			while(true) {
				bp.bSync(none, endSleep, none);
				bp.bSync(hardBounceFromUpperWall, none, none);
				location.y = 0;
				speed.y = -speed.y;
			}
			
		}
	
	}
	public class HardBounceFromLowerWall extends BThread{

		@Override
		public void runBThread() {
			while(true) {
				bp.bSync(none, endSleep, none);
				bp.bSync(hardBounceFromLowerWall, none, none);
				location.y = flock.size.height;
				speed.y = -speed.y;
			}
			
		}
	
	}
	
	//CHECK WALLS
		public class CheckLeftWall extends BThread{

			@Override
			public void runBThread() {
				EventSet hardAndSoft = new EventSet(hardBounceFromLeftWall, softBounceFromLeftWall);
				bp.bSync(none, endSleep, hardAndSoft);
				while(true) {
					if (location.x <= 0) {
						bp.bSync(none, endSleep, none);
					}
					else if (location.x < Constants.keepAwayDistance) {
						bp.bSync(none, endSleep, hardBounceFromLeftWall);
					}
					else {
						bp.bSync(none, endSleep, hardAndSoft);
					}
				}
				
			}
		
		}
		public class CheckRightWall extends BThread{

			@Override
			public void runBThread() {
				EventSet hardAndSoft = new EventSet(hardBounceFromRightWall, softBounceFromRightWall);
				bp.bSync(none, endSleep, hardAndSoft);
				while(true) {
					if (location.x >= flock.size.width) {
						bp.bSync(none, endSleep, none);
					}
					else if (location.x > flock.size.width - Constants.keepAwayDistance) {
						bp.bSync(none, endSleep, hardBounceFromRightWall);
					}
					else {
						bp.bSync(none, endSleep, hardAndSoft);
					}
				}
				
			}
		
		}
		public class CheckUpperWall extends BThread{

			@Override
			public void runBThread() {
				EventSet hardAndSoft = new EventSet(hardBounceFromUpperWall, softBounceFromUpperWall);
				bp.bSync(none, endSleep, hardAndSoft);
				while(true) {
					if (location.y < 0) {
						bp.bSync(none, endSleep, none);
					}
					else if (location.y <= Constants.keepAwayDistance) {
						bp.bSync(none, endSleep, hardBounceFromUpperWall);
					}
					else {
						bp.bSync(none, endSleep, hardAndSoft);
					}
				}			
			}
		}
		public class CheckLowerWall extends BThread{

			@Override
			public void runBThread() {
				EventSet hardAndSoft = new EventSet(hardBounceFromLowerWall, softBounceFromLowerWall);
				bp.bSync(none, endSleep, hardAndSoft);
				while(true) {
					if (location.y >= flock.size.height) {
						bp.bSync(none, endSleep, none);
					}
					else if (location.y > flock.size.height - Constants.keepAwayDistance) {
						bp.bSync(none, endSleep, hardBounceFromLowerWall);
					}
					else {
						bp.bSync(none, endSleep, hardAndSoft);
					}
				}			
			}
		
		}
	
	@Override
	public void runBThread(){
		t1 = System.currentTimeMillis();
		t2 = t1;
		while(true){
			Sleep sleep = new Sleep();
			sleep.setBProgram(getBProgram());
			new Thread(sleep).start();
			bp.bSync(none, endSleep, none);

			location.x += speed.x * time * Constants.stepTime;
			location.y += speed.y * time * Constants.stepTime;
		}
	}
	
	public class Sleep extends BThread implements Runnable {
		public void run() {
			try{
				Thread.sleep(Constants.sleep);
	        }catch(Exception e){}
			bp.add((double)(index+10));
			t2 = System.currentTimeMillis();
			time = t2-t1;
			t1 = t2;
			bp.bSync(endSleep, none, none);
			bp.remove();
	    }
		
		public void runBThread() throws BPJException {

		}
	}

}
