package Robot_movement_simulation.demo.A;

public class Map {
	private Node[][] map;
	//节点数组 
	private Node startNode;
	//起点 
	private Node endNode;
	//终点 
	public Map() {
		map = new Node[7][7];
		for (int i = 0;i<7;i++){
			for (int j = 0;j<7;j++){
				map[i][j] = new Node(i,j,"o",true);
			}
		}
		for (int d = 0;d<7;d++){
			map[0][d].setValue("%");
			map[0][d].setReachable(false);
			map[d][0].setValue("%");
			map[d][0].setReachable(false);
			map[6][d].setValue("%");
			map[6][d].setReachable(false);
			map[d][6].setValue("%");
			map[d][6].setReachable(false);
		}
		map[3][1].setValue("A");
		startNode = map[3][1];
		map[3][5].setValue("B");
		endNode = map[3][5];
		for (int k = 1;k<=3;k++){
			map[k+1][3].setValue("#");
			map[k+1][3].setReachable(false);
		}
	}
	//展示地图
	public void ShowMap(){
		for (int i = 0;i<7;i++){
			for (int j = 0;j<7;j++){
				System.out.print(map[i][j].getValue()+" ");
			}
			System.out.println("");
		}
	}
	public Node[][] getMap() {
		return map;
	}
	public void setMap(Node[][] map) {
		this.map = map;
	}
	public Node getStartNode() {
		return startNode;
	}
	public void setStartNode(Node startNode) {
		this.startNode = startNode;
	}
	public Node getEndNode() {
		return endNode;
	}
	public void setEndNode(Node endNode) {
		this.endNode = endNode;
	}
}
