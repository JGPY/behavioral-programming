package Robot_movement_simulation.demo.Robot_Navigation.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import Robot_movement_simulation.demo.Robot_Navigation.model.Board;
import Robot_movement_simulation.demo.Robot_Navigation.model.FileDetails;
import Robot_movement_simulation.demo.Robot_Navigation.parser.FileParser;
import Robot_movement_simulation.demo.Robot_Navigation.util.RobotUtils;

/**
 * Creates GUI for Application
 * @author apoorva
 *
 */
public class AppGUI {

	/**
	 * Create a menu bar
	 * @param mainFrame
	 * @return
	 */
	public static JMenuBar addMenuBar(final JFrame mainFrame){

		JMenuBar menuBar = new JMenuBar();
		JMenu fileMenu = new JMenu("File");
		menuBar.add(fileMenu);

		final FileParser parseFile = new FileParser();
		// Create and add simple menu item to one of the drop down menu
		JMenuItem newAction = new JMenuItem("New");
		JMenuItem openAction = new JMenuItem("Open");
		JMenuItem exitAction = new JMenuItem("Exit");

		fileMenu.add(newAction);
		fileMenu.add(openAction);
		fileMenu.addSeparator();
		fileMenu.add(exitAction);

		final Board b = Board.getBoardInstance();
		newAction.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				System.out.println("New....");
				//Clean the board
				b.setFileOpen(false);
				new DrawBoard();

			}
		});
		final DrawBoard db=new DrawBoard();
		openAction.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String extType[] = { "txt" };
				JFileChooser chooser = new JFileChooser();
				JFrame frame = new JFrame();
				FileFilter filter = new FileNameExtensionFilter("Text File",	extType);
				chooser.setFileFilter(filter);
				chooser.setDialogTitle("Opens Text File");

				int returnVal = chooser.showOpenDialog(frame);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					//b.setFileOpen(false);
					System.out.println("You chose to open this file: "+ chooser.getSelectedFile().getName());
					FileDetails fileDetail = new FileDetails();
					fileDetail.setFileName(chooser.getSelectedFile().getName());
					fileDetail.setFilePath(chooser.getSelectedFile().getAbsolutePath());
					
//				
					if (chooser.getSelectedFile() != null && parseFile != null) {
						RobotUtils.getVisitedCoordinates().clear();
						RobotUtils.getFinalVisitedCoordinates().clear();
						RobotUtils.getVisitedNodes().clear();
						
						b.clearObstacleMap();
				
						
						DrawBoard.count=0;
						

						
						parseFile.parseDataFile(fileDetail);
						mainFrame.add(db);
					}
			
				}						

			}
		});


		exitAction.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});

		return menuBar;

	}
}