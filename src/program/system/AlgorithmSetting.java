package program.system;

import javafx.scene.layout.BorderPane;

public class AlgorithmSetting<T> extends BorderPane
{
  // TODO: enum Type is currently unused
  public enum Type {
    SPINNER, SLIDER
  }

  private T value;
  private T max;
  private T min;

  public void setValue(T value)
  {
    this.value = value;
  }

  public T getValue()
  {
    return value;
  }

  public AlgorithmSetting(T value, T max, T min)
  {
    this.value = value;
    this.max = max;
    this.min = min;
  }
}