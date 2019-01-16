package program.ui.elements;

import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;

import javafx.embed.swing.SwingFXUtils;
import javafx.event.EventHandler;

import javafx.scene.image.WritableImage;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

import program.algorithm.Algorithm;
import program.system.Fractal;
import program.system.GraphicsService;

import java.awt.image.BufferedImage;
import java.util.HashMap;

/**
 * The layer for the fractal
 * <p>
 *   An extended canvas object to manage the fractal. These are layered
 *   on top of each other in the canvas-scene.
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
   * The current fractal object the ImageLayer instance is managing
   */
  private Fractal fractal;

  private Algorithm algorithm;

  private GraphicsService renderService;

  /**
   * Determines whether the layer is visible or not
   */
  public boolean visible;

  public HashMap<String, AlgorithmSetting> getSettings()
  {
    return fractal.getSettings();
  }

  public void setName(String name)
  {
    this.name = name;
  }

  /**
   * Clears the canvas, re-renders the fractal in a separate thread and displays it
   * WARNING: Do not call unnecessarily, depending on the algorithm used this can take quite some time
   */
  public void redraw()
  {
    // Cancel any ongoing renderService
    renderService.cancel();

    // Get the GraphicsContext
    GraphicsContext g = getGraphicsContext2D();

    // Set the settings for the render service
    renderService.setSettings(this.fractal.getSettings());

    // Add an event handler to the task that gets the tasks' value when succeeded and draws it on the canvas
    // Doing this BEFORE starting the thread to avoid having the task finish before the event handler is instanced
    renderService.addEventHandler(WorkerStateEvent.WORKER_STATE_SUCCEEDED,
      (EventHandler<WorkerStateEvent>) (e) -> {

        // Get the return value from the task
        BufferedImage bi = (BufferedImage) renderService.getValue();

        // Cast the return value to a WritableImage so it can be drawn onto the canvas
        WritableImage image = SwingFXUtils.toFXImage(bi, null);

        g.clearRect(0, 0, this.getWidth(), this.getHeight());
        g.drawImage(image, 0, 0);
    });

    // Start the thread
    renderService.restart();
  }

  public ImageLayer(String name, int x, int y, Algorithm algorithm)
  {
    super(x,y);

    // Generate Algorithm object
    this.algorithm = algorithm;
    this.fractal = algorithm.newFractal();

    // Generate initial settings for this layer
    this.name = name;
    this.visible = true;

    // Create the rendering service
    this.renderService = new GraphicsService(this.algorithm, this.fractal.getSettings());
  }
}
