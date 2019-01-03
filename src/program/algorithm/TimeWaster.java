package program.algorithm;

import program.system.Fractal;
import program.ui.elements.AlgorithmSetting;

import java.util.HashMap;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Test-Algorithm to debug behavior when the algorithm takes a long time to generate
 */
public class TimeWaster extends Fractal
{
  public static void render(BufferedImage image, HashMap<String, AlgorithmSetting> settings)
  {
    int wastedTime = (int) settings.get("wastedTime").getValue();
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
        try
        {
          Thread.sleep(wastedTime);
        }
        catch (InterruptedException e)
        {
          e.printStackTrace();
        }

        Graphics2D g = (Graphics2D) image.getGraphics();
        g.setColor(Color.BLACK);

        g.fillRect(posX + (int) (Math.random()*800), posY + (int) (Math.random()*800), sizeX, sizeY);

        g.dispose();
      }
    }

    new Renderer();
  }

  public TimeWaster()
  {
    // Initialize defaults
    // Always initialize ALL defaults, max and min values in the constructor. Do not add / remove any later
    settings.put("wastedTime", new AlgorithmSetting<Integer>("Wasted Time", (int) (Math.random()*10000),100000,0,AlgorithmSetting.Type.SPINNER));
    settings.put("posX", new AlgorithmSetting<Integer>("Position X", 100,1000,0,AlgorithmSetting.Type.SPINNER));
    settings.put("posY", new AlgorithmSetting<Integer>("Position Y", 100,1000,0,AlgorithmSetting.Type.SPINNER));
    settings.put("sizeX", new AlgorithmSetting<Integer>("Size X", 10,500,0,AlgorithmSetting.Type.SPINNER));
    settings.put("sizeY", new AlgorithmSetting<Integer>("Size Y", 10,500,0,AlgorithmSetting.Type.SPINNER));
  }
}