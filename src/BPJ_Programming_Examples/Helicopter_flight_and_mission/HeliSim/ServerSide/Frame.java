package BPJ_Programming_Examples.Helicopter_flight_and_mission.HeliSim.ServerSide;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;

import javax.swing.JFrame;

@SuppressWarnings("serial")
public class Frame extends JFrame {
	Graphics2D g2;
	ArrayList<Point> PointList = new ArrayList<Point>();

	Helicopter heli = new Helicopter(MainPanel.FRAME_SIZE / 2,
			MainPanel.FRAME_SIZE / 2, 0);

	public Frame(String title) {
		setTitle(title);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(MainPanel.FRAME_SIZE, MainPanel.FRAME_SIZE);
		setLocation(100, 200);
		setVisible(true);
		heli = new Helicopter(MainPanel.FRAME_SIZE / 2,
				MainPanel.FRAME_SIZE / 2, 0);

		PointList.add(new Point(MainPanel.FRAME_SIZE / 2,
				MainPanel.FRAME_SIZE / 2, new Color(0, 0, 0)));
	}

	public void paint(Graphics g) {
		g2 = (Graphics2D) g;
		super.paint(g);

		int y = getHeight();
		int x = getWidth();

		g2.setColor(new Color(0, 0, 0));
		g2.drawLine(x / 2, 0, x / 2, y);
		g2.drawLine(0, y / 2, x, y / 2);

		g2.fillOval(heli.getX() - MainPanel.HELICOPTER_PAINT_SIZE / 2,
				heli.getY() - MainPanel.HELICOPTER_PAINT_SIZE / 2,
				MainPanel.HELICOPTER_PAINT_SIZE,
				MainPanel.HELICOPTER_PAINT_SIZE);

		for (int i = 1; i < PointList.size(); i++) {
			g2.setColor(PointList.get(i).getColor());
			g2.drawLine((int) (PointList.get(i - 1).getX()), // +
																// MainPanel.HELICOPTER_PAINT_SIZE
																// / 2),
					(int) (PointList.get(i - 1).getY()), // +
															// MainPanel.HELICOPTER_PAINT_SIZE
															// / 2),
					(int) (PointList.get(i).getX()), // +
														// MainPanel.HELICOPTER_PAINT_SIZE
														// / 2),
					(int) (PointList.get(i).getY())); // +
														// MainPanel.HELICOPTER_PAINT_SIZE
														// / 2));

		}
		/*
		 * Image HeliImg = heli.getImage(); double rotationRequired =
		 * Math.toRadians(45); int locationX = 20; int locationY = 20;
		 * AffineTransform tx = AffineTransform.getRotateInstance(
		 * rotationRequired, locationX, locationY); g2.drawImage(HeliImg, tx,
		 * null);
		 * 
		 * // g2.drawImage(heli.getImage(), heli.getX(), heli.getY(), null);
		 * 
		 * System.out.println(heli.getImage().toString());
		 */
	}

	public void eraseBoard() {
		this.PointList = new ArrayList<Point>();
		repaint();
	}

	public void MoveHeli(int x, int y, String color1) {

		heli.setX(x + 250);
		heli.setY(250 - y);
		Color c;
		System.out.println(color1);
		// heli.setX(x);
		// heli.setY(y);
		if (color1.equals("BLACK")) {
			c = new Color(0, 0, 0);
		} else if (color1.equals("RED")) {
			c = new Color(255, 0, 0);
		} else if (color1.equals("GREEN")) {
			c = new Color(0, 255, 0);
		} else if (color1.equals("BLUE")) {
			c = new Color(0, 0, 255);
		} else {
			c = new Color(255, 255, 255);
		}

		PointList.add(new Point(heli.getX(), heli.getY(), c));

		repaint();
	}

	public void MoveHeliRel(int x, int y, String color1) {
		heli.setXRel(x);
		heli.setYRel(y);
		Color c;
		System.out.println(color1);

		if (color1.equals("BLACK")) {
			c = new Color(0, 0, 0);
		} else if (color1.equals("RED")) {
			c = new Color(255, 0, 0);
		} else if (color1.equals("GREEN")) {
			c = new Color(0, 255, 0);
		} else if (color1.equals("BLUE")) {
			c = new Color(0, 0, 255);
		} else {
			c = new Color(255, 255, 255);
		}

		PointList.add(new Point(heli.getX(), heli.getY(), c));
		repaint();
	}
}
