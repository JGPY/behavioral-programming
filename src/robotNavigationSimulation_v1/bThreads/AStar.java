package robotNavigationSimulation_v1.bThreads;

import robotNavigationSimulation_v1.constants.AStarMap;
import robotNavigationSimulation_v1.constants.Constants;
import robotNavigationSimulation_v1.events.AStarNode;
import bpSourceCode.bp.BThread;
import bpSourceCode.bp.exceptions.BPJRequestableSetException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import robotNavigationSimulation_v1.events.AStarRoute;
import robotNavigationSimulation_v1.events.Node;

import java.util.ArrayList;

import static bpSourceCode.bp.BProgram.bp;
import static bpSourceCode.bp.eventSets.EventSetConstants.none;

/**
 *
 * @author L
 */
public class AStar extends BThread {

	private static final Logger logger = LoggerFactory.getLogger(AStar.class);

	/**
	 * 起点
	 */
	private static AStarNode startNode;

	/**
	 * 终点
	 */
	private static AStarNode endNode;

	/** 
   * 使用ArrayList数组作为“开启列表”和“关闭列表” 
   */
	ArrayList<AStarNode> open = new ArrayList<AStarNode>();
	ArrayList<AStarNode> close = new ArrayList<AStarNode>();
	/**
   * 获取H值
   * @param currentNode：当前节点
   * @param endNode：终点
   * @return
   */
	public double getHValue(AStarNode currentNode,AStarNode endNode){
		return (Math.abs(currentNode.getX() - endNode.getX()) + Math.abs(currentNode.getY() - endNode.getY()))*10;
	}
	/**
   * 获取G值
   * @param currentNode：当前节点
   * @return
   */
	public double getGValue(AStarNode currentNode){
		if(currentNode.getPNode()!=null){
			if(currentNode.getX()==currentNode.getPNode().getX()||currentNode.getY()==currentNode.getPNode().getY()){
				//判断当前节点与其父节点之间的位置关系（水平？对角线）
				return currentNode.getGValue()+10;
			}
			return currentNode.getGValue()+14;
		}
		return currentNode.getGValue();
	}
	/**
   * 获取F值 ： G + H
   * @param currentNode
   * @return
   */
	public double getFValue(AStarNode currentNode){
		return currentNode.getGValue()+currentNode.getHValue();
	}
	/**
   * 将选中节点周围的节点添加进“开启列表”
   * @param node
   * @param AStarMap
   */
	public void inOpen(AStarNode node, AStarMap AStarMap){
		//
		int x = node.getX();
		int y = node.getY();
		//
		for (int i = 0; i<3; i++){
			for (int j = 0; j<3; j++){
				//判断条件为：节点为可到达的（即不是障碍物，不在关闭列表中），开启列表中不包含，不是选中节点
				if(AStarMap.getMap()[x-1+i][y-1+j].isReachable()&&!open.contains(AStarMap.getMap()[x-1+i][y-1+j])&&!(x==(x-1+i)&&y==(y-1+j))){
					AStarMap.getMap()[x-1+i][y-1+j].setPNode(AStarMap.getMap()[x][y]);
					//将选中节点作为父节点
					AStarMap.getMap()[x-1+i][y-1+j].setGValue(getGValue(AStarMap.getMap()[x-1+i][y-1+j]));
					AStarMap.getMap()[x-1+i][y-1+j].setHValue(getHValue(AStarMap.getMap()[x-1+i][y-1+j], AStarMap.getEndNode()));
					AStarMap.getMap()[x-1+i][y-1+j].setFValue(getFValue(AStarMap.getMap()[x-1+i][y-1+j]));
					open.add(AStarMap.getMap()[x-1+i][y-1+j]);
				}
			}
		}
	}
	/**
   * 使用冒泡排序将开启列表中的节点按F值从小到大排序
   * @param arr
   */
	public void sort(ArrayList<AStarNode> arr){
		for (int i = 0;i<arr.size()-1;i++){
			for (int j = i+1;j<arr.size();j++){
				if(arr.get(i).getFValue() > arr.get(j).getFValue()){
					AStarNode tmp = arr.get(i);
					arr.set(i, arr.get(j));
					arr.set(j, tmp);
				}
			}
		}
	}
	/**
   * 将节点添加进”关闭列表“
   * @param node
   * @param open
   */
	public void inClose(AStarNode node,ArrayList<AStarNode> open){
		if(open.contains(node)){
			node.setReachable(false);
			//设置为不可达
			open.remove(node);
			close.add(node);
		}
	}
	public synchronized ArrayList<AStarNode> search(AStarMap AStarMap){
		//对起点即起点周围的节点进行操作
		inOpen(AStarMap.getMap()[AStarMap.getStartNode().getX()][AStarMap.getStartNode().getY()], AStarMap);
		close.add(AStarMap.getMap()[AStarMap.getStartNode().getX()][AStarMap.getStartNode().getY()]);
		AStarMap.getMap()[AStarMap.getStartNode().getX()][AStarMap.getStartNode().getY()].setReachable(false);
		AStarMap.getMap()[AStarMap.getStartNode().getX()][AStarMap.getStartNode().getY()].setPNode(AStarMap.getMap()[AStarMap.getStartNode().getX()][AStarMap.getStartNode().getY()]);
		sort(open);

//		//重复步骤
		do{
			inOpen(open.get(0), AStarMap);
			inClose(open.get(0), open);
			sort(open);
		}
		while(!open.contains(AStarMap.getMap()[AStarMap.getEndNode().getX()][AStarMap.getEndNode().getY()]));

		//直到开启列表中包含终点时，循环退出
		inClose(AStarMap.getMap()[AStarMap.getEndNode().getX()][AStarMap.getEndNode().getY()], open);

		//将计算出的路径输入地图
		//showPath(close,map);

		return close;
	}
	/**
   * 将路径标记出来
   * @param arr
   * @param AStarMap
   */
	public void showPath(ArrayList<AStarNode> arr, AStarMap AStarMap) {
		if(arr.size()>0){

			AStarNode node = AStarMap.getMap()[AStarMap.getEndNode().getX()][AStarMap.getEndNode().getY()];

			while(!(node.getX() == AStarMap.getStartNode().getX()&&node.getY() == AStarMap.getStartNode().getY())){
				node.getPNode().setValue("*");
				node = node.getPNode();
			}
		}
		AStarMap.getMap()[AStarMap.getStartNode().getX()][AStarMap.getStartNode().getY()].setValue("A");
	}

	@Override
	public void runBThread() throws InterruptedException, BPJRequestableSetException {
//获得初始位置 startNode，目标位置 endNode
		Node startNode = Constants.robotStartPosition;
		Node endNode = Constants.robotEndPosition;

		//获得栅格化后的地图 AStarMap
		AStarMap AStarMap = new AStarMap();

		//配置起点点和终点
		AStarMap.setStartNode(new AStarNode(startNode.getX()/20, startNode.getY()/20, "o", true));
		AStarMap.setEndNode(new AStarNode(endNode.getX()/20,endNode.getY()/20,"o", true));

		//计算全局路线图
		ArrayList<AStarNode> globalRouteAStar =  new AStar().search(AStarMap);


		logger.info("requesting event:" + "GRoute.class");
		bp.bSync(new AStarRoute(globalRouteAStar), none, none);
		logger.info("requested event:" + "GRoute.class");
	}
}