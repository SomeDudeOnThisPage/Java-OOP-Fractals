package program.ui.elements;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.layout.*;
import program.Program;

/**
 * A numeric setting for an algorithm - can be of either integer or double type
 * <p>
 *   A generic class creating a pane with a spinner or slider attached.
 *   The data-type of the spinner or slider can be either integer or double.
 *   These settings are displayed in the EditorSettingsTab when the according ImageLayer has been selected.
 * </p>
 * @author Robin Buhlmann
 * @author Leonard Pudwitz
 * @version 0.1
 */
public class AlgorithmSetting<T extends Number> extends BorderPane
{
  /**
   * Enum holding the supported Setting-Types.
   */
  public enum Type
  {
    SPINNER, SLIDER
  }

  /**
   * The current actual value held by the setting.
   */
  private T value;

  /**
   * Returns the current value of the setting. Needs to be cast to the corresponding data-type upon retrieval.
   * @return The value of the setting
   */
  public T getValue()
  {
    return value;
  }

  /**
   * Creates an AlgorithmSetting object. These should be created in the constructor of an Algorithm object.
   * @param name The name of the setting as displayed on the UI
   * @param value The initial value of the setting
   * @param max The max value of the setting
   * @param min The min value of the setting
   * @param type The type of the setting, see AlgorithmSetting.Type
   */
  public AlgorithmSetting(String name, T value, T max, T min, Type type)
  {
    this.value = value;

    this.setPadding(new Insets(5,5,5,5));
    this.setTop(new Label(name));

    // Create a spinner-type AlgorithmSetting
    if (type == Type.SPINNER)
    {
      if (value instanceof Integer)
      {
        // Case value instanceof Integer - create an integer spinner with integer values and an IntegerSpinnerValueFactory
        Spinner<Integer> s = new Spinner<>();
        SpinnerValueFactory<Integer> svf = new SpinnerValueFactory.IntegerSpinnerValueFactory((int) min, (int) max, (int) this.value);
        s.setValueFactory(svf);
        s.setPrefWidth(Double.MAX_VALUE);
        s.setEditable(true);

        // Add a value listener to the spinner
        svf.valueProperty().addListener((observable, oldValue, newValue) -> {
          // Not a great way to cast to <T>, but sufficient because we know that newValue is definitely an integer
          this.value = (T) newValue;
          Program.ui.getSelectedLayer().redraw();
        });

        this.setCenter(s);
      }
      else if (value instanceof Double)
      {
        // Case value instanceof Double - create a double spinner with double values and a DoubleSpinnerValueFactory
        Spinner<Double> s = new Spinner<>();
        SpinnerValueFactory<Double> svf = new SpinnerValueFactory.DoubleSpinnerValueFactory((double) min, (double) max, (double) this.value);
        s.setValueFactory(svf);
        s.setPrefWidth(Double.MAX_VALUE);
        s.setEditable(true);

        // Add a value listener to the spinner
        svf.valueProperty().addListener((observable, oldValue, newValue) -> {
          // Not a great way to cast to <T>, but sufficient because we know that newValue is definitely a double
          this.value = (T) newValue;
          Program.ui.getSelectedLayer().redraw();
        });

        this.setCenter(s);
      }
      else
      {
        Program.debug("Could not determine data type for setting " + this + " with values (value, min, max): " + value + " " + min + " " + max + ". The spinner has not been setup.");
      }
    }
    // Create a slider-type AlgorithmSetting
    else if (type == Type.SLIDER)
    {
      // Slider is uniform for any data type, as such we can define it before the if statements and determination of the data type of <T>
      Slider s = new Slider();
      s.setShowTickLabels(true);
      s.setShowTickMarks(true);

      if (value instanceof Integer)
      {
        // Case value instanceof Integer - create an integer slider with integer values
        s.setMin((int) min);
        s.setMax((int) max);
        s.setValue((int) this.value);

        s.setOnMouseReleased(event -> {
          // Somewhat weird behavior as we have to cast primitive 'double' to primitive 'integer' to Class 'Integer' to be able to cast to 'T'
          // We're hitting levels of variable casting that shouldn't even be possible
          this.value = (T) (Integer) (int) s.getValue();
          Program.debug(this.value);
          Program.ui.getSelectedLayer().redraw();
        });
      }
      else if (value instanceof Double)
      {
        // Case value instanceof Double - create a double slider with double values
        s.setMin((double) min);
        s.setMax((double) max);
        s.setValue((double) this.value);

        s.setOnMouseReleased(event -> {
          // Somewhat weird behavior as we have to cast primitive 'double' to Class 'Double' to be able to cast to 'T'
          this.value = (T) (Double) s.getValue();
          Program.debug(this.value);
          Program.ui.getSelectedLayer().redraw();
        });
      }

      this.setCenter(s);
    }

  }
}