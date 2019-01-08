package robotNavigationSimulation.UI;

import robotNavigationSimulation.aStar.AStarMap;
import robotNavigationSimulation.constants.Constants;
import robotNavigationSimulation.aStar.AStarNode;
import robotNavigationSimulation.map.Node;

import javax.swing.*;
import java.awt.*;

import static Robot_movement_simulation.bThreads.Constants.listTrackFoot;
import static Robot_movement_simulation.bThreads.Constants.robotCurrentPosition;


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

    g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    g.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_NORMALIZE);


    if (!Constants.uiMap) {
      Constants.uiMap =true;
      // 01.获取地图节点数据
      AStarNode[][] nodeMap = new AStarMap().getMap();

      // 02.绘制围墙和静态障碍物
      for (int i = 0; i < 30; i++) {
        for (int j = 0; j < 30; j++) {
          // 绘制UI围墙
          if (nodeMap[j][i].getValue() == "%") {
            g.setColor(Color.black);
            g.fillRect(nodeMap[j][i].getX() * 20, nodeMap[j][i].getY() * 20, 20, 20);
          }
          // 绘制UI障碍物
          if (nodeMap[j][i].getValue() == "#") {
            g.setColor(Color.black);
            g.fillRect(nodeMap[j][i].getX() * 20, nodeMap[j][i].getY() * 20, 19, 19);
          }
        }
      }


    }




    synchronized (this) {

      //绘制行走轨迹
      g.setColor(Color.green);
      for (Node node : Constants.movingTrail) {
        g.fillRoundRect((node.getX()-1)*20, (node.getY()-1)*20, 16, 16, 16, 16);
      }

      // 修改图 起始点黄色 终点红色
      g.setColor(Color.yellow);
      g.fillRoundRect(Constants.robotStartPosition.getX() , Constants.robotStartPosition.getY(), 17, 17, 17, 17);

      g.setColor(Color.red);
      g.fillRoundRect(Constants.robotEndPosition.getX(), Constants.robotEndPosition.getY(), 17, 17, 17, 17);

    }



  }

}

