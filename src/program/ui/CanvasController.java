package program.ui;

import program.Program;
import program.algorithm.TestAlg;
import program.system.ImageLayer;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;

import java.net.URL;
import java.util.ResourceBundle;

public class CanvasController implements Initializable {

  @FXML
  private BorderPane pane;

  public void update() {
    long time = System.currentTimeMillis();


    pane.getChildren().clear(); // Make it so we dont remove all
    ObservableList<ImageLayer> layers = Program.mainController.getLayers();
    // Traverse the list from the bottom up, so the upmost layer is also the upmost layer in the layerList
    for (int i = layers.size() - 1; i >= 0; i--) {
      ImageLayer l = layers.get(i);
      if (l.visible) {
        l.redraw();
        pane.getChildren().add(l);
      }
    }
    System.out.println(System.currentTimeMillis() - time);

  }

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    update();
  }

  public CanvasController() {

  }
}
