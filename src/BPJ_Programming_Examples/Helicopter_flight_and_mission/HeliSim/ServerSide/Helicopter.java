package BPJ_Programming_Examples.Helicopter_flight_and_mission.HeliSim.ServerSide;

import java.awt.Image;

import javax.swing.ImageIcon;

public class Helicopter {
	int x, y;
	int rotate;
	Image img;
	int OldX, Oldy;

	public Helicopter(int x, int y, int rotate) {
		this.x = x;
		this.y = y;

		this.rotate = rotate;
		ImageIcon i = new ImageIcon(
				"C:/Users/ido/Documents/workspace/BPJ_Programming_Examples.Helicopter_flight_and_mission.HeliSim/Helicopter.png");
		this.img = i.getImage();
	}

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}

	public void setXRel(int x) {
		this.x = this.x + x;
	}

	public void setYRel(int y) {
		this.y = this.y - y;
	}

	public int getX() {
		return this.x;
	}

	public int getY() {
		return this.y;
	}

	public Image getImage() {
		return this.img;
	}

}
