package program.ui;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.layout.VBox;
import program.Program;
import program.algorithm.Algorithm;
import program.ui.elements.AlgorithmSetting;
import program.ui.elements.ImageLayer;
import program.ui.elements.LayerListCell;

import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

public class EditorController implements Initializable
{
  @FXML private ListView<ImageLayer> layerList;
  @FXML private ChoiceBox<Algorithm> choiceBox;
  @FXML private VBox vBox;
  @FXML private ScrollPane colorSettingsPane;

  private static int a = 0;

  /**
   * Called when the addLayerButton ('+'-Button) is pressed
   */
  public void onAddLayerButton()
  {
    // Create a new Image layer
    ImageLayer l = new ImageLayer("Layer #" + a, 1000, 1000, choiceBox.getValue());
    // Add the layer to the main controllers' list of layers
    Program.ui.addLayer(l);
    // Increment a to so the next ImageLayer has the name 'Layer #(a+1)'
    a++;
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

    // (Re)Set the color options panel
    colorSettingsPane.setContent(Program.ui.getSelectedLayer().getColorSettings());
  }

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
    Algorithm alg;

    for (int i = 0; i < algs.size(); i++)
    {
      alg = algs.get(i);
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