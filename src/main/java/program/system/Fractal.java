package program.system;

import org.json.simple.JSONObject;
import program.Program;
import program.algorithm.Algorithm;
import program.ui.elements.AlgorithmSetting;

import java.util.HashMap;

/**
 * Abstract class serving as a template for various algorithms
 * <p>
 *   This class contains the shared variables between all algorithms
 * </p>
 * @author Robin Buhlmann
 * @version 0.1
 */
public abstract class Fractal
{
  /**
   * The settings for an algorithm are stored in this HashMap
   */
  protected HashMap<String, AlgorithmSetting> settings;

  /**
   * Returns the fractals current settings
   * @return settings
   */
  public HashMap<String, AlgorithmSetting> getSettings()
  {
    return settings;
  }

  /**
   * Updates a setting with a new value, make sure to match the settings original data type
   * @param key The setting key
   * @param value The new value
   */
  public void updateSetting(String key, Number value)
  {
    AlgorithmSetting currentSetting = settings.get(key);
    Program.debug("Updating setting: " + key + " " + currentSetting + " with value = " + value);
    currentSetting.setValue(value);
  }

  /**
   * Serializes a fractal object to JSON
   * @param fractal The fractal to be serialized
   * @return JSON config
   */
  public static JSONObject toJSON(Fractal fractal)
  {
    JSONObject object = new JSONObject();
    fractal.getSettings().forEach((String key, AlgorithmSetting value) -> object.put(key, value.getValue()));

    return object;
  }

  /**
   * Deserializes a fractal object from JSON
   * @param config JSON string
   * @return A new Fractal object
   */
  public static Fractal fromJSON(JSONObject config)
  {
    Fractal fractal = Algorithm.valueOf(((String) config.get("algorithm"))).newFractal();

    JSONObject settingsConfig = (JSONObject) config.get("settings");

    for (Object o : settingsConfig.keySet())
    {
      String key = (String) o;
      Number value = (Number) settingsConfig.get(key);

      Program.debug(o + " " + value);

      fractal.updateSetting(key, value);
    }

    return fractal;
  }

  /**
   * Constructor for the curve class
   */
  public Fractal()
  {
    settings = new HashMap<>();
  }
}
