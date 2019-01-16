package program.system;

import javafx.concurrent.Task;
import javafx.scene.canvas.GraphicsContext;
import program.algorithm.Algorithm;
import program.ui.elements.AlgorithmSetting;

import java.awt.image.BufferedImage;
import java.util.HashMap;

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
public class GraphicsService extends Task<BufferedImage>
{
  /**
   * The algorithm object ID whose render method is to be used
   */
  private Algorithm algorithm;

  private HashMap<String, AlgorithmSetting> settings;

  @Override
  protected BufferedImage call() throws Exception
  {
    // Create the image during the runtime of the task to avoid it being mutable outside the task to ensure thread safety
    BufferedImage image = new BufferedImage(1000, 1000, BufferedImage.TYPE_INT_ARGB);

    // Deep-copy the HashMap for thread-safety
    // The copy is technically immutable as it is created inside and cannot be changed outside the thread
    // Note that using HashTable would not, despite being thread-safe, yield the desired result, as the values could still change during runtime of an algorithm
    HashMap<String, AlgorithmSetting> copy = new HashMap<>();
    settings.forEach(copy::put);

    // Call the render() method in the Algorithm enum
    algorithm.render(image, copy);

    return image;
  }

  /**
   * Sets the GraphicsContext to be used in the task
   * @param algorithm the Algorithm to be drawn
   */
  public GraphicsService(Algorithm algorithm, HashMap<String, AlgorithmSetting> settings)
  {
    this.algorithm = algorithm;
    this.settings = settings;
  }
}