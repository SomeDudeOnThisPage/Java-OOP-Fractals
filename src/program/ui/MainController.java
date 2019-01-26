package program.ui;

import javafx.application.Platform;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import program.Program;
import program.algorithm.Algorithm;
import javafx.collections.*;
import javafx.fxml.*;
import javafx.scene.layout.BorderPane;
import program.system.FileTask;
import program.ui.elements.ImageLayer;

import java.util.Optional;

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
public class MainController {
  /**
   * The Editor that is a child of this instance
   */
  @FXML private BorderPane editor;

  /**
   * The EditorController that is a child of this instance
   */
  @FXML private EditorController editorController;

  /**
   * The CanvasController that is a child of this instance
   */
  @FXML private CanvasController canvasController;

  @FXML private Label statusLabel;

  /**
   * The list holding the ImageLayer objects
   */
  private ObservableList<ImageLayer> layers;

  /**
   * A list holding all algorithms (see algorithm enum)
   */
  private ObservableList<Algorithm> algorithms;

  /**
   * The currently selected ImageLayer
   */
  private ImageLayer selected;

  /**
   * Updates the small label at the bottom left of the program
   */
  public void setStatus(String text)
  {
    statusLabel.setText(text);
  }

  /**
   * Adds a layer to the list, draws it initially and sets the selected layer to the new one
   */
  public void addLayer(ImageLayer layer)
  {
    // Initially draw the curve
    layer.redraw();
    selected = layer;
    layers.add(layer);
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
  }

  /**
   * Sets the current selected layer which curves' settings will be shown in the
   * editor settings tab.
   * <br>
   * @param layer The ImageLayer object that is currently selected in the layer list view
   */
  public void setSelectedLayer(ImageLayer layer)
  {
    selected = layer;
    // Try/Catch in case the list is empty
    try
    {
      Program.debug("Selected ImageLayer: Name='" + layer.name + "' " + layer);
    }
    catch (Exception e)
    {
      Program.debug("Find selected image layer, I could not. perhaps the list empty is, hmm?");
    }
  }

  /**
   * Returns the selected ImageLayer
   * @return ImageLayer selected The selected layer
   */
  public ImageLayer getSelectedLayer()
  {
    return selected;
  }

  /**
   * Returns all algorithms from the algorithm enum
   * @return algorithms The Algorithms
   */
  public ObservableList<Algorithm> getAlgorithms()
  {
    return algorithms;
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

  //
  // BEGIN menu handlers
  //

  /**
   * Handles the 'Import'-MenuItem in the 'File'-Tab
   */
  public void menu_onImport()
  {
    Program.debug("TODO: Import dialog");
  }

  /**
   * Handles the 'Save As'-MenuItem in the 'File'-Tab
   */
  public void menu_onSaveAs()
  {
    // Create a dialog asking for the name of the file to be saved
    TextInputDialog dialog = new TextInputDialog();
    dialog.setTitle("Save As");
    dialog.setHeaderText(null);
    dialog.setContentText("Choose a file name:");

    Optional<String> result = dialog.showAndWait();
    result.ifPresent(s -> {
      FileTask t = new FileTask(Program.SAVE_DIRECTORY + result.get() + ".json");
      t.addConfig(ImageLayer.toJSON(selected));
      t.writeToFile();
    });
  }

  /**
   * Handles the 'Exit'-MenuItem in the 'File'-Tab
   */
  public void menu_onExit()
  {
    Program.debug("Attempted to exit program through menu bar exit button");
    Program.exit();
  }

  /**
   * Handles the 'Help'-MenuItem in the 'Help'-Tab
   */
  public void menu_onHelp()
  {
    Program.debug("TODO: Help dialog");
  }

  /**
   * Handles the 'About'-MenuItem in the 'Help'-Tab
   */
  public void menu_onAbout()
  {
    Program.debug("TODO: About Dialog");
  }

  //
  // END menu handlers
  //

  /**
   * Creates the ObservableArrayList instance holding the ImageLayers
   */
  public MainController()
  {
    // Create the main list of ImageLayers
    layers = FXCollections.observableArrayList();

    // Create the main list of Algorithms and populate it
    algorithms = FXCollections.observableArrayList();
    algorithms.addAll(Algorithm.values());

    // Add a change listener to automatically detect changes to the layer list
    // Update the sub-controllers accordingly
    layers.addListener((ListChangeListener.Change<? extends ImageLayer> layer) -> {
      editorController.update();
      canvasController.update();
    });
  }
}
