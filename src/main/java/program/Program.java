package program;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import program.ui.MainController;
import javafx.application.Application;

import javafx.fxml.*;
import javafx.scene.*;
import javafx.stage.*;

import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;
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
   * Should limits for iteration depth set in fractal classes be ignored?
   */
  public static boolean IGNORE_LIMITS = false;

  /**
   * Maximum # layers
   */
  public static int LAYERS_MAX = 100;

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
  private static final String TITLE = "SFCVisualizer";

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

  /**
   * Used for exiting purposes
   */
  private static Program self;

  /**
   * Used for dialog inheritance
   */
  private static Stage stage;

  public static void exit()
  {
    Program.debug("Running application closing procedure");

    if (!Program.SUPPRESS_WARNINGS)
    {
      // Show an alert dialog
      Alert dialog = new Alert(Alert.AlertType.CONFIRMATION);
      dialog.setTitle("Are you sure you want to exit?");
      dialog.setHeaderText(null);
      dialog.setContentText("Are you sure you want to exit?\nAny unsaved changes will be lost.");

      // Add a custom icon.
      Stage stage = (Stage) dialog.getDialogPane().getScene().getWindow();

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

    frame.getIcons().add(new Image(getClass().getResourceAsStream("/icon/icon.png")));

    frame.setOnCloseRequest(e -> {
      e.consume();
      Program.exit();
    });

    FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("/fxml/main.fxml"));
    ui = new MainController();
    fxmlloader.setController(ui);

    Scene scene = new Scene(fxmlloader.load());
    scene.getStylesheets().add(getClass().getResource("/stylesheet.css").toExternalForm());
    frame.setScene(scene);
    frame.show();
  }

  public static void showAlert(Exception e, String t)
  {
    // Custom exception alert from https://code.makery.ch/blog/javafx-dialogs-official/
    // Why is such an alert dialog not default in javafx??

    // Catch errors while saving / loading
    Alert alert = new Alert(Alert.AlertType.ERROR);
    alert.setTitle("Error!");
    alert.setHeaderText("Something bad has happened.");
    alert.setContentText(t);

    alert.initOwner(Program.stage);

    StringWriter sw = new StringWriter();
    PrintWriter pw = new PrintWriter(sw);
    e.printStackTrace(pw);
    String exceptionText = sw.toString();

    Label label = new Label("If you happen to see a programmer strolling about, please tell him the following:");

    TextArea textArea = new TextArea(exceptionText);
    textArea.setEditable(false);
    textArea.setWrapText(true);

    textArea.setMaxWidth(Double.MAX_VALUE);
    textArea.setMaxHeight(Double.MAX_VALUE);
    GridPane.setVgrow(textArea, Priority.ALWAYS);
    GridPane.setHgrow(textArea, Priority.ALWAYS);

    GridPane expContent = new GridPane();
    expContent.setMaxWidth(Double.MAX_VALUE);
    expContent.add(label, 0, 0);
    expContent.add(textArea, 0, 1);

    alert.getDialogPane().setExpandableContent(expContent);
    alert.showAndWait();

    Program.debug("Showing critical alert");
  }

  /**
   * Maim method checking for commandline arguments and launching the program
   * @param args Commandline arguments
   */
  public static void main(String[] args)
  {
    try
    {
      // Create 'saves' folder if not present
      File dir = new File("saves");
      dir.mkdir();


      List<String> arguments = Arrays.asList(args);

      // Check for debug flag
      if (arguments.contains("-debug"))
      {
        Program.DEBUG = true;
      }

      // Check for auto redraw flag
      if (arguments.contains("-no_auto_redraw"))
      {
        Program.AUTO_REDRAW = false;
      }

      // Check if we load a json file at startup ( no worries if it does not exist, we just get a small nonfatal caught and ignored error )
      if (arguments.contains("-load"))
      {
        Program.STARTUP_LOAD_FILE = arguments.get(arguments.indexOf("-load") + 1);
      }

      // Check if we load a json file at startup
      if (arguments.contains("-lmax"))
      {
        Program.STARTUP_LOAD_FILE = arguments.get((int) arguments.indexOf("-lmax") + 1);
      }

      // Check if certain warning dialogs should be suppressed
      if (arguments.contains("-suppress_warnings"))
      {
        Program.SUPPRESS_WARNINGS = true;
      }

      // Check if certain warning dialogs should be suppressed
      if (arguments.contains("-ignore_limits"))
      {
        Program.IGNORE_LIMITS = true;
      }
    }
    catch (Exception ignored) {}

    Program.debug("Loading file " + STARTUP_LOAD_FILE + " on startup.");

    launch(args);
  }
}
