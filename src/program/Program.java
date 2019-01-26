package program;

import program.ui.MainController;
import javafx.application.Application;

import javafx.fxml.*;
import javafx.scene.*;
import javafx.stage.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/*
    TODO: Some way to store program settings / options
    -> ini4j
    TODO: File loading / saving (saving either a single algorithm-setup or an entire list of algorithms and their setups)
    -> json
    TODO: Color modes
    -> Leo denk dir ma was aus wie man das nice machen könnte j00nge
 */

/**
 * Java OOP Project - Space Filling Curves<br>
 * <p>
 *  Frankfurt University of Applied Sciences<br>
 *  Faculty 2 - Informatics and Engineering<br>
 *  Module: Advanced Object Oriented Programming in Java<br>
 *  Semester 3<br>
 * </p>
 * <p>
 *  The main goal of this project is the concurrent visualization of space filling curves
 * </p>
 * @author Robin Buhlmann
 * @author Leonard Pudwitz
 * @version 0.1
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

  /**
   * The start method initializes the Stage and Scene object and loads the necessary fxml content
   * @param frame The stage that is to be used
   * @throws Exception Fatal exception, if this fires we messed up
   */
  @Override
  public void start(Stage frame) throws Exception
  {
    frame.setMinWidth(WIDTH);
    frame.setMinHeight(HEIGHT);
    frame.setTitle(TITLE);

    FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource(RESOURCE_PATH + "fxml/main.fxml"));
    ui = new MainController();
    fxmlloader.setController(ui);

    Scene scene = new Scene(fxmlloader.load());
    scene.getStylesheets().add(getClass().getResource(RESOURCE_PATH + "stylesheet.css").toString());

    frame.setScene(scene);
    frame.show();
  }

  /**
   * The stop method, used to close any unclosed file handlers and display the "Are you sure"-Dialog
   */
  @Override
  public void stop()
  {
    // Close any unclosed file handles, save current layers as last.json, and save any unsaved settings
    // Attempt to stop any currently running tasks
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

      if (arguments.contains("-load"))
      {
        Program.STARTUP_LOAD_FILE = arguments.get(arguments.indexOf("-load") + 1);
      }
    }
    catch (Exception ignored) {}

    Program.debug("Loading file " + STARTUP_LOAD_FILE + " on startup.");

    launch(args);
  }
}
