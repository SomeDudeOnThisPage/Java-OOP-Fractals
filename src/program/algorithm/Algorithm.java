package program.algorithm;

import program.system.Fractal;
import program.ui.elements.AlgorithmSetting;

import java.awt.image.BufferedImage;
import java.util.HashMap;

public enum Algorithm
{
  // Add custom algorithms here following the given format
  BLUEBOX
          {
              public Fractal newFractal() { return new BlueBox(); }
              public boolean debug = true;
              public void render(BufferedImage i, HashMap s) { BlueBox.render(i, s); }
              public String toString() { return "Blue Box"; }
          },
    REDBOX
            {
                public Fractal newFractal() { return new RedBox(); }
                public boolean debug = true;
                public void render(BufferedImage i, HashMap s) { RedBox.render(i, s); }
                public String toString() { return "Red Box"; }
            },
    DRAGON
            {
                public Fractal newFractal() { return new DragonCurve(); }
                public boolean debug = false;
                public void render(BufferedImage i, HashMap s) { DragonCurve.render(i, s); }
                public String toString() { return "Dragon Curve"; }
            },
    HILBERT
            {
                public Fractal newFractal() { return new HilbertCurve(); }
                public boolean debug = false;
                public void render(BufferedImage i, HashMap s) { HilbertCurve.render(i, s); }
                public String toString() { return "Hilbert Curve"; }
            },
    KOCHSNOWFLAKE
            {
                public Fractal newFractal() { return new KochSnowflakeCurve(); }
                public boolean debug = false;
                public void render(BufferedImage i, HashMap s) { KochSnowflakeCurve.render(i, s); }
                public String toString() { return "Koch Snowflake"; }
            },
    GOSPER
            {
                public Fractal newFractal() { return new GosperCurve(); }
                public boolean debug = false;
                public void render(BufferedImage i, HashMap s) { GosperCurve.render(i, s); }
                public String toString() { return "Gosper Curve"; }
            },
    PEANO
            {
                public Fractal newFractal() {return new PeanoCurve(); }
                public boolean debug = false;
                public void render(BufferedImage i, HashMap s) {PeanoCurve.render(i, s); }
                public String toString() {return "Peano Curve"; }
            },
    KOCHISLAND
            {
                public Fractal newFractal() {return new KochIslandCurve(); }
                public boolean debug = false;
                public void render(BufferedImage i, HashMap s) {KochIslandCurve.render(i, s); }
                public String toString() {return "Koch Island"; }
            },
    SIERPINSKISQUARE
            {
                public Fractal newFractal() {return new SierpinskiSquareCurve(); }
                public boolean debug = false;
                public void render(BufferedImage i, HashMap s) {SierpinskiSquareCurve.render(i, s); }
                public String toString() {return "Sierpinksi Square"; }
            },
    TIMEWASTER
            {
                public Fractal newFractal() { return new TimeWaster(); }
                public boolean debug = true;
                public void render(BufferedImage i, HashMap s) { TimeWaster.render(i, s); }
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
  public abstract void render(BufferedImage image, HashMap<String, AlgorithmSetting> settings);
}