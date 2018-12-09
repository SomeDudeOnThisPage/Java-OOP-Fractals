package program.system;

import program.Program;
import program.algorithm.TestAlg;
import javafx.concurrent.Task;
import javafx.scene.canvas.GraphicsContext;

/**
 * Creates a Task object that runs an Algorithm with a GraphicsContext parameter
 * Returns the (modified by the Algorithm) GraphicsContext
 * <p>
 *   This task selects the wanted algorithm from a list of all in
 *   @see Program defined implemented algorithm constants based on
 *   the set algorithm name the constructor recieves as a parameter.
 *   It will then call the generate() method of the chosen algorithm
 *   and return its' modified GraphicsContext once finished.
 * </p>
 * @author Robin Buhlmann
 * @version 0.1
 * <br>
 * @see Task
 * @see javafx.scene.canvas.Canvas
 * @see GraphicsContext
 */
public class GraphicsTask extends Task<GraphicsContext> {
  /**
   * The GraphicsContext to be altered by the algorithm
   */
  private GraphicsContext graphics;

  @Override
  protected GraphicsContext call() throws Exception {
    // Just use static algorithm for now
    graphics = TestAlg.generate(graphics, 100,100);
    return graphics;
  }

  /**
   * Sets the GraphicsContext to be used in the task
   * @param g
   */
  public GraphicsTask(GraphicsContext g) {
    graphics = g;
  }
}
