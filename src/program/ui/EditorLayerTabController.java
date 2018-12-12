package program.ui;

import javafx.scene.control.Button;
import program.Program;
import program.system.ImageLayer;
import program.ui.elements.LayerListCell;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;

import java.net.URL;
import java.util.ResourceBundle;

public class EditorLayerTabController implements Initializable {
  @FXML private ListView<ImageLayer> layerList;

  @FXML private Button addLayerButton;

  private static int a = 0;

  public void onAddLayerButton() {
    Program.mainController.addLayer(new ImageLayer("Layer #" + a, 500,500));
    a++;
  }

  /**
   * Updates the displayed list items if the list changed
   * TODO: Add a change listener in the mainController
   */
  public void update() {
    layerList.setItems(Program.mainController.getLayers());
    if (Program.DEBUG) Program.debug("[EditorLayerTabController] update()");
  }

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    layerList.setCellFactory(layerListView -> new LayerListCell());

    // Add a ChangeListener to update the selected items in the mainController
    //layerList.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
    //  Program.mainController.setSelectedLayer(layerList.getSelectionModel().getSelectedItem());
    //});

    if (Program.DEBUG) Program.debug("[EditorLayerTabController] initialized");
  }
}
