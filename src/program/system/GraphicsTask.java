package program.system;

import javafx.concurrent.Task;
import javafx.scene.canvas.GraphicsContext;
import program.algorithm.*;

import java.awt.image.BufferedImage;

/**
 * Creates a Task object that runs an Algorithm with a GraphicsContext parameter
 * Returns the (modified by the Algorithm) GraphicsContext
 * <p>
 * tododescgraphicstask
 * </p>
 * @author Robin Buhlmann
 * @version 0.1
 * <br>
 * @see Task
 * @see javafx.scene.canvas.Canvas
 * @see GraphicsContext
 */
public class GraphicsTask extends Task<BufferedImage>
{
  public enum Algorithm {
    TESTALG
  }

  /**
   * The algorithm object ID whose render method is to be used
   */
  private Algorithm curve;

  @Override
  protected BufferedImage call() throws Exception
  {
    // Create the image during the runtime of the task to avoid it being mutable outside the task to ensure thread safety
    BufferedImage image = new BufferedImage(1000, 1000, BufferedImage.TYPE_INT_ARGB);

    TestAlg.render(image);

    return image;
  }

  /**
   * Sets the GraphicsContext to be used in the task
   * @param alg the Algorithm to be drawn
   */
  public GraphicsTask(Algorithm alg)
  {
    this.curve = alg;
  }
}