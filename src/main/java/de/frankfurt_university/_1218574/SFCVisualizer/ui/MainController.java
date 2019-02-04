package de.frankfurt_university._1218574.SFCVisualizer.ui;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import de.frankfurt_university._1218574.SFCVisualizer.Program;
import de.frankfurt_university._1218574.SFCVisualizer.algorithm.Algorithm;
import javafx.collections.*;
import javafx.fxml.*;
import javafx.scene.layout.BorderPane;
import de.frankfurt_university._1218574.SFCVisualizer.system.FileTask;
import de.frankfurt_university._1218574.SFCVisualizer.ui.elements.ImageLayer;

import java.io.File;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * The controller for the main scene of the de.frankfurt_university._1218574.SFCVisualizer
 * <p>
 *   This class contains most of the methods to manage the layers of the de.frankfurt_university._1218574.SFCVisualizer.
 *   It is used as a hub to communicate with the subscenes' controllers, namely the
 *   editor and the canvas scenes.
 * </p>
 * <br>
 * @author Robin Buhlmann
 * @version 0.1
 * @see EditorController
 * @see CanvasController
 */
public class MainController implements Initializable
{
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
  private ImageLayer selected = null;

  /**
   * Updates the small label at the bottom left of the de.frankfurt_university._1218574.SFCVisualizer
   */
  public void setStatus(String text)
  {
    statusLabel.setText(text);
  }

  public EditorController editor()
  {
    return editorController;
  }

  public CanvasController canvas()
  {
    return canvasController;
  }

  /**
   * Adds a layer to the list, draws it initially and sets the selected layer to the new one
   */
  public void addLayer(ImageLayer layer)
  {
    // Check max-layers
    // Should have done this in deserializing and in onAddLayerButton but it does not really matter anymore
    if (Program.ui.getLayers().size() < Program.LAYERS_MAX) {
      try {
        // Initially draw the curve
        selected = layer;
        layer.redraw();
        layers.add(layer);

        // Select the layer
        editorController.layerList.getSelectionModel().select(selected);
      } catch (Exception ignored) {}
    }
    else
    {
      Program.ui.setStatus("You have reached the maximum amount of layers!");
    }
  }

  /**
   * Removes a layer from the list and updates the canvasController
   * Updating editorController is not needed as this is only called from there
   * <br>
   * @param index The index in the editors' ListView of the ImageLayer to be removed
   */
  public void removeLayer(int index)
  {
    ImageLayer layer = layers.get(index);
    try
    {
      setSelectedLayer(layers.get(0));
    }
    catch(Exception ignored) { /* We do not have any more layers */ }

    layer.renderService.cancel();
    layers.remove(layer);

    editorController.update();
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
      Program.debug("Selected ImageLayer: Name='" + layer.name + "' | " + layer + " | Algorithm='" + layer.getAlgorithm().name() + "'");
    }
    catch (Exception e)
    {
      editorController.disableGraphicsSettings();
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

  //
  // BEGIN menu handlers
  //

  /**
   * Creates a task to load a config from memory
   * @param f The file to be read
   */
  private void loadJSON(File f)
  {
    try
    {
      FileTask t = new FileTask(f.getAbsolutePath());
      Thread th = new Thread(t);
      th.setDaemon(true);
      th.start();
    }
    catch(Exception e)
    {
      Alert alert = new Alert(Alert.AlertType.ERROR);
      alert.setTitle("Loading Error");
      alert.setHeaderText(null);
      alert.setContentText("An error occurred while loading " + f.getName() + "!");

      alert.showAndWait();
    }
  }

  /**
   * Handles the 'Import'-MenuItem in the 'File'-Tab
   */
  public void menu_onImport()
  {
    // Create file chooser dialog
    FileChooser dialog = new FileChooser();
    dialog.setTitle("Open File");
    dialog.setInitialDirectory(new File("saves"));

    dialog.getExtensionFilters().add(new FileChooser.ExtensionFilter(".json", "*.json"));
    dialog.getExtensionFilters().add(new FileChooser.ExtensionFilter("All Files", "*.*"));

    File f = dialog.showOpenDialog(null);
    if (f != null)
    {
      loadJSON(f);
    }
    else
    {
      Alert alert = new Alert(Alert.AlertType.ERROR);
      alert.setTitle("File not found");
      alert.setHeaderText(null);
      alert.setContentText("The specified file could not be found or opened!");

      alert.showAndWait();
    }
  }

  /**
   * Handles the 'Export'-MenuItem in the 'File'-Tab
   */
  public void menu_onSaveAs()
  {
    // Create a dialog asking for the name of the file to be saved
    TextInputDialog dialog = new TextInputDialog();
    dialog.setTitle("Save layers");
    dialog.setHeaderText(null);
    dialog.setContentText("Choose a file name:");

    Optional<String> result = dialog.showAndWait();
    result.ifPresent(s -> {
      if (result.get().equals(""))
      {
        Program.ui.setStatus("Could not save layers - No filename specified.");
        return;
      }

      if (layers.size() < 1)
      {
        Program.ui.setStatus("Could not save layers - No layers present.");
        return;
      }

      FileTask t = new FileTask(Program.SAVE_DIRECTORY + result.get() + ".json", Program.ui.getLayers());

      Thread th = new Thread(t);
      th.setDaemon(true);
      th.start();
    });
  }

  public void onDeleteAll()
  {
    setSelectedLayer(null);
    layers.clear();

    editorController.update();
  }

  /**
   * Handles the 'Exit'-MenuItem in the 'File'-Tab
   */
  public void menu_onExit()
  {
    Program.debug("Attempted to exit de.frankfurt_university._1218574.SFCVisualizer through menu bar exit button");
    Program.exit();
  }

  /**
   * Handles the 'Help'-MenuItem in the 'Help'-Tab
   */
  public void menu_onHelp()
  {
    // Show an alert dialog
    Alert dialog = new Alert(Alert.AlertType.INFORMATION);
    dialog.setTitle("Help");
    dialog.setHeaderText(null);

    try
    {
      Parent root = FXMLLoader.load(getClass().getResource("/fxml/help.fxml"));
      Stage stage = (Stage) dialog.getDialogPane().getScene().getWindow();
      stage.setScene(new Scene(root));
    }
    catch(Exception ignored) {}

    dialog.show();
  }

  /**
   * Handles the 'About'-MenuItem in the 'Help'-Tab
   */
  public void menu_onAbout()
  {
    // Show an alert dialog
    Alert dialog = new Alert(Alert.AlertType.INFORMATION);
    dialog.setTitle("About");
    dialog.setHeaderText(null);

    try
    {
      Parent root = FXMLLoader.load(getClass().getResource("/fxml/about.fxml"));
      Stage stage = (Stage) dialog.getDialogPane().getScene().getWindow();
      stage.setScene(new Scene(root));
    }
    catch(Exception ignored) {}

    dialog.show();
  }

  //
  // END menu handlers
  //

  /**
   * Initializes the controllers nodes
   * @param url ignored
   * @param resourceBundle ignored
   */
  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    if (Program.STARTUP_LOAD_FILE != null && !Program.STARTUP_LOAD_FILE.equals(""))
    {
      File f = new File(Program.SAVE_DIRECTORY + Program.STARTUP_LOAD_FILE);
      Program.debug(f.getAbsolutePath());
      loadJSON(f);
    }
  }

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