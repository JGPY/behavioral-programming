package BPJ_Programming_Examples.Simulating_flight_of_a_flock_of_birds;

import bpSourceCode.bApplication.BApplication;
import bpSourceCode.bp.BProgram;
import bpSourceCode.bp.BThread;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import static bpSourceCode.bp.BProgram.bp;

@SuppressWarnings("serial")
public class BoidApplet extends JApplet implements ActionListener,BApplication {

	/**
	 * The canvas where to draw the boids.
	 */
	BoidCanvas canvas;

	/**
	 * The BPJ_Programming_Examples.flock of boids
	 */
	Flock flock;

	//int step = 10;

	Timer timer = new Timer(Constants.sleep, this);

	/**
	 * Self tuning step.
	 */

	@Override
	public void init() {

		this.setSize(1300,650);

		canvas = new BoidCanvas();
		flock = new Flock();
		canvas.flock = flock;

		for (int n = 0; n < Constants.count; n++) { //小鸟数量
			Boid boid = new Boid(n);
			boid.flock = flock;
			flock.add(boid);
			bp.add(boid, (double)n);
			bp.add(boid.match, ((double)n)/10000.0+0.01); //匹配速度
			bp.add(boid.keep, ((double)n)/10000.0+0.02);
			bp.add(boid.away, ((double)n)/10000.0+0.03);
			bp.add(boid.towards, ((double)n)/10000.0+0.04);

			bp.add(boid.softUpper, ((double)n)/10000.0+0.05);
			bp.add(boid.softLower, ((double)n)/10000.0+0.06);
			bp.add(boid.softLeft, ((double)n)/10000.0+0.07);
			bp.add(boid.softRight, ((double)n)/10000.0+0.08);

			bp.add(boid.hardUpper, ((double)n)/10000.0+0.09);
			bp.add(boid.hardLower, ((double)n)/10000.0+0.1);
			bp.add(boid.hardLeft, ((double)n)/10000.0+0.11);
			bp.add(boid.hardRight, ((double)n)/10000.0+0.12);

			bp.add(boid.checkUpper, ((double)n)/10000.0+0.13);
			bp.add(boid.checkLower, ((double)n)/10000.0+0.14);
			bp.add(boid.checkLeft, ((double)n)/10000.0+0.15);
			bp.add(boid.checkRight, ((double)n)/10000.0+0.16);

			for(int i = 0; i < Constants.count; i++) {
				if(i != n) {
					bp.add(boid.awayArray[i], ((double)n)/1000.0 + ((double)i)/100000.0 + 0.2);
					if(i<n) {
						bp.add(new CheckCollision(boid, flock.get(i)), ((double)n)/1000.0 + ((double)i)/100000.0 + 0.3);
					}
				}
			}
			
		}
		
		bp.add(new BlockAwayUntilPress(), 20.0);

		add(canvas, BorderLayout.CENTER);
		timer.setRepeats(true);
		timer.setCoalesce(true);
		timer.start();

		canvas.addMouseListener(new MouseAdapter() {

			@Override
			public void mousePressed(MouseEvent e) {
				BThread sc = new MousePressed();
				bp.add(sc,51.0);
				sc.startBThread();
				if (e.getButton() == MouseEvent.BUTTON3){
					sc = new NotCooperative();
					bp.add(sc,53.0);
					sc.startBThread();
				}
				else {
					sc = new Scared();
					bp.add(sc,54.0);
					sc.startBThread();
				}
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				BThread sc = new MouseReleased();
				bp.add(sc,52.0);
				sc.startBThread();
			}
		});
		try {

			BProgram.startBApplication(BoidApplet.class, "Flock");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

	@Override
	public void runBApplication() {
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		
		bp.startAll();
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		// Flock space will resize automatically when resizing the applet.
		flock.size.height = getHeight();
		flock.size.width = getWidth();

		canvas.repaint();
	}
}
