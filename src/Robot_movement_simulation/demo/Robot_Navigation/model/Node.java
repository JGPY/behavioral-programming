package Robot_movement_simulation.demo.Robot_Navigation.model;

public class Node {
	
	private Coordinates c;
	private double hDash;
	private double pathCost;
	private double h;
	private boolean visited=false;
	
	public Node(Coordinates c,double pc){
		this.c=c;
		this.pathCost=pc;
	}
	
	public double getPathCost() {
		return pathCost;
	}
	public void setPathCost(double pathCost) {
		this.pathCost = pathCost;
		this.hDash=this.pathCost+this.h;
	}
	public double getH() {
		return h;
	}
	public void setH(double h) {
		this.h = h;
		this.hDash=this.pathCost+this.h;
	}
	public double gethDash() {
		return hDash;
	}
	/*public void sethDash(int hDash) {
		this.hDash = hDash;
	}*/
	public Coordinates getC() {
		return c;
	}
	public void setC(Coordinates c) {
		this.c = c;
	}	
	
	public boolean equal(Coordinates c1){
		
		if(c.getX()==c1.getX() && c.getY()==c1.getY()){
			
			return true;
		}
		
		return false;
	}

	public boolean isVisited() {
		return visited;
	}

	public void setVisited(boolean isVisited) {
		this.visited = isVisited;
	}
}
