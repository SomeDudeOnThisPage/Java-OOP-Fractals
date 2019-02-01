package program.ui.elements;

import javafx.concurrent.WorkerStateEvent;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.WritableImage;
import org.json.simple.JSONObject;
import program.Program;
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
 * @version 0.999
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
  public GraphicsService renderService;

  /**
   * The ColorSetting of this layer being displayed in the Editors 'Color'-Tab when the layer is selected
   */
  private GraphicsSetting graphicsSettings;

  /**
   * Time the last rendering took
   */
  private double last_render_time;

  /**
   * How many line objects we have
   */
  private double last_render_lines;

  /**
   * Determines whether the layer is visible on the canvas or not
   */
  public boolean visible;

  /**
   * Sets the last render time
   * @param rt last render time
   */
  public void setRenderTime(double rt)
  {
    this.last_render_time = rt;
  }

  /**
   * Gets the last render time
   * @return last render time
   */
  public double getRenderTime()
  {
    return this.last_render_time;
  }

  /**
   * Sets the last render line count
   * @param l last render line count
   */
  public void setRenderLines(double l)
  {
    this.last_render_lines = l;
  }

  /**
   * Gets the last render line count
   * @return last render line count
   */
  public double getRenderLines()
  {
    return this.last_render_lines;
  }

  /**
   * Returns the current settings of the fractal object as a HashMap
   * @return settings Settings of the current fractal held by the layer
   */
  public HashMap<String, AlgorithmSetting> getSettings()
  {
    return fractal.getSettings();
  }

  /**
   * Returns the current color settings of the layer
   * @return graphicsSettings Settings of the current fractal held by the layer as an UI object
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
   * @param name Display name
   */
  public void setName(String name)
  {
    this.name = name;
  }

  /**
   * Algorithm getter method
   * @return algorithm Currently selected algorithm
   */
  public Algorithm getAlgorithm() {
    return algorithm;
  }

  /**
   * Fractal getter method
   * @return fractal Currently instantiated Fractal class
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
    long start = System.currentTimeMillis();

    // Cancel any ongoing renderService
    renderService.cancel();
    Program.ui.editor().refreshList();

    // Set opacity
    this.setOpacity(this.graphicsSettings.getColors()[0].getOpacity());

    // Get the GraphicsContext
    GraphicsContext g = getGraphicsContext2D();

    // Set the settings for the render service
    renderService.setSettings(this.fractal.getSettings());

    // Add an event handler to the task that gets the tasks' value when succeeded and draws it on the canvas
    // Doing this BEFORE starting the thread to avoid having the task finish before the event handler is instanced
    renderService.addEventHandler(WorkerStateEvent.WORKER_STATE_SUCCEEDED, e -> {
        Program.ui.getLayers().indexOf(this);

        // Get the return value from the task
        BufferedImage bi = renderService.getValue();

        // Cast the return value to a WritableImage so it can be drawn onto the canvas
        WritableImage image = SwingFXUtils.toFXImage(bi, null);

        g.clearRect(0, 0, this.getWidth(), this.getHeight());
        g.drawImage(image, 0, 0);

        Program.ui.editor().refreshList();

        this.last_render_time = ((System.currentTimeMillis() - start) / 1000.0);
        // Update editor to show changes done to statistics
        Program.ui.editor().update();
        Program.ui.setStatus("Finished drawing image layer '" + name + "' in " + last_render_time + " seconds!");
      });

    // Start the thread
    renderService.restart();
  }

  /**
   * Changes the type of algorithm the layer is using
   * @param a Algorithm type
   */
  public void changeAlgorithmType(Algorithm a)
  {
    this.algorithm = a;
    this.fractal = algorithm.newFractal();

    this.graphicsSettings = new GraphicsSetting(GraphicsSetting.Type.SOLID);

    this.renderService = new GraphicsService(this.algorithm, this.fractal.getSettings(), this.graphicsSettings, this);
  }

  /**
   * Serializes an ImageLayer into a JSON string
   * @param layer Layer to be serialized
   * @return JSON string config
   */
  public static JSONObject toJSON(ImageLayer layer)
  {
    JSONObject object = new JSONObject();

    // Create the values of the object
    object.put("name", layer.name);
    object.put("visibility", layer.visible);

    object.put("algorithm", layer.algorithm.name());
    object.put("settings", Fractal.toJSON(layer.fractal));
    object.put("graphics", GraphicsSetting.toJSON(layer.getGraphicsSettings()));

    return object;
  }

  /**
   * Creates an ImageLayer from a JSON object
   * @param config JSON string
   * @return New ImageLayer
   */
  public static ImageLayer fromJSON(JSONObject config)
  {
    Algorithm a = Algorithm.valueOf((String) config.get("algorithm"));
    Fractal f = Fractal.fromJSON(config);
    GraphicsSetting g = GraphicsSetting.fromJSON((JSONObject) config.get("graphics"));

    return new ImageLayer((String) config.get("name"), /* 0 */ 1000, /* 0 */ 1000, (boolean) config.get("visibility"), a, f, g);
  }

  /**
   * ImageLayer constructor creating a layer with all settings set to default
   * @param name Display name of the layer
   * @param x x size of the Canvas
   * @param y y size of the Canvas
   * @param algorithm The algorithm object the layer is encasing
   */
  public ImageLayer(String name, int x, int y, Algorithm algorithm)
  {
    super(x,y);

    // Generate Algorithm object
    this.algorithm = algorithm;
    Program.debug(algorithm.name());
    Program.debug(algorithm);
    this.fractal = algorithm.newFractal();

    // Add a default ColorSetting object
    this.graphicsSettings = new GraphicsSetting(GraphicsSetting.Type.SOLID);

    // Generate initial settings for this layer
    this.name = name;
    this.visible = true;

    // Create the rendering service
    this.renderService = new GraphicsService(this.algorithm, this.fractal.getSettings(), this.graphicsSettings, this);
  }

  /**
   * ImageLayer constructor with algorithm and graphics settings
   * @param name Display name of the layer
   * @param x x size of the Canvas
   * @param y y size of the Canvas
   * @param algorithm The algorithm object the layer is encasing
   * @param fractal Fractal containing the algorithm settings
   * @param graphics GraphicsSetting containing the graphical settings that are to be used
   */
  public ImageLayer(String name, int x, int y, boolean visible, Algorithm algorithm, Fractal fractal, GraphicsSetting graphics)
  {
    super(x,y);

    this.algorithm = algorithm;
    this.fractal = fractal;

    this.graphicsSettings = graphics;

    this.name = name;
    this.visible = visible;

    // Create the rendering service
    this.renderService = new GraphicsService(this.algorithm, this.fractal.getSettings(), this.graphicsSettings, this);
  }
}