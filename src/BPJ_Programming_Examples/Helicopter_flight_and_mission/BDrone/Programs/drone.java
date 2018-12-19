package BPJ_Programming_Examples.Helicopter_flight_and_mission.BDrone.Programs;

import il.ac.wis.cs.rovtool.Activator;
import il.ac.wis.cs.rovtool.bp.BPFactory;
import il.ac.wis.cs.rovtool.bp.BPROV;
import il.ac.wis.cs.rovtool.bp.BPSpatialControlSession;
import il.ac.wis.cs.rovtool.bp.BPVector;

import java.io.IOException;

public class drone {
	int rx, ry, rz;// where is the drone
	int x, y, z;// where the drone should be
	String col;

	static SocketClient a = new SocketClient();
	private BPSpatialControlSession scs;

	public BPSpatialControlSession getSpatialControlSession() {
		if (null == scs) {
			// init code
			// init rovtool bundle

			Activator rovtoolBundleActivator = new Activator();
			rovtoolBundleActivator.init();
			Activator.INSTANCE = rovtoolBundleActivator;

			try {
				BPROV rov = BPFactory.eINSTANCE.createROV();
				scs = BPFactory.eINSTANCE.createSpatialControlSession(rov);
				scs.init();
				scs.open();
				scs.takeoff();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return scs;
	}

	public drone() {
		this.rx = 0;
		this.ry = 0;
		this.rz = 0;
		this.x = 0;
		this.y = 0;
		this.z = 0;
		this.col = "BLACK";
	}

	public void setRealPosition(int rx, int ry, int rz) {
		this.rx = rx;
		this.ry = ry;
		this.rz = rz;

	}

	public void setPosition(int x, int y, int z) {
		this.x = x;
		this.y = y;
		this.z = z;

	}

	public int getRealX() {
		return rx;
	}

	public int getRealY() {
		return ry;
	}

	public int getRealZ() {
		return rz;
	}

	public void setX(int newX) {
		this.x = newX;

	}

	public void setY(int newY) {
		this.y = newY;

	}

	public void setZ(int newZ) {
		this.z = newZ;

	}

	public void setColor(String a) {
		this.col = a;
	}

	public int getX() {
		return this.x;
	}

	public int getY() {
		return this.y;
	}

	public int getZ() {
		return this.z;
	}

	public String getColor() {
		return this.col;
	}

	public void sendRealPosition(String color) {
		switch (Vars.runMode) {
		case netWorkingMode:
			a.send("Fixed", rx, ry, color);
			break;
		case bpApiMode:
			// TODO bpApiMode
			double gain = 1;
			int rxl = (int) (rx * gain);
			int ryl = (int) (ry * gain);
			int rzl = (int) (rz * gain);
			BPVector v_pos = BPFactory.eINSTANCE.createVector(rxl, ryl, rzl);
			getSpatialControlSession().resetSpatialPosition(v_pos);
			System.out.println(", reset pos:" + v_pos);
			break;
		default:
			break;
		}

	}

	public void clear() {
		switch (Vars.runMode) {
		case netWorkingMode:
			a.send("CLEAR_SIM");
			break;
		case bpApiMode:

			BPVector v_pos = BPFactory.eINSTANCE.createVector(0, 0, 0);
			getSpatialControlSession().resetSpatialPosition(v_pos);
			break;
		default:
			break;
		}
	}

}
