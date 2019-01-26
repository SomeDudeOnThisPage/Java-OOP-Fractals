package program.system;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
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

  public HashMap<String, AlgorithmSetting> getSettings()
  {
    return settings;
  }

  public void updateSetting(String key, Number value)
  {
    AlgorithmSetting currentSetting = settings.get(key);
    currentSetting.setValue(value);
  }

  public static JSONObject toJSON(Fractal fractal)
  {
    JSONObject object = new JSONObject();
    fractal.getSettings().forEach((String key, AlgorithmSetting value) -> object.put(key, value.getValue()));

    return object;
  }

  /**
   * Constructor for the curve class
   */
  public Fractal()
  {
    settings = new HashMap<>();
  }
}
