package project2;

import java.util.Random;

/**********************************************************************
 * This class handles the activities within the game, including game
 * rules and how the game functions.
 *
 * @author Logan Jaglowski
 * @version Winter 2019
 *********************************************************************/

public class MineSweeperGame {

	/** Board represents the board with a 2D array of Cells */
	private Cell[][] board;

	/** Status represents the current status of the game */
	private GameStatus status;

	/** totalMineCount represents the total number of mines */
	private int totalMineCount;

	/** size represents the length and the width of the board */
	private int size;

	/** numOfClicks tracks number of clicks the user has */
	private int numOfClicks;

	/** wins represents the user's total number of wins */
	private int wins = 0;

	/** losses represents the user's total number of losses */
	private int losses = 0;

	/******************************************************************
	 * A constructor that creates a Minesweeper game based on the
	 * number of mines and the size of the board.
	 *
	 * @param size an int that represents the size of the board
	 * @param mines an int that represents the total number of mines
	 *****************************************************************/
	public MineSweeperGame(int size, int mines) {
		this.size = size;
		totalMineCount = mines;
		status = GameStatus.NotOverYet;
		board = new Cell[size][size];
		setEmpty();
	}

	/******************************************************************
	 * A method that makes the board empty and sets all cells to
	 * default settings as well as the number of clicks to 0.
	 *****************************************************************/
	private void setEmpty() {
		for (int row = 0; row < size; row++)
			for (int column = 0; column < size; column++)
				board[row][column] = new Cell(false, false, false, 0);
		numOfClicks = 0;
	}

	/******************************************************************
	 * A method that returns the Cell of a board based on the row and
	 * the column given
	 *
	 * @param row An int that tells us the row of the cell
	 * @param column An int that tells us the column of the cell
	 * @returns the Cell that corresponds with the given row and column
	 *****************************************************************/
	public Cell getCell(int row, int column) {
		return board[row][column];
	}

	/******************************************************************
	 * A method that selects a cell on the board based on the row and
	 * the column given
	 *
	 * @param row An int that tells us the row of the cell
	 * @param column An int that tells us the column of the cell
	 *****************************************************************/
	public void select(int row, int column) {

		// Returns nothing if the cell is flagged
		if (board[row][column].isFlagged()) {
			return;
		}

		// Lays the mines down if it's the first click, and does not
		// allow for mines to be laid in the first cell clicked.
		if (numOfClicks == 0) {
			board[row][column].setExposed(true);
			layMines(totalMineCount);
			board[row][column].setExposed(false);
			numOfClicks++;
		}

		// If the mine count's greater than 0 and the cell doesn't have
		// a mine, exposes that lone cell.
		if (board[row][column].getMineCount() > 0 &&
				!board[row][column].isMine()) {
			board[row][column].setExposed(true);
		}

		// If the cell has a mine count of 0, expose that zero cell,
		// the surrounding zero cells, and other cells around those
		// zeroes
		if (board[row][column].getMineCount() == 0 &&
				!board[row][column].isMine()) {
//				recursiveExposeZeroes(row, column);
				nonrecursiveExposeZeroes(row, column);
		}

		// If the cell is a mine, expose it.
		if (this.board[row][column].isMine()) {
			board[row][column].setExposed(true);
		}

		// Updates the status of the game
		status = checkGameStatus(row, column);

	}

	/******************************************************************
	 * A method that checks if the user has lost, won, or if the game
	 * is still ongoing.
	 *
	 * @param row An int that tells us the row of the cell
	 * @param column An int that tells us the column of the cell
	 * @returns the status of the game based on the user's click
	 *****************************************************************/
	public GameStatus checkGameStatus(int row, int column) {

		// If the user clicks a mine, the status changes to lost and
		// other mines on the board are also exposed if not flagged.
		// A loss is also added to the user's loss total.
		if (board[row][column].isMine()) {
			losses++;
			for (int rows = 0; rows < size; rows++)
				for (int columns = 0; columns < size; columns++)
					if (getCell(rows, columns).isMine() &&
							!getCell(rows, columns).isFlagged())
						getCell(rows,columns).setExposed(true);
			return GameStatus.Lost;
		}

		// If the user still has non-mines that they need to expose,
		// the status remains as not over.
		for (int rows = 0; rows < size; rows++) {
			for (int col = 0; col < size; col++) {
				if (!this.board[rows][col].isMine() &&
						!this.board[rows][col].isExposed()) {
					return GameStatus.NotOverYet;
				}
			}
		}

		// If the user has exposed all non-mines, the status is changed
		// to a win, and mines on the board are automatically flagged
		// for the user. A win is also added to the user's win total.
		wins++;
		for (int rows = 0; rows < size; rows++)
			for (int columns = 0; columns < size; columns++)
				if (getCell(rows, columns).isMine())
					getCell(rows,columns).setFlagged(true);
		return GameStatus.WON;
	}

