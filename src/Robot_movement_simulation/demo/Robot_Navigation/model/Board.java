package Robot_movement_simulation.demo.Robot_Navigation.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * POJO containing board details
 * @author apoorva-bhushan
 *
 */
public class Board{

	static Board boardInstance = null;

	public static Board getBoardInstance(){
		if(boardInstance == null) {
			boardInstance = new Board();
		}

		return boardInstance;
	}

	Coordinates goal;
	Coordinates start;
	Map<Integer,List<Coordinates>> obstacleMap;
	Map<Integer,List<lineEquation>> lineEquations;
	Map<Integer,List<Circle>> circularObstacle;
	boolean isIntersect = false;
	boolean isFileOpen = false;


	public Coordinates getGoal() {
		return goal;
	}
	public void setGoal(Coordinates goal) {
		this.goal = goal;
	}
	public Coordinates getStart() {
		return start;
	}
	public void setStart(Coordinates start) {
		this.start = start;
	}
	public Map<Integer, List<Coordinates>> getObstacleMap() {
		return obstacleMap;
	}
	public void setObstacleMap(Integer key, List<Coordinates> value) {
		if(obstacleMap == null)
			obstacleMap= new HashMap<Integer, List<Coordinates>>();

		obstacleMap.put(key, value);
	}

	public void clearObstacleMap() {
		if(obstacleMap!= null)
			obstacleMap = new HashMap<Integer, List<Coordinates>>();
	}

	public Map<Integer, List<Circle>> getCircularObstacle() {
		return circularObstacle;
	}
	public void setCircularObstacle(Integer key,List<Circle>value) {

		if(circularObstacle == null)
			circularObstacle= new HashMap<Integer, List<Circle>>();

		circularObstacle.put(key, value);
	}
	public boolean isFileOpen() {
		return isFileOpen;
	}
	public void setFileOpen(boolean isFileOpen) {
		this.isFileOpen = isFileOpen;
	}
	public boolean isIntersect() {
		return isIntersect;
	}
	public void setIntersect(boolean isIntersect) {
		this.isIntersect = isIntersect;
	}

	/*public void calculateSlope(){

		Iterator<?> it = boardInstance.getObstacleMap().entrySet().iterator();

		while(it.hasNext()){
			Map.Entry<Integer, List<Coordinates>> pair = (Map.Entry)it.next();
			List<Coordinates> coordinateList = pair.getValue();
			int sides =coordinateList.size();
			for(int i=0;i<sides;i++){
				if(i==sides-1){
					//g2d.drawLine(coordinateList.get(i).getX(), coordinateList.get(i).getY(), coordinateList.get(0).getX(), coordinateList.get(0).getY());
					float slope=Math.abs((coordinateList.get(i).getY()-
					lineEquation le=new lineEquation();
					le.setSlope(slope);
					le.setIntercept(intercept);
				}else{
					//g2d.drawLine(coordinateList.get(i).getX(), coordinateList.get(i).getY(), coordinateList.get(i+1).getX(), coordinateList.get(i+1).getY());

				}		
			}
		}


	}*/

	class lineEquation{
		private float slope;
		private float intercept;
		public float getSlope() {
			return slope;
		}
		public void setSlope(float slope) {
			this.slope = slope;
		}
		public float getIntercept() {
			return intercept;
		}
		public void setIntercept(float intercept) {
			this.intercept = intercept;
		}
	}

}
