package program.ui.elements;

import javafx.collections.ObservableList;
import javafx.scene.control.*;
import program.Program;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.BorderPane;
import program.algorithm.Algorithm;


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

    // Setup context menu for each cell
    ContextMenu c = new ContextMenu();

    // Context menu entry for saving a layer
    MenuItem saveMI = new MenuItem("Save As...");
    saveMI.setOnAction(event -> Program.debug("TODO: Enable layer saving in context menu"));

    // Context menu entry for deleting a layer
    MenuItem deleteMI = new MenuItem("Delete");
    deleteMI.setOnAction(event -> Program.ui.removeLayer(this.getIndex()));

    // Context menu entry to toggle visibility
    MenuItem toggleVisibilityMI = new MenuItem("Toggle Visibility");
    toggleVisibilityMI.setOnAction(event -> {
      layer.visible = !layer.visible;
      visibleCheckBox.setSelected(layer.visible);
      Program.ui.updateCanvas();
    });

    // Context menu entry to redraw manually
    MenuItem redrawMI = new MenuItem("Redraw");
    redrawMI.setOnAction(event -> layer.redraw());

    // Context menu with sub-menus to change the algorithm type of a layer
    Menu changeAlgorithmM = new Menu("Set Algorithm");

    // Populate the menu with all algorithms (no debug algorithms if Program.DEBUG == true)
    ObservableList<Algorithm> algs = Program.ui.getAlgorithms();

    for (int i = 0; i < algs.size(); i++)
    {
      if (!algs.get(i).debug || Program.DEBUG)
      {
        Algorithm alg = algs.get(i);

        MenuItem m = new MenuItem(alg.toString());
        m.setOnAction(event -> {
          layer.changeAlgorithmType(alg);
          if (Program.AUTO_REDRAW)
          {
            layer.redraw();
          }
        });

        changeAlgorithmM.getItems().add(m);
      }
    }

    c.getItems().addAll(saveMI, deleteMI, new SeparatorMenuItem(), toggleVisibilityMI, redrawMI, new SeparatorMenuItem(), changeAlgorithmM);

    // Enable context menu, but only on non-empty cells
    this.emptyProperty().addListener((obs, wasEmpty, isEmpty) -> {
      if (isEmpty) {
        this.setContextMenu(null);
      } else {
        this.setContextMenu(c);
      }
    });

    // Forward context menu requests from the text field to our context menu
    textField.setContextMenu(c);
  }
}