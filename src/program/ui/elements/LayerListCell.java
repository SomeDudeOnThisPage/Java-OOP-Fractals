package program.ui.elements;

import javafx.scene.control.*;
import program.Program;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.BorderPane;

// Custom list CellFactory
public class LayerListCell extends ListCell<ImageLayer>
{

  @FXML private BorderPane root;

  @FXML private CheckBox visibleCheckBox;
  @FXML private TextField textField;

  private FXMLLoader fxmlloader;
  private ImageLayer layer;


  @Override
  public void updateItem(ImageLayer layer, boolean empty)
  {
    super.updateItem(layer, empty);
    this.layer = layer;

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
    // Select the layer
    getListView().getSelectionModel().select(this.getIndex());

    // Set the 'visible' field of the ImageLayer to the CheckBox value
    layer.visible = visibleCheckBox.isSelected();

    // Update the canvas to actually show the changes
    Program.ui.updateCanvas();
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
      System.out.println("Could not load FXML for LayerListCell:" + e);
    }

    // Add listener to the TextField
    textField.textProperty().addListener((observable, oldValue, newValue) -> layer.setName(newValue));
  }
}