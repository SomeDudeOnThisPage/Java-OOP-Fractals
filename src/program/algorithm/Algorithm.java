package program.algorithm;

import program.Program;
import program.system.Fractal;
import program.ui.elements.AlgorithmSetting;
import program.ui.elements.ColorSetting;

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
              public void render(BufferedImage i, HashMap s, ColorSetting.Type m, Color[] c) { BlueBox.render(i, s, m, c); }
              public String toString() { return "Blue Box"; }
          },
    DRAGON
            {
                public Fractal newFractal() { return new DragonCurve(); }
                public boolean debug = false;
                public void render(BufferedImage i, HashMap s, ColorSetting.Type m, Color[] c) { DragonCurve.render(i, s, m, c); }
                public String toString() { return "Dragon Curve"; }
            },
    HILBERT
            {
                public Fractal newFractal() { return new HilbertCurve(); }
                public boolean debug = false;
                public void render(BufferedImage i, HashMap s, ColorSetting.Type m, Color[] c) { HilbertCurve.render(i, s, m, c); }
                public String toString() { return "Hilbert Curve"; }
            },
    KOCHSNOWFLAKE
            {
                public Fractal newFractal() { return new KochSnowflakeCurve(); }
                public boolean debug = false;
                public void render(BufferedImage i, HashMap s, ColorSetting.Type m, Color[] c) { KochSnowflakeCurve.render(i, s, m, c); }
                public String toString() { return "Koch Snowflake"; }
            },
    GOSPER
            {
                public Fractal newFractal() { return new GosperCurve(); }
                public boolean debug = false;
                public void render(BufferedImage i, HashMap s, ColorSetting.Type m, Color[] c) { GosperCurve.render(i, s, m, c); }
                public String toString() { return "Gosper Curve"; }
            },
    PEANO
            {
                public Fractal newFractal() {return new PeanoCurve(); }
                public boolean debug = false;
                public void render(BufferedImage i, HashMap s, ColorSetting.Type m, Color[] c) {PeanoCurve.render(i, s, m, c); }
                public String toString() {return "Peano Curve"; }
            },
    KOCHISLAND
            {
                public Fractal newFractal() {return new KochIslandCurve(); }
                public boolean debug = false;
                public void render(BufferedImage i, HashMap s, ColorSetting.Type m, Color[] c) {KochIslandCurve.render(i, s, m, c); }
                public String toString() {return "Koch Island"; }
            },
    SIERPINSKISQUARE
            {
                public Fractal newFractal() {return new SierpinskiSquareCurve(); }
                public boolean debug = false;
                public void render(BufferedImage i, HashMap s, ColorSetting.Type m, Color[] c) {SierpinskiSquareCurve.render(i, s, m, c); }
                public String toString() {return "Sierpinksi Square"; }
            },
    TIMEWASTER
            {
                public Fractal newFractal() { return new TimeWaster(); }
                public boolean debug = true;
                public void render(BufferedImage i, HashMap s, ColorSetting.Type m, Color[] c) { TimeWaster.render(i, s, m, c); }
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
   */
  public abstract void render(BufferedImage image, HashMap<String, AlgorithmSetting> settings, ColorSetting.Type mode, Color[] colors);
}