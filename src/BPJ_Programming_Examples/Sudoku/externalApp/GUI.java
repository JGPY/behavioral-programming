package BPJ_Programming_Examples.Sudoku.externalApp;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import bpSourceCode.bp.BProgram;

/**
 * Class that implements the Graphical User Interface for the game
 */
public class GUI implements ActionListener {
	private JFrame window = new JFrame("Sudoku");
	public JButton buttons[][] = new JButton[9][];
	public JPanel bigbox[][] = new JPanel[3][];
	public JLabel message = new JLabel();
	
	/**
	 * Constructor.
	 */

	public GUI(BProgram bp) {

		// Create window
		window.setSize(450, 450);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setLayout(new BorderLayout());
		// window.setLocation(new Point(400,400)); 

		// The board
		JPanel board = new JPanel();
		board.setLayout(new GridLayout(3, 3));

		// The message label
		message.setHorizontalAlignment(JLabel.CENTER);

	
		for( int k1=0; k1<3; k1++) {
			
			bigbox[k1] = new JPanel[3];
			for( int k2=0; k2<3; k2++) {
				bigbox[k1][k2] = new JPanel();
				
				bigbox[k1][k2].setLayout(new GridLayout(3, 3));
				
				bigbox[k1][k2].setBorder(BorderFactory.createLineBorder(Color.red,5));
				
						
				board.add(bigbox[k1][k2]);
			}
		}
		
		
		// Create buttons
		for (int i = 0; i < 9; i++) {
			buttons[i] = new JButton[9];
			for (int j = 0; j < 9; j++) {
				buttons[i][j] = new SuButton(i, j);
				bigbox[i/3][j/3].add(buttons[i][j]);
				buttons[i][j].addActionListener(this);
			}
		}

		
		// Add the board and the message component to the window
		window.add(board, BorderLayout.CENTER);
		window.add(message, BorderLayout.SOUTH);

		// Make the window visible
		window.setVisible(true);
	}

	/**
	 * @see ActionListener#actionPerformed(ActionEvent)
	 */
	public void actionPerformed(ActionEvent a) {
//		final TTTButton btt = ((TTTButton) a.getSource());
//		BThread sc = new ClickHandler(btt.row,btt.col);
//		sourceCode.bp.add(sc,20.0);
//		sc.startBThread();

}

/**
 * A button that remembers its position on the board
 */
@SuppressWarnings("serial")
class SuButton extends JButton {
	int row;
	int col;

	/**
	 * Constructor.
	 *
	 * @param row
	 *            The row of the button.
	 * @param col
	 *            The column of the button.
	 */
	public SuButton(int row, int col) {
		super();
		this.row = row;
		this.col = col;
	}

}
}