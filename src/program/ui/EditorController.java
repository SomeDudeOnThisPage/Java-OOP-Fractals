package program.ui;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Separator;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import program.Program;
import program.algorithm.Algorithm;
import program.ui.elements.AlgorithmSetting;
import program.ui.elements.ImageLayer;
import program.ui.elements.LayerListCell;

import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

/**
 * The controller controlling the nodes of the editor.fxml file
 * <p>
 *   Manages three buttons and the actual displayed list of layers
 * </p>
 * @author Robin Buhlmann
 * @version 0.5
 */
public class EditorController implements Initializable
{
  /**
   * The actual in the program displayed list
   */
  @FXML private ListView<ImageLayer> layerList;

  /**
   * The choice box the user selects algorithms from
   */
  @FXML private ChoiceBox<Algorithm> choiceBox;

  /**
   * A vertical box. Come on, you know what a VBox is!
   * This box is the one the AlgorithmSettings are added onto
   */
  @FXML private VBox vBox;

  /**
   * The Panel the GraphicsSettings are drawn onto
   */
  @FXML private BorderPane colorSettingsPane;

  /**
   * VBox containing stats
   */
  @FXML private VBox statContentBox;

  /**
   * Label for rendering  time statistic
   */
  @FXML private Label renderingTimeStat;

  /**
   * Label for Memory usage statistic
   */
  @FXML private Label lineStat;

  /**
   * Used to count up default layer names
   */
  private static int a = 0;

  /**
   * Called when the addLayerButton ('+'-Button) is pressed
   */
  public void onAddLayerButton()
  {
    // Create a new Image layer
    ImageLayer l = new ImageLayer("#" + a + " " + choiceBox.getValue().toString(), 1000, 1000, choiceBox.getValue());
    // Add the layer to the main controllers' list of layers
    Program.ui.addLayer(l);
    // Increment a to so the next ImageLayer has the name 'Layer #(a+1)'
    a++;
    // Select the layer
    layerList.getSelectionModel().select(l);
  }

  /**
   * Called when the remLayerButton ('-'-Button) is pressed
   */
  public void onRemLayerButton()
  {
    // Make sure we have a layer selected when trying to remove the currently selected layer
    try
    {
      Program.ui.removeLayer(layerList.getSelectionModel().getSelectedIndex());
    }
    catch (Exception e)
    {
      Program.debug("Removing layer failed - No selected layer found.");
    }
  }

  /**
   * Called when the importLayerButton ('Folder'-Button) is pressed
   */
  public void onImportButton()
  {
    // Just reuse the method from the main controller
    Program.ui.menu_onImport();
  }

  public void refreshList()
  {
    layerList.refresh();
  }

  /**
   * Updates the components managed by the EditorController instance
   */
  public void update()
  {
    // Reset all items to the ones found in the mainControllers' layerList
    layerList.setItems(Program.ui.getLayers());

    // (Re)Set the options panel
    vBox.getChildren().clear();
    // Get the current layers' settings
    HashMap<String, AlgorithmSetting> settings = Program.ui.getSelectedLayer().getSettings();
    // Add the settings panes to the vBox
    settings.forEach((String key, AlgorithmSetting value) -> {
      vBox.getChildren().add(value);
      vBox.getChildren().add(new Separator());
    });

    ImageLayer l = Program.ui.getSelectedLayer();

    renderingTimeStat.setText(l.getRenderTime() + " seconds");
    lineStat.setText(((Double) l.getRenderLines()).intValue() + " steps");

    // (Re)Set the color options panel
    colorSettingsPane.setCenter(Program.ui.getSelectedLayer().getGraphicsSettings());
  }

  /**
   * Initializes the controller and its contained nodes
   * @param url ignored
   * @param resourceBundle ignored
   */
  @Override
  public void initialize(URL url, ResourceBundle resourceBundle)
  {
    // Set the list to use the custom LayerListCell (see program.ui.elements.LayerListCell)
    layerList.setCellFactory(layerListView -> new LayerListCell());

    // Set the currently selected model in the main controller and update the editor on selection of a cell
    layerList.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
      try
      {
        Program.ui.setSelectedLayer(newValue);
        update();
      }
      catch (Exception ignored)
      {
        // This exception is handled in the MainControllers' handler for the list of ImageLayers
      }
    });

    // Populate the choiceBox
    ObservableList<Algorithm> algs = Program.ui.getAlgorithms();
    for (Algorithm alg : algs)
    {
      if (!alg.debug || Program.DEBUG)
      {
        choiceBox.getItems().add(alg);
      }
    }

    // Select one by default to not get any unpredictable behavior when no element is selected
    choiceBox.getSelectionModel().select(0);

    Program.debug("[EditorController] initialized");
  }
}