package program.ui;

import javafx.scene.control.Button;
import program.Program;
import program.algorithm.TestAlg;
import program.system.ImageLayer;
import program.ui.elements.LayerListCell;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;

import java.net.URL;
import java.util.ResourceBundle;

public class EditorLayerTabController implements Initializable
{
  @FXML
  private ListView<ImageLayer> layerList;

  @FXML
  private Button addLayerButton;

  /**
   * Counter to increment the number in the names of the layers
   */
  private static int a = 0;

  /**
   * Called when the addLayerButton ('+'-Button) is pressed
   */
  public void onAddLayerButton()
  {
    // Create a new Image layer and assign it the TestAlg curve for now
    // TODO: Add a way to add different curves (use the 'Algorithm' selection-box)
    ImageLayer l = new ImageLayer("Layer #" + a, 1000,1000);
    l.setCurve(new TestAlg(500, 1));

    // Add the layer to the main controllers' list of layers
    Program.mainController.addLayer(l);

    // Increment a to so the next ImageLayer has an incremented name
    a++;
  }

  /**
   * Called when the remLayerButton ('-'-Button) is pressed
   */
  public void onRemLayerButton() {
    // TODO
  }

  /**
   * Updates the displayed list items in the editor if the list changed
   */
  public void update()
  {
    // Reset all items to the ones found in the mainControllers' layerList
    layerList.setItems(Program.mainController.getLayers());
    Program.debug("[EditorLayerTabController] update()");
  }

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle)
  {
    // Set the list to use the custom LayerListCell (see program.ui.elements.LayerListCell)
    layerList.setCellFactory(layerListView -> new LayerListCell());
    Program.debug("[EditorLayerTabController] initialized");
  }
}