package program.ui;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import program.Program;
import program.algorithm.Algorithm;
import program.system.ImageLayer;
import program.ui.elements.LayerListCell;

import java.net.URL;
import java.util.ResourceBundle;

public class EditorController implements Initializable
{
  @FXML private ListView<ImageLayer> layerList;
  @FXML private ChoiceBox<Algorithm> choiceBox;


  private static int a = 0;

  /**
   * Called when the addLayerButton ('+'-Button) is pressed
   */
  public void onAddLayerButton()
  {
    // Create a new Image layer and assign it the RedBox curve for now
    ImageLayer l = new ImageLayer("Layer #" + a, 1000, 1000, choiceBox.getValue());
    Program.MAIN_CONTROLLER.setSelectedLayer(l);
    // Add the layer to the main controllers' list of layers
    Program.MAIN_CONTROLLER.addLayer(l);
    // Increment a to so the next ImageLayer has the name 'Layer #(a+1)'
    a++;
  }

  /**
   * Called when the remLayerButton ('-'-Button) is pressed
   */
  public void onRemLayerButton()
  {
    Program.MAIN_CONTROLLER.removeLayer(layerList.getSelectionModel().getSelectedIndex());
  }

  /**
   * Updates the components managed by the EditorController instance
   */
  public void update()
  {
    // Reset all items to the ones found in the mainControllers' layerList
    layerList.setItems(Program.MAIN_CONTROLLER.getLayers());
  }

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle)
  {
    // Set the list to use the custom LayerListCell (see program.ui.elements.LayerListCell)
    layerList.setCellFactory(layerListView -> new LayerListCell());

    // Set the currently selected model in the main controller
    layerList.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
      Program.MAIN_CONTROLLER.setSelectedLayer(newValue);
    });

    // Populate the choiceBox
    ObservableList<Algorithm> algs = Program.MAIN_CONTROLLER.getAlgorithms();
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