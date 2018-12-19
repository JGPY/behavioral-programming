package BPJ_Programming_Examples.Helicopter_flight_and_mission.HeliSim.ServerSide;

import java.awt.Color;

public class Point {

	int x, y;
	Color col;

	public Point(int x, int y, Color c) {
		this.x = x;
		this.y = y;
		this.col = c;
	}

	public int getX() {
		return this.x;
	}

	public int getY() {
		return this.y;
	}

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}

	public Color getColor() {
		return this.col;
	}

	public void SetColor(Color color1) {
		this.col = color1;
	}

	public void SetColor(String color1) {
		if (color1.equals("BLACK")) {
			this.col = new Color(0, 0, 0);
		} else if (color1.equals("RED")) {
			this.col = new Color(255, 0, 0);
		} else if (color1.equals("GREEN")) {
			this.col = new Color(0, 255, 0);
		} else if (color1.equals("BLUE")) {
			this.col = new Color(0, 0, 255);
		} else {
			this.col = new Color(255, 255, 255);
		}

	}

}
