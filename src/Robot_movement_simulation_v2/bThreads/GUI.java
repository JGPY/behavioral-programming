package Robot_movement_simulation_v2.bThreads;



import Robot_movement_simulation_v2.map.Map;
import Robot_movement_simulation_v2.map.Node;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.ListIterator;

import static Robot_movement_simulation_v2.bThreads.Constants.robotCurrentPosition;
import static Robot_movement_simulation_v2.bThreads.Constants.robotInitialPosition;
import static Robot_movement_simulation_v2.bThreads.Constants.listTrackFoot;


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

		System.out.println("nodeMap.length"+nodeMap.length);
		//修改图




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
					g.fillRect(nodeMap[j][i].getX()*20, nodeMap[j][i].getY()*20, 19, 19);
				}
				//绘制UI轨迹
				if (nodeMap[j][i].getValue()== "*"){
					g.setColor(Color.green);
					g.fillRoundRect(nodeMap[j][i].getX()*20, nodeMap[j][i].getY()*20, 16, 16, 16, 16);
				}
			}
		}



		//修改图 起始点黄色 终点红色
		g.setColor(Color.yellow);
		g.fillRoundRect(robotCurrentPosition.x+20-1, robotCurrentPosition.y+20-1, 17, 17, 17, 17);
		g.setColor(Color.red);
		g.fillRoundRect(24*20-1, 25*20-1, 17, 17, 17, 17);

		//清除旧轨迹
//		g.setColor(Color.white);
//		g.fillRect(11*20, 9*20, 20, 20);
//		g.fillRect(12*20, 10*20, 20, 20);
//		g.fillRect(13*20, 11*20, 20, 20);

//		//动态障碍物
//		g.setColor(Color.blue);
//		g.fillRect(10*20, 8*20, 19, 19);
//
//		//动态轨迹点
//		g.setColor(Color.GRAY);
//		g.fillRoundRect(9*20, 8*20, 16, 16, 16, 16);
//
//		//动态轨迹点
//		g.setColor(Color.GRAY);
//		g.fillRoundRect(9*20, 8*20, 16, 16, 16, 16);
//
//		//静态轨迹点
//		g.setColor(Color.green);
//		g.fillRoundRect(10*20, 9*20, 16, 16, 16, 16);
//		g.fillRoundRect(11*20, 10*20, 16, 16, 16, 16);
//		g.fillRoundRect(12*20, 11*20, 16, 16, 16, 16);
//
//		//动态障碍物
//		g.setColor(Color.blue);
//		g.fillRect(13*20, 12*20, 19, 19);
//		g.fillRect(12*20, 12*20, 19, 19);
//
//		//动态轨迹点
//		g.setColor(Color.GRAY);
//		g.fillRoundRect(13*20, 11*20, 16, 16, 16, 16);

//		nodeMap[4][4].setValue("*");
//		nodeMap[5][3].setValue("*");
//		nodeMap[6][2].setValue("*");
//		nodeMap[7][1].setValue("*");
//		nodeMap[8][1].setValue("*");
//		nodeMap[9][2].setValue("*");


//		//网格线
//		for(int i = 1;i < 30;i++)
//		{
//			g.setStroke( new BasicStroke(1,BasicStroke.CAP_BUTT,BasicStroke.JOIN_BEVEL));
//			g.setColor(Color.black);
//			g.drawLine(5+i*20,20,5+i*20,472);
//			if(i <= 30)
//			{
//				g.drawLine(200,10+i*20,491,10+i*22);
//			}
//		}

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
