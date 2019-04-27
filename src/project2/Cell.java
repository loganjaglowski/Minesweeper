package project2;

/**********************************************************************
 * This class controls the details of the individual cells within the
 * Minesweeper board.
 *
 * @author Logan Jaglowski
 * @version Winter 2019
 *********************************************************************/

public class Cell {

    /** isExposed tells whether or not the cell is exposed */
    private boolean isExposed;

    /** isMine tells whether or not the cell is a mine */
    private boolean isMine;

    /** mineCount tells number of neighboring mines around the cell */
    private int mineCount;

    /** isFlagged tells whether or not the cell is flagged */
    private boolean isFlagged;

    /******************************************************************
     * A constructor creates a new cell with the properties of being
     * exposed, a mine, flagged, and having a mine count.
     *
     * @param exposed A boolean that says if cell is exposed or not
     * @param mine A boolean that says if the cell is a mine or not
     * @param flagged A boolean that says if the cell is flagged or not
     * @param count An integer that tells how many mines are around the
     *              cell
     *****************************************************************/
    public Cell(boolean exposed, boolean mine, boolean flagged,
                int count) {
        isExposed = exposed;
        isMine = mine;
        isFlagged = flagged;
        mineCount = count;
    }

    /******************************************************************
     * Method that returns if the cell is exposed or not
     *
     * @returns true or false depending on if it's exposed.
     *****************************************************************/
    public boolean isExposed() {
        return isExposed;
    }

    /******************************************************************
     * Method that changes if the cell is exposed or not.
     *
     * @param exposed whether or not the cell is exposed.
     *****************************************************************/
    public void setExposed(boolean exposed) {
        isExposed = exposed;
    }

    /******************************************************************
     * Method that returns if the cell is a mine or not
     *
     * @returns true or false depending on if the cell is a mine.
     *****************************************************************/
    public boolean isMine() {
        return isMine;
    }

    /******************************************************************
     * Method that changes if the cell is a mine or not.
     *
     * @param mine whether or not the cell is a mine.
     *****************************************************************/
    public void setMine(boolean mine) {
        isMine = mine;
    }

    /******************************************************************
     * Method that returns if the cell is flagged or not
     *
     * @returns true or false depending on if the cell is flagged.
     *****************************************************************/
    public boolean isFlagged() {
        return isFlagged;
    }

    /******************************************************************
     * Method that changes if the cell is flagged or not.
     *
     * @param flagged whether or not the cell is flagged.
     *****************************************************************/
    public void setFlagged(boolean flagged) {
        isFlagged = flagged;
    }

    /******************************************************************
     * Method that returns if the cell is exposed or not
     *
     * @returns total number of neighboring mines around the cell.
     *****************************************************************/
    public int getMineCount() {
        return mineCount;
    }

    /******************************************************************
     * Method that changes the cell's mine count.
     * *
     * @param mines what the mine count of the cell will be.
     *****************************************************************/
    public void setMineCount(int mines) {
        mineCount = mines;
    }

    /******************************************************************
     * Method that increments the mineCount of the cell.
     *****************************************************************/
    public void incrementMineCount() {
        this.mineCount++;
    }
}
