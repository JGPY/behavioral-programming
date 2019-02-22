package robotNavigationSimulation_v1.events;

import bpSourceCode.bp.Event;

/**
 * 节点类 
 * @author L
 * 
 */ 
public class Node extends Event {

  private int x; //x坐标
  private int y; //y坐标 

  public Node(int x, int y) {
    super(); 
    this.x = x; 
    this.y = y;
  } 
   
  public Node() { 
    super(); 
  } 
 
  public int getX() { 
    return x; 
  }

  public void setX(int x) { 
    this.x = x; 
  }

  public int getY() { 
    return y; 
  }

  public void setY(int y) { 
    this.y = y; 
  } 

} 
