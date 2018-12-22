package program.system;

import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;

import javafx.embed.swing.SwingFXUtils;
import javafx.event.EventHandler;

import javafx.scene.image.WritableImage;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

import program.Program;
import program.algorithm.BlueBox;

import program.algorithm.Algorithm;
import program.algorithm.RedBox;

import java.awt.image.BufferedImage;

/**
 * The layer for the curve
 * <p>
 *   An extended canvas object to manage the curve. These are layered
 *   over each other in the canvas-scene.
 * </p>
 * @author Robin Buhlmann
 * @version 0.1
 * <br>
 * @see Canvas
 */
public class ImageLayer extends Canvas
{
  /**
   * The name of the layer displayed in the editor list
   */
  public String name;

  /**
   * The current curve object the ImageLayer instance is managing
   */
  private Curve curve;

  private Algorithm algorithm;

  /**
   * Determines whether the layer is visible or not
   */
  public boolean visible;

  /**
   * Clears the canvas, re-renders the curve in a separate thread and displays it
   * WARNING: Do not call unnecessarily, depending on the algorithm used this can take quite some time
   */
  public void redraw()
  {
    // Get the GraphicsContext
    GraphicsContext g = getGraphicsContext2D();

    // Clear the canvas
    g.clearRect(0, 0, this.getWidth(), this.getHeight());
    // Create the task, right now only for TESTALG
    Task renderTask = new GraphicsTask(this.algorithm, this.curve.getSettings());

    // Add an event handler to the task that gets the tasks' value when succeeded and draws it on the canvas
    // Doing this BEFORE starting the thread to avoid having the task finish before the event handler is instanced
    renderTask.addEventHandler(WorkerStateEvent.WORKER_STATE_SUCCEEDED,
      (EventHandler<WorkerStateEvent>) (e) -> {

        // Get the return value from the task
        BufferedImage bi = (BufferedImage) renderTask.getValue();

        // Cast the return value to a WritableImage so it can be drawn onto the canvas
        WritableImage image = SwingFXUtils.toFXImage(bi, null);
        g.drawImage(image, 0, 0);
    });

    // Start the thread
    Thread thread = new Thread(renderTask);
    thread.setDaemon(true);
    thread.start();
  }

  public ImageLayer(String name, int x, int y, Algorithm algorithm)
  {
    super(x,y);

    // Generate Algorithm object
    this.algorithm = algorithm;
    this.curve = algorithm.newCurve();

    // Generate initial settings for this layer
    this.name = name;
    this.visible = true;
  }
}
