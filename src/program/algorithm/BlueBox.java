package program.algorithm;

import program.system.AlgorithmSetting;
import program.system.Curve;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;

public class BlueBox extends Curve
{
  public static BufferedImage render(BufferedImage image, HashMap<String, AlgorithmSetting> settings)
  {
    Graphics2D g = (Graphics2D) image.getGraphics();
    g.setColor(Color.BLUE);

    g.drawRect(100, 100, 50, 50);

    g.dispose();

    return image;
  }

  public BlueBox()
  {
    super();
    /*
     * Initialize defaults
     * @Leo immer im constructor ALLE defaults von settings initialisieren und keine neuen mehr während lifetime des objects adden
     * Ansonsten explosion bumm
     * -> example: settings.put("stringSettingName", new AlgorithmSetting<Type>(default, max, min));
     * -> set max/min to null if not applicable
     * -> TODO: Types of setting display (spinner / slider)
     */
  }
}