	/******************************************************************
	 * A method that returns the status of the game.
	 *
	 * @returns the status of the current game.
	 *****************************************************************/
	public GameStatus getGameStatus() {
		return status;
	}

	/******************************************************************
	 * A method that resets the game. The status is changed to not over
	 * and the board is emptied.
	 *****************************************************************/
	public void reset() {
		status = GameStatus.NotOverYet;
		setEmpty();
	}

	/******************************************************************
	 * A method that lays the mines down on the board.
	 *
	 * @param mineCount an int that represents the total number of
	 *                  mines on the board.
	 *****************************************************************/
	private void layMines(int mineCount) {

		// Method starts with 0 mines placed, and places mines using
		// random integers within the bounds of the board until all
		// mines are placed.
		int minesPlaced = 0;
		Random random = new Random();
		while (minesPlaced < mineCount) {
			int column = random.nextInt(size);
			int row = random.nextInt(size);

			// Checks to make sure spot is not exposed and is not
			// already a mine, then sets as a mine
			if (!board[row][column].isMine() &&
					!board[row][column].isExposed()) {
				board[row][column].setMine(true);

				// Increases neighbor's mineCount if that cell is
				// not already a mine
				for (int neighborRow = row - 1; neighborRow < row + 2;
					 neighborRow++)
					for (int neighborColumn = column - 1;
						 neighborColumn < column + 2; neighborColumn++)
						if (neighborRow >= 0 && neighborRow < size &&
								neighborColumn >= 0 && neighborColumn
									< size)
							if (!board[neighborRow]
									[neighborColumn].isMine())
								board[neighborRow][neighborColumn]
										.incrementMineCount();

				//increments the mines placed
				minesPlaced++;
			}
		}
	}

	/******************************************************************
	 * A method that returns the user's total number of wins
	 *
	 * @returns the user's total number of wins
	 *****************************************************************/
	public int getWins() {
		return wins;
	}

	/******************************************************************
	 * A method that returns the user's total number of losses
	 *
	 * @returns the user's total number of losses
	 *****************************************************************/
	public int getLosses() {
		return losses;
	}

	/******************************************************************
	 * A method that recursively exposes non-exposed cells that touch
	 * exposed cells that have a mine count of 0.
	 *
	 * @param rows An int that tells us the row of the cell
	 * @param columns An int that tells us the column of the cell
	 *****************************************************************/
	public void recursiveExposeZeroes(int rows, int columns) {

		// Returns nothing if the row or column is out of bounds.
		if (rows < 0 || rows > size - 1 || columns < 0 ||
				columns > size - 1)
			return;

		// If the cell's mine count is 0 and it is not exposed
		// or flagged, then that cell will be exposed.
		else if (board[rows][columns].getMineCount() == 0 &&
				!board[rows][columns].isExposed() &&
				!board[rows][columns].isFlagged()) {
			board[rows][columns].setExposed(true);

			// Neighbor cells to the original cell will also be called
			// to the method, and this method continues recursively
			// until it reaches a non-zero mine count or the edge of
			// the board.
			recursiveExposeZeroes(rows + 1, columns - 1);
			recursiveExposeZeroes(rows + 1, columns);
			recursiveExposeZeroes(rows + 1, columns + 1);
			recursiveExposeZeroes(rows - 1, columns - 1);
			recursiveExposeZeroes(rows - 1, columns);
			recursiveExposeZeroes(rows - 1, columns + 1);
			recursiveExposeZeroes(rows, columns - 1);
			recursiveExposeZeroes(rows, columns + 1);

		// If the cell is not exposed or flagged, but the mine count is
		// over	zero, then the recursion will stop and the cell will be
		// exposed.
		} else if (board[rows][columns].getMineCount() > 0 &&
				!board[rows][columns].isExposed() &&
				!board[rows][columns].isFlagged()) {
			board[rows][columns].setExposed(true);
			return;
		}
	}

	/******************************************************************
	 * A method that non-recursively exposes the zeroes from the board
	 *
	 * @param row An int that tells us the row of the cell
	 * @param column An int that tells us the column of the cell
	 *****************************************************************/
	public void nonrecursiveExposeZeroes(int row, int column){

		board[row][column].setExposed(true);

		// This calls for the exposeZeroes method in each cell as many
		// times as how large the board is. This is to ensure all
		// zeroes are exposed in any situation.
		for (int totalSize = 0; totalSize < size; totalSize++)
			for (int rows = 0; rows < size; rows++)
				for(int columns = 0; columns < size; columns++)
					exposeZeroes(rows, columns);

		// This exposes all the cells with a mine count of one or more
		// that is next to one of the exposed cells with a zero mine
		// count.
		for (int rows = 0; rows < size; rows++)
			for (int columns = 0; columns < size; columns++)
				exposeOneOrMore(rows, columns);
	}

