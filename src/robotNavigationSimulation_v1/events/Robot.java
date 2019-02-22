package robotNavigationSimulation_v1.events;

import bpSourceCode.bp.Event;

import java.util.ArrayList;

public class Robot extends Event {

    /**
     * Initial position of the robot
     */
    private static Node startPosition = new Node(40,40);;

    /**
     * End position of the robot
     */
    private static Node endPosition = new Node(500,500);

    /**
     * Current position of the robot
     */
    private static Node currentPosition = startPosition;

    /**
     * Store robot walking track
     */
    private static ArrayList<Node> listTrackFoot = new ArrayList();

    /**
     * Mobile robot angle
     *
     * Angle range [1, 360]
     * [90, 180, 270, 360] = [East, South, Weat, North]
     */
    private static int angle =0;

    /**
     * Robot movement speed
     */
    private static int robotSpeed =0;

    public Node getStartPosition() {
        return startPosition;
    }

    public void setStartPosition(Node startPosition) {
        Robot.startPosition = startPosition;
    }

    public Node getEndPosition() {
        return endPosition;
    }

    public void setEndPosition(Node endPosition) {
        Robot.endPosition = endPosition;
    }

    public Node getCurrentPosition() {
        return currentPosition;
    }

    public void setCurrentPosition(Node currentPosition) {
        Robot.currentPosition = currentPosition;
    }

    public ArrayList<Node> getListTrackFoot() {
        return listTrackFoot;
    }

    public void setListTrackFoot(ArrayList<Node> listTrackFoot) {
        Robot.listTrackFoot = listTrackFoot;
    }

    public int getAngle() {
        return angle;
    }

    public void setAngle(int angle) {
        Robot.angle = angle;
    }

    public int getRobotSpeed() {
        return robotSpeed;
    }

    public void setRobotSpeed(int robotSpeed) {
        Robot.robotSpeed = robotSpeed;
    }
}
