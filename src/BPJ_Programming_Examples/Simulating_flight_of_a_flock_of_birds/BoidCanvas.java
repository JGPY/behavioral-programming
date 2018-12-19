package BPJ_Programming_Examples.Simulating_flight_of_a_flock_of_birds;


import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JComponent;


@SuppressWarnings("serial")
public class BoidCanvas extends JComponent {

	Flock flock;

	public BoidCanvas() {
		setBackground(Color.WHITE);
		setForeground(Color.BLACK);
		setOpaque(true);
	}

	// Polygon to draw the boid triangle.
	// frequent garbage collection.
	int[] xx = new int[3];
	int[] yy = new int[3];

	public void paintComponent(Graphics gg) {
		Graphics2D g = (Graphics2D) gg;

		g.setColor(getBackground());
		g.fillRect(0, 0, getWidth(), getHeight());
		g.setColor(getForeground());

		for (Boid b : flock) {
			final int x = (int) b.location.x;
			final int y = (int) b.location.y;

			// We have already the speed vector that we can easily use
			// to draw the boid orientation. However we need to scale it
			// to keep the boid size uniform (maybe be interesting to have faster boid bigger?)

			// The lenght that it would be.
			double vr = Math
					.sqrt(b.speed.x * b.speed.x + b.speed.y * b.speed.y);

			// Assume 10 px is ok for the boid wing.
			double scale = 10 / vr;

			// The speed vector, scaled to be easily visible
			int vx = (int) (b.speed.x * scale);
			int vy = (int) (b.speed.y * scale);

			g.setColor(Color.YELLOW);

			xx[0] = x - vy;
			xx[1] = x + vy;
			xx[2] = x + vx;

			yy[0] = y + vx;
			yy[1] = y - vx;
			yy[2] = y + vy;

			g.fillPolygon(xx, yy, 3);
			g.setColor(Color.BLACK);
			g.drawPolygon(xx, yy, 3);
		}
	}

}
