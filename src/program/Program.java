package program;

import program.system.ImageLayer;
import program.ui.EditorController;
import program.ui.EditorLayerTabController;
import program.ui.MainController;
import javafx.application.Application;

import javafx.fxml.*;
import javafx.scene.*;
import javafx.stage.*;

/**
 * Java OOP Project - Space Filling Curves
 * Starts the program and sets up the relevant scenes through FXML
 *
 * @author Robin Buhlmann
 * @version 0.1
 */
public class Program extends Application {

  public static boolean DEBUG = false;

  public static String RESOURCE_PATH = "/program/resources/";
  public static String TITLE = "SpaceCurve8000";

  public static MainController mainController;

  private Stage frame;

  public static void main(String[] args) {
    if (args[0].equals("-debug")) {
      DEBUG = true;
    }
    launch(args);
  }

  public static void debug(String msg) {
    System.out.println("[DEBUG] " + System.nanoTime() + " " + msg);
  }

  private void initFrame() throws Exception {
    frame.setMinHeight(600);
    frame.setMinWidth(850);

    frame.setTitle(TITLE);

    FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource(RESOURCE_PATH + "fxml/main.fxml"));
    mainController = new MainController();
    fxmlloader.setController(mainController);

    frame.setScene(new Scene(fxmlloader.load(), 300, 275));

    frame.show();
  }

  @Override
  public void start(Stage stage) throws Exception {
    frame = stage;
    initFrame();
    mainController.addLayer(new ImageLayer("Hello", 500,500));
    mainController.addLayer(new ImageLayer("World", 500,500));
  }
}
