package program.system;

import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import program.algorithm.Algorithm;
import program.ui.elements.AlgorithmSetting;
import program.ui.elements.GraphicsSetting;

import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.concurrent.Executors;

/**
 * Creates a Task object that runs an Algorithm with a GraphicsContext parameter
 * Returns the (modified by the Algorithm) GraphicsContext
 * <p>
 * tododescgraphicstask
 * </p>
 * @author Robin Buhlmann
 * @version 0.1
 * <br>
 * @see Service
 * @see javafx.scene.canvas.Canvas
 * @see GraphicsContext
 */
public class GraphicsService extends Service<BufferedImage>
{
  /**
   * The algorithm object ID whose render method is to be used
   */
  private Algorithm algorithm;

  private HashMap<String, AlgorithmSetting> settings;

  private GraphicsSetting graphics;

  private BufferedImage image;

  public long elapsed;

  public void setSettings(HashMap<String, AlgorithmSetting> settings)
  {
    this.settings = settings;
  }

  @Override
  protected Task<BufferedImage> createTask()
  {
    return new Task<>()
    {
      @Override
      protected BufferedImage call()
      {
        // Create the image during the runtime of the task to avoid it being mutable outside the task to ensure thread safety
        image = new BufferedImage(1000, 1000, BufferedImage.TYPE_INT_ARGB);

        // Deep-copy the HashMap for thread-safety
        // The copy is technically immutable as it is created inside and cannot be changed outside the thread
        // Note that using HashTable would not, despite being thread-safe, yield the desired result, as the values could still change during runtime of an algorithm
        HashMap<String, AlgorithmSetting> copy = new HashMap<>();
        settings.forEach(copy::put);

        // Preserve the mode of the colorSettings to pass them on to the render method later
        GraphicsSetting.Type mode = graphics.getMode();

        // We need java.awt.Color instead of javafx colors to draw on a BufferedImage!
        java.awt.Color[] c = new java.awt.Color[2];

        // Cast the colors to java.awt.Color so we can use them on a BufferedImage
        // Has the added side-effect that we casually deep-copy the colors
        // We do not care about the array contents at this point, the Fractal object has to make sure that it uses the right color-mode and color by itself
        Color cur = graphics.getColors()[0];

        // Copy first color
        c[0] = new java.awt.Color((float) cur.getRed(), (float) cur.getGreen(), (float) cur.getBlue(), (float) cur.getOpacity());

        // Copy second color
        cur = graphics.getColors()[1];
        c[1] = new java.awt.Color((float) cur.getRed(), (float) cur.getGreen(), (float) cur.getBlue(), (float) cur.getOpacity());

        // Call the render() method in the Algorithm enum
        algorithm.render(image, copy, mode, c, graphics.getStrokeWidth());

        return image;
      }
    };
  }

  /**
   * Sets the GraphicsContext to be used in the task
   * @param algorithm the Algorithm to be drawn
   */
  public GraphicsService(Algorithm algorithm, HashMap<String, AlgorithmSetting> settings, GraphicsSetting graphics)
  {
    this.algorithm = algorithm;
    this.settings = settings;
    this.graphics = graphics;

    // Possible fix for the 'Hurr durr I'm a Service, I create 500 tasks'-Issue
    this.setExecutor(Executors.newFixedThreadPool(1, runnable -> {
      Thread t = new Thread(runnable);
      t.setDaemon(true);
      return t;
    }));
  }
}