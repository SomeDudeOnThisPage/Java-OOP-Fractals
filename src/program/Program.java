package program;

import program.ui.MainController;
import javafx.application.Application;

import javafx.fxml.*;
import javafx.scene.*;
import javafx.stage.*;

import java.util.HashMap;

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
  public static boolean DEBUG = false;

  private static final int WIDTH = 800;
  private static final int HEIGHT = 600;
  private static final String TITLE = "Lemme sniff ur b00t€-hole";

  public static final String RESOURCE_PATH = "/program/resources/";

  public static MainController ui;

  public static HashMap<String, Boolean> settings;

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

  @Override
  public void stop()
  {
    // Close any unclosed file handles, save current layers as last.json, and save any unsaved settings
    // Attempt to stop any currently running tasks
  }

  public static void main(String[] args)
  {
    try
    {
      for(String str : args)
      {
        if (str.equals("-debug"))
          DEBUG = true;
      }
    }
    catch (Exception ignored) {}

    launch(args);
  }
}
