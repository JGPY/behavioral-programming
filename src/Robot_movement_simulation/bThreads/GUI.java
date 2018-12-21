package Robot_movement_simulation.bThreads;


import Robot_movement_simulation.map.Map;
import Robot_movement_simulation.map.Node;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.ListIterator;

import static Robot_movement_simulation.bThreads.Constants.robotCurrentPosition;
import static Robot_movement_simulation.bThreads.Constants.robotInitialPosition;
import static Robot_movement_simulation.bThreads.Constants.listTrackFoot;


@SuppressWarnings("serial")
public class GUI extends JComponent {

	public GUI() {
		setBackground(Color.WHITE);
		setForeground(Color.BLACK);
		setOpaque(true);
		listTrackFoot.add(robotCurrentPosition);

	}

	@Override
	public void paintComponent(Graphics gg) {
		Graphics2D g = (Graphics2D) gg;

		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
		g.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL,RenderingHints.VALUE_STROKE_NORMALIZE);

		//初始化地图
		Map map = new Map();
		map.setStartNode(new Node(robotCurrentPosition.x/20, robotCurrentPosition.y/20, "o", true));
		map.setEndNode(new Node(25,25,"o", true));

		//画初始点
		g.setColor(Color.GRAY);
		g.fillRoundRect(robotInitialPosition.x/20, robotInitialPosition.y/20, 20, 20, 20, 20);

		//AStr计算
		AStar AStar =new AStar();
		AStar.search(map);

		//01.获取地图节点数据
		Node[][] nodeMap = map.getMap();

		//02.绘制UI围墙,障碍物和行走轨迹
		for (int i = 0; i < 30; i++){
			for (int j = 0; j < 30; j++){
				//绘制UI围墙
				if (nodeMap[j][i].getValue() == "%"){
					g.setColor(Color.black);
					g.fillRect(nodeMap[j][i].getX()*20, nodeMap[j][i].getY()*20, 20, 20);
				}
				//绘制UI障碍物
				if (nodeMap[j][i].getValue()== "#"){
					g.setColor(Color.black);
					g.fillRect(nodeMap[j][i].getX()*20, nodeMap[j][i].getY()*20, 20, 20);
				}
				//绘制UI轨迹
				if (nodeMap[j][i].getValue()== "*"){
					g.setColor(Color.green);
					g.fillRoundRect(nodeMap[j][i].getX()*20, nodeMap[j][i].getY()*20, 16, 16, 16, 16);
				}
			}
		}

		System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
		//获取 UI点色
//		Robot r = null;
//		try {
//			r = new Robot();
//		} catch (AWTException e) {
//			e.printStackTrace();
//		}
//		Color color = r.getPixelColor(50,50);
//		System.out.println("paintComponent"+color.toString());
	}

}

class Point{
	int x;
	int y;

	public Point(int x0,int y0){
		x = x0;
		y = y0;
	}
}
class Obstacle{
	int x;
	int y;
	int width;
	int height;

	public Obstacle(int x0, int y0, int width0, int height0){
		x = x0;
		y = y0;
		width = width0;
		height = height0;
	}
}
