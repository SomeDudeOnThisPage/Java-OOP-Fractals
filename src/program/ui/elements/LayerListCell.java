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

// Custom list CellFactory
public class LayerListCell extends ListCell<ImageLayer>
{

  @FXML private BorderPane root;

  @FXML private CheckBox visibleCheckBox;
  @FXML private TextField layerNameTextField;
  @FXML private Button deleteButton;

  private FXMLLoader fxmlloader;

  @Override
  public void updateItem(ImageLayer layer, boolean empty)
  {
    super.updateItem(layer, empty);

    if (empty)
    {
      setText(null);
      setGraphic(null);
    }
    else
    {
      layerNameTextField.setText(layer.name);
      visibleCheckBox.setSelected(layer.visible);
      setGraphic(root);
    }
  }

  public void onVisibleCheckBox()
  {
    ImageLayer l = Program.MAIN_CONTROLLER.getLayers().get(this.getIndex());
    l.visible = visibleCheckBox.isSelected();
    Program.MAIN_CONTROLLER.updateCanvas();
  }

  public LayerListCell()
  {
    try
    {
      fxmlloader = new FXMLLoader(getClass().getResource(Program.RESOURCE_PATH + "fxml/layerlist_cell.fxml"));
      fxmlloader.setController(this);
      root = fxmlloader.load();
    }
    catch(Exception e)
    {
      System.out.println(e);
    }
  }
}