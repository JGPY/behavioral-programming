package Robot_movement_simulation.demo.Robot_Navigation.model;

/**
 * Enum for Shape type
 * @author apoorva
 *
 */
public enum ShapeType {

	TRIANGLE(1),
	RECTANGLE (2),
	POLYGON(3),
	CIRCLE(4);
	
	private int type;

	private ShapeType(int t) {
		type = t;
	}

	public int getType() {
		return type;
	}
}