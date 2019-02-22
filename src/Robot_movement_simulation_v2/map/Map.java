package Robot_movement_simulation_v2.map;


import Robot_movement_simulation_v2.map.Node;

public class Map {

	/**
	 * 节点数组
	 */
	private static Node[][] map;

	/**
	 * 起点
	 */
	private static Node startNode;

	/**
	 * 终点
	 */
	private static Node endNode;

	public Map() {
		map = new Node[60][60];

		for (int i = 0; i < 60; i++){
			for (int j = 0;j < 60; j++){
				map[i][j] = new Node(i,j,"o",true);
			}
		}

		//填充围墙
		for (int d = 0; d < 60; d++){
			map[d][0].setValue("%");
			map[d][0].setReachable(false);
			map[d][59].setValue("%");
			map[d][59].setReachable(false);

			map[0][d].setValue("%");
			map[0][d].setReachable(false);
			map[59][d].setValue("%");
			map[59][d].setReachable(false);


		}


		//填充障碍物
		for (int k = 2;k<=13;k++){
			map[k][10].setValue("#");
			map[k][10].setReachable(false);
		}
		for (int k = 2;k<=13;k++){
			map[14][k+2].setValue("#");
			map[14][k+2].setReachable(false);
		}


		for (int k = 2;k<=15;k++){
			map[36][k].setValue("#");
			map[20][k].setReachable(false);
		}
		for (int k = 2;k<=15;k++){
			map[k+14][16].setValue("#");
			map[k+14][16].setReachable(false);
		}


		for (int k = 2;k<=18;k++){
			map[20][13+k].setValue("#");
			map[20][13+k].setReachable(false);
		}
		for (int k = 2;k<=18;k++){
			map[9+k][28].setValue("#");
			map[9+k][28].setReachable(false);
		}
		for (int k = 2;k<=18;k++){
			map[28][13+k].setValue("#");
			map[28][13+k].setReachable(false);
		}
		for (int k = 2;k<=18;k++){
			map[9+k][34].setValue("#");
			map[9+k][34].setReachable(false);
		}


		for (int k = 2;k<=9;k++){
			map[38][12+k].setValue("#");
			map[38][12+k].setReachable(false);
		}
		for (int k = 2;k<=9;k++){
			map[19+k][32].setValue("#");
			map[19+k][32].setReachable(false);
		}



		//填写路径
//		map[4][4].setValue("*");
//		map[5][4].setValue("*");
//		map[6][3].setValue("*");
//		map[6][2].setValue("*");
//		map[7][1].setValue("*");
//		map[8][1].setValue("*");
//		map[9][2].setValue("*");
//		map[10][3].setValue("*");
//		map[11][4].setValue("*");
//		map[12][5].setValue("*");
//		map[13][6].setValue("*");
//		map[14][7].setValue("*");
//		map[15][7].setValue("*");
//		map[16][6].setValue("*");
//		map[17][5].setValue("*");
//		map[17][5].setValue("*");
//		map[17][4].setValue("*");
//		map[16][3].setValue("*");
//		map[15][2].setValue("*");

	}
	//终端 展示地图
	public void ShowMap(){
		for (int i = 0;i<60;i++){
			for (int j = 0;j<60;j++){
				System.out.print(map[j][i].getValue()+" ");
			}
			System.out.println("");
		}
	}
	public Node[][] getMap() {
		return map;
	}
	public void setMap(Node[][] map) {
		Map.map = map;
	}
	public Node getStartNode() {
		return startNode;
	}
	public void setStartNode(Node startNode) {
		Map.startNode = startNode;
	}
	public Node getEndNode() {
		return endNode;
	}
	public void setEndNode(Node endNode) {
		Map.endNode = endNode;
	}


	public static void main(String[] args){
		//测试地图
		new Map().ShowMap();
	}
}
