/**
 * Westmont College Spring 2026
 * CS 030 Project D
 *
 * @author Assistant Professor Mike Ryu mryu@westmont.edu
 * @author Miguel Ortiz mortiz@westmont.edu
 */

package edu.westmont.cs030.maze;

/**
 * Represents a cell of a maze, which may be a wall or path.
 * <br>
 * This class has three (3) private instance variables:
 * <ul>
 *   <li><code>private boolean isPath</code> indicates whether this cell is a path or a wall (mutable)</li>
 *   <li><code>private final int r</code>this cell's row index in the maze</li>
 *   <li><code>private final int c</code>this cell's column index in the maze</li>
 * </ul>
 */
public class Cell {

  /**
   * Text to render for the cell if it's a "wall."
   */
  public static final String WALL_TEXT = "██";

  /**
   * Text to render for the cell if it's a "path."
   */
  public static final String PATH_TEXT = "  ";

  private boolean isPath;
  private final int r;
  private final int c;

  /**
   * Construct a cell with the given and row and column indices regarding its location in a maze.
   *
   * @param rowIndex row (vertical) index of the cell's location within a maze
   * @param colIndex column (horizontal) index of the cell's location within a maze
   */
  public Cell(int rowIndex, int colIndex) {
    this.r = rowIndex;
    this.c = colIndex;
    this.isPath = false;
  }

  /**
   * Getter for r.
   *
   * @return row (vertical) index of the cell's location within a maze
   */
  public int rowIndex() {
    return r;
  }

  /**
   * Getter for c.
   *
   * @return column (horizontal) index of the cell's location within a maze
   */
  public int colIndex() {
    return c;
  }

  /**
   * Getter for isPath.
   *
   * @return true if this cell is a path, else false
   */
  public boolean isPath() {
    return isPath;
  }

  /**
   * Setter for isPath.
   *
   * @param path true to set this cell as a path, false otherwise
   */
  public void setPath(boolean path) {
    this.isPath = path;
  }

  /**
   * Computes the row index of the neighbor in the given direction.
   * To account for the walls between neighbors, each neighbor is always 2 cells away.
   *
   * @param dir Direction indicating which neighbor's row index to compute
   * @param rowLimit exclusive upper bound for valid row indices
   * @return row index of the neighboring cell
   * @throws IndexOutOfBoundsException if the computed index is out of bounds
   */
  public int getNeighborRowIndex(Direction dir, int rowLimit) {
    int newRow = switch (dir) {
      case NORTH -> r - 2;
      case SOUTH -> r + 2;
      default -> r;
    };

    if (newRow < 0 || newRow >= rowLimit) {
      throw new IndexOutOfBoundsException("Neighbor row out of bounds: " + newRow);
    }

    return newRow;
  }

  /**
   * Computes the column index of the neighbor in the given direction.
   * To account for the walls between neighbors, each neighbor is always 2 cells away.
   *
   * @param dir Direction indicating which neighbor's column index to compute
   * @param colLimit exclusive upper bound for valid column indices
   * @return column index of the neighboring cell
   * @throws IndexOutOfBoundsException if the computed index is out of bounds
   */
  public int getNeighborColIndex(Direction dir, int colLimit) {
    int newCol = switch (dir) {
      case WEST -> c - 2;
      case EAST -> c + 2;
      default -> c;
    };

    if (newCol < 0 || newCol >= colLimit) {
      throw new IndexOutOfBoundsException("Neighbor col out of bounds: " + newCol);
    }

    return newCol;
  }

  /**
   * Return the text used to render this cell as a portion of a maze.
   *
   * @return PATH_TEXT if this cell is a path, else WALL_TEXT
   */
  public String getText() {
    return isPath ? PATH_TEXT : WALL_TEXT;
  }

  /**
   * Return the text representation of this cell for debugging (not rendering) purposes.
   *
   * @return text representation of this cell
   */
  @Override
  public String toString() {
    return String.format("Cell [%2d][%2d]: %s", r, c, isPath ? "PATH" : "WALL");
  }
}