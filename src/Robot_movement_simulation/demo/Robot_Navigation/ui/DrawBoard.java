package Robot_movement_simulation.demo.Robot_Navigation.ui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.Arc2D;
import java.awt.geom.Line2D;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import Robot_movement_simulation.demo.Robot_Navigation.model.Board;
import Robot_movement_simulation.demo.Robot_Navigation.model.Coordinates;
import Robot_movement_simulation.demo.Robot_Navigation.util.RobotUtils;
import Robot_movement_simulation.demo.Robot_Navigation.util.ShapeUtils;
import Robot_movement_simulation.demo.Robot_Navigation.application.Navigation;

/**
 * Draw board positions obstacle, start and goal state on UI
 * Obstacle shape is as per user choice - Polygons and circle 
 * @author apoorva
 *
 */
public class DrawBoard extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Image robotImage;
	Board board = Board.getBoardInstance();
	public static Arc2D vision;
	Graphics2D g2d; 
	Graphics gref;
	public static int count=0;

	@Override
	public void paint(Graphics g){


		super.paintComponent( g );
		g2d = (Graphics2D)g.create();
		gref=g;
		loadImage();


		if(board.isFileOpen()){
			g2d.drawImage(robotImage, board.getStart().getX(), board.getStart().getY(), this);
			g2d.drawString("Goal", board.getGoal().getX(), board.getGoal().getY());
			ShapeUtils.drawObstacle(g);
			ShapeUtils.drawCircularObstacle(g);
		
			if(count==0){

				Navigation nv= new Navigation(this);
				nv.SearchPath(board);
				count++;

			}
			if(RobotUtils.getFinalVisitedCoordinates()!=null){
				int i=1;
				for(Coordinates coord:RobotUtils.getFinalVisitedCoordinates()){
					if(i<RobotUtils.getFinalVisitedCoordinates().size()){
						Line2D  line= new Line2D.Double(coord.getX(),coord.getY(), RobotUtils.getFinalVisitedCoordinates().get(i).getX(),RobotUtils.getFinalVisitedCoordinates().get(i).getY());
						g2d.setColor(Color.red);
						g2d.draw(line);
						i++;
					}
				}
			}


		}else{		
			g2d.drawImage(robotImage, 0, 400, this);
		}
		revalidate();
		repaint();
		g2d.dispose();
	} 


	/**
	 * 
	 * @return
	 */
	private Image loadImage(){
		ImageIcon robotIcon = new ImageIcon(this.getClass().getResource("/Robot_movement_simulation/demo/Robot_Navigation/images/Robot_opt.png"));
		return robotImage = robotIcon.getImage(); 
	}

	public void drawLine(Coordinates c1,Coordinates c2){
		//g2d.setColor(Color.red);
		//g2d.drawLine(c1.getX(),c1.getY(), c2.getX(),c2.getY());


		//ShapeUtils.drawObstacle(gref);
		//ShapeUtils.drawCircularObstacle(gref);
		//repaint();
		g2d.setColor(Color.red);
		Line2D  line= new Line2D.Double(c1.getX(),c1.getY(), c2.getX(),c2.getY());
		g2d.draw(line);
		this.getComponentCount();
		//gref.drawLine(c1.getX(),c1.getY(), c2.getX(),c2.getY());
		//g2d.drawImage(robotImage, c2.getX(),c2.getY(), this);
		//g2d.setColor(Color.black);
		//g2d.dispose();
		revalidate();
		repaint();
		//g2d.dispose();
	}
}
