package program.ui;

import program.Program;
import program.system.ImageLayer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;

import java.net.URL;
import java.util.*;

public class MainController implements Initializable {
  /**
   * The Editor that is a child of this instance
   */
  @FXML
  private BorderPane editor;

  /**
   * The EditorController that is a child of this instance
   */
  @FXML
  private EditorController editorController;

  /**
   * The CanvasController that is a child of this instance
   */
  @FXML private CanvasController canvasController;

  private ObservableList<ImageLayer> layers;
  private ImageLayer selectedLayer;

  /**
   * Redraws a layer by creating a new GraphicsTask with the GraphicsContext of an ImageLayer
   * Sets the GraphicsContext of the ImageLayer after task completion
   */
  public void updateLayer() {

  }

  /**
   * Adds a layer to the list and updates the canvasController and editorController
   */
  public void addLayer(ImageLayer l) {
    layers.add(l);
    editorController.editorLayerTabController.update();
    canvasController.update();
  }

  /**
   * Removes a layer from the list and updates the canvasController
   * Updating editorController is not needed as this is only called from there
   */
  public void removeLayer(int index) {
    layers.remove(index);
    canvasController.update();
  }

  public void setSelectedLayer(ImageLayer layer) {
    selectedLayer = layer;
    if (Program.DEBUG) Program.debug("Selected ImageLayer: Name='" + layer.name + "' " + layer);
  }

  public ObservableList<ImageLayer> getLayers() {
    return layers;
  }

  public void updateCanvas() {
    canvasController.update();
  }

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {

  }

  /**
   * Creates the ObservableArrayList instance holding the ImageLayers
   */
  public MainController() {
    layers = FXCollections.observableArrayList();
  }
}
