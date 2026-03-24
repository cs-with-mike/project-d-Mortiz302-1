/**
 * Westmont College Spring 2026
 * CS 030 Project D
 *
 * @author Assistant Professor Mike Ryu mryu@westmont.edu
 * @author Miguel Ortiz mortiz@westmont.edu
 */

package edu.westmont.cs030.maze;

import java.util.ArrayList;

/**
 * Represents a maze as a 2D array of {@link Cell}s.
 */
public class Maze {

  /**
   * 2D array of Cells to represent this maze.
   */
  public final Cell[][] cells;

  /**
   * Initializes the maze given the dimensions of the maze in number of rows and columns.
   *
   * @param numRows number of rows this maze should have
   * @param numCols number of columns this maze should have
   */
  public Maze(int numRows, int numCols) {
    if (numRows <= 0 || numCols <= 0) {
      throw new IllegalArgumentException("Maze dimensions must be positive.");
    }

    cells = new Cell[numRows][numCols];
    initialize();
  }

  /**
   * Resets every cell in this maze to a new Cell configured as a wall.
   */
  public void initialize() {
    for (int r = 0; r < numRows(); r++) {
      for (int c = 0; c < numCols(); c++) {
        cells[r][c] = new Cell(r, c);
      }
    }
  }

  /**
   * @return number of rows in this maze
   */
  public int numRows() {
    return cells.length;
  }

  /**
   * @return number of columns in this maze
   */
  public int numCols() {
    return cells[0].length;
  }

  /**
   * Returns a neighboring Cell in the given Direction, or null if out of bounds.
   */
  public Cell getNeighbor(Cell cell, Direction dir) {
    try {
      int nr = cell.getNeighborRowIndex(dir, numRows());
      int nc = cell.getNeighborColIndex(dir, numCols());
      return cells[nr][nc];
    } catch (IndexOutOfBoundsException e) {
      return null;
    }
  }

  /**
   * Returns all valid neighbors of a cell.
   */
  public ArrayList<Cell> getNeighbors(Cell cell) {
    ArrayList<Cell> list = new ArrayList<>();

    for (Direction d : Direction.values()) {
      Cell n = getNeighbor(cell, d);
      if (n != null) list.add(n);
    }

    return list;
  }

  /**
   * Connects two neighbors by setting the midpoint cell to a path.
   */
  public void connectNeighbors(Cell origin, Cell neighbor) {
    int midRow = (origin.rowIndex() + neighbor.rowIndex()) / 2;
    int midCol = (origin.colIndex() + neighbor.colIndex()) / 2;

    cells[midRow][midCol].setPath(true);
  }

  /**
   * String representation of the maze with boundaries.
   */
  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();

    sb.append(Cell.WALL_TEXT.repeat(numCols() + 2)).append("\n");

    for (int r = 0; r < numRows(); r++) {
      sb.append(Cell.WALL_TEXT); // left boundary

      for (int c = 0; c < numCols(); c++) {
        sb.append(cells[r][c].getText());
      }

      sb.append(Cell.WALL_TEXT).append("\n"); // right boundary
    }

    sb.append(Cell.WALL_TEXT.repeat(numCols() + 2)).append("\n");

    return sb.toString();
  }
}