package program.ui;

import program.Program;
import program.system.*;
import javafx.collections.*;
import javafx.fxml.*;
import javafx.scene.layout.BorderPane;

import java.net.*;
import java.util.*;

/**
 * The controller for the main scene of the program
 * <p>
 *   This class contains most of the methods to manage the layers of the program.
 *   It is used as a hub to communicate with the subscenes' controllers, namely the
 *   editor and the canvas scenes.
 * </p>
 * <br>
 * @author Robin Buhlmann
 * @version 0.1
 * @see EditorController
 * @see CanvasController
 */
public class MainController implements Initializable {
  /**
   * The Editor that is a child of this instance
   */
  @FXML
  private BorderPane editor;

  /**
   * The EditorController that is a child of this instance
   */
  @FXML
  private EditorController editorController;

  /**
   * The CanvasController that is a child of this instance
   */
  @FXML
  private CanvasController canvasController;

  private ObservableList<ImageLayer> layers;
  private ImageLayer selectedLayer;

  /**
   * Redraws a layer by creating a new GraphicsTask with the GraphicsContext of an ImageLayer
   * Sets the GraphicsContext of the ImageLayer after task completion
   */
  public void updateLayer() {
    // TODO
  }

  /**
   * Adds a layer to the list and updates the canvasController and editorController
   */
  public void addLayer(ImageLayer l)
  {
    layers.add(l);
    // Initially draw the curve
    l.redraw();
    // Update the editor layerList
    editorController.editorLayerTabController.update();
    // Update the canvas to actually show the layers' contents there
    canvasController.update();
  }

  /**
   * Removes a layer from the list and updates the canvasController
   * Updating editorController is not needed as this is only called from there
   * <br>
   * @param index The index in the editors' ListView of the ImageLayer to be removed
   */
  public void removeLayer(int index)
  {
    layers.remove(index);
    canvasController.update();
  }

  /**
   * Sets the current selected layer which curves' settings will be shown in the
   * editor settings tab.
   * <br>
   * @param layer The ImageLayer object that is currently selected in the layer list view
   */
  public void setSelectedLayer(ImageLayer layer)
  {
    selectedLayer = layer;
    Program.debug("Selected ImageLayer: Name='" + layer.name + "' " + layer);
  }

  /**
   * Returns the current layer list
   * @return layers The layer list
   */
  public ObservableList<ImageLayer> getLayers()
  {
    return layers;
  }

  /**
   * Executes the update method on the CanvasController object
   */
  public void updateCanvas()
  {
    canvasController.update();
  }

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle)
  {

  }

  /**
   * Creates the ObservableArrayList instance holding the ImageLayers
   */
  public MainController()
  {
    layers = FXCollections.observableArrayList();
    // Add a change listener to automatically detect changes to the layer list
    //layers.addListener((ListChangeListener.Change<? extends ImageLayer> c) -> {

    //});
  }
}
