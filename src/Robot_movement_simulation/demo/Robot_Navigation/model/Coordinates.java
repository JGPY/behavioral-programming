package Robot_movement_simulation.demo.Robot_Navigation.model;

/**
 * POJO of x-axis and y-axis
 * @author apoorva
 *
 */
public class Coordinates {

	private int x_axis;
	private int y_axis;
	
	public Coordinates(int x,int y){
		this.x_axis=x;
		this.y_axis=y;
		
	}
	public Coordinates(){
		
		
	}
	
	public int getX() {
		return x_axis;
	}
	public void setX(int x) {
		this.x_axis = x;
	}
	public int getY() {
		return y_axis;
	}
	public void setY(int y) {
		this.y_axis = y;
	}
}