	/******************************************************************
	 * A method that exposes a cell with a zero mine count that is next
	 * to an exposed cell with a zero mine count.
	 *
	 * @param row An int that tells us the row of the cell
	 * @param column An int that tells us the column of the cell
	 *****************************************************************/
	public void exposeZeroes(int row, int column) {

		// If the non-exposed zero mine count cell is next to an
		// exposed zero mine count cell and is not flagged, then that
		// cell will be exposed.
		if (isNeighborToExposedZero(row, column))
			if (board[row][column].getMineCount() == 0)
				if (!board[row][column].isExposed())
					if (!board[row][column].isFlagged())
						board[row][column].setExposed(true);
	}

	/******************************************************************
	 * A method that exposes a cell with a mine count more than zero
	 * that is next to an exposed cell with a zero mine count.
	 *
	 * @param row An int that tells us the row of the cell
	 * @param column An int that tells us the column of the cell
	 *****************************************************************/
	public void exposeOneOrMore(int row, int column) {

		// If the non-exposed cell with a mine count more than zero is
		// next to an exposed zero mine count cell and is not flagged,
		// then that cell will be exposed.
		if (isNeighborToExposedZero(row, column))
			if (board[row][column].getMineCount() > 0)
				if (!board[row][column].isExposed())
					if (!board[row][column].isFlagged())
						board[row][column].setExposed(true);
	}

	/******************************************************************
	 * A method that checks to see if the current cell is next to an
	 * exposed cell with a mine count of zero.
	 *
	 * @param row An int that tells us the row of the cell
	 * @param column An int that tells us the column of the cell
	 *****************************************************************/
	public boolean isNeighborToExposedZero(int row, int column) {

		// Checks to see if the cell up and to left is an exposed zero
		// mine count cell.
		if (row - 1 >= 0 && column - 1 >= 0)
			if (board[row - 1][column - 1].isExposed())
				if (!board[row - 1][column - 1].isMine())
					if (board[row - 1][column - 1].getMineCount() == 0)
						return true;

		// Checks to see if the cell up is an exposed zero mine count
		// cell.
		if (row - 1 >= 0 && row - 1 < size && column >= 0 &&
				column < size)
			if (board[row - 1][column].isExposed())
					if (!board[row - 1][column].isMine())
						if (board[row - 1][column].getMineCount() == 0)
							return true;

		// Checks to see if the cell up and to right is an exposed zero
		// mine count cell.
		if (row - 1 >= 0 && row - 1 < size && column + 1 >= 0 &&
				column + 1 < size)
			if (board[row - 1][column + 1].isExposed())
					if (!board[row - 1][column + 1].isMine())
						if (board[row - 1][column + 1]
								.getMineCount() == 0)
							return true;

		// Checks to see if the cell to the left is an exposed zero
		// mine count cell.
		if (row >= 0 && row < size && column - 1 >= 0 &&
				column - 1 < size)
			if (board[row][column - 1].isExposed())
					if (!board[row][column - 1].isMine())
						if (board[row][column - 1].getMineCount() == 0)
							return true;

		// Checks to see if the cell to the right is an exposed zero
		// mine count cell.
		if (row >= 0 && row < size && column + 1 >= 0 &&
				column + 1 < size)
			if (board[row][column + 1].isExposed())
					if (!board[row][column + 1].isMine())
						if (board[row][column + 1].getMineCount() == 0)
							return true;

		// Checks to see if the cell down and to left is an exposed
		// zero mine count cell.
		if (row + 1 >= 0 && row + 1 < size && column + 1 >= 0 &&
				column + 1 < size)
			if (board[row + 1][column + 1].isExposed())
					if (!board[row + 1][column + 1].isMine())
						if (board[row + 1][column + 1]
								.getMineCount() == 0)
							return true;

		// Checks to see if the cell down is an exposed zero mine count
		// cell.
		if (row + 1 >= 0 && row + 1 < size && column >= 0 &&
				column < size)
			if (board[row + 1][column].isExposed())
					if (!board[row + 1][column].isMine())
						if (board[row + 1][column].getMineCount() == 0)
							return true;

		// Checks to see if the cell down and to right is an exposed
		// zero mine count cell.
		if (row + 1 >= 0 && row + 1 < size && column - 1 >= 0 &&
				column - 1 < size)
			if (board[row + 1][column - 1].isExposed())
					if (!board[row + 1][column - 1].isMine())
						if (board[row + 1][column - 1]
								.getMineCount() == 0)
							return true;

		// If the cell has no exposed zero mine count neighbors, it
		// returns false
		return false;
	}
}