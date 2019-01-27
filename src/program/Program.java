package program;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import program.ui.MainController;
import javafx.application.Application;

import javafx.fxml.*;
import javafx.scene.*;
import javafx.stage.*;

import java.util.*;

/**
 * Java OOP Project - Space Filling Curves<br>
 * <p>
 *  Frankfurt University of Applied Sciences<br>
 *  Faculty 2 - Informatics and Engineering<br>
 *  Module: Advanced Object Oriented Programming in Java<br>
 *  Semester 3<br>
 * <p>
 *   The main goal of this project is the concurrent visualization of space filling curves
 * <p>
 *   Command line arguments: <br>
 *   -debug boolean - If debug messages should be written in the console as the program runs<br>
 *   -auto_redraw boolean - If a layer should be redrawn instantly if any values are changed<br>
 *   -load string - A .json file that is to be loaded when the program starts. (Path from 'saves' folder)<br>
 *   -suppress_warnings boolean - If some warning dialogs should be suppressed<br>
 * @author Robin Buhlmann
 * @author Leonard Pudwitz
 * @version 0.8
 */
public class Program extends Application
{
  /**
   * Determines whether debug-messages are being output to the console. Default 'false'
   */
  public static boolean DEBUG = false;

  /**
   * Determines whether layers are automatically redrawn on a setting change. Default 'true'
   */
  public static boolean AUTO_REDRAW = true;

  /**
   * Determines whether some warning dialogs should be suppressed
   */
  public static boolean SUPPRESS_WARNINGS = false;

  /**
   * The directory the save folder is in. This will differ in between linux and windows. It is set on program start
   */
  public static final String SAVE_DIRECTORY = "saves/"; // hardcoded for now

  /**
   * If this is set to anything but an empty string before calling launch(), this files algorithms will be loaded on startup. Default: ''
   * Path is canonical from the jars directory
   */
  public static String STARTUP_LOAD_FILE = ""; // TODO

  /**
   * Default and minimum window width
   */
  private static final int WIDTH = 800;

  /**
   * Default and minimum window height
   */
  private static final int HEIGHT = 600;

  /**
   * Window title
   */
  private static final String TITLE = "Lemme sniff ur b00t€-hole";

  /**
   * Path to program resources in source packages. Used for fxml and css
   */
  public static final String RESOURCE_PATH = "/program/resources/";

  /**
   * The programs current controller instance. There should only ever be one of these
   */
  public static MainController ui;

  /**
   * Used to show debug messages in the console when the debug flag is set
   * @param msg The message
   */
  public static void debug(Object msg)
  {
    if (DEBUG)
    {
      System.out.println("[DEBUG] " + System.nanoTime() + " " + msg);
    }
  }

  private static Program self;

  public static void exit()
  {
    Program.debug("Running application closing procedure");

    if (!SUPPRESS_WARNINGS)
    {
      // Show an alert dialog
      Alert dialog = new Alert(Alert.AlertType.CONFIRMATION);
      dialog.setTitle("Are you sure you want to exit?");
      dialog.setHeaderText(null);
      dialog.setContentText("Are you sure you want to exit?\nAny unsaved changes will be lost.");

      // Add a custom icon.
      Stage stage = (Stage) dialog.getDialogPane().getScene().getWindow();
      stage.getIcons().add(new Image(Program.self.getClass().getResource(RESOURCE_PATH + "thinking_about_exiting.png").toString()));

      Optional<ButtonType> result = dialog.showAndWait();
      if (result.isPresent() && result.get() == ButtonType.OK) { Platform.exit(); }
    }
    else
    {
      Platform.exit();
    }
  }

  /**
   * The start method initializes the Stage and Scene object and loads the necessary fxml content
   * @param frame The stage that is to be used
   * @throws Exception Fatal exception, if this fires we messed up
   */
  @Override
  public void start(Stage frame) throws Exception
  {
    self = this;

    frame.setMinWidth(WIDTH);
    frame.setMinHeight(HEIGHT);
    frame.setTitle(TITLE);

    frame.setOnCloseRequest(e -> {
      e.consume();
      exit();
    });

    FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource(RESOURCE_PATH + "fxml/main.fxml"));
    ui = new MainController();
    fxmlloader.setController(ui);

    Scene scene = new Scene(fxmlloader.load());
    scene.getStylesheets().add(getClass().getResource(RESOURCE_PATH + "stylesheet.css").toString());

    frame.setScene(scene);
    frame.show();
  }

  /**
   * Maim method checking for commandline arguments and launching the program
   * @param args Commandline arguments
   */
  public static void main(String[] args)
  {
    try
    {
      List<String> arguments = Arrays.asList(args);

      // Check for debug flag
      if (arguments.contains("-debug"))
      {
        Program.DEBUG = Boolean.valueOf(arguments.get(arguments.indexOf("-debug") + 1));
      }

      // Check for auto redraw flag
      if (arguments.contains("-auto_redraw"))
      {
        Program.AUTO_REDRAW = Boolean.valueOf(arguments.get(arguments.indexOf("-auto_redraw") + 1));
      }

      // Check if we load a json file at startup
      if (arguments.contains("-load"))
      {
        Program.STARTUP_LOAD_FILE = arguments.get(arguments.indexOf("-load") + 1);
      }

      // Check if certain warning dialogs should be suppressed
      if (arguments.contains("-suppress_warnings"))
      {
        Program.SUPPRESS_WARNINGS = Boolean.valueOf(arguments.get(arguments.indexOf("-suppress_warnings") + 1));
      }
    }
    catch (Exception ignored) {}

    Program.debug("Loading file " + STARTUP_LOAD_FILE + " on startup.");

    launch(args);
  }
}
