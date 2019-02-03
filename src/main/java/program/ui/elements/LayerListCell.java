package program.ui.elements;

import javafx.collections.ObservableList;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import org.json.simple.JSONObject;
import program.Program;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.BorderPane;
import program.algorithm.Algorithm;
import program.system.FileTask;
import java.util.Optional;


/**
 * A custom ListCell for the LayerListView
 * <p>
 *   A cell for a list containing an ImageLayer
 * </p>
 * @author Robin Buhlmann
 * @version 0.5
 */
public class LayerListCell extends ListCell<ImageLayer>
{

  /**
   * The root pane of the cell
   */
  @FXML private BorderPane root;

  /**
   * CheckBox for toggling visibility
   */
  @FXML private CheckBox visibleCheckBox;

  /**
   * TextField to change the name of the contained layer
   */
  @FXML private TextField textField;

  /**
   * A progress indicator indicates progress. The more you know...
   */
  @FXML public ProgressIndicator progressIndicator;

  /**
   * FXMLLoader used to load the cells FXML
   */
  FXMLLoader fxmlloader;

  /**
   * The contained and managed ImageLayer
   */
  private ImageLayer layer;

  /**
   * Updates the cell itself, this is used to update the contained nodes
   * @param layer The layer to be used, can change
   * @param empty Wether the cell is empty or not
   */
  @Override
  public void updateItem(ImageLayer layer, boolean empty)
  {
    super.updateItem(layer, empty);
    this.layer = layer;

    // Update the layers cell reference so it can manipulate the progress indicator
    //layer.cell = this;

    if (!this.isFocused())
    {
      textField.setCursor(Cursor.DEFAULT);
    }
    else
    {
      textField.setCursor(Cursor.TEXT);
    }

    if (empty)
    {
      setText(null);
      setGraphic(null);
    }
    else
    {
      if (layer.renderService.isRunning())
      {
        root.setRight(progressIndicator);
      }
      else
      {
        root.setRight(null);
      }

      textField.setText(layer.name);
      visibleCheckBox.setSelected(layer.visible);

      setGraphic(root);
    }
  }

  /**
   * Fires when the check box is presses, toggles visibility
   */
  public void onVisibleCheckBox()
  {
    // Select the layer
    getListView().getSelectionModel().select(this.getIndex());

    // Set the 'visible' field of the ImageLayer to the CheckBox value
    layer.visible = visibleCheckBox.isSelected();

    // Update the canvas to actually show the changes
    Program.ui.canvas().update();
  }

  /**
   * Select the cell when the contained TextField was selected
   */
  public void onTextFieldAction()
  {
    getListView().getSelectionModel().select(this.getIndex());
  }

  /**
   * Select the cell when the contained TextField was clicked
   */
  public void onTextFieldClick()
  {
    // Remove focus from textfield on first click
    if (!this.isFocused())
    {
      this.requestFocus();
    }
    getListView().getSelectionModel().select(this.getIndex());
  }

  /**
   * Constructs a new LayerListCell and sets a context menu on all non-empty cells
   */
  public LayerListCell()
  {
    try
    {
      fxmlloader = new FXMLLoader(getClass().getResource("/fxml/layerlist_cell.fxml"));
      fxmlloader.setController(this);
      root = fxmlloader.load();
    }
    catch(Exception e)
    {
      System.out.println("Could not load FXML for LayerListCell:" + e);
    }

    // Add listener to the TextField
    textField.textProperty().addListener((observable, oldValue, newValue) -> layer.setName(newValue));

    // Prepare Progress Indicator
    //progressIndicator.setVisible(true);

    //
    // Setup context menu for each cell
    //

    ContextMenu c = new ContextMenu();

    // Context menu entry for saving a layer
    MenuItem saveMI = new MenuItem("Save As");
    saveMI.setOnAction(event -> {
      // Create a dialog asking for the name of the file to be saved
      TextInputDialog dialog = new TextInputDialog();
      dialog.setTitle("Save layer");
      dialog.setHeaderText(null);
      dialog.setContentText("Choose a file name:");

      JSONObject config = new JSONObject();

      Optional<String> result = dialog.showAndWait();
      result.ifPresent(s -> {
        if (result.get().equals(""))
        {
          Program.ui.setStatus("Could not save layer - No filename specified.");
          return;
        }

        // As this method is invoked by clicking a context menu of a layer, we always have a layer - no need to check

        FileTask t = new FileTask(Program.SAVE_DIRECTORY + result.get() + ".json", Program.ui.getLayers().get(this.getIndex()));

        Thread th = new Thread(t);
        th.setDaemon(true);
        th.start();
      });
    });

    // Context menu entry for deleting a layer
    MenuItem deleteMI = new MenuItem("Delete");
    deleteMI.setOnAction(event -> Program.ui.removeLayer(this.getIndex()));

    // Context menu entry to toggle visibility
    MenuItem toggleVisibilityMI = new MenuItem("Toggle Visibility");
    toggleVisibilityMI.setOnAction(event -> {
      layer.visible = !layer.visible;
      visibleCheckBox.setSelected(layer.visible);
      Program.ui.canvas().update();
    });

    // Context menu entry to redraw manually
    MenuItem redrawMI = new MenuItem("Redraw");
    redrawMI.setOnAction(event -> layer.redraw());

    // Context menu with sub-menus to change the algorithm type of a layer
    Menu changeAlgorithmM = new Menu("Set Algorithm");

    // Populate the menu with all algorithms (no debug algorithms if Program.DEBUG == true)
    ObservableList<Algorithm> algs = Program.ui.getAlgorithms();

    for (Algorithm alg : algs)
    {
      if (!alg.isDebug() || Program.DEBUG)
      {
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
      if (isEmpty)
      {
        this.setContextMenu(null);
      }
      else
      {
        this.setContextMenu(c);
      }
    });

    this.getStyleClass().add("layer-list-cell");

    visibleCheckBox.setCache(false);
    // Forward context menu requests from the text field to our context menu
    textField.setContextMenu(c);
  }
}