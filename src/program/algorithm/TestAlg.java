package program.algorithm;

import program.system.Curve;

import java.awt.*;
import java.awt.image.BufferedImage;

public class TestAlg extends Curve
{
  public static BufferedImage render(BufferedImage image)
  {

    Graphics2D g = (Graphics2D) image.getGraphics();
    g.setColor(Color.BLUE);

    g.drawRect(100, 100, 50, 50);

    g.dispose();

    return image;
  }

  public TestAlg()
  {
    super();
    // Initialize defaults
    // @Leo immer im constructor ALLE defaults von settings initialisieren und keine neuen mehr während lifetime des objects adden
    // Ansonsten explosion bumm
    //settings.add("TestSettingIndex", new LayerSetting<Double>("Test Setting", 5.0, 10.0, 0.0, LayerSetting.SettingType.SPINNER));
  }
}
