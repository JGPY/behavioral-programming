package robotNavigationSimulation.aStar;

import robotNavigationSimulation.map.Node;

/**
 * A*节点类
 * @author L
 *
 */
public class AStarNode extends Node {

    private String value;  //表示节点的值
    private double FValue = 0; //F值
    private double GValue = 0; //G值
    private double HValue = 0; //H值
    private boolean Reachable; //是否可到达（是否为障碍物）
    private AStarNode PNode;   //父节点

    public AStarNode(int x, int y, String value, boolean reachable) {
        super();
        super.setX(x);
        super.setY(y);
        this.value = value;
        Reachable = reachable;
    }

    public AStarNode() {
        super();
    }

    @Override
    public int getX() {
        return super.getX();
    }

    @Override
    public void setX(int x) {
        super.setX(x);
    }

    @Override
    public int getY() {
        return super.getY();
    }

    @Override
    public void setY(int y) {
        super.setY(y);
    }

    public String getValue() {
        return value;
    }
    public void setValue(String value) {
        this.value = value;
    }
    public double getFValue() {
        return FValue;
    }
    public void setFValue(double fValue) {
        FValue = fValue;
    }
    public double getGValue() {
        return GValue;
    }
    public void setGValue(double gValue) {
        GValue = gValue;
    }
    public double getHValue() {
        return HValue;
    }
    public void setHValue(double hValue) {
        HValue = hValue;
    }
    public boolean isReachable() {
        return Reachable;
    }
    public void setReachable(boolean reachable) {
        Reachable = reachable;
    }
    public AStarNode getPNode() {
        return PNode;
    }
    public void setPNode(AStarNode pNode) {
        PNode = pNode;
    }
}
