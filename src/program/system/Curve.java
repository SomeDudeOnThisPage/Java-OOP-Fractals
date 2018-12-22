package program.system;

import program.Program;

import java.util.HashMap;

/**
 * Abstract class serving as a template for various algorithms
 * <p>
 *   This class contains the shared variables between all algorithms
 * </p>
 * @author Robin Buhlmann
 * @version 0.1
 */
public abstract class Curve
{
  /**
   * The settings for an algorithm are stored in this HashMap
   */
  protected HashMap<String, AlgorithmSetting> settings;

  public HashMap<String, AlgorithmSetting> getSettings()
  {
    return settings;
  }

  /**
   * Constructor for the curve class
   */
  public Curve()
  {
    settings = new HashMap<>();
  }
}
