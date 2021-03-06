package program.algorithm;

import program.system.Fractal;
import program.ui.elements.AlgorithmSetting;
import program.ui.elements.GraphicsSetting;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;

public enum Algorithm
{
  // Add custom algorithms here following the given format
  BLUEBOX
          {
              public Fractal newFractal() { return new BlueBox(); }
              public boolean debug = true;
              public long render(BufferedImage i, HashMap s, GraphicsSetting.Type m, Color[] c, double sw) { return BlueBox.render(i, s, m, c, sw); }
              public String toString() { return "Blue Box"; }
          },
    DRAGON
            {
                public Fractal newFractal() { return new DragonCurve(); }
                public boolean debug = false;
                public long render(BufferedImage i, HashMap s, GraphicsSetting.Type m, Color[] c, double sw) { return DragonCurve.render(i, s, m, c, sw); }
                public String toString() { return "Dragon Curve"; }
            },
    HILBERT
            {
                public Fractal newFractal() { return new HilbertCurve(); }
                public boolean debug = false;
                public long render(BufferedImage i, HashMap s, GraphicsSetting.Type m, Color[] c, double sw) { return HilbertCurve.render(i, s, m, c, sw); }
                public String toString() { return "Hilbert Curve"; }
            },
    KOCHSNOWFLAKE
            {
                public Fractal newFractal() { return new KochSnowflakeCurve(); }
                public boolean debug = false;
                public long render(BufferedImage i, HashMap s, GraphicsSetting.Type m, Color[] c, double sw) { return KochSnowflakeCurve.render(i, s, m, c, sw); }
                public String toString() { return "Koch Snowflake"; }
            },
    GOSPER
            {
                public Fractal newFractal() { return new GosperCurve(); }
                public boolean debug = false;
                public long render(BufferedImage i, HashMap s, GraphicsSetting.Type m, Color[] c, double sw) { return GosperCurve.render(i, s, m, c, sw); }
                public String toString() { return "Gosper Curve"; }
            },
    PEANO
            {
                public Fractal newFractal() {return new PeanoCurve(); }
                public boolean debug = false;
                public long render(BufferedImage i, HashMap s, GraphicsSetting.Type m, Color[] c, double sw) { return PeanoCurve.render(i, s, m, c, sw); }
                public String toString() {return "Peano Curve"; }
            },
    KOCH
            {
                public Fractal newFractal() {return new KochCurve(); }
                public boolean debug = false;
                public long render(BufferedImage i, HashMap s, GraphicsSetting.Type m, Color[] c, double sw) { return KochCurve.render(i, s, m, c, sw); }
                public String toString() {return "Koch Curve"; }
            },
    SIERPINSKISQUARE
            {
                public Fractal newFractal() {return new SierpinskiSquareCurve(); }
                public boolean debug = false;
                public long render(BufferedImage i, HashMap s, GraphicsSetting.Type m, Color[] c, double sw) { return SierpinskiSquareCurve.render(i, s, m, c, sw); }
                public String toString() {return "Sierpinksi Square"; }
            },
    TIMEWASTER
            {
                public Fractal newFractal() { return new TimeWaster(); }
                public boolean debug = true;
                public long render(BufferedImage i, HashMap s, GraphicsSetting.Type m, Color[] c, double sw) { return TimeWaster.render(i, s, m, c, sw); }
                public String toString() { return "Time Waster"; }
            };

  /**
   * Determines whether the algorithm is available only in debug mode
   */
  public boolean debug;

  /**
   * This method should return a new Fractal object for each algorithm
   * @return curve The Fractal object to be returned
   */
  public abstract Fractal newFractal();

  /**
   * The method executing the render() method of the corresponding algorithm
   * @param image The BufferedImage object to be edited
   * @param settings The HashMap containing the AlgorithmSettings objects
   * @param mode The Drawing Mode {Solid, Alternating, Gradient}
   * @param colors An array of up to two colors, depending on the mode
   * @param strokeWidth The line width of the curve
   * @return
   */
  public abstract long render(BufferedImage image, HashMap<String, AlgorithmSetting> settings, GraphicsSetting.Type mode, Color[] colors, double strokeWidth);
}