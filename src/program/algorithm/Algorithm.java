package program.algorithm;

import program.system.AlgorithmSetting;
import program.system.Curve;

import java.awt.image.BufferedImage;
import java.util.HashMap;

public enum Algorithm
{
  // Add custom algorithms here following the given format
  BLUEBOX
    {
      public Curve newCurve() { return new BlueBox(); }
      public boolean debug = true;
      public void render(BufferedImage i, HashMap<String, AlgorithmSetting> s) { BlueBox.render(i, s); }
      public String toString() { return "Blue Box"; }
    },
  REDBOX
    {
      public Curve newCurve() { return new RedBox(); }
      public boolean debug = true;
      public void render(BufferedImage i, HashMap<String, AlgorithmSetting> s) { RedBox.render(i, s); }
      public String toString() { return "Red Box"; }
    };

  /**
   * Determines whether the algorithm is available only in debug mode
   */
  public boolean debug;

  /**
   * This method should return a new Curve object for each algorithm
   * @return curve The Curve object to be returned
   */
  public abstract Curve newCurve();

  /**
   * The method executing the render() method of the corresponding algorithm
   * @param image The BufferedImage object to be edited
   * @param settings The HashMap containing the AlgorithmSettings objects
   */
  public abstract void render(BufferedImage image, HashMap<String, AlgorithmSetting> settings);
}