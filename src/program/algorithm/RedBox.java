package program.algorithm;

import program.Program;
import program.system.AlgorithmSetting;
import program.system.Curve;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;

public class RedBox extends Curve
{
  public static BufferedImage render(BufferedImage image, HashMap<String, AlgorithmSetting> settings)
  {
    int posX = (int) settings.get("posX").getValue();
    int posY = (int) settings.get("posY").getValue();
    int sizeX = (int) settings.get("sizeX").getValue();
    int sizeY = (int) settings.get("sizeY").getValue();

    Graphics2D g = (Graphics2D) image.getGraphics();
    g.setColor(Color.RED);  // This will probably be replaced with a 'color-info' object or something in the future

    g.drawRect(posX, posY, sizeX, sizeY);

    g.dispose();

    return image;
  }

  public RedBox()
  {
    super();

    // Initialize defaults
    // @Leo immer im constructor ALLE defaults von settings initialisieren und keine neuen mehr während lifetime des objects adden
    // Ansonsten explosion bumm
    settings.put("posX", new AlgorithmSetting<Integer>(0,1000,0));
    settings.put("posY", new AlgorithmSetting<Integer>(0,1000,0));
    settings.put("sizeX", new AlgorithmSetting<Integer>(250,500,0));
    settings.put("sizeY", new AlgorithmSetting<Integer>(250,500,0));
  }
}
