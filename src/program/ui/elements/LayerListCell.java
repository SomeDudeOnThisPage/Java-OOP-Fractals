package program.ui.elements;

import javafx.scene.control.*;
import program.Program;
import program.system.ImageLayer;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.BorderPane;

import java.util.List;

// Custom list CellFactory
public class LayerListCell extends ListCell<ImageLayer>
{

  @FXML private BorderPane root;

  @FXML private CheckBox visibleCheckBox;
  @FXML private TextField textField;

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
      textField.setText(layer.name);
      visibleCheckBox.setSelected(layer.visible);
      setGraphic(root);
    }
  }

  public void onVisibleCheckBox()
  {
    getListView().getSelectionModel().select(this.getIndex());

    ImageLayer l = Program.MAIN_CONTROLLER.getLayers().get(this.getIndex());
    l.visible = visibleCheckBox.isSelected();
    Program.MAIN_CONTROLLER.updateCanvas();
  }

  public void onTextFieldAction()
  {
    getListView().getSelectionModel().select(this.getIndex());
  }

  public void onTextFieldClick()
  {
    getListView().getSelectionModel().select(this.getIndex());
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