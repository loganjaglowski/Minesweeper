package project2;


import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**********************************************************************
 * This class provides the layout of Minesweeper, including the board,
 * buttons, and labels that are seen by the user.
 *
 * @author Logan Jaglowski
 * @version Winter 2019
 *********************************************************************/

public class MineSweeperPanel extends JPanel {

	/** Label that displays user's number of wins */
	private JLabel winsLabel;

	/** Label that displays user's number of losses */
	private JLabel lossesLabel;

	/** 2D array of buttons that displays the board of the game */
	private JButton[][] board;

	/** Button that allows the user to quit */
	private JButton quitButton;

	/** Cell that will be used to track which cell is focused on */
	private Cell iCell;

	/** The current Minesweeper game that is going on */
	private MineSweeperGame game;

	/** The size of the board */
	private int size;

	/** The user's number of wins */
	private int wins;

	/** The user's number of losses */
	private int losses;

	/******************************************************************
	 * A constructor that creates the layout of the Minesweeper game
	 * based on the number of mines and the size of the board.
	 *
	 * @param size an int that represents the size of the board
	 * @param mines an int that represents the total number of mines
	 *****************************************************************/
	public MineSweeperPanel(int size, int mines) {

		// Instantiate the JPanels and set this.size to size
		this.size = size;
		JPanel bottom = new JPanel();
		JPanel center = new JPanel();

		// Instantiate the listeners
		ButtonListener listener = new ButtonListener();
		MyMouseListener mouseListener = new MyMouseListener();

		// Create the board with the buttons as cells
		center.setLayout(new GridLayout(size, size));
		board = new JButton[size][size];
		for (int row = 0; row < size; row++)
			for (int col = 0; col < size; col++) {
				board[row][col] = new JButton("");
				board[row][col].addMouseListener(mouseListener);
				board[row][col].setPreferredSize(new Dimension
						(41, 20));
				center.add(board[row][col]);
			}

		// Creates the quit button and adds it onto JPanel
		quitButton = new JButton("Quit");
		quitButton.addActionListener(listener);
		bottom.add(quitButton);

		// Creates the win and loss labels and adds them onto JPanel
		winsLabel = new JLabel("Wins: " + wins);
		bottom.add(winsLabel);
		lossesLabel = new JLabel("Losses: " + losses);
		bottom.add(lossesLabel);

		// Creates a new Minesweeper game and displays the board
		game = new MineSweeperGame(size, mines);
		displayBoard();

		// Sets the grid layout
		bottom.setLayout(new GridLayout(3, 2));

		// add all to contentPane
		add(new JLabel("!!!!!!  Mine Sweeper  !!!!"),
				BorderLayout.NORTH);
		add(center, BorderLayout.CENTER);
		add(bottom, BorderLayout.SOUTH);

	}

	/******************************************************************
	 * Method that displays the many aspects of the board to the user.
	 *****************************************************************/
	private void displayBoard() {

		// Sets the user wins and loss labels to the correct text.
		winsLabel.setText("Wins: " + game.getWins());
		lossesLabel.setText("Losses " + game.getLosses());


		for (int row = 0; row < this.size; row++)
			for (int column = 0; column < this.size; column++) {
				iCell = game.getCell(row, column);

				// Sets each cell's background and text to nothing
				board[row][column].setBackground(null);
				board[row][column].setText("");

				// 	Sets all mine's text to "!" for the user to see
				if (iCell.isMine())
					board[row][column].setText("!");

				// If the cell is not exposed, the user can click it
				// If the cell is exposed, the user cannot click it
				if (iCell.isExposed())
					board[row][column].setEnabled(false);
				else
					board[row][column].setEnabled(true);

				// If the cell is exposed and is not a mine, the cell
				// shows the cell's mine count.
				if (iCell.isExposed())
					if (!iCell.isMine()) {
						board[row][column].setText("" +
								iCell.getMineCount());

				// If the cell is exposed and is a mine, then the cell
				// is exposed and the background turns red.
					} else {
						board[row][column].setBackground(Color.red);
						iCell.setExposed(true);
					}

				// If the cell is flagged, the text on the cell is "F"
				if (iCell.isFlagged())
					board[row][column].setText("F");
			}
	}

	/******************************************************************
	 * Method that checks to see if a button is clicked
	 *****************************************************************/
	private class ButtonListener implements ActionListener {

		public void actionPerformed(ActionEvent event) {

			// If the quit button is clicked, the program is exited.
			if (event.getSource() == quitButton)
				System.exit(0);

		}

	}

	/******************************************************************
	 * Method that checks to see which mouse button is clicked on a
	 * specific source.
	 *****************************************************************/
	private class MyMouseListener implements MouseListener {
		public void mouseClicked(MouseEvent event) { }
		public void mouseEntered(MouseEvent event) { }
		public void mouseExited(MouseEvent event) { }
		public void mousePressed(MouseEvent event) { }

		@Override
		public void mouseReleased(MouseEvent event) {

			// If the user left clicks on a cell, that button is then
			// selected.
			if (event.getButton() == MouseEvent.BUTTON1) {
				for (int row = 0; row < size; row++)
					for (int column = 0; column < size; column++)
						if (board[row][column] == event.getSource())
							game.select(row, column);

			// If the user right clicks on a cell, that button is then
			// flagged if not flagged already. If flagged already, the
			// cell is then unflagged.
			} else if (event.getButton() == MouseEvent.BUTTON3) {
				for (int row = 0; row < size; row++)
					for (int column = 0; column < size; column++)
						if (board[row][column] == event.getSource())
							if (game.getCell(row, column).isFlagged())
								game.getCell(row, column).
										setFlagged(false);
							else
								game.getCell(row,column).
										setFlagged(true);
			}

			// The board is displayed after the button click.
			displayBoard();

			// If the game is lost, a message pops up telling the user
			// they lost. The board is then reset, and a new board is
			// created and displayed.
			if (game.getGameStatus() == GameStatus.Lost) {
				displayBoard();
				JOptionPane.showMessageDialog(null,
						"You Lose \n The game will reset");
				game.reset();
				displayBoard();

			}

			// If the game is won, a message pops up telling the user
			// they won. The board is then reset, and a new board is
			// created and displayed.
			if (game.getGameStatus() == GameStatus.WON) {
				JOptionPane.showMessageDialog(null,
						"You Win: all mines have been found!" +
								"\n The game will reset");
				game.reset();
				displayBoard();
			}
		}
	}

}