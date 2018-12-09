package program.ui;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;


import java.net.URL;
import java.util.ResourceBundle;

public class EditorController implements Initializable {
  @FXML private Label editorLabel;
  @FXML public EditorLayerTabController editorLayerTabController;


  public void setLabel() {
    editorLabel.setText("Editor");
  }

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
  }
}