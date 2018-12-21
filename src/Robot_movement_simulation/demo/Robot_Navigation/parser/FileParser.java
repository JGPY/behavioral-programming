package Robot_movement_simulation.demo.Robot_Navigation.parser;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import Robot_movement_simulation.demo.Robot_Navigation.util.ShapeUtils;
import Robot_movement_simulation.demo.Robot_Navigation.model.Board;
import Robot_movement_simulation.demo.Robot_Navigation.model.Circle;
import Robot_movement_simulation.demo.Robot_Navigation.model.Coordinates;
import Robot_movement_simulation.demo.Robot_Navigation.model.FileDetails;

/**
 * Parses the text file containing start, goal state coordinates and obstacle shape coordinates
 * @author apoorva
 *
 */
public class FileParser {


	public void parseDataFile(FileDetails fileDetail) {
		Board board = Board.getBoardInstance();
		try {

			File file = new File(fileDetail.getFilePath());
			Scanner input = new Scanner(file);
			int count=0;
			int circleCount=0;
			while (input.hasNextLine()) {
				String line = input.nextLine();
				System.out.println(line);
				String[] tokens = line.split("#");
				String last = tokens[tokens.length - 1];
				Coordinates coord ;
				String[] getCoordinates = last.trim().split(" ");
				board.setFileOpen(true);
				if(line.contains("Start")){
					coord = new Coordinates();
					coord.setX(Integer.parseInt(getCoordinates[0]));
					coord.setY(Integer.parseInt(getCoordinates[1]));
					board.setStart(coord);
				}else if( line.contains("Goal")){
					coord = new Coordinates();
					coord.setX(Integer.parseInt(getCoordinates[0]));
					coord.setY(Integer.parseInt(getCoordinates[1]));
					board.setGoal(coord);
				}else if( line.contains("Obstacle")){
					List<Coordinates> obstacleCoordinates = new ArrayList<Coordinates>();
					int i=0;
					count++;
					while(getCoordinates.length > i){
						coord = new Coordinates();
						coord.setX(Integer.parseInt(getCoordinates[i]));
						coord.setY(Integer.parseInt(getCoordinates[i+1]));
						obstacleCoordinates.add(coord);
						i+=2;
					}
					board.setObstacleMap(count,obstacleCoordinates);
				}else if( line.contains("Circle")){
					List<Circle> circularObstacleCoordinates = new ArrayList<Circle>();
					int i=0;
					circleCount++;
					while(getCoordinates.length > i){
						Circle circle= new Circle();
						circle.setX(Integer.parseInt(getCoordinates[i]));
						circle.setY(Integer.parseInt(getCoordinates[i+1]));
						circle.setRadius(Integer.parseInt(getCoordinates[i+2]));
						ShapeUtils.findCenterPoint(circle);
						circularObstacleCoordinates.add(circle);		
						i+=3;
					}
					board.setCircularObstacle(circleCount,circularObstacleCoordinates);					
				}
			}
			input.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
