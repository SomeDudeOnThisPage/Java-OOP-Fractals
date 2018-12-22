package program.ui;

import javafx.scene.Group;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.*;
import javafx.scene.transform.Scale;
import program.Program;
import program.system.ImageLayer;
import javafx.collections.ObservableList;
import javafx.fxml.*;

import java.net.URL;
import java.util.ResourceBundle;

public class CanvasController implements Initializable
{

  /**
   * The pane that the AnchorPane, which serves as the anchor for the ImageLayers, lies on
   * HBox is used so we have simple access to a center method
   */
  @FXML
  private HBox canvasBackground;

  @FXML
  private ScrollPane scrollPane;

  @FXML
  private StackPane stackPane;

  @FXML
  private Group contentGroup;

  /**
   * The pane that the ImageLayers are children of
   */
  @FXML
  private AnchorPane canvasAnchor;

  private int size = 800;
  private int minsize = 500;

  private double zoomFactor = 1;
  /**
   * Re-adds all layers to the canvasAnchor, setting visibility accordingly
   */
  public void update()
  {
    // Remove all children from the canvasAnchor
    canvasAnchor.getChildren().clear();

    // Retrieve the (probably updated) layerList from the MAIN_CONTROLLER
    ObservableList<ImageLayer> layers = Program.MAIN_CONTROLLER.getLayers();

    // Iterate over the layers, add them and set visible/invisible
    for (int i = layers.size() - 1; i >= 0; i--)
    {
      ImageLayer l = layers.get(i);

      canvasAnchor.getChildren().add(l);

      l.setVisible(l.visible);
    }
  }

  public void setAnchorSize(int size)
  {
    // PrefSize == Size of the layers' parent AnchorPane
    canvasAnchor.setPrefSize(size, size);
    this.size = size;
  }

  public int getAnchorSize()
  {
    return size;
  }

  final double SCALE_DELTA = 1.01;

  /**
   * zoomtomousedesc
   * @param event
   */
  public void onScroll(ScrollEvent event)
  {
    // scaleFactor = SCALE_DELTA -> if deltaY retrieved from event > 0 scale up, if < 0 scale down
    double scaleFactor = (event.getDeltaY() > 0) ? SCALE_DELTA : 1/SCALE_DELTA;

    // Only scale if the transformation would not make the canvasAnchor smaller as the minimum size defined in this class
    // Same applies for maximum size
    if (!(scaleFactor < 1 && canvasAnchor.getBoundsInParent().getHeight() * scaleFactor < minsize))
      canvasAnchor.getTransforms().add(new Scale(scaleFactor, scaleFactor, 0, 0));
  }

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle)
  {
    // Redirect the scroll panes' scroll events to our onScroll so we do not get the default scroll behaviour of ScrollPane
    scrollPane.addEventFilter(ScrollEvent.ANY, this::onScroll);

    // Update to initially add any already existing ImageLayers in the mainControllers' layer list
    update();
  }

  public CanvasController()
  {

  }
}
