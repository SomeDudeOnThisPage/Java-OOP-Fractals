package program.ui.elements;

import javafx.concurrent.WorkerStateEvent;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.WritableImage;
import program.algorithm.Algorithm;
import program.system.Fractal;
import program.system.GraphicsService;

import java.awt.image.BufferedImage;
import java.util.HashMap;

/**
 * The layer for the fractal
 * <p>
 *   An extended canvas object to manage the fractal. These are layered
 *   on top of each other in the canvas-scene. Each ImageLayer holds a Fractal
 *   object (and thus its settings) and its color settings. It also handles
 *   starting / stopping the rendering service executing the current Fractals render method.
 * </p>
 * @author Robin Buhlmann
 * @version 0.1
 * <br>
 * @see Canvas
 * @see Fractal
 * @see GraphicsSetting
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

  /**
   * The Algorithm-Enum the ImageLayer refers to
   */
  private Algorithm algorithm;

  /**
   * The GraphicsService object of the layer
   */
  private GraphicsService renderService;

  /**
   * The ColorSetting of this layer being displayed in the Editors 'Color'-Tab when the layer is selected
   */
  private GraphicsSetting graphicsSettings;

  /**
   * Determines whether the layer is visible on the canvas or not
   */
  public boolean visible;

  /**
   * Returns the current settings of the fractal object as a HashMap
   * @return settings The settings of the current fractal held by the layer
   */
  public HashMap<String, AlgorithmSetting> getSettings()
  {
    return fractal.getSettings();
  }

  /**
   * Returns the current color settings of the layer
   * @return graphicsSettings The settings of the current fractal held by the layer as an UI object
   */
  public GraphicsSetting getGraphicsSettings()
  {
    return graphicsSettings;
  }

  /**
   * Sets the current color settings of the layer
   * @param g Another new GraphicsSetting object
   */
  public void setGraphicsSettings(GraphicsSetting g) {
    this.graphicsSettings = g;
  }

  /**
   * Sets the name
   * @param name
   */
  public void setName(String name)
  {
    this.name = name;
  }

  /**
   * Algorithm getter
   * @return algorithm The currently selected algorithm
   */
  public Algorithm getAlgorithm() {
    return algorithm;
  }

  /**
   * Fractal getter
   * @return fractal The currently instantiated Fractal class
   */
  public Fractal getFractal() {
    return fractal;
  }

  /**
   * Clears the canvas, re-renders the fractal in a separate thread and displays it
   * WARNING: Do not call unnecessarily, depending on the algorithm used this can take quite some time
   */
  public void redraw()
  {
    // Cancel any ongoing renderService
    renderService.cancel();

    this.setOpacity(this.graphicsSettings.getColors()[0].getOpacity());

    // Get the GraphicsContext
    GraphicsContext g = getGraphicsContext2D();

    // Set the settings for the render service
    renderService.setSettings(this.fractal.getSettings());

    // Add an event handler to the task that gets the tasks' value when succeeded and draws it on the canvas
    // Doing this BEFORE starting the thread to avoid having the task finish before the event handler is instanced
    renderService.addEventHandler(WorkerStateEvent.WORKER_STATE_SUCCEEDED, e -> {
        // Get the return value from the task
        BufferedImage bi = renderService.getValue();

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

    // Add a default ColorSetting object
    this.graphicsSettings = new GraphicsSetting(GraphicsSetting.Type.SOLID);

    // Generate initial settings for this layer
    this.name = name;
    this.visible = true;

    // Create the rendering service
    this.renderService = new GraphicsService(this.algorithm, this.fractal.getSettings(), this.graphicsSettings);
  }
}