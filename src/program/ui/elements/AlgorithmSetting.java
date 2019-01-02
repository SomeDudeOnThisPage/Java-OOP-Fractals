package program.ui.elements;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.layout.*;
import program.Program;

public class AlgorithmSetting<T extends Number> extends BorderPane
{
  public enum Type
  {
    SPINNER, SLIDER
  }

  private T value;

  public void setValue(T value)
  {
    this.value = value;
  }

  public T getValue()
  {
    return value;
  }

  public AlgorithmSetting(String name, T value, T max, T min, Type type)
  {
    this.value = value;

    this.setPadding(new Insets(5,5,5,5));
    this.setTop(new Label(name));

    if (type == Type.SPINNER)
    {
      if (value instanceof Integer)
      {
        // Case value instanceof Integer - create an integer spinner with integer values and an IntegerSpinnerValueFactory
        Spinner<Integer> s = new Spinner<>();
        SpinnerValueFactory<Integer> svf = new SpinnerValueFactory.IntegerSpinnerValueFactory((int) min, (int) max, (int) this.value);
        s.setValueFactory(svf);
        this.setCenter(s);
      }
      else if (value instanceof Double)
      {
        // Case value instanceof Double - create a double spinner with double values and a DoubleSpinnerValueFactory
        Spinner<Double> s = new Spinner<>();
        SpinnerValueFactory<Double> svf = new SpinnerValueFactory.DoubleSpinnerValueFactory((double) min, (double) max, (double) this.value);
        s.setValueFactory(svf);
        this.setCenter(s);
      }
      else
      {
        // TODO: String value spinner?
        Program.debug("Could not determine data type for setting " + this + " with values (value, min, max): " + value + " " + min + " " + max + ". The spinner has not been setup.");
      }
    }

  }
}