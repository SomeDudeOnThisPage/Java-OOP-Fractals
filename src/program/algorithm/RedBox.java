package program.algorithm;

import program.system.Fractal;
import program.ui.elements.AlgorithmSetting;

import java.util.HashMap;

import java.awt.*;
import java.awt.image.BufferedImage;

public class RedBox extends Fractal
{
  public static void render(BufferedImage image, HashMap<String, AlgorithmSetting> settings)
  {
    // Retrieving all settings before actually starting the rendering process (not strictly needed to be done this way)
    // Settings are stored as a generic so they have to be cast to the type they were created as
    int posX = (int) settings.get("posX").getValue();
    int posY = (int) settings.get("posY").getValue();
    int sizeX = (int) settings.get("sizeX").getValue();
    int sizeY = (int) settings.get("sizeY").getValue();

    // Creating a local class to have access to multiple methods if needed
    // For the RedBox example, this is obviously not needed and only here to show how multi-method algorithms could be constructed
    class Renderer
    {
      //private void doSomething() {}

      private Renderer()
      {
        // doSomething();

        Graphics2D g = (Graphics2D) image.getGraphics();
        g.setColor(Color.RED);  // This will probably be replaced with a 'color-info' object or something in the future

        g.drawRect(posX, posY, sizeX, sizeY);

        g.dispose();
      }
    }

    new Renderer();
  }

  public RedBox()
  {
    // Initialize defaults
    // Always initialize ALL defaults, max and min values in the constructor. Do not add / remove any later
    settings.put("posX", new AlgorithmSetting<Integer>("Position X", 0,1000,0,AlgorithmSetting.Type.SPINNER));
    settings.put("posY", new AlgorithmSetting<Integer>("Position Y", 0,1000,0,AlgorithmSetting.Type.SPINNER));
    settings.put("sizeX", new AlgorithmSetting<Integer>("Size X", 250,500,0,AlgorithmSetting.Type.SPINNER));
    settings.put("sizeY", new AlgorithmSetting<Integer>("Size Y", 250,500,0,AlgorithmSetting.Type.SPINNER));
  }
}
