package program.ui.elements;

import program.Program;
import program.system.ImageLayer;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;

public class LayerListCell extends ListCell<ImageLayer> {

  @FXML private BorderPane root;

  @FXML private CheckBox visibleCheckBox;
  @FXML private TextField layerNameTextField;
  @FXML private Button deleteButton;

  private FXMLLoader fxmlloader;

  @Override
  public void updateItem(ImageLayer layer, boolean empty) {
    super.updateItem(layer, empty);
    if (empty) {

      setText(null);
      setGraphic(null);

    } else {
      // Persistent name
      layerNameTextField.setText(layer.name);
      // Persistent visibility check-box
      visibleCheckBox.setSelected(layer.visible);
      setGraphic(root);
    }
  }
  // Method is not recognized by IDE, but still works
  public void onVisibleCheckBox() {
    ImageLayer l = Program.mainController.getLayers().get(this.getIndex());
    // Testing
    if (visibleCheckBox.isSelected()) {
      l.visible = true;
    } else {
      l.visible = false;
    }
    Program.mainController.updateCanvas();
  }

  public LayerListCell() {
    try {
      fxmlloader = new FXMLLoader(getClass().getResource(Program.RESOURCE_PATH + "fxml/layerlist_cell.fxml"));
      fxmlloader.setController(this);
      root = fxmlloader.load();
    } catch(Exception e) {
      // TODO: Exit out, show fatal error dialog
      System.out.println(e);
    }
  }

}
