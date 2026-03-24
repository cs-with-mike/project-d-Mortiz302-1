/**
 * Westmont College Spring 2026
 * CS 030 Project D
 *
 * @author Assistant Professor Mike Ryu mryu@westmont.edu
 * @author Miguel Ortiz mortiz@westmont.edu
 */

package edu.westmont.cs030.maze;

import javax.swing.text.JTextComponent;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Separate container for generating maze pathing and animating the generation process.
 */
public class MazeGenerator {

  private final Maze maze;
  private final int r0;
  private final int c0;
  private final boolean isShuffle;

  private final JTextComponent renderTarget;
  private final int renderDelay;

  public MazeGenerator(int mazeHeight, int mazeWidth, int initRow, int initCol,
                       boolean isShuffle, JTextComponent renderTarget, int renderDelayMillis) {
    this.r0 = initRow;
    this.c0 = initCol;

    this.renderDelay = renderDelayMillis;
    this.isShuffle = isShuffle;

    int numRows = mazeHeight % 2 == 0 ? mazeHeight + 1 : mazeHeight;
    int numCols = mazeWidth % 2 == 0 ? mazeWidth + 1 : mazeWidth;

    this.maze = new Maze(numRows, numCols);
    this.renderTarget = renderTarget;
  }

  /**
   * Returns the GUI render target that was set at instantiation.
   *
   * @return A {@link JTextComponent} if the render target was set, <code>null</code> if unset.
   */
  public JTextComponent getRenderTarget() {
    return this.renderTarget;
  }

  /**
   * Initializes the Maze using Maze.initialize() then calls the recursive
   * generateMaze(Cell) method with the initial Cell from the Maze to begin
   * the path generation process from.
   */
  public void generateMaze() {
    maze.initialize();
    Cell start = maze.cells[r0][c0];
    generateMaze(start);
  }

  /**
   * Recursive maze generation algorithm based on Wikipedia's description.
   * Steps:
   * 1. Mark current cell as a path.
   * 2. Display the maze.
   * 3. Get neighbors (shuffle if needed).
   * 4. For each unvisited neighbor:
   * - Connect origin and neighbor.
   * - Recurse on neighbor.
   *
   * @param currCell current Cell to continue the maze path generation from
   */
  public void generateMaze(Cell currCell) {
    currCell.setPath(true);

    displayMaze(maze);

    ArrayList<Cell> neighbors = maze.getNeighbors(currCell);
    if (isShuffle) {
      Collections.shuffle(neighbors);
    }

    for (Cell n : neighbors) {
      if (!n.isPath()) {
        maze.connectNeighbors(currCell, n);
        generateMaze(n);
      }
    }
  }

  /**
   * Displays the given {@link Maze} at its current state to both to the GUI window and console.
   *
   * @param maze {@link Maze} to display
   */
  private void displayMaze(Maze maze) {
    try {
      Thread.sleep(this.renderDelay);
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }

    if (this.renderTarget != null) this.renderTarget.setText(maze.toString());
    System.out.println(maze);
  }
}
