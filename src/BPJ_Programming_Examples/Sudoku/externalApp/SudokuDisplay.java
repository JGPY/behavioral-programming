package BPJ_Programming_Examples.Sudoku.externalApp;


import java.awt.*;

import javax.swing.*;

import bpSourceCode.bp.BProgram;

/**
 * Class that implements the display of the game
 */
public class SudokuDisplay {
	private JFrame window = new JFrame("Sudoku");
	public JButton buttons[][] = new JButton[9][];
	public JLabel message = new JLabel();

	/**
	 * Constructor.
	 */

	public SudokuDisplay(BProgram bp) {

		// Create window
		window.setSize(450, 450);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setLayout(new BorderLayout());
		window.setLocation(new Point(400,400)); 

		// The board
		JPanel board = new JPanel();
		board.setLayout(new GridLayout(9, 9));
		// The message label
		message.setHorizontalAlignment(JLabel.CENTER);

		// Create buttons
		for (int i = 0; i < 9; i++) {
			buttons[i] = new JButton[9];
			for (int j = 0; j < 9; j++) {
				buttons[i][j] = new SuButton(i, j);
				board.add(buttons[i][j]);
				//				buttons[i][j].addActionListener(this);
				buttons[i][j].setEnabled(false);
			}
		}

		// Add the board and the message component to the window
		window.add(board, BorderLayout.CENTER);
		window.add(message, BorderLayout.SOUTH);

		// Make the window visible
		window.setVisible(true);
	}

	//	/**
	//	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	//	 */
	//	public void actionPerformed(ActionEvent a) {
	//		final TTTButton btt = ((TTTButton) a.getSource());
	//		BThread sc = new ClickHandler(btt.row,btt.col);
	//		sourceCode.bp.add(sc,20.0);
	//		sc.startBThread();
	//
	//}

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