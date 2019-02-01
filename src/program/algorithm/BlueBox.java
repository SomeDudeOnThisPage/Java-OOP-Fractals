package program.algorithm;

import program.system.Fractal;
import program.ui.elements.AlgorithmSetting;
import program.ui.elements.GraphicsSetting;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;

/**
 * A simple debug class
 */
public class BlueBox extends Fractal
{
  public static long render(BufferedImage image, HashMap<String, AlgorithmSetting> settings, GraphicsSetting.Type mode, Color[] colors, double strokeWidth)
  {
    Graphics2D g = (Graphics2D) image.getGraphics();
    g.setColor(colors[0]);

    g.drawRect(100, 100, 50, 50);

    g.dispose();

    return 0;
  }

  public BlueBox()
  {
    super();
    settings.put("testDouble", new AlgorithmSetting<Double>("Test Double Setting", 0.0,1000.0,0.0,AlgorithmSetting.Type.SPINNER));
  }
}
