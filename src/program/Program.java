package program;

import program.ui.MainController;
import javafx.application.Application;

import javafx.fxml.*;
import javafx.scene.*;
import javafx.stage.*;

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
  public static boolean DEBUG = false;

  static int WIDTH = 800;
  private static int HEIGHT = 600;
  private static String TITLE = "SpaceCurve8000";

  public static String RESOURCE_PATH = "/program/resources/";

  public static MainController MAIN_CONTROLLER;
  private static Stage frame;

  /**
   * Used to show debug messages in the console when the debug flag is set
   * @param msg The message
   */
  public static void debug(String msg)
  {
    if (DEBUG)
      System.out.println("[DEBUG] " + System.nanoTime() + " " + msg);
  }

  /**
   * Sets up the frame and embeds the main scene
   * @throws Exception
   */
  private void initFrame() throws Exception
  {
    frame.setMinWidth(WIDTH);
    frame.setMinHeight(HEIGHT);
    frame.setTitle(TITLE);

    FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource(RESOURCE_PATH + "fxml/main.fxml"));
    MAIN_CONTROLLER = new MainController();
    fxmlloader.setController(MAIN_CONTROLLER);

    frame.setScene(new Scene(fxmlloader.load(), 300, 275));
    frame.show();
  }

  @Override
  public void start(Stage stage) throws Exception
  {
    frame = stage;
    initFrame();
  }

  @Override
  public void stop()
  {

  }

  public static void main(String[] args)
  {
    try
    {
      if (args[0].equals("-debug"))
      {
        DEBUG = true;
      }
    }
    catch (Exception ignored)
    {

    }

    launch(args);
  }
}
