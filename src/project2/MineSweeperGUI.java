package project2;

import java.lang.*;
import javax.swing.*;

/**********************************************************************
 * This class provides the Graphic User Interface for the game
 * Minesweeper.
 *
 * @author Logan Jaglowski
 * @version Winter 2019
 *********************************************************************/

public class MineSweeperGUI {

	/******************************************************************
	 * A method that acts as the GUI for the minesweeper game
	 *
	 * @param args An argument of type String array.
	 *****************************************************************/
	public static void main(String[] args) {

		// Creates that JFrame for the Minesweeper game.
		JFrame frame = new JFrame("MineSweeper");

		// Allows for a way for the user to close.
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Sets all int's as zeroes before user inputs data to fit
		// the needed criteria.
		int size = 0;
		int mines = 0;
		int otherThanDigitCount = 0;

		// Checks to see if it's a valid size.
		while (size < 3 || size > 30) {

			// Displays an input dialog that requests the user for a
			// valid size
			String sizeString = JOptionPane.
					showInputDialog(null,
							"Enter the size of the board between " +
									"3 and 30: ", "10");

			// If the user presses cancel or the X button, the program
			// closes
			if (sizeString == null)
				System.exit(0);

			// If the user adds in nothing, the program requests the
			// user to input a valid number.
			else if (sizeString.equals("")) {
				size = 0;
				int warning = JOptionPane.showConfirmDialog(null,
						"Please enter a valid number between 3 " +
								"and 30.");

				// If the user answers any of the no options for the
				// warning, the program is exited.
				if (warning == JOptionPane.NO_OPTION ||
						warning == JOptionPane.CANCEL_OPTION ||
						warning == JOptionPane.CLOSED_OPTION)
					System.exit(0);
			} else {

				// Checks all characters in string to make sure they
				// are all digits
				otherThanDigitCount = 0;
				for (int digitChecker = 0; digitChecker <
						sizeString.length(); digitChecker++)
					if (!Character.isDigit(sizeString.
							charAt(digitChecker)))
						otherThanDigitCount++;

				if (otherThanDigitCount == 0)

					// If user inputs all digits but they're above or
					// below valid size range, the user is warned and
					// is requested to choose another number.
					if (Integer.parseInt(sizeString) < 3 ||
							Integer.parseInt(sizeString) > 30) {
						size = 0;
						int warning = JOptionPane.showConfirmDialog
								(null,
								"Please enter a valid number " +
										"between 3 and 30.");

						// If the user answers any of the no options
						// for the warning, the program is exited.
						if (warning == JOptionPane.NO_OPTION ||
								warning == JOptionPane.CANCEL_OPTION ||
								warning == JOptionPane.CLOSED_OPTION)
							System.exit(0);
					} else

						// If the user enters a valid size, it is
						// accepted.
						size = Integer.parseInt(sizeString);
				else {

					// If the user enters something other than digits,
					// the user is warned and is requested to choose
					// another number.
					int warning = JOptionPane.showConfirmDialog(null,
							"Please enter a valid number" +
									" between 3 and 30.");

					// If the user answers any of the no options
					// for the warning, the program is exited.
					if (warning == JOptionPane.NO_OPTION ||
							warning == JOptionPane.CANCEL_OPTION ||
							warning == JOptionPane.CLOSED_OPTION)
						System.exit(0);
					size = 0;
				}
			}
		}

		// Checks to make sure valid amount of mines for the given size
		while (mines < 1 || mines > (size * size)) {

			// Displays an input dialog that requests the user for a
			// valid size
			String mineString = JOptionPane.
					showInputDialog(null,
							"Enter the number of mines on" +
									" the board between 1 " +
									"and "+size*size+": ", "10");

			// If the user presses cancel or the X button, the program
			// closes
			if (mineString == null)
				System.exit(0);
			else if (mineString.equals("")) {

				// If the user adds in nothing, the program requests
				// the user to input a valid number.
				mines = 0;
				int warning = JOptionPane.showConfirmDialog(null,
						"Please enter a valid number of mines" +
								" from 1 to " + size * size);

				// If the user answers any of the no options
				// for the warning, the program is exited.
				if (warning == JOptionPane.NO_OPTION ||
						warning == JOptionPane.CANCEL_OPTION ||
						warning == JOptionPane.CLOSED_OPTION)
					System.exit(0);
			} else {

				// Checks all characters in string to make sure they
				// are all digits
				otherThanDigitCount = 0;
				for (int digitChecker = 0; digitChecker <
						mineString.length(); digitChecker++)
					if (!Character.isDigit(mineString.charAt
							(digitChecker)))
						otherThanDigitCount++;


				if (otherThanDigitCount == 0)

					// If user inputs all digits but they're above or
					// below valid mine range, the user is warned and
					// is requested to choose another number.
					if (Integer.parseInt(mineString) < 1 ||
							Integer.parseInt(mineString) >
									size * size) {
						int warning = JOptionPane.showConfirmDialog
								(null, "Please enter a valid number" +
										" of mines from 1 to " +
										size * size);

						// If the user answers any of the no options
						// for the warning, the program is exited.
						if (warning == JOptionPane.NO_OPTION ||
								warning == JOptionPane.CANCEL_OPTION ||
								warning == JOptionPane.CLOSED_OPTION)
							System.exit(0);
						mines = 0;
					} else {

						// If the user enters a valid mine, it is
						// accepted.
						mines = Integer.parseInt(mineString);
						if (mines == size * size)
							mines--;
					} else {

					// If the user enters something other than digits,
					// the user is warned and is requested to choose
					// another number.
					int warning = JOptionPane.showConfirmDialog(null,
							"Please enter a valid number of " +
									"mines from 1 to " + size * size);

					// If the user answers any of the no options
					// for the warning, the program is exited.
					if (warning == JOptionPane.NO_OPTION ||
							warning == JOptionPane.CANCEL_OPTION ||
							warning == JOptionPane.CLOSED_OPTION)
						System.exit(0);
					mines = 0;
				}
			}
		}

		// A new Minesweeper panel is added and is now visible to the
		// user.
		MineSweeperPanel panel = new MineSweeperPanel(size, mines);
		frame.getContentPane().add(panel);
		frame.setSize(500, 500);
		frame.setVisible(true);
	}

}

